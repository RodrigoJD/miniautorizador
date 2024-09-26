package com.example.miniautorizador.service.imp;

import com.example.miniautorizador.controller.request.TransacaoRequest;
import com.example.miniautorizador.exception.CartaoNaoCadastradoException;
import com.example.miniautorizador.exception.SaldoInsuficienteException;
import com.example.miniautorizador.exception.SenhaInvalidaException;
import com.example.miniautorizador.repository.CartaoRepository;
import com.example.miniautorizador.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoServiceImp implements TransacaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Override
    public void realizarTransacao(TransacaoRequest request) {
        var cartaoToBeUpdated = cartaoRepository.findById(request.getNumeroCartao()).orElseThrow(CartaoNaoCadastradoException::new);
        if (!request.getSenha().equals(cartaoToBeUpdated.getSenha())) {
            throw new SenhaInvalidaException();
        }
        if(request.getValor() > cartaoToBeUpdated.getValor()){
            throw new SaldoInsuficienteException();
        }
        cartaoToBeUpdated.setValor(cartaoToBeUpdated.getValor() - request.getValor());
        cartaoRepository.save(cartaoToBeUpdated);
    }
}
