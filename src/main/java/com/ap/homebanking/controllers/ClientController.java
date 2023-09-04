package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.CardRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.utils.AccountUtils;
import static com.ap.homebanking.utils.CardUtils.getRandomNumberCvv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;

    @RequestMapping("/clients")
    private List<ClientDTO> getClients(){
       return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
   }

   @RequestMapping("/clients/{id}")
    private ClientDTO getId(@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse(null));
   }

            ////////////////////////Creación de Cliente/////////////////////////
    @RequestMapping(value = "/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)

    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Account account = null;
        do {
            String number = "VIN" + AccountUtils.getRandomNumberAccount(100000000,1000000);
            account = new Account(number,LocalDate.now(),0.0);
        }
        while (accountRepository.existsByNumber(account.getNumber()));

        Integer numberCvv = getRandomNumberCvv(0,999);

        Client clientRegistered = new Client(firstName,lastName,email,passwordEncoder.encode(password));
        clientRegistered.addAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}