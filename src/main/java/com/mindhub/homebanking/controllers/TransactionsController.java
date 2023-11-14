package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionsController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;


    @Transactional
    @PostMapping("/clients/current/transfers")
    public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                    @RequestParam Double amount,
                                                    @RequestParam String description,
                                                    @RequestParam String originNumber,
                                                    @RequestParam String destinationNumber) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Account accountDebit = accountService.findAccountByNumber(originNumber);
        Account accountCredit = accountService.findAccountByNumber(destinationNumber);
        if (client == null) {
            throw new UsernameNotFoundException("Unknow client " + authentication.getName());
        }

        if (accountDebit.getClient() != client) {
            return new ResponseEntity<>("The origin account doesn´t belong to the authenticated client",
                    HttpStatus.FORBIDDEN);
        }
        if (accountDebit == null) {
            return new ResponseEntity<>("The origin account doesn´t exist",
                    HttpStatus.FORBIDDEN);
        }
        if (accountCredit == null) {
            return new ResponseEntity<>("The destination account doesn´t exist",
                    HttpStatus.FORBIDDEN);
        }
        if (accountDebit.getBalance() < amount) {
            return new ResponseEntity<>("Your funds are insufficient",
                    HttpStatus.FORBIDDEN);
        }
        if (accountDebit.getNumber() == accountCredit.getNumber()) {
            return new ResponseEntity<>("the destination account cannot be the same as the origin account",
                    HttpStatus.FORBIDDEN);
        } else {
            Transaction transactionDebit = new Transaction(TransactionType.DEBIT,
                    (-amount),
                    accountDebit.getNumber() + description,
                    LocalDateTime.now());

            Transaction transactionCredit = new Transaction(TransactionType.CREDIT,
                    amount,
                    accountCredit.getNumber() + description,
                    LocalDateTime.now());

            transactionService.saveTransaction(transactionDebit);
            accountDebit.addTransaction(transactionDebit);
            transactionService.saveTransaction(transactionCredit);
            accountCredit.addTransaction(transactionCredit);

            accountDebit.setBalance(accountDebit.getBalance() - amount);
            accountCredit.setBalance(accountCredit.getBalance() + amount);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
