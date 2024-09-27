package com.example.miniautorizador.exception;

import lombok.Getter;

@Getter
public class InsufficientBalanceException extends RuntimeException {
    private final String message = "SALDO_INSUFICIENTE";
}
