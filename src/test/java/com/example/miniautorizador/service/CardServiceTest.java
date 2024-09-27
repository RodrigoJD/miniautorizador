package com.example.miniautorizador.service;

import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CardAlreadyRegisteredException;
import com.example.miniautorizador.exception.CardNotFoundException;
import com.example.miniautorizador.repository.CardRepository;
import com.example.miniautorizador.service.imp.CardServiceImp;
import com.example.miniautorizador.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {



    private Cartao card;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService = new CardServiceImp();

    @BeforeEach
    void setUp(){
        card = TestUtil.buildCard();
    }


    @Test
    void shouldCreateCard(){
        assertDoesNotThrow(() -> cardService.createCard(card));
        verify(cardRepository, times(1)).findById(TestUtil.CARD_NUMBER);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void shouldThrowCardAlreadyRegisteredExceptionWhenCreateCard(){
        when(cardRepository.findById(TestUtil.CARD_NUMBER)).thenReturn(Optional.of(card));
        assertThrows(CardAlreadyRegisteredException.class, () -> cardService.createCard(card));
        verify(cardRepository, times(1)).findById(TestUtil.CARD_NUMBER);
        verifyNoMoreInteractions(cardRepository);
    }

    @Test
    void shouldGetBalance(){
        card.setValor(TestUtil.EXPECTED_VALUE);
        when(cardRepository.findById(TestUtil.CARD_NUMBER)).thenReturn(Optional.of(card));
        var result = cardService.getBalance(TestUtil.CARD_NUMBER);
        assertEquals(TestUtil.EXPECTED_VALUE, result);
        verify(cardRepository, times(1)).findById(TestUtil.CARD_NUMBER);
    }

    @Test
    void shouldThrowCardNotFoundExceptionWhenGetBalance(){
        when(cardRepository.findById(TestUtil.CARD_NUMBER)).thenReturn(Optional.empty());
        assertThrows(CardNotFoundException.class, () -> cardService.getBalance(TestUtil.CARD_NUMBER));
        verify(cardRepository, times(1)).findById(TestUtil.CARD_NUMBER);
    }
}
