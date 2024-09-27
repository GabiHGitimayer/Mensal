package com.mensal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mensal.entity.Jogador;
import com.mensal.service.JogadorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorControl {

	@Autowired
	private JogadorService jogadorService;
	
	@PostMapping("/salvar")
	public ResponseEntity<Jogador> saveJogador(@Valid @RequestBody Jogador jogador) {
	    Jogador salvarJogador = jogadorService.saveJogador(jogador);
	    return new ResponseEntity<>(salvarJogador, HttpStatus.CREATED);
	}
	
	 @ExceptionHandler(MethodArgumentNotValidException.class) //mensagem caso digite o formato seja invalido
	 public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	            errors.put(error.getField(), error.getDefaultMessage());
	        }
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	  }
	
	@GetMapping("findbyid/{id}")
	public ResponseEntity<Jogador> findById(@PathVariable Long id){
	    return ResponseEntity.ok(jogadorService.findById(id));
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Jogador>> findAll(){
	    return ResponseEntity.ok(jogadorService.findAll());
	}
	
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Jogador> atualizarJogador(
        @PathVariable Long id,
        @RequestBody Jogador jogadorAtualizado
    ) {
        Jogador jogador = jogadorService.atualizarJogador(id, jogadorAtualizado);
        return ResponseEntity.ok(jogador);
    }
	
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarJogador(@PathVariable Long id) {
        jogadorService.deletarJogador(id);
        return ResponseEntity.noContent().build();
    }
}
