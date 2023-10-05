package com.ap.homebanking.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountUtilsTest {

    @Test
    void getRandomNumberAccountGenerate() {
        Integer randomNumberAccount = AccountUtils.getRandomNumberAccount(10000000,99999999);
        assertTrue(randomNumberAccount <= 99999999);


    }
}