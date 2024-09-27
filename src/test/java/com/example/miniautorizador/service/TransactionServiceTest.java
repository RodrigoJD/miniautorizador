package com.example.miniautorizador.service;

import com.example.miniautorizador.controller.request.TransactionRequest;
import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CardNotFoundException;
import com.example.miniautorizador.exception.InsufficientBalanceException;
import com.example.miniautorizador.exception.InvalidPasswordException;
import com.example.miniautorizador.repository.CardRepository;
import com.example.miniautorizador.service.imp.TransactionServiceImp;
import com.example.miniautorizador.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    private Cartao registeredCard;

    private TransactionRequest request;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private TransactionService transactionService = new TransactionServiceImp();

    @BeforeEach
    void setUp(){
        // TransactionRequest for card number 6549873025634501, psw 1234 e value 10.00
        request = TestUtil.buildTransactionRequest();
        registeredCard = TestUtil.buildCard();
        registeredCard.setValor(500.00); //saldo do cartão no banco de dados.
    }

    @Test
    void shouldMakeTransaction(){
        when(cardRepository.findById(TestUtil.CARD_NUMBER)).thenReturn(Optional.of(registeredCard));

        assertDoesNotThrow(() -> transactionService.makeTransaction(request));

        verify(cardRepository, times(1)).findById(TestUtil.CARD_NUMBER);
        registeredCard.setValor(490.00); //balance after making transaction
        verify(cardRepository, times(1)).save(registeredCard);
    }

    @Test
    void shouldThrowCardNotFoundExceptionWhemMakeTransaction(){
        when(cardRepository.findById(TestUtil.CARD_NUMBER)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> transactionService.makeTransaction(request));

        verify(cardRepository, times(1)).findById(TestUtil.CARD_NUMBER);
        verify(cardRepository, times(0)).save(registeredCard);
    }

    @Test
    void shouldThrowInvalidPasswordExceptionWhemMakeTransaction(){
        request.setSenha("0000"); //Senha invalida.
        when(cardRepository.findById(TestUtil.CARD_NUMBER)).thenReturn(Optional.of(registeredCard));

        assertThrows(InvalidPasswordException.class, () -> transactionService.makeTransaction(request));

        verify(cardRepository, times(1)).findById(TestUtil.CARD_NUMBER);
        verify(cardRepository, times(0)).save(registeredCard);
    }

    @Test
    void shouldThrowInsufficientBalanceExceptionWhemMakeTransaction(){
        request.setValor(1000.00); //valor a debitar maior que o saldo existente
        when(cardRepository.findById(TestUtil.CARD_NUMBER)).thenReturn(Optional.of(registeredCard));

        assertThrows(InsufficientBalanceException.class, () -> transactionService.makeTransaction(request));

        verify(cardRepository, times(1)).findById(TestUtil.CARD_NUMBER);
        verify(cardRepository, times(0)).save(registeredCard);
    }
}
