package com.ap.homebanking.service.implement;

import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.service.ClientService;
import com.ap.homebanking.utils.AccountUtils;
import com.ap.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientImplement implements ClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<ClientDTO> getClients(){
        return clientRepository
                .findAll()
                .stream()
                .map(element ->new ClientDTO(element))
                .collect(Collectors.toList());
    }

    @Override
    public Client getClientById(Long id){
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client getCurrentClient (String email){
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {

        Account account = null;
        do {

            String number = "VIN" + AccountUtils.getRandomNumberAccount(100000000,1000000);
            account= new Account(number,LocalDate.now(),0.0);
        }
        while(accountRepository.existsByNumber(account.getNumber()));

        String numberCard = CardUtils.getRandomNumberCard();

        Integer cvv = CardUtils.getRandomNumberCvv(0,999);

        accountRepository.save(account);
        clientRepository.save(client);
    }

}
