package com.example.miniautorizador.service.imp;

import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CardAlreadyRegisteredException;
import com.example.miniautorizador.exception.CardNotFoundException;
import com.example.miniautorizador.repository.CardRepository;
import com.example.miniautorizador.service.CardService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImp implements CardService {

    private static final Logger logger = LoggerFactory.getLogger(CardServiceImp.class);

    @Autowired
    private CardRepository repository;

    @Override
    @Transactional
    public void createCard(Cartao card) {
        repository.findById(card.getNumeroCartao()).ifPresent(s -> {
            logger.info("Cartão ja cadastrado na base de dados.");
            throw new CardAlreadyRegisteredException();
        });
        card.setValor(500.00);
        repository.save(card);
    }

    @Override
    public Double getBalance(Long cardNumber) {
        var card = repository.findById(cardNumber).orElseThrow(CardNotFoundException::new);
        return card.getValor();
    }
}
