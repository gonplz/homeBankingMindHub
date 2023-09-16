package com.ap.homebanking.repositories;

import com.ap.homebanking.models.Loan;
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
class LoanRepositoryTest {

    @Autowired
    LoanRepository loanRepository;

    @Test
    void existLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, is(not(empty())));
    }

    @Test
    void existMaxAmountThan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("maxAmount",lessThan(700000.0)))); //El monto max es de 600.000
    }

}