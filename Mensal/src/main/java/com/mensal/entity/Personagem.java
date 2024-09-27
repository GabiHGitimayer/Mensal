package com.mensal.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "personagem")
public class Personagem {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "O nome do personagem não pode estar em branco")
    @Size(max = 15)
	private String nome;
	@NotBlank(message = "A classe não pode estar em branco")
    @Size(max = 15)
	private String classe;
	@NotBlank(message = "A raça de usuário não pode estar em branco")
    @Size(max = 15)
	private String raca;
	
	private Long nivel = 1L;
	
	private Long vida = 100L;
	
	private Long ataque = 10L;
	
	private Long mana = 50L;
	
	private Long defesa = 5L;
	
	private Boolean ativo = true;

    @OneToMany(mappedBy = "atacante")
    @JsonBackReference // Impede que o ciclo continue na serialização
    @JsonIgnoreProperties(value = {"id", "npc"})
    private List<Combate> combates = new ArrayList<>();
    
	
    @ManyToMany
    @JoinTable(
        name = "personagem_jogador",
        joinColumns = @JoinColumn(name = "personagem_id"),
        inverseJoinColumns = @JoinColumn(name = "jogador_id")
    )
    @JsonIgnoreProperties(value = {"nome", "email", "senha", "nivel", "exp", "personagens"})
    private List<Jogador> jogadores = new ArrayList<>();
	
}
