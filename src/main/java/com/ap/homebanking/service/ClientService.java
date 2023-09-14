package com.ap.homebanking.service;

import com.ap.homebanking.dtos.ClientDTO;
import com.ap.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClients();

    Client getClientById (Long id);

    Client getCurrentClient (String email);

    void saveClient (Client client);
}
