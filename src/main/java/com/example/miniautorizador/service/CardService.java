package com.example.miniautorizador.service;

import com.example.miniautorizador.entity.Cartao;

public interface CardService {
    void createCard(Cartao cartao);

    Double getBalance(Long numeroCartao);
}
