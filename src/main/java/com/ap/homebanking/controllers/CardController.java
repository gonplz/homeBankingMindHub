package com.ap.homebanking.controllers;


import com.ap.homebanking.dtos.CardDTO;
import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.CardType;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.ColorType;
import com.ap.homebanking.repositories.CardRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.service.CardService;
import com.ap.homebanking.service.ClientService;
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
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    @RequestMapping("/cards")
    public List<CardDTO> getCards() {
        return cardService.getCards();
    }

    @RequestMapping(value = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createdCard(@RequestParam CardType cardType, @RequestParam ColorType cardColor,
                                              Authentication authentication) {
        Client client = clientService.getCurrentClient(authentication.getName());
        List<Card> cardFiltered = client.getCards().stream().filter
                (card -> card.getType() == cardType && card.getColor() == cardColor).collect(Collectors.toList());

        if ((long) cardFiltered.size() == 1) {
            return new ResponseEntity<>("Already this type of Card", HttpStatus.FORBIDDEN);}

        String numberCard;

        Integer cvv = CardUtils.getRandomNumberCvv(0, 999);

        do {numberCard = CardUtils.getRandomNumberCard();}

        while (cardService.existCardbyNumber(numberCard));

        Card newCard = new Card(client.getFirstName() + " " + client.getLastName(),
                cardType, cardColor, numberCard, LocalDate.now().plusYears(5), LocalDate.now(), cvv);

        client.addCard(newCard);
        cardService.createdCard(newCard);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

//    @RequestMapping(value = "/clients/current/cards", method = RequestMethod.POST)
//    public ResponseEntity<Object> createdCard (@RequestParam CardType cardType, @RequestParam ColorType cardColor, Authentication authentication){
//
//        Client clientAuth = clientRepository.findByEmail(authentication.getName());
//
//        List<Card> cardFiltered = clientAuth.getCards().stream()
//                .filter(card -> card.getType() == cardType).collect(Collectors.toList());
//
//        if (cardFiltered.stream().count() ==3){
//            return new ResponseEntity<>("Already max number of Card" + cardType, HttpStatus.FORBIDDEN);
//        }
//
//        String numberCard;
//
//        Integer cvv = CardUtils.getRandomNumberCvv(0,999);
//
//        do { numberCard = CardUtils.getRandomNumberCard();
//        }
//
//        while (cardRepository.existsByNumber(numberCard));
//        Card newCard = new Card(clientAuth.getFirstName() + " "+ clientAuth.getLastName(),
//                cardType, cardColor, numberCard,LocalDate.now(), LocalDate.now().plusYears(5),cvv);
//        clientAuth.addCard(newCard);
//        cardRepository.save(newCard);
//        return new ResponseEntity<> (HttpStatus.CREATED);
//    }

