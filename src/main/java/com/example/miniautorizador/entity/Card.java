package com.example.miniautorizador.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Card {

    @Id
    private Long cardNumber;

    private String password;

    private Double balance;
}
