package com.ap.homebanking;

import com.ap.homebanking.models.Account;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.models.Transaction;
import com.ap.homebanking.models.TransactionType;
import com.ap.homebanking.repositories.AccountRepository;
import com.ap.homebanking.repositories.ClientRepository;
import com.ap.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
public class HomemakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomemakingApplication.class, args);}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return( args-> {


			Client client1 = new Client("Gonza", "Plaza","gonza@gmail.com");
			Client client2 = new Client("Seba","Gale","seba@gmail.com");

			Account account1 = new Account("VIN001","",5000.0);
			LocalDate today = LocalDate.now();
			Account account2 = new Account("VIN002","",7500.0);
			account2.setCreationDate(today.plusDays(1));

			Transaction transaction1 = new Transaction(-500.0, TransactionType.DEBIT,"Carga", LocalDateTime.now());
			Transaction transaction2 = new Transaction(500.0, TransactionType.CREDIT,"Carga", LocalDateTime.now());

			clientRepository.save(client1);
			clientRepository.save(client2);

			client1.addAccount(account1);
			client1.addAccount(account2);

			accountRepository.save(account1);
			accountRepository.save(account2);

			account1.addTransaction(transaction1);
			account2.addTransaction(transaction2);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			accountRepository.save(account1);
			accountRepository.save(account2);

		});
	}
}
