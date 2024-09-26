package com.example.miniautorizador.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cartao {

    @Id
    private Long numeroCartao;

    private String senha;

    private Double valor;
}
