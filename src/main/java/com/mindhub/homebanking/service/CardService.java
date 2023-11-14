package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.*;

import java.util.List;

public interface CardService {
    List<Card> findAllCard();
    boolean existsCardByTypeAndColorAndClient (CardType type, CardColor color, Client client);
    void saveCard (Card card);
    boolean existsCardByNumber (String number);
}
