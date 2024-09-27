package com.mensal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "combate")
public class Combate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "personagem")
    @JsonIgnoreProperties({"ativo, jogadores"})
    //@JsonManagedReference
    private Personagem atacante;

    @ManyToOne
    @JoinColumn(name = "npc")
    //@JsonManagedReference
    @JsonIgnoreProperties({"combates"})
    private NPC defensor;

    private String resultado;
}
