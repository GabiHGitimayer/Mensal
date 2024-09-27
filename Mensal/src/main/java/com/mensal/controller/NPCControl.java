package com.mensal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mensal.entity.NPC;
import com.mensal.service.NPCService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/npc")
public class NPCControl {

	@Autowired
	private NPCService npcService;
	
	
	@PostMapping("/saveNPC")
	public ResponseEntity<NPC> saveNPC(@Valid @RequestBody NPC npc) {
	    NPC salvarNPC = npcService.saveNPC(npc);
	    return new ResponseEntity<>(salvarNPC, HttpStatus.CREATED);
	}
	
	@GetMapping("findbyid/{id}")
	public ResponseEntity<NPC> findById(@PathVariable Long id){
	    return ResponseEntity.ok(npcService.findById(id));
	}

	@GetMapping("/findAll")
	public List<NPC> findAll(){
	    return npcService.findAll();
	}
	
	@PutMapping("atualizar/{id}")
    public ResponseEntity<NPC> atualizarNPC(
            @PathVariable Long id,
            @RequestBody NPC npcAtualizado
        ) {
            NPC npc = npcService.atualizarNPC(id, npcAtualizado);
            return ResponseEntity.ok(npc);
        }
	
	@DeleteMapping("deletar/{id}")
	public ResponseEntity<String> deletarNPC(@PathVariable Long id) {
	    npcService.deletarNPC(id);
	    return ResponseEntity.noContent().build();
	}

}
