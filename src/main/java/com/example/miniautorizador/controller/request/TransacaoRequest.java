package com.example.miniautorizador.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransacaoRequest {

    @NotNull(message = "numeroCartao é obrigatorio")
    private Long numeroCartao;
    @NotBlank(message = "senha é obrigatorio")
    private String senha;
    @NotNull(message = "valor é obrigatorio")
    private Double valor;
}
