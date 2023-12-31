package com.ap.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName, lastName, email, password;

    @OneToMany(mappedBy="owner", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "client")
    private Set<ClientLoan> loans = new HashSet<>();

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "clientCard")
    private Set<Card> cards = new HashSet<>();

    public Client(){}

     public Client(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
     }

     ////////////////////GETTERS///////////////////////
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

    public String getPassword() {return password;}

    public Set<Account> getAccounts() {
        return accounts;
    }

    public Set<ClientLoan> getLoans() {
        return loans;
    }

    public Set<ClientLoan> getClientLoans() {
        return loans;
    }

    public Set<Card> getCards() {return cards;}


    /////////////////SETTERS////////////////////////
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public void setPassword(String password) {this.password = password;}

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void setLoans(Set<ClientLoan> loans) {
        this.loans = loans;
    }

    public void setCards(Set<Card> cards) {this.cards = cards;}


    /////////////////// METODOS ADD////////////////
    public void addAccount(Account account) {
        account.setOwner(this);
        accounts.add(account);
    }

    public void addClientLoan (ClientLoan clientLoan){
        clientLoan.setClient(this);
        loans.add(clientLoan);
    }

    public void addCard (Card card) {
        card.setClientCard(this);
        cards.add(card);
    }
}