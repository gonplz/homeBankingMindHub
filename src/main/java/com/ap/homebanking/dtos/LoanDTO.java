package com.ap.homebanking.dtos;

import com.ap.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {

    private Long id;
    private String name;
    private Double maxAmount;
    private List<Integer> payments;


    public LoanDTO (Loan loan){

        this.id = loan.getId();
        this.name= loan.getName();
        this.maxAmount= loan.getMaxAmount();
        this.payments= loan.getPayments();

    }

    public Long getId() {return id;}

    public String getName() {return name;}

    public Double getMaxAmount() {return maxAmount;}

    public List<Integer> getPayments() {return payments;}
}
