package com.example.miniautorizador.service;

import com.example.miniautorizador.entity.Cartao;

public interface TransacaoService {
    void realizarTransacao(Cartao cartao);
}
