package com.example.miniautorizador.util;

import com.example.miniautorizador.controller.request.TransacaoRequest;
import com.example.miniautorizador.entity.Cartao;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtil {

    public static final Long NUMERO_CARTAO = 6549873025634501L;

    public static final String SENHA_CARTAO = "1234";

    public static Cartao buildCartao(){
        return Cartao.builder()
                .numeroCartao(NUMERO_CARTAO)
                .senha(SENHA_CARTAO)
                .build();
    }

    public static TransacaoRequest buildTransacaoRequest() {
        return TransacaoRequest.builder()
                .numeroCartao(NUMERO_CARTAO) // numero do cartão
                .senha(SENHA_CARTAO) // senha do cartão
                .valor(10.00) // valor a ser debitado
                .build();
    }
}
