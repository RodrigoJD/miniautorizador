package com.example.miniautorizador.repository;

import com.example.miniautorizador.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
