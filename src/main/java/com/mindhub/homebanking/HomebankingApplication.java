package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
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
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Autowired
	private PasswordEncoder passwordEnconder;
	@Bean
	public CommandLineRunner initData( ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository ){
		return args ->{

		//instanciar el objeto client
			Client client = new Client("Melba", "Morel", "melba@mindhub.com", passwordEnconder.encode("client"), false);
			clientRepository.save(client);

			Client clientJhossy = new Client("Jhossy", "Vilchez", "jhossy@gmail.com", passwordEnconder.encode("clientJhossy"), false);
			clientRepository.save(clientJhossy);

			Client admin = new Client("Admin", "Admin", "admin@mail.com", passwordEnconder.encode("admin"),true );
			clientRepository.save(admin);


		//instanciar el objeto account
			Account accountMelba1 = new Account("VIN001", LocalDate.now(), 5000.0);
			client.addAccount(accountMelba1);
			accountRepository.save(accountMelba1);

			Account accountMelba2 = new Account("VIN002",LocalDate.now().plusDays(1),7500.0);
			client.addAccount(accountMelba2);
			accountRepository.save(accountMelba2);

			Account accountJhossy1 = new Account("YES001", LocalDate.now(), 15000.0);
			clientJhossy.addAccount(accountJhossy1);
			accountRepository.save(accountJhossy1);

			Account accountJhossy2 = new Account("YES002", LocalDate.now().plusDays(1), 8500.0);
			clientJhossy.addAccount(accountJhossy2);
			accountRepository.save(accountJhossy2);

		//instanciar el objeto transaction
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


			//instanciar el objeto transaction para la accountJhossy2
			Transaction transactionJhossy3 = new Transaction(TransactionType.DEBIT, -200.5, "cinema", LocalDateTime.now());
			accountJhossy2.addTransaction( transactionJhossy3 );
			transactionRepository.save( transactionJhossy3 );

			Transaction transactionJhossy4 = new Transaction(TransactionType.CREDIT, 15000.2, "coffe", LocalDateTime.now());
			accountJhossy2.addTransaction( transactionJhossy4 );
			transactionRepository.save( transactionJhossy4 );


		// Instanciar el objeto Loan
			Loan mortgage = new Loan("Mortgage", 500000.00, List.of(12, 24, 36, 48, 60));
			loanRepository.save(mortgage);

			Loan personal = new Loan("Personal", 100000.00, List.of(6, 12, 24));
			loanRepository.save(personal);

			Loan car = new Loan("Car", 300000.00, List.of(6, 12, 24, 36));
			loanRepository.save(car);


		//Instanciar el objeto ClientLoan
			ClientLoan mortgageMelba = new ClientLoan( 400000.00, 60);
			client.addClientLoan(mortgageMelba);
			mortgage.addClientLoan(mortgageMelba);
			clientLoanRepository.save(mortgageMelba);

			ClientLoan personalMelba = new ClientLoan( 50000.00, 12);
			client.addClientLoan(personalMelba);
			personal.addClientLoan(personalMelba);
			clientLoanRepository.save(personalMelba);


			ClientLoan personalClientJhossy = new ClientLoan(100000.00, 24);
			clientJhossy.addClientLoan(personalClientJhossy);
			personal.addClientLoan(personalClientJhossy);
			clientLoanRepository.save(personalClientJhossy);

			ClientLoan carClientJhossy = new ClientLoan(200000.00, 36);
			clientJhossy.addClientLoan(carClientJhossy);
			car.addClientLoan(carClientJhossy);
			clientLoanRepository.save(carClientJhossy);

		//Instanciar el objeto Card
			Card cardMelba1 = new Card(client.getLastName() + " " + client.getFirstName(), CardType.DEBIT, CardColor.GOLD, "2486-9575-4555-9245", "015", LocalDate.now(), LocalDate.now().plusYears(5));
			client.addCard(cardMelba1);
			cardRepository.save(cardMelba1);

			Card cardMelba2 = new Card(client.getLastName() + " " + client.getFirstName(), CardType.CREDIT, CardColor.TITANIUM, "8564-3258-6174-6251", "085", LocalDate.now(), LocalDate.now().plusYears(5));
			client.addCard(cardMelba2);
			cardRepository.save(cardMelba2);

			Card cardJhossy1 = new Card(clientJhossy.getLastName() + " " + clientJhossy.getFirstName(), CardType.CREDIT, CardColor.SILVER, "7543-2280-2932-1047", "150", LocalDate.now(), LocalDate.now().plusYears(5));
			clientJhossy.addCard(cardJhossy1);
			cardRepository.save(cardJhossy1);
		};
	}
}


