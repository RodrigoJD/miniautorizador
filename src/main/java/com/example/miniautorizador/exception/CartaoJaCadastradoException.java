package com.example.miniautorizador.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartaoJaCadastradoException extends RuntimeException {

    private final HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
}
