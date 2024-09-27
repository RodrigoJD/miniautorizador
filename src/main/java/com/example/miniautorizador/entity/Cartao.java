package com.example.miniautorizador.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Cartao {

    @Id
    @NotNull(message = "numeroCartao é obrigatorio")
    @Positive(message = "numeroCartao deve ser maior que zero")
    private Long numeroCartao;

    @NotBlank(message = "senha é obrigatorio")
    private String senha;

    private Double valor;
}
