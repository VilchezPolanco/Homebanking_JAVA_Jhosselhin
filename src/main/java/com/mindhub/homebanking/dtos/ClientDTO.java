package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountDTO> accounts;
    private List<ClientLoanDTO> loans;

    //constructor
    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client       //obj del tipo cliente
                .getAccounts()  //Set de cuentas
                .stream()       //Stream de cuentas
                .map(account -> new AccountDTO(account)) //Stream de cuentasDTO
                .collect(Collectors.toList());           //Lista de cuentasDTO
        this.loans =
                client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toList());
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

    public List<ClientLoanDTO> getLoans() { return loans; }
}
