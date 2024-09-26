package com.example.miniautorizador.service;

import com.example.miniautorizador.controller.request.TransacaoRequest;

public interface TransacaoService {
    void realizarTransacao(TransacaoRequest request);
}
