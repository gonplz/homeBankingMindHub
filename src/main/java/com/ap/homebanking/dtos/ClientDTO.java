package com.ap.homebanking.dtos;

import com.ap.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;


public class ClientDTO {
    private Long id;
    private String firstName,lastName, mail;
    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> loans;

    public ClientDTO (Client client){

        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.mail = client.getMail();
        this.accounts= client.getAccounts().stream()
                .map(element -> new AccountDTO(element))
                .collect(Collectors.toSet());
        loans=client.getClientLoans().stream()
                .map(clientLoan-> new ClientLoanDTO(clientLoan))
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
}

