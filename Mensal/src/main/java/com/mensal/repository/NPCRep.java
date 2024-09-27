package com.mensal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mensal.entity.NPC;

public interface NPCRep extends JpaRepository<NPC, Long> {
	List<NPC> findByNome(String nome);
}
