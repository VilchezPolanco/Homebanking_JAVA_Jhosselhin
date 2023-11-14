package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientLoanService clientLoanService;
    @Autowired
    private TransactionService transactionService;


    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = now.format(formatter);
    LocalDateTime formattedLocalDateTime = LocalDateTime.parse(formattedDateTime, formatter);

    @RequestMapping("/loans")
    public ResponseEntity<List<LoanDTO>> getAllLoans(Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            List<LoanDTO> loans = loanService.findAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
            return ResponseEntity.ok(loans);
        }
    }

    @PostMapping("/loans")
    @Transactional
    public ResponseEntity<String> newLoan(@RequestBody LoanApplicationDTO loanApplication, Authentication authentication) {

        Client client = clientService.findClientByEmail(authentication.getName());

        Loan loan = loanService.findLoanById(loanApplication.getId());

        if (loanApplication.getDestinationAccount().isBlank()) {
            return new ResponseEntity<>("Please Fill 'TO' account field", HttpStatus.FORBIDDEN);
        }

        if (loanApplication.getAmount() <= 0) {
            return new ResponseEntity<>("Amount must not be zero", HttpStatus.FORBIDDEN);
        }

        if (loanApplication.getPayments() <= 0) {
            return new ResponseEntity<>("Payment amount must not be zero", HttpStatus.FORBIDDEN);
        }

        // Verifico que exista el prestamo
       /* Long idLoan = loanApplication.getId();
        if (!loanRepository.existsById(idLoan)) {
            return new ResponseEntity<>("This type of loan does not exists", HttpStatus.FORBIDDEN);
        }*/

        if (loanApplication.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("Amount exceeds the max loan limits", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplication.getPayments())) {
            return new ResponseEntity<>("The installment pay plan is not available", HttpStatus.FORBIDDEN);
        }
        if (!accountService.existsAccountByNumber(loanApplication.getDestinationAccount())) {
            return new ResponseEntity<>("The account does not exists", HttpStatus.FORBIDDEN);
        }

        Account toAccount = accountService.findAccountByNumber(loanApplication.getDestinationAccount());
        if (!client.getAccounts().contains(toAccount)) {
            return new ResponseEntity<>("The account does not belong client", HttpStatus.FORBIDDEN);
        }

        // Agrego el 20%
        double add20 = loanApplication.getAmount() * 1.2;

        // Crear la transacción de crédito
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, loanApplication.getAmount(), loan.getName() + " Loan approved", formattedLocalDateTime);
        toAccount.addTransaction(creditTransaction);
        transactionService.saveTransaction(creditTransaction);

        ClientLoan newLoan = new ClientLoan(add20, loanApplication.getPayments());
        client.addClientLoan(newLoan);
        loan.addClientLoan(newLoan);

        clientLoanService.saveClientLoan(newLoan);

        toAccount.setBalance(loanApplication.getAmount() + toAccount.getBalance());
        accountService.saveAccount(toAccount);

        return new ResponseEntity<>("Approved credit", HttpStatus.CREATED);

    }

}
