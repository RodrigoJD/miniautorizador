package com.example.miniautorizador.controller;

import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CartaoNaoCadastradoException;
import com.example.miniautorizador.exception.SaldoInsuficienteException;
import com.example.miniautorizador.exception.SenhaInvalidaException;
import com.example.miniautorizador.service.TransacaoService;
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
public class TransacaoController {

    public static final String OK = "OK";
    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<String> transacao (@RequestBody Cartao cartao) {
        try{
            transacaoService.realizarTransacao(cartao);
            return new ResponseEntity<>(OK, new HttpHeaders(), HttpStatus.CREATED);
        } catch (CartaoNaoCadastradoException | SaldoInsuficienteException | SenhaInvalidaException e) {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


}
