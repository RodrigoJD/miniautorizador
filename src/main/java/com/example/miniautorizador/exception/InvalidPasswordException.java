package com.example.miniautorizador.exception;

import lombok.Getter;

@Getter
public class InvalidPasswordException extends RuntimeException {
    private final String message = "SENHA_INVALIDA";
}
