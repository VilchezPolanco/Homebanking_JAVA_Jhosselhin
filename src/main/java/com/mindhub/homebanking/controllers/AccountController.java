package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController //controlador escucha y responde peticiones
@RequestMapping("/api")
public class AccountController {

    @Autowired //injeccion de dependencias
    private AccountRepository accountRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {

        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @RequestMapping("accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){

        Optional<Account> account=accountRepository.findById(id);
        AccountDTO accountDTO= account.map(acc -> new AccountDTO(acc)).orElse(null);
        return accountDTO;
    }
}
