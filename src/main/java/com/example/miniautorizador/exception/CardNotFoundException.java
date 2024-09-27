package com.example.miniautorizador.exception;

import lombok.Getter;

@Getter
public class CardNotFoundException extends RuntimeException{
    private final String message = "CARTAO_INEXISTENTE";
}
