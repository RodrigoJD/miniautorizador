package com.example.miniautorizador.repository;

import com.example.miniautorizador.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Cartao, Long> {
}
