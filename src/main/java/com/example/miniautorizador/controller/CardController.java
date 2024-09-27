package com.example.miniautorizador.controller;

import com.example.miniautorizador.controller.response.CardResponse;
import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CardAlreadyRegisteredException;
import com.example.miniautorizador.exception.CardNotFoundException;
import com.example.miniautorizador.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cartoes")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody Cartao card) {
        try {
            cardService.createCard(card);
            return new ResponseEntity<>(new CardResponse(card.getNumeroCartao(), card.getSenha()),new HttpHeaders(), HttpStatus.CREATED);
        } catch (CardAlreadyRegisteredException e) {
            return new ResponseEntity<>(new CardResponse(card.getNumeroCartao(), card.getSenha()),new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<Double> getBalance (@PathVariable Long numeroCartao){
        try {
            var balance = cardService.getBalance(numeroCartao);
            return new ResponseEntity<>(balance, new HttpHeaders(), HttpStatus.OK);
        } catch (CardNotFoundException e) {
            return new ResponseEntity<>(new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }
}
