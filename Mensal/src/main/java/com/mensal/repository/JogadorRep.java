package com.mensal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mensal.entity.Jogador;

public interface JogadorRep extends JpaRepository<Jogador, Long> {
	List<Jogador> findByNome(String nome);
}
