package com.example.miniautorizador.service;

import com.example.miniautorizador.entity.Cartao;

public interface CartaoService {
    void criarCartao(Cartao cartao);

    Double retornarSaldo(Long numeroCartao);
}
