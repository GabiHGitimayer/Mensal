package com.mensal.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mensal.entity.Jogador;
import com.mensal.repository.JogadorRep;
import com.mensal.service.JogadorService;

class JogadorServiceTest {

    @InjectMocks
    private JogadorService jogadorService;

    @Mock
    private JogadorRep jogadorRep;

    private Jogador jogador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jogador = new Jogador(1L, "Gabi", "GHG", "gabil@gmail.com", "12345", 1L, 0L, new ArrayList<>());
    }

    @Test
    void testSaveJogador() {
        when(jogadorRep.save(any(Jogador.class))).thenReturn(jogador);

        Jogador resultado = jogadorService.saveJogador(jogador);

        assertEquals(jogador, resultado);
        verify(jogadorRep, times(1)).save(jogador);
    }

    @Test
    void testFindById() {
        when(jogadorRep.findById(anyLong())).thenReturn(Optional.of(jogador));

        Jogador resultado = jogadorService.findById(1L);

        assertEquals(jogador, resultado);
        verify(jogadorRep, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<Jogador> jogadores = new ArrayList<>();
        jogadores.add(jogador);
        when(jogadorRep.findAll()).thenReturn(jogadores);

        List<Jogador> resultado = jogadorService.findAll();

        assertEquals(jogadores, resultado);
        verify(jogadorRep, times(1)).findAll();
    }

    @Test
    void testAtualizarJogador() {
        Jogador jogadorAtualizado = new Jogador(1L, "Ana", "AJu", "ana@gmail.com", "56789", 1L, 0L, new ArrayList<>());

        when(jogadorRep.findById(1L)).thenReturn(Optional.of(jogador));
        when(jogadorRep.save(any(Jogador.class))).thenReturn(jogadorAtualizado);

        Jogador resultado = jogadorService.atualizarJogador(1L, jogadorAtualizado);

        assertEquals(jogadorAtualizado, resultado);
        verify(jogadorRep, times(1)).findById(1L);
        verify(jogadorRep, times(1)).save(jogador);
    }

    @Test
    void testDeletarJogador() {
        when(jogadorRep.findById(1L)).thenReturn(Optional.of(jogador));
        jogadorService.deletarJogador(1L);
        verify(jogadorRep, times(1)).delete(jogador);
    }
}
