package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController //controlador escucha y responde peticiones
@RequestMapping("/api")
public class AccountController {
    @Autowired //injeccion de dependencias
    private AccountService accountService;

    @Autowired
    private ClientService clientService;
    /*@Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;*/

    @GetMapping("/accounts")
    public List<AccountDTO> getAllAccounts() {
        return accountService.findAllAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable Long id) {

        Account account = accountService.findAccountById(id);

        if (account == null) {
            return new ResponseEntity<>("Account not found.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);

    }
    /*@GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }*/

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccountClientCurrent(Authentication authentication) {
        return clientService.findClientByEmail(authentication.getName()) //del serviceClient
                .getAccounts()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());  //del serviceclient

        if (client == null) {
            throw new UsernameNotFoundException("unknow client" + authentication.getName());
        }
        if (client.getAccounts().size() == 3){
           return new ResponseEntity<>("Exede limite de cuentas", HttpStatus.FORBIDDEN);
        }else {
            Account account = new Account(generateNumber(1, 100000000), LocalDate.now(), 0.00);
            accountService.saveAccount(account);
            client.addAccount(account);
            clientService.saveClient(client);

            return new ResponseEntity<>("Accounts created successfully", HttpStatus.CREATED);
        }
    }

    public String generateNumber(int min, int max){
        List<AccountDTO> accounts = getAllAccounts();
        Set<String> numberAccount = accounts.stream().map(account -> account.getNumber()).collect(Collectors.toSet());

        String aux = "VIN";
        long number;
        String numberComplete;
        do {
            number = (int) (Math.random() * (max - min) + min);
            String numberFormat = String.format("%03d", number);
            numberComplete = aux + number;
        }while (numberAccount.contains(numberComplete));
            return numberComplete;
        }
}