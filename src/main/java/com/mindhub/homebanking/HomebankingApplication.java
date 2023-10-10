package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return args ->{

			//instanciar el objeto client y el objeto account
			Client client = new Client("Melba", "Morel", "melba@mindhub.com");
			clientRepository.save(client);

			Account accountMelba1 = new Account("VIN001", LocalDate.now(), 5000.0);
			client.addAccount(accountMelba1);
			accountRepository.save(accountMelba1);

			Account accountMelba2 = new Account("VIN002",LocalDate.now().plusDays(1),7500.0);
			client.addAccount(accountMelba2);
			accountRepository.save(accountMelba2);



			//instanciar el objeto client y el objeto account
			Client clientJhossy = new Client("Jhossy", "Vilchez", "jhossy@gmail.com");
			clientRepository.save(clientJhossy);

			Account accountJhossy1 = new Account("YES001", LocalDate.now(), 15000.0);
			clientJhossy.addAccount(accountJhossy1);
			accountRepository.save(accountJhossy1);

			Account accountJhossy2 = new Account("YES002", LocalDate.now().plusDays(1), 8500.0);
			clientJhossy.addAccount(accountJhossy2);
			accountRepository.save(accountJhossy2);



			//instanciar el objeto transaction para la accountMelba1
			Transaction transactionMelba1 = new Transaction(TransactionType.CREDIT, 5000.2, "coffe", LocalDateTime.now());
			accountMelba1.addTransaction(transactionMelba1);
			transactionRepository.save(transactionMelba1);

			Transaction transactionMelba2 = new Transaction(TransactionType.DEBIT, -2000.2, "supermarket", LocalDateTime.now());
			accountMelba1.addTransaction(transactionMelba2);
			transactionRepository.save(transactionMelba2);



			//instanciar el objeto transaction para la accountMelba2
			Transaction transactionMelba3 = new Transaction(TransactionType.DEBIT, -800.5, "clothes", LocalDateTime.now());
			accountMelba2.addTransaction(transactionMelba3);
			transactionRepository.save(transactionMelba3);

			Transaction transactionMelba4 = new Transaction(TransactionType.CREDIT, 5000.2, "snack", LocalDateTime.now());
			accountMelba2.addTransaction(transactionMelba4);
			transactionRepository.save(transactionMelba4);


			//instanciar el objeto transaction para la accountJhossy1
			Transaction transactionJhossy1 = new Transaction(TransactionType.DEBIT, -4000.5, "cinema", LocalDateTime.now());
			accountJhossy1.addTransaction( transactionJhossy1 );
			transactionRepository.save( transactionJhossy1 );

			Transaction transactionJhossy2 = new Transaction(TransactionType.CREDIT, 3000.2, "bookshop", LocalDateTime.now());
			accountJhossy1.addTransaction( transactionJhossy2 );
			transactionRepository.save( transactionJhossy2 );

			//instanciar el objeto transaction para la accountJhossy1
			Transaction transactionJhossy3 = new Transaction(TransactionType.DEBIT, -200.5, "cinema", LocalDateTime.now());
			accountJhossy2.addTransaction( transactionJhossy3 );
			transactionRepository.save( transactionJhossy3 );

			Transaction transactionJhossy4 = new Transaction(TransactionType.CREDIT, 15000.2, "coffe", LocalDateTime.now());
			accountJhossy2.addTransaction( transactionJhossy4 );
			transactionRepository.save( transactionJhossy4 );

		};
	}
}


