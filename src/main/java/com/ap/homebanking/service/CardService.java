package com.ap.homebanking.service;

import com.ap.homebanking.dtos.CardDTO;
import com.ap.homebanking.models.Card;

import java.util.List;

public interface CardService {

    List<CardDTO> getCards();

     boolean existCardbyNumber(String number);

     public void createdCard(Card card);
}
