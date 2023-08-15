package com.ap.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;

    private Double amount;

    private Integer payments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="client_id")
    private Client client;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    public ClientLoan(){}

    public ClientLoan(Double amount, Integer payments, Client client, Loan loan){

        this.amount= amount;
        this.payments= payments;
        this.client= client;
        this.loan= loan;
    }
    public Long getId() {return id;}

    public Double getAmount() {return amount;}

    public Integer getPayments() {return payments;}

    public Client getClient() {return client;}

    public Loan getLoan() {return loan;}

    ///////////////////////////////

    public void setAmount(Double amount) {this.amount = amount;}

    public void setPayments(Integer payments) {this.payments = payments;}

    public void setClient(Client client) {this.client = client;}

    public void setLoan(Loan loan) {this.loan = loan;}

}
