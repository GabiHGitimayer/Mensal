package com.mensal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mensal.entity.Combate;
import com.mensal.entity.NPC;
import com.mensal.entity.Personagem;

public interface CombateRep  extends JpaRepository<Combate, Long> {
	    List<Combate> findByAtacante(Personagem atacante);
	    List<Combate> findByDefensor(NPC defensor);
	}


