package com.mensal.controllerTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mensal.controller.PersonagemControl;
import com.mensal.entity.Personagem;
import com.mensal.service.PersonagemService;

class PersonagemControlTest {

    @Mock
    private PersonagemService personagemService;

    @InjectMocks
    private PersonagemControl personagemControl;

    private Personagem personagem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personagem = new Personagem(1L, "Herói", "Guerreiro", "Humano", 10L, 100L, 20L, 50L, 100L, null, null, null);
    }

    @Test
    void savePersonagem_deveRetornarPersonagemCriado() {
        when(personagemService.savePersonagem(any(Personagem.class))).thenReturn(personagem);

        ResponseEntity<Personagem> response = personagemControl.savePersonagem(personagem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Herói", response.getBody().getNome());
        verify(personagemService, times(1)).savePersonagem(personagem);
    }

    @Test
    void findById_deveRetornarPersonagem() {
        when(personagemService.findById(1L)).thenReturn(personagem);

        ResponseEntity<Personagem> response = personagemControl.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Herói", response.getBody().getNome());
        verify(personagemService, times(1)).findById(1L);
    }

    @Test
    void findAll_deveRetornarListaDePersonagens() {
        List<Personagem> personagens = Arrays.asList(personagem);
        when(personagemService.findAll()).thenReturn(personagens);

        List<Personagem> response = personagemControl.findAll();

        assertEquals(1, response.size());
        assertEquals("Herói", response.get(0).getNome());
        verify(personagemService, times(1)).findAll();
    }

    @Test
    void atualizarPersonagem_deveRetornarPersonagemAtualizado() {
        Personagem personagemAtualizado = new Personagem(1L, "Novo Herói", "Mago", "Elfo", 20L, 200L, 40L, 100L, 100L, null, null, null);
        when(personagemService.atualizarPersonagem(1L, personagemAtualizado)).thenReturn(personagemAtualizado);

        ResponseEntity<Personagem> response = personagemControl.atualizarPersonagem(1L, personagemAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Novo Herói", response.getBody().getNome());
        verify(personagemService, times(1)).atualizarPersonagem(1L, personagemAtualizado);
    }

    @Test
    void deletarPersonagem_deveDeletarPersonagem() {
        ResponseEntity<Void> response = personagemControl.deletarPersonagem(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(personagemService, times(1)).deletarPersonagem(1L);
    }
}

