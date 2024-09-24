package com.example.miniautorizador.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Card {

    @Id
    private Integer cardNumber;

    private Integer password;

    private Double balance;
}
