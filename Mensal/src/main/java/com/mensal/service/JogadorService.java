package com.mensal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mensal.entity.Jogador;
import com.mensal.repository.JogadorRep;

@Service
public class JogadorService {
	
	@Autowired
    private JogadorRep jogadorRep;

	public Jogador saveJogador(Jogador jogador){
		return jogadorRep.save(jogador);
	}
	
    public Jogador findById(Long id){
        try{
            return jogadorRep.findById(id).orElseThrow(); // orElseThrow(); é oq lança a exceção se der null
        }catch (Exception e){
            System.out.println(e.getCause()); //getCause indica pq ocorreu a exceção
            return new Jogador();
        }
    }
	   
	public List<Jogador> findAll() {
		return jogadorRep.findAll();
	}
	
    public Jogador atualizarJogador(Long id, Jogador jogadorAtualizado) {
        Jogador jogadorExistente = jogadorRep.findById(id)
            .orElseThrow();

        jogadorExistente.setNome(jogadorAtualizado.getNome());
        jogadorExistente.setUsuario(jogadorAtualizado.getUsuario());
        jogadorExistente.setEmail(jogadorAtualizado.getEmail());
        jogadorExistente.setSenha(jogadorAtualizado.getSenha());


        return jogadorRep.save(jogadorExistente);
    }
    
    public void deletarJogador(Long id) {
        Jogador jogador = jogadorRep.findById(id)
            .orElseThrow();
        jogadorRep.delete(jogador);
    }
    
}

