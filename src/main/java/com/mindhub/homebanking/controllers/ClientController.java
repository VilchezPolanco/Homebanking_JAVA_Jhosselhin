package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController //controlador escucha y responde peticiones
@RequestMapping("/api") //ruta base del controlador(relaciona peticion con endpoint)
public class ClientController {

    @Autowired //injeccion un valor
    private ClientRepository clientRepository;  //para poder usar los CRUD

    @RequestMapping("/clients") //microprograma quer hace una peticion en especifico
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(ClientDTO::new) // Convierte el cliente a un DTO
                .orElse(null); // Si no se encuentra, retorna null
    }
}
