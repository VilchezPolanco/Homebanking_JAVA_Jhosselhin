package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {
    List<Account> findAllAccount();

    Account findAccountById(Long id);

    void saveAccount(Account account);

    boolean existsAccountByNumber (String number);

    Account findAccountByNumber (String number);
}
