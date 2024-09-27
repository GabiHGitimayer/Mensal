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

import com.mensal.entity.Personagem;
import com.mensal.service.PersonagemService;

@RestController
@RequestMapping("/api/personagens")
public class PersonagemControl {
	
	@Autowired
	private PersonagemService personagemService;
	
	@PostMapping("/salvar")
	public ResponseEntity<Personagem> savePersonagem(@RequestBody Personagem personagem) {
	    Personagem salvarPersonagem = personagemService.savePersonagem(personagem);
	    return new ResponseEntity<>(salvarPersonagem, HttpStatus.CREATED);
	}

	@GetMapping("/localizar/{id}")
	public ResponseEntity<Personagem> findById(@PathVariable Long id){
	    return ResponseEntity.ok(personagemService.findById(id));
	}

	@GetMapping("/findAll")
	public List<Personagem> findAll(){
	    return personagemService.findAll();
	}
	
	@PutMapping("atualizar/{id}")
    public ResponseEntity<Personagem> atualizarPersonagem(
            @PathVariable Long id,
            @RequestBody Personagem npcAtualizado
        ) {
            Personagem npc = personagemService.atualizarPersonagem(id, npcAtualizado);
            return ResponseEntity.ok(npc);
        }
	
	@DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deletarPersonagem(@PathVariable Long id) {
        personagemService.deletarPersonagem(id);
        return ResponseEntity.noContent().build();
	}
}
