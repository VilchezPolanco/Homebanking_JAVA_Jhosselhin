package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAllClients();
    Client findClientById (Long id);
    Client findClientByEmail (String email);
    void saveClient (Client client);
    boolean existsClientByEmail(String email);
}