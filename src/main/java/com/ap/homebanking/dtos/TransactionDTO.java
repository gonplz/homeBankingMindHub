package com.ap.homebanking.dtos;

import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;

    public TransactionDTO(Transaction transaction){
        this.id= transaction.getId();
        this.type = transaction.getType();
        this.amount= transaction.getAmount();
        this.description= transaction.getDescription();
        this.date= transaction.getDate();
    }

    public Long getId() {return id;}

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }
}
