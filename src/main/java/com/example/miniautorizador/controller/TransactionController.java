package com.example.miniautorizador.controller;

import com.example.miniautorizador.controller.request.TransactionRequest;
import com.example.miniautorizador.exception.CardNotFoundException;
import com.example.miniautorizador.exception.InsufficientBalanceException;
import com.example.miniautorizador.exception.InvalidPasswordException;
import com.example.miniautorizador.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/transacoes")
public class TransactionController {

    public static final String OK = "OK";

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> makeTransaction (@Valid @RequestBody TransactionRequest request) {
        try{
            transactionService.makeTransaction(request);
            return new ResponseEntity<>(OK, new HttpHeaders(), HttpStatus.CREATED);
        } catch (CardNotFoundException | InsufficientBalanceException | InvalidPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}
