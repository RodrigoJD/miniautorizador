package com.example.miniautorizador.service.imp;

import com.example.miniautorizador.entity.Cartao;
import com.example.miniautorizador.exception.CartaoJaCadastradoException;
import com.example.miniautorizador.exception.CartaoNaoCadastradoException;
import com.example.miniautorizador.repository.CartaoRepository;
import com.example.miniautorizador.service.CartaoService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoServiceImp implements CartaoService {

    private static Logger logger = LoggerFactory.getLogger(CartaoServiceImp.class);

    @Autowired
    private CartaoRepository repository;

    @Override
    @Transactional
    public void createCartao(Cartao cartao) {
        repository.findById(cartao.getNumeroCartao()).ifPresent(s -> {
            logger.info("Cartão ja cadastrado na base de dados.");
            throw new CartaoJaCadastradoException();
        });
        cartao.setValor(500.00);
        repository.save(cartao);
    }

    @Override
    public Double getSaldo(Long numeroCartao) {
        var cartao = repository.findById(numeroCartao).orElseThrow(CartaoNaoCadastradoException::new);
        return cartao.getValor();
    }
}
