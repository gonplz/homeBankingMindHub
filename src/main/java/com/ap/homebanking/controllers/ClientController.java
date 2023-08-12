package com.ap.homebanking.controllers;

import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    public ClientRepository clientRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
       return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
   }

   @RequestMapping("/clients/{id}")
    public ClientDTO getId(@PathVariable Long id){
        return new ClientDTO(clientRepository.findById(id).orElse(null));
   }
}