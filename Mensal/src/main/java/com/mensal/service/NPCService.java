package com.mensal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mensal.entity.NPC;
import com.mensal.repository.NPCRep;


@Service
public class NPCService {

	@Autowired
    private NPCRep npcRep;

	public NPC saveNPC(NPC npc){
		return npcRep.save(npc);
	}
	
    public NPC findById(Long id){
        try{
            return npcRep.findById(id).orElseThrow(); // orElseThrow(); é oq lança a exceção se der null
        }catch (Exception e){
            System.out.println(e.getCause()); //getCause indica pq ocorreu a exceção
            return new NPC();
        }
    }
	   
	public List<NPC> findAll() {
		return npcRep.findAll();
	}
	
    public NPC atualizarNPC(Long id, NPC npcAtualizado) {
        NPC npc = npcRep.findById(id)
            .orElseThrow();
        npc.setNome(npcAtualizado.getNome());
        npc.setVida(npcAtualizado.getVida());
        npc.setAtaque(npcAtualizado.getAtaque());
        npc.setDefesa(npcAtualizado.getDefesa());
        npc.setMana(npcAtualizado.getMana());

        return npcRep.save(npc);
    }
    
    public void deletarNPC(Long id) {
        NPC npc = npcRep.findById(id)
            .orElseThrow(() -> new RuntimeException("NPC não encontrado."));
        
        try {
            npcRep.delete(npc);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Não pode ser excluído, NPC em combate.");
        }
    }
	
}
