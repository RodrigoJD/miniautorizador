package com.example.miniautorizador.exception;

import lombok.Getter;

@Getter
public class SaldoInsuficienteException extends RuntimeException {
    private final String message = "SALDO_INSUFICIENTE";
}
