package com.example.miniautorizador.controller;

import com.example.miniautorizador.controller.response.CardResponse;
import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CardAlreadyRegisteredException;
import com.example.miniautorizador.exception.CardNotFoundException;
import com.example.miniautorizador.service.CardService;
import com.example.miniautorizador.util.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardControllerTest {

    private static Cartao card;

    private static CardResponse expectedResponseBody;

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController = new CardController();

    @BeforeAll
    static void setUp(){
        card = TestUtil.buildCard();
        expectedResponseBody = new CardResponse(TestUtil.CARD_NUMBER, TestUtil.CARD_PASSWORD);
    }

    @Test
    void shouldCreateCartao() {
        var responseEntity = cardController.createCard(card);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponseBody, responseEntity.getBody());
        verify(cardService, times(1)).createCard(card);
    }

    @Test
    void shouldReturnStatus422WhenCreatingCartao() {
        doThrow(CardAlreadyRegisteredException.class).when(cardService).createCard(card);
        var responseEntity = cardController.createCard(card);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(expectedResponseBody, responseEntity.getBody());
        verify(cardService, times(1)).createCard(card);
    }

    @Test
    void shouldGetSaldo(){
        card.setValor(TestUtil.EXPECTED_VALUE);
        when(cardService.getBalance(TestUtil.CARD_NUMBER)).thenReturn(TestUtil.EXPECTED_VALUE);
        var responseEntity = cardController.getBalance(TestUtil.CARD_NUMBER);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TestUtil.EXPECTED_VALUE, responseEntity.getBody());
        verify(cardService, times(1)).getBalance(TestUtil.CARD_NUMBER);
    }

    @Test
    void shouldReturnStatus404WhenGetSaldo(){
        doThrow(CardNotFoundException.class).when(cardService).getBalance(TestUtil.CARD_NUMBER);
        var responseEntity = cardController.getBalance(TestUtil.CARD_NUMBER);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(cardService, times(1)).getBalance(TestUtil.CARD_NUMBER);
    }

}
