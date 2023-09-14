package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.TransactionDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.models.TransactionType;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.repositories.TransactionRepository;
import com.ap.homebanking.service.AccountService;
import com.ap.homebanking.service.ClientService;
import com.ap.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/transactions")
    public List<TransactionDTO> getTransactions(){return transactionService.getTransactions();}
    @RequestMapping("/transactions/{id}")
    public TransactionDTO getTransactions (@PathVariable Long id){
        return new TransactionDTO(transactionService.getTransactionById(id));}


    @Transactional
    @RequestMapping (value = "/transactions",method = RequestMethod.POST)
    public ResponseEntity<Object> createdTransaction (
            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String fromAccountNumber ,@RequestParam String toAccountNumber,
            Authentication authentication) {

        Client clientAuth = clientService.getCurrentClient(authentication.getName());
        Account accountSource = accountService.findByNumber(fromAccountNumber);
        Account accountDestination = accountService.findByNumber(toAccountNumber);

        //Verificar que los parámetros no estén vacíos
        if (amount == null) {
            return new ResponseEntity<>("Missing Data, amount is required", HttpStatus.FORBIDDEN);
        }
        if (description.isBlank()) {
            return new ResponseEntity<>("Missing Data, description is required", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data, source account is required", HttpStatus.FORBIDDEN);
        }
        if (toAccountNumber.isBlank()) {
            return new ResponseEntity<>("Missing Data, destination account is required", HttpStatus.FORBIDDEN);
        }
        // Verificar que los números de cuenta no sean iguales

        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("You are not allowed to perform this operation", HttpStatus.FORBIDDEN);
        }
        //Verificar que exista la cuenta de origen

        if (!accountService.existByNumber(fromAccountNumber)) {
            return new ResponseEntity<>("Source account don't exists", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de origen pertenezca al cliente autenticado
        if (!clientAuth.getAccounts().contains(accountSource)) {
            return new ResponseEntity<>("The source account does not belong to the authenticated client ", HttpStatus.FORBIDDEN);
        }

        //Verificar que exista la cuenta de destino
        if (!accountService.existByNumber(toAccountNumber)) {
            return new ResponseEntity<>("Account destination don't exists", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de origen tenga el monto disponible.
        if (accountSource.getBalance() < amount) {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }

        //Creacion de tipos de transacciones

        //Debit Transaction
        Transaction transactionDebit = new Transaction(-amount, TransactionType.DEBIT, "DEBIT" + fromAccountNumber, LocalDateTime.now());
        accountSource.addTransaction(transactionDebit);
        accountSource.setBalance(accountSource.getBalance() - amount);
        transactionService.createdTransaction(transactionDebit);
        accountService.createdAccount(accountSource);

        // Credit Transaction
        Transaction transactionCredit = new Transaction(amount, TransactionType.CREDIT, " CREDIT " + toAccountNumber,LocalDateTime.now());
        accountDestination.setBalance(accountDestination.getBalance() + amount);
        accountDestination.addTransaction(transactionCredit);
        transactionService.createdTransaction(transactionCredit);
        accountService.createdAccount(accountDestination);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}



//    @Transactional
//    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
//    public ResponseEntity<Object> createdTransaction (
//            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
//            @RequestParam Double amount, @RequestParam String description,Authentication authentication){
//
//        Client clientAuth = clientRepository.findByEmail(authentication.getName());
//        Account accountSource = accountRepository.findByNumber(fromAccountNumber);
//        Account accountDestination = accountRepository.findByNumber(toAccountNumber);
//
//        if (amount <= 0) {
//            return new ResponseEntity<>("Missing Data, amount is required", HttpStatus.FORBIDDEN);}
//
//        if (description.isBlank()) {
//            return new ResponseEntity<>("Missing Data, description is required", HttpStatus.FORBIDDEN);}
//
//        if (fromAccountNumber.isBlank()) {
//            return new ResponseEntity<>("Missing Data, source account is required", HttpStatus.FORBIDDEN);}
//
//        if (toAccountNumber.isBlank()) {
//            return new ResponseEntity<>("Missing Data, destination account is required", HttpStatus.FORBIDDEN);}
//
//        if (fromAccountNumber.equals(toAccountNumber)) {
//            return new ResponseEntity<>("You are not allowed to perform this operation", HttpStatus.FORBIDDEN);}
//
//        if (!accountRepository.existsByNumber(fromAccountNumber)) {
//            return new ResponseEntity<>("Source account don't exists", HttpStatus.FORBIDDEN);}
//
//        if (!clientAuth.getAccounts().contains(accountSource)) {
//            return new ResponseEntity<>("The source account does not belong to the authenticated client ", HttpStatus.FORBIDDEN);}
//
//        if (!accountRepository.existsByNumber(toAccountNumber)) {
//            return new ResponseEntity<>("Account destination don't exists", HttpStatus.FORBIDDEN);}
//
//        if (accountSource.getBalance() < amount) {
//            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);}
//
//        ///////////////////////////////////Debit Transaction
//        Transaction transactionDebit = new Transaction(-amount, TransactionType.DEBIT, "DEBIT" + fromAccountNumber, LocalDateTime.now());
//        accountSource.addTransaction(transactionDebit);
//        accountSource.setBalance(accountSource.getBalance() - amount);
//        transactionRepository.save(transactionDebit);
//        accountRepository.save(accountSource);
//
//        /////////////////////////////////Credit Transaction
//        Transaction transactionCredit = new Transaction(amount, TransactionType.CREDIT, " CREDIT " + toAccountNumber,LocalDateTime.now());
//        accountDestination.setBalance(accountDestination.getBalance() + amount);
//        accountDestination.addTransaction(transactionCredit);
//        transactionRepository.save(transactionCredit);
//        accountRepository.save(accountDestination);
//
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
