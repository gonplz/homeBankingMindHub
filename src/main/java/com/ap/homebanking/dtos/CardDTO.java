package com.ap.homebanking.dtos;

import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.CardType;
import com.ap.homebanking.models.ColorType;

import java.time.LocalDate;

public class CardDTO {
    private long id;
    private String cardHolder;
    private CardType type;
    private ColorType color;
    private String number;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private Long cvv;

    public CardDTO (Card card){

        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
        this.cvv = card.getCvv();
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

    public Long getCvv() {
        return cvv;
    }
}


