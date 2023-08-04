package com.ap.homebanking;

import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomemakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomemakingApplication.class, args);}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return( args-> {
			Client client1 = new Client("1","Gonza", "Plaza","gonza@gmail.com");
			Client client2 = new Client("2","Seba","Gale","seba@gmail.com");

			clientRepository.save(client1);
			clientRepository.save(client2);
		});
	}
}
