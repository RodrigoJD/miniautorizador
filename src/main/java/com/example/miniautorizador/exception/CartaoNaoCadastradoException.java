package com.example.miniautorizador.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CartaoNaoCadastradoException extends RuntimeException{

    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
}
