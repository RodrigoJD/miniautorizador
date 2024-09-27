package com.example.miniautorizador.controller;

import com.example.miniautorizador.controller.request.TransactionRequest;
import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CardNotFoundException;
import com.example.miniautorizador.exception.InsufficientBalanceException;
import com.example.miniautorizador.exception.InvalidPasswordException;
import com.example.miniautorizador.service.TransactionService;
import com.example.miniautorizador.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    private Cartao registeredCard;

    private TransactionRequest request;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController = new TransactionController();

    @BeforeEach
    void setUp(){
        // TransactionRequest for card number 6549873025634501, psw 1234 e value 10.00
        request = TestUtil.buildTransactionRequest();
        registeredCard = TestUtil.buildCard();
        registeredCard.setValor(500.00); //card balance in database.
    }

    @Test
    void shouldMakeTransaction(){
        var responseEntity = transactionController.makeTransaction(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(TransactionController.OK, responseEntity.getBody());
        verify(transactionService, times(1)).makeTransaction(request);
    }

    @Test
    void shouldReturnStatus422WhenMakeTransactionServiceThrowCardNotFoundException(){
        doThrow(CardNotFoundException.class).when(transactionService).makeTransaction(request);

        var responseEntity = transactionController.makeTransaction(request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(new CardNotFoundException().getMessage(), responseEntity.getBody());
        verify(transactionService, times(1)).makeTransaction(request);
    }

    @Test
    void shouldReturnStatus422WhenMakeTransactionServiceThrowInvalidPasswordException(){
        doThrow(InvalidPasswordException.class).when(transactionService).makeTransaction(request);

        var responseEntity = transactionController.makeTransaction(request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(new InvalidPasswordException().getMessage(), responseEntity.getBody());
        verify(transactionService, times(1)).makeTransaction(request);
    }

    @Test
    void shouldReturnStatus422WhenMakeTransactionServiceThrowInsufficientBalanceException(){
        doThrow(InsufficientBalanceException.class).when(transactionService).makeTransaction(request);

        var responseEntity = transactionController.makeTransaction(request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(new InsufficientBalanceException().getMessage(), responseEntity.getBody());
        verify(transactionService, times(1)).makeTransaction(request);
    }
}
