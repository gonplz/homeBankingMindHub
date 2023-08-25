package com.ap.homebanking;

import com.ap.homebanking.models.*;
import com.ap.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomemakingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomemakingApplication.class, args);}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return( args-> {


			Client client1 = new Client("Gonza", "Plaza","gonza@gmail.com", passwordEncoder.encode("1234"));
			Client client2 = new Client("Seba","Gale","seba@gmail.com",passwordEncoder.encode("4321"));

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

			Loan loan1 = new Loan("HIP LOAN",600000.0, List.of(12,24,36,48,60));
			Loan loan2= new Loan("SWITCH LOAN", 500000.0,List.of(3,6,12));

			loanRepository.save(loan1);
			loanRepository.save(loan2);

			ClientLoan clientLoan1 = new ClientLoan(400000.0,60);
			ClientLoan clientLoan2 = new ClientLoan(50000.0, 12);

			client1.addClientLoan(clientLoan1);
			loan1.addClientLoan(clientLoan1);

			client1.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan2);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);

			Card card1 = new Card("Gonza Plaza", CardType.DEBIT, ColorType.GOLD,
					"0000 5498 5555 7892",LocalDate.now().plusYears(5), LocalDate.now(),
					148L);

			Card card2 = new Card("Gonza Plaza", CardType.CREDIT,ColorType.TITANIUM,
					"0000 5489 5555 7214",LocalDate.now().plusYears(5),LocalDate.now(),
					231L);

			client1.addCard(card1);
			client1.addCard(card2);

			cardRepository.save(card1);
			cardRepository.save(card2);

		});
	}
}
