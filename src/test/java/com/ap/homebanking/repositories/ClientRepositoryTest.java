package com.ap.homebanking.repositories;

import com.ap.homebanking.models.Client;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void existClient () {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void emailsNotNulls (){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,hasItem(hasProperty("email", notNullValue())));
    }

    @Test
    public void emailIsCorrect(){
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList, hasItem(hasProperty("email",containsString("@"))));}
}