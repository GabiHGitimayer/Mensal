package com.mensal.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mensal.controller.JogadorControl;
import com.mensal.entity.Jogador;
import com.mensal.service.JogadorService;

class JogadorControlTest {

    @InjectMocks
    private JogadorControl jogadorControl;

    @Mock
    private JogadorService jogadorService;

    private Jogador jogador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jogador = new Jogador(1L, "Ana", "AJu", "ana@gmail.com", "12345", 1L, 100L, new ArrayList<>());
    }

    @Test
    void testSaveJogador() {
        when(jogadorService.saveJogador(any(Jogador.class))).thenReturn(jogador);

        ResponseEntity<Jogador> response = jogadorControl.saveJogador(jogador);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(jogador, response.getBody());
        verify(jogadorService, times(1)).saveJogador(jogador); //só pode chamar uma vez o objeto
    }

    @Test
    void testFindById() {
        when(jogadorService.findById(1L)).thenReturn(jogador);

        ResponseEntity<Jogador> response = jogadorControl.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jogador, response.getBody());
        verify(jogadorService, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<Jogador> jogadores = new ArrayList<>();
        jogadores.add(jogador);
        when(jogadorService.findAll()).thenReturn(jogadores);

        ResponseEntity<List<Jogador>> response = jogadorControl.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jogadores, response.getBody());
        verify(jogadorService, times(1)).findAll();
    }

    @Test
    void testAtualizarJogador() {
        Jogador jogadorAtualizado = new Jogador(1L, "Gabi", "GabiHG", "gabihg@gmail.com", "56789", 1L, 100L, new ArrayList<>());
        when(jogadorService.atualizarJogador(1L, jogadorAtualizado)).thenReturn(jogadorAtualizado);

        ResponseEntity<Jogador> response = jogadorControl.atualizarJogador(1L, jogadorAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jogadorAtualizado, response.getBody());
        verify(jogadorService, times(1)).atualizarJogador(1L, jogadorAtualizado);
    }

    @Test
    void testDeletarJogador() {
        doNothing().when(jogadorService).deletarJogador(1L);
        ResponseEntity<String> response = jogadorControl.deletarJogador(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(jogadorService, times(1)).deletarJogador(1L);
    }

}

