package com.example.miniautorizador.controller;

import com.example.miniautorizador.controller.response.CartaoResponse;
import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CartaoJaCadastradoException;
import com.example.miniautorizador.exception.CartaoNaoCadastradoException;
import com.example.miniautorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<CartaoResponse> createCartao(@RequestBody Cartao cartao) {
        try {
            cartaoService.createCartao(cartao);
            return new ResponseEntity<>(new CartaoResponse(cartao.getNumeroCartao(), cartao.getSenha()),new HttpHeaders(), HttpStatus.CREATED);
        } catch (CartaoJaCadastradoException e) {
            return new ResponseEntity<>(new CartaoResponse(cartao.getNumeroCartao(), cartao.getSenha()),new HttpHeaders(), e.getHttpStatus());
        }
    }
    
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<Double> getSaldo(@PathVariable Long numeroCartao){
        try {
            var saldo = cartaoService.getSaldo(numeroCartao);
            return new ResponseEntity<>(saldo, new HttpHeaders(), HttpStatus.OK);
        } catch (CartaoNaoCadastradoException e) {
            return new ResponseEntity<>(new HttpHeaders(), e.getHttpStatus());
        }
    }
}
