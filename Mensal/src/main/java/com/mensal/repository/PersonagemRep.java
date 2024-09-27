package com.mensal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mensal.entity.Personagem;

public interface PersonagemRep extends JpaRepository<Personagem, Long>{
	
	List<Personagem> findByNome(String nome);
}
