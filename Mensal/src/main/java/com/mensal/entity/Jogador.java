package com.mensal.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jogador")
@Entity
public class Jogador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "O nome não pode estar em branco")
    @Size(max = 20, message = "O limite é 20 caracteres")
    private String nome;
    
    @NotBlank(message = "O nome de usuário não pode estar em branco")
    @Size(max = 15)
    private String usuario;
    @Email(message = "formato de email invalido")
    @NotBlank(message = "informe um e-mail")
    private String email;
    
    @Pattern(regexp = "[0-9]{4,}", message = "A senha deve ser de quatro dígitos numéricos")
    private String senha;
    
    @NotNull
    private Long nivel=1L;
    
    @NotNull
    private Long exp=0L;

    @ManyToMany(mappedBy = "jogadores")
    @JsonIgnoreProperties(value = {"classe", "raca", "nivel", "vida", "ataque", "mana", "defesa", "combates"})
    private List<Personagem> personagens = new ArrayList<>();

}
