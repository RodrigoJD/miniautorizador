package com.example.miniautorizador.service.imp;

import com.example.miniautorizador.controller.request.TransactionRequest;
import com.example.miniautorizador.exception.CardNotFoundException;
import com.example.miniautorizador.exception.InsufficientBalanceException;
import com.example.miniautorizador.exception.InvalidPasswordException;
import com.example.miniautorizador.repository.CardRepository;
import com.example.miniautorizador.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImp implements TransactionService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void makeTransaction(TransactionRequest request) {
        var cardToBeUpdated = cardRepository.findById(request.getNumeroCartao()).orElseThrow(CardNotFoundException::new);
        if (!request.getSenha().equals(cardToBeUpdated.getSenha())) {
            throw new InvalidPasswordException();
        }
        if(request.getValor() > cardToBeUpdated.getValor()){
            throw new InsufficientBalanceException();
        }
        cardToBeUpdated.setValor(cardToBeUpdated.getValor() - request.getValor());
        cardRepository.save(cardToBeUpdated);
    }
}
