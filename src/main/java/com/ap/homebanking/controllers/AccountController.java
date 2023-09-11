package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.AccountDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    private List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    private AccountDTO getId(@PathVariable Long id){
        return new  AccountDTO(accountRepository.findById(id).orElse(null));
    }

    @RequestMapping(value = "/clients/current/accounts", method = RequestMethod.GET)
    private List<AccountDTO> getAccountsOfCurrent(Authentication authentication){
        Client clientAuth =  clientRepository.findByEmail(authentication.getName());
        return  clientAuth.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/clients/current/accounts", method = RequestMethod.POST)

    public ResponseEntity<Object> createdAccount (Authentication authentication){
        Client clientAuth =  clientRepository.findByEmail(authentication.getName());
        if (clientAuth.getAccounts().stream().count()==3){
            System.out.println("tiene 3 cuentas, alcanzo el maximo");
            return new ResponseEntity<>("Already max number accounts", HttpStatus.FORBIDDEN);
        }

        Account account = null;
        do {
            String number = "VIN" + AccountUtils.getRandomNumberAccount(10000000,99999999);
            account= new Account(number,LocalDate.now(),0.0);
        }
        while(accountRepository.existsByNumber(account.getNumber()));

        clientAuth.addAccount(account);
        accountRepository.save(account);
        System.out.println("creaste una cuenta");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
