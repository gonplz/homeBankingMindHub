package com.ap.homebanking.controllers;


import com.ap.homebanking.dtos.CardDTO;
import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.CardType;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.ColorType;
import com.ap.homebanking.repositories.CardRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/cards")
    public List<CardDTO> getCards(){
        return cardRepository.findAll().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @RequestMapping("/cards/{id}")
    public CardDTO getCard (@PathVariable Long id){
        return new CardDTO(cardRepository.findById(id).orElse(null));
    }

    @RequestMapping(value = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createdCard (@RequestParam CardType cardType, @RequestParam ColorType colorType, Authentication authentication){

        Client clientAuth = clientRepository.findByEmail(authentication.getName());

        List<Card> cardFiltered = clientAuth.getCards().stream()
                .filter(card -> card.getType() == cardType).collect(Collectors.toList());

        if (cardFiltered.stream().count() ==3){
            return new ResponseEntity<>("Already max number of Card" + cardType, HttpStatus.FORBIDDEN);
        }

        String numberCard;

        Integer cvv = CardUtils.getRandomNumberCvv(0,999);

        do { numberCard = CardUtils.getRandomNumberCard();
        }

        while (cardRepository.existsByNumber(numberCard));
        Card newCard = new Card(clientAuth.getFirstName() + " "+ clientAuth.getLastName(),
                cardType, colorType, numberCard,LocalDate.now(), LocalDate.now().plusYears(5),cvv);
        clientAuth.addCard(newCard);
        cardRepository.save(newCard);
        return new ResponseEntity<> (HttpStatus.CREATED);
    }
}
