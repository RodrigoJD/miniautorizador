package com.example.miniautorizador.service.imp;

import com.example.miniautorizador.entity.Cartao;
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
    public void realizarTransacao(Cartao cartao) {
        var cartaoToBeUpdated = cartaoRepository.findById(cartao.getNumeroCartao()).orElseThrow(CartaoNaoCadastradoException::new);
        if (!cartao.getSenha().equals(cartaoToBeUpdated.getSenha())) {
            throw new SenhaInvalidaException();
        }
        if(cartao.getValor() > cartaoToBeUpdated.getValor()){
            throw new SaldoInsuficienteException();
        }
        cartaoToBeUpdated.setValor(cartaoToBeUpdated.getValor() - cartao.getValor());
        cartaoRepository.save(cartaoToBeUpdated);
    }
}
