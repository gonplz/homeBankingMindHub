package com.ap.homebanking;

import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class HomemakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomemakingApplication.class, args);}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,AccountRepository accountRepository){
		return( args-> {


			Client client1 = new Client("Gonza", "Plaza","gonza@gmail.com");
			Client client2 = new Client("Seba","Gale","seba@gmail.com");

			Account account1 = new Account("VIN001","",5000.0);
			LocalDate today = LocalDate.now();
			Account account2 = new Account("VIN002","",7500.0);
			LocalDate today2 = LocalDate.now();

			clientRepository.save(client1);
			clientRepository.save(client2);
			accountRepository.save(account1);
			accountRepository.save(account2);
		});
	}
}
