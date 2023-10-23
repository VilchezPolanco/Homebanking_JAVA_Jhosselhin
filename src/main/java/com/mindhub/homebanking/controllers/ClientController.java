package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//controlador escucha y responde peticiones
@RestController // Controlador bajo los parametros de REST(HTTP)/ le digo que esta clase va hacer el controlador.
@RequestMapping("/api/clients") //ruta base del controlador(relaciona peticion con endpoint)
public class ClientController {

    @Autowired //injeccion un valor
    private ClientRepository clientRepository;  //para poder usar los CRUD

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping // Servlest / microprograma quer hace una peticion en especifico
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientRepository.findById(id)
                .map(ClientDTO::new) // Convierte el cliente a un DTO
                .orElse(null); // Si no se encuentra, retorna null
    }

    @PostMapping
    public ResponseEntity<Object> newClient(
            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password), false);
        clientRepository.save(client);
        return new ResponseEntity<>("Client created successfully", HttpStatus.CREATED);
    }
    @RequestMapping("/currents")
    public ClientDTO getClientCurrent(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
}
