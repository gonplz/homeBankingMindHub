package com.ap.homebanking.repositories;

import com.ap.homebanking.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
     void existAccount () {
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }

    @Test
     void numberNotNull () {
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,hasItem(hasProperty("number", notNullValue())));
    }
}