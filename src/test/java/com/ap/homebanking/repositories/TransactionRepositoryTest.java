package com.ap.homebanking.repositories;

import com.ap.homebanking.models.Transaction;
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
class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void transactionExist(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }


    @Test
    void existTransactionThanZero(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("amount", greaterThan(0.0))));
    }

}