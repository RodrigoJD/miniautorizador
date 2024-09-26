package com.example.miniautorizador.service;

import com.example.miniautorizador.controller.request.TransacaoRequest;
import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CartaoNaoCadastradoException;
import com.example.miniautorizador.exception.SaldoInsuficienteException;
import com.example.miniautorizador.exception.SenhaInvalidaException;
import com.example.miniautorizador.repository.CartaoRepository;
import com.example.miniautorizador.service.imp.TransacaoServiceImp;
import com.example.miniautorizador.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransacaoServiceTest {

    private Cartao cartaoRegistrado;

    private TransacaoRequest request;

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private TransacaoService transacaoService = new TransacaoServiceImp();

    @BeforeEach
    void setUp(){
        // TransacaoRequest para cartão numero 6549873025634501, senha 1234 e valor 10.00
        request = TestUtil.buildTransacaoRequest();
        cartaoRegistrado = TestUtil.buildCartao();
        cartaoRegistrado.setValor(500.00); //saldo do cartão no banco de dados.
    }

    @Test
    void deveRealizarTransacao(){
        when(cartaoRepository.findById(TestUtil.NUMERO_CARTAO)).thenReturn(Optional.of(cartaoRegistrado));

        assertDoesNotThrow(() -> transacaoService.realizarTransacao(request));

        verify(cartaoRepository, times(1)).findById(TestUtil.NUMERO_CARTAO);
        cartaoRegistrado.setValor(490.00); //resultado após realizar transacao
        verify(cartaoRepository, times(1)).save(cartaoRegistrado);
    }

    @Test
    void deveLancarCartaoNaoCadastradoExceptionAoRealizarTransacao(){
        when(cartaoRepository.findById(TestUtil.NUMERO_CARTAO)).thenReturn(Optional.empty());

        assertThrows(CartaoNaoCadastradoException.class, () -> transacaoService.realizarTransacao(request));

        verify(cartaoRepository, times(1)).findById(TestUtil.NUMERO_CARTAO);
        verify(cartaoRepository, times(0)).save(cartaoRegistrado);
    }

    @Test
    void deveLancarSenhaInvalidaExceptionAoRealizarTransacao(){
        request.setSenha("0000"); //Senha invalida.
        when(cartaoRepository.findById(TestUtil.NUMERO_CARTAO)).thenReturn(Optional.of(cartaoRegistrado));

        assertThrows(SenhaInvalidaException.class, () -> transacaoService.realizarTransacao(request));

        verify(cartaoRepository, times(1)).findById(TestUtil.NUMERO_CARTAO);
        verify(cartaoRepository, times(0)).save(cartaoRegistrado);
    }

    @Test
    void deveLancarSaldoInsuficienteExceptionAoRealizarTransacao(){
        request.setValor(1000.00); //valor a debitar maior que o saldo existente
        when(cartaoRepository.findById(TestUtil.NUMERO_CARTAO)).thenReturn(Optional.of(cartaoRegistrado));

        assertThrows(SaldoInsuficienteException.class, () -> transacaoService.realizarTransacao(request));

        verify(cartaoRepository, times(1)).findById(TestUtil.NUMERO_CARTAO);
        verify(cartaoRepository, times(0)).save(cartaoRegistrado);
    }
}
