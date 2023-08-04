package com.ap.homebanking;

import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return( args-> {
			Client client1 = new Client("Gonza", "Plaza","gonza@gmail.com");
			Client client2 = new Client("Seba","Gale","seba@gmail.com");

			clientRepository.save(client1);
			clientRepository.save(client2);
		});
	}
}
