package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.TransactionDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.models.TransactionType;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/transactions")
    public List<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(element -> new  TransactionDTO(element)).collect(Collectors.toList());
    }

    @RequestMapping("/transactions/{id}")
    public TransactionDTO gettransactions (@PathVariable Long id){
        return new TransactionDTO(transactionRepository.findById(id).orElse(null));
    }

    public ResponseEntity<Object> createdTransaction (
            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String fromAccountNumber, @RequestParam String toAccounNumber, Authentication authentication){

        Client clientAuth = clientRepository.findByEmail(authentication.getName());
        Account accountSource = accountRepository.findByNumber(fromAccountNumber);
        Account accountDestination = accountRepository.findByNumber(toAccounNumber);

        if (amount == null) {
            return new ResponseEntity<>("Missing Data, amount is required", HttpStatus.FORBIDDEN);}

        if (description.isBlank()) {
            return new ResponseEntity<>("Missing Data, description is required", HttpStatus.FORBIDDEN);}

        if (fromAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data, source account is required", HttpStatus.FORBIDDEN);}

        if (toAccounNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data, destination account is required", HttpStatus.FORBIDDEN);}

        if (fromAccountNumber.equals(toAccounNumber)) {
            return new ResponseEntity<>("You are not allowed to perform this operation", HttpStatus.FORBIDDEN);}

        if (!accountRepository.existsByNumber(fromAccountNumber)) {
            return new ResponseEntity<>("Source account don't exists", HttpStatus.FORBIDDEN);}

        if (!clientAuth.getAccounts().contains(accountSource)) {
            return new ResponseEntity<>("The source account does not belong to the authenticated client ", HttpStatus.FORBIDDEN);}

        if (!accountRepository.existsByNumber(toAccounNumber)) {
            return new ResponseEntity<>("Account destination don't exists", HttpStatus.FORBIDDEN);}

        if (accountSource.getBalance() < amount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);}

        ///////////////////////////////////Debit Transaction
        Transaction transactionDebit = new Transaction(-amount, TransactionType.DEBIT, "DEBIT" + fromAccountNumber, LocalDateTime.now());
        accountSource.addTransaction(transactionDebit);
        accountSource.setBalance(accountSource.getBalance() - amount);
        transactionRepository.save(transactionDebit);
        accountRepository.save(accountSource);

        /////////////////////////////////Credit Transaction
        Transaction transactionCredit = new Transaction(amount, TransactionType.CREDIT, " CREDIT " + toAccounNumber,LocalDateTime.now());
        accountDestination.setBalance(accountDestination.getBalance() + amount);
        accountDestination.addTransaction(transactionCredit);
        transactionRepository.save(transactionCredit);
        accountRepository.save(accountDestination);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
