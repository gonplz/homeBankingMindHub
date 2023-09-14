package com.ap.homebanking.dtos;

public class LoanAplicationDTO {

private Long loanId;
private Double amount;
private Integer payments;
private String toAccountNumber;

public LoanAplicationDTO (){}


    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
