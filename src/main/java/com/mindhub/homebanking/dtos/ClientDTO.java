package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountDTO> accounts;
    private Set<ClientLoanDTO> loans;

    //constructor
    public ClientDTO(Client client) {
        id = client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        accounts = client       //obj del tipo cliente
                .getAccounts()  //Set de cuentas
                .stream()       //Stream de cuentas
                .map(account -> new AccountDTO(account)) //Stream de cuentasDTO
                .collect(Collectors.toList());           //Lista de cuentasDTO
        loans = client.getClientLoans().stream().map(loan-> new ClientLoanDTO(loan)).collect(Collectors.toSet());
    }

    //metodos

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
}
