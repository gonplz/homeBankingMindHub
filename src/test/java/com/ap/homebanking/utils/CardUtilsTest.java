package com.ap.homebanking.utils;

import jdk.jshell.execution.Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
class CardUtilsTest {

    @Test
    void getRandomNumberCvv() {
        Integer randomNumber = CardUtils.getRandomNumberCvv(100,999);
        assertTrue(randomNumber >= 100  && randomNumber <= 999 );
    }

    @Test
    void CardNumberIsCreated() {
        String number = CardUtils.getRandomNumberCard();
        assertThat(number,is(not(emptyOrNullString())));
    }
}