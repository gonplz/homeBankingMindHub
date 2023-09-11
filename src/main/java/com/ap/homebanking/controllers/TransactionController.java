package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.AccountDTO;
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
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    @RequestMapping("/transaction")
    public List<TransactionDTO> getTransactions(){
        return transactionRepository.findAll().stream().map(element -> new  TransactionDTO(element)).collect(Collectors.toList());
    }
    @RequestMapping("/transaction/{id}")
    public TransactionDTO gettransactions (@PathVariable Long id) {
        return new TransactionDTO(transactionRepository.findById(id).orElse(null));
    }

    @Transactional
    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createdTransaction (
            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
            @RequestParam Double amount, @RequestParam String description,Authentication authentication){

        Client clientAuth = clientRepository.findByEmail(authentication.getName());
        Account accountSource = accountRepository.findByNumber(fromAccountNumber);
        Account accountDestination = accountRepository.findByNumber(toAccountNumber);

        if (amount <= 0) {
            return new ResponseEntity<>("Missing Data, amount is required", HttpStatus.FORBIDDEN);}

        if (description.isBlank()) {
            return new ResponseEntity<>("Missing Data, description is required", HttpStatus.FORBIDDEN);}

        if (fromAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data, source account is required", HttpStatus.FORBIDDEN);}

        if (toAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data, destination account is required", HttpStatus.FORBIDDEN);}

        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("You are not allowed to perform this operation", HttpStatus.FORBIDDEN);}

        if (!accountRepository.existsByNumber(fromAccountNumber)) {
            return new ResponseEntity<>("Source account don't exists", HttpStatus.FORBIDDEN);}

        if (!clientAuth.getAccounts().contains(accountSource)) {
            return new ResponseEntity<>("The source account does not belong to the authenticated client ", HttpStatus.FORBIDDEN);}

        if (!accountRepository.existsByNumber(toAccountNumber)) {
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
        Transaction transactionCredit = new Transaction(amount, TransactionType.CREDIT, " CREDIT " + toAccountNumber,LocalDateTime.now());
        accountDestination.setBalance(accountDestination.getBalance() + amount);
        accountDestination.addTransaction(transactionCredit);
        transactionRepository.save(transactionCredit);
        accountRepository.save(accountDestination);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
