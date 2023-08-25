package com.ap.homebanking.dtos;

import com.ap.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;


public class ClientDTO {
    private Long id;
    private String firstName,lastName, email, password;
    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;

    public ClientDTO (Client client){

        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.password = client.getPassword();
        this.accounts= client.getAccounts().stream()
                .map(element -> new AccountDTO(element))
                .collect(Collectors.toSet());
        loans=client.getClientLoans().stream()
                .map(clientLoan-> new ClientLoanDTO(clientLoan))
                .collect(Collectors.toSet());
        this.cards= client.getCards()
                .stream()
                .map(element -> new CardDTO(element))
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

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {return cards;}

    public String getPassword() {return password;}
}

