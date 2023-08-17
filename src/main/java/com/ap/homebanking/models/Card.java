package com.ap.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String cardHolder;
    private CardType type;
    private ColorType color;
    private String number;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private Integer cvv;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientCard_id")
    private Client clientCard;

    public Card(){}

    public Card(String cardHolder, CardType type, ColorType color, String number, LocalDate thruDate,
                LocalDate fromDate, Integer cvv){

        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.cvv = cvv;
    }


    /////////////// GETTERS//////////////////////////
    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public ColorType getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public Integer getCvv() {
        return cvv;
    }


    /////////////////////SETTERS/////////////////////////
    public void setId(long id) {
        this.id = id;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setColor(ColorType color) {
        this.color = color;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }


    //////////////////////METODOS PARA LA RELACION DE ENTIDADES/////////////////////
    public Client getClientCard() {return clientCard;}

    public void setClientCard(Client clientCard) {
        this.clientCard = clientCard;
    }
}
