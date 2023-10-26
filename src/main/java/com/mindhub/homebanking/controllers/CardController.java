package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController //controlador escucha y responde peticiones
@RequestMapping("/api")
public class CardController {

    @Autowired //injeccion de dependencias
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/card")
    public ResponseEntity<Object> CreateCard(Authentication authentication, @RequestParam CardType type, @RequestParam CardColor color){
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client == null) {
            throw new UsernameNotFoundException("unknow client" + authentication.getName());
        }
        if (client.getCards().stream().filter(card -> card.getType() == type).count() < 3){
            Card card = new Card((client.getLastName() + " " + client.getFirstName()), type, color, (generateNumber(1, 10000) + " " + generateNumber(1, 10000) + " " + generateNumber(1, 10000) + " " + generateNumber(1, 10000)), generateCvv(1, 1000), LocalDate.now(), LocalDate.now().plusYears(5));
            cardRepository.save(card);
            client.addCard(card);
            clientRepository.save(client);

            return new ResponseEntity<>("Client card successfully", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Exede limite de tarjetas", HttpStatus.FORBIDDEN);
        }
    }

    public String generateNumber(int min, int max){
        List<CardDTO> cards = cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
        Set<String> numberCard = cards.stream().map(card -> card.getNumber()).collect(Collectors.toSet());

        String aux = "VIN";
        long number;
        String numberComplete;
        do {
            number = (int) (Math.random() * (max - min) + min);
            String numberFormat = String.format("%04d", number);
            numberComplete = aux + number;
        }while (numberCard.contains(numberComplete));
        return numberComplete;
    }

    public String generateCvv(int min, int max){
        List<CardDTO> cards = cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
        Set<String> numberCard = cards.stream().map(card -> card.getNumber()).collect(Collectors.toSet());

        String aux = "VIN";
        long number;
        String numberComplete;
        do {
            number = (int) (Math.random() * (max - min) + min);
            String numberFormat = String.format("%03d", number);
            numberComplete = aux + number;
        }while (numberCard.contains(numberComplete));
        return numberComplete;
    }

}
