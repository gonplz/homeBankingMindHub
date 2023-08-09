package com.ap.homebanking.dtos;

import com.ap.homebanking.models.Account;

import java.time.LocalDate;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;

    public AccountDTO (Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }
}
