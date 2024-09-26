package com.example.miniautorizador.service;

import com.example.miniautorizador.entity.Cartao;

public interface CartaoService {
    void createCartao(Cartao cartao);

    Double getSaldo(Long numeroCartao);
}
