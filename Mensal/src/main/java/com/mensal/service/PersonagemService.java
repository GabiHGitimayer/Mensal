package com.mensal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mensal.entity.Jogador;
import com.mensal.entity.Personagem;
import com.mensal.repository.JogadorRep;
import com.mensal.repository.PersonagemRep;

@Service
public class PersonagemService {

	@Autowired
	private PersonagemRep personagemRep;
	
	@Autowired
	private JogadorRep jogadorRep;

	public Personagem savePersonagem(Personagem personagem) {
	    if (personagem.getJogadores() != null && !personagem.getJogadores().isEmpty()) {
	        Jogador jogador = personagem.getJogadores().get(0);
	        if (jogador.getId() != null) {
	            jogador = jogadorRep.findById(jogador.getId()).orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
	            jogador.getPersonagens().add(personagem);
	            personagem.setJogadores(List.of(jogador));
	            System.out.println(personagem.getJogadores());
	        }
	    }
	    return personagemRep.save(personagem);
	}
	
	
	
    public Personagem findById(Long id){
        try{
            return personagemRep.findById(id).orElseThrow();
        }catch (Exception e){
            System.out.println(e.getCause());
            return new Personagem();
        }
    }
	   
	public List<Personagem> findAll() {
		return personagemRep.findAll();
	}
	
    public Personagem atualizarPersonagem(Long id, Personagem personagemAtualizado) {
        Personagem personagem = personagemRep.findById(id)
            .orElseThrow();
        personagem.setNome(personagemAtualizado.getNome());
        personagem.setClasse(personagemAtualizado.getClasse());
        personagem.setRaca(personagemAtualizado.getRaca());               
        return personagemRep.save(personagem);
    }
    
    
    public void atualizarAuto(Personagem personagem) {
        if (personagem.getAtivo()) {
            personagem.setNivel(personagem.getNivel() + 1);
            personagem.setAtaque(personagem.getAtaque() + 5);
            personagem.setVida(personagem.getVida() + 10);
            personagem.setDefesa(personagem.getDefesa() + 3);
            
            personagemRep.save(personagem);
        }
    }
    
    public void desativarDerrota(Personagem personagem) {
        personagem.setAtivo(false);
        personagemRep.save(personagem);
    }
    
    public void deletarPersonagem(Long id) {
        Personagem personagem = personagemRep.findById(id)
            .orElseThrow();
        personagemRep.delete(personagem);
    }
	
	
}
