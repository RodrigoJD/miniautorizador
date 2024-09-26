package com.example.miniautorizador.service;

import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CartaoJaCadastradoException;
import com.example.miniautorizador.exception.CartaoNaoCadastradoException;
import com.example.miniautorizador.repository.CartaoRepository;
import com.example.miniautorizador.service.imp.CartaoServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CartaoServiceTest {

    public static final long NUMERO_CARTAO = 6549873025634501L;
    public static final double EXPECTED_VALOR = 500.00;

    private Cartao cartao;

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService = new CartaoServiceImp();

    @BeforeEach
    void setUp(){
        cartao = Cartao.builder()
                .numeroCartao(NUMERO_CARTAO)
                .senha("1234")
                .build();
    }


    @Test
    void shouldCreateCartao(){
        assertDoesNotThrow(() -> cartaoService.createCartao(cartao));
        verify(cartaoRepository, times(1)).findById(NUMERO_CARTAO);
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void shouldThrowCartaoJaCadastradoExceptionCreateCartao(){
        when(cartaoRepository.findById(NUMERO_CARTAO)).thenReturn(Optional.of(cartao));
        assertThrows(CartaoJaCadastradoException.class, () -> cartaoService.createCartao(cartao));
        verify(cartaoRepository, times(1)).findById(NUMERO_CARTAO);
        verify(cartaoRepository, times(0)).save(cartao);
    }

    @Test
    void shouldGetSaldoCartao(){
        cartao.setValor(EXPECTED_VALOR);
        when(cartaoRepository.findById(NUMERO_CARTAO)).thenReturn(Optional.of(cartao));
        var result = cartaoService.getSaldo(NUMERO_CARTAO);
        assertEquals(EXPECTED_VALOR, result);
        verify(cartaoRepository, times(1)).findById(NUMERO_CARTAO);
    }

    @Test
    void shouldThrowCartaoNaoCadastradoWhenGetSaldoCartao(){
        when(cartaoRepository.findById(NUMERO_CARTAO)).thenReturn(Optional.empty());
        assertThrows(CartaoNaoCadastradoException.class, () -> cartaoService.getSaldo(NUMERO_CARTAO));
        verify(cartaoRepository, times(1)).findById(NUMERO_CARTAO);
    }
}
