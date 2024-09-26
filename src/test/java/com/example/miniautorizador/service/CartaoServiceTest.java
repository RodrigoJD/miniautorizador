package com.example.miniautorizador.service;

import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CartaoJaCadastradoException;
import com.example.miniautorizador.exception.CartaoNaoCadastradoException;
import com.example.miniautorizador.repository.CartaoRepository;
import com.example.miniautorizador.service.imp.CartaoServiceImp;
import com.example.miniautorizador.util.TestUtil;
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

    public static final double EXPECTED_VALOR = 500.00;

    private Cartao cartao;

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService = new CartaoServiceImp();

    @BeforeEach
    void setUp(){
        cartao = TestUtil.buildCartao();
    }


    @Test
    void deveCriarCartao(){
        assertDoesNotThrow(() -> cartaoService.criarCartao(cartao));
        verify(cartaoRepository, times(1)).findById(TestUtil.NUMERO_CARTAO);
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void deveLancarCartaoJaCadastradoExceptionQuandoCriarCartao(){
        when(cartaoRepository.findById(TestUtil.NUMERO_CARTAO)).thenReturn(Optional.of(cartao));
        assertThrows(CartaoJaCadastradoException.class, () -> cartaoService.criarCartao(cartao));
        verify(cartaoRepository, times(1)).findById(TestUtil.NUMERO_CARTAO);
        verify(cartaoRepository, times(0)).save(cartao);
    }

    @Test
    void devePegarSaldoCartao(){
        cartao.setValor(EXPECTED_VALOR);
        when(cartaoRepository.findById(TestUtil.NUMERO_CARTAO)).thenReturn(Optional.of(cartao));
        var result = cartaoService.retornarSaldo(TestUtil.NUMERO_CARTAO);
        assertEquals(EXPECTED_VALOR, result);
        verify(cartaoRepository, times(1)).findById(TestUtil.NUMERO_CARTAO);
    }

    @Test
    void deveLancarCartaoNaoCadastradoExceptionQuandoPegarSaldoCartao(){
        when(cartaoRepository.findById(TestUtil.NUMERO_CARTAO)).thenReturn(Optional.empty());
        assertThrows(CartaoNaoCadastradoException.class, () -> cartaoService.retornarSaldo(TestUtil.NUMERO_CARTAO));
        verify(cartaoRepository, times(1)).findById(TestUtil.NUMERO_CARTAO);
    }
}
