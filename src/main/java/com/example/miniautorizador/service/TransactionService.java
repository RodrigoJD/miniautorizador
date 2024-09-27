package com.example.miniautorizador.service;

import com.example.miniautorizador.controller.request.TransactionRequest;

public interface TransactionService {
    void makeTransaction(TransactionRequest request);
}
