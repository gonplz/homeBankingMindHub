package com.ap.homebanking.repositories;

import com.ap.homebanking.models.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;

    @Test
    void existCard() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }

    @Test
     public void numberNotNull(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,hasItem(hasProperty("number",notNullValue() )));
    }

}