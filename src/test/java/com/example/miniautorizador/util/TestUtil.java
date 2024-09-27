package com.example.miniautorizador.util;

import com.example.miniautorizador.controller.request.TransactionRequest;
import com.example.miniautorizador.entity.Cartao;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtil {

    public static final Long CARD_NUMBER = 6549873025634501L;
    public static final String CARD_PASSWORD = "1234";
    public static final double EXPECTED_VALUE = 500.00;

    public static Cartao buildCard(){
        return Cartao.builder()
                .numeroCartao(CARD_NUMBER)
                .senha(CARD_PASSWORD)
                .build();
    }

    public static TransactionRequest buildTransactionRequest() {
        return TransactionRequest.builder()
                .numeroCartao(CARD_NUMBER)
                .senha(CARD_PASSWORD)
                .valor(10.00) // value do be deducted
                .build();
    }
}
