package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return args ->{

			Client client = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(client);

			Account accountMelba1 = new Account("VIN001", LocalDate.now(), 5000.0);
			client.addAccount(accountMelba1);
			accountRepository.save(accountMelba1);

			Account accountMelba2 = new Account("VIN002",LocalDate.now().plusDays(1),7500.0);
			client.addAccount(accountMelba2);
			accountRepository.save(accountMelba2);



			Client clientJhossy = new Client("Jhossy", "Vilchez", "jhossy@gmail.com");
			clientRepository.save(clientJhossy);

			Account accountJhossy1 = new Account("YES001", LocalDate.now(), 15000.0);
			clientJhossy.addAccount(accountJhossy1);
			accountRepository.save(accountJhossy1);

			Account accountJhossy2 = new Account("YES002", LocalDate.now().plusDays(1), 8500.0);
			clientJhossy.addAccount(accountJhossy2);
			accountRepository.save(accountJhossy2);
		};
	}
}


