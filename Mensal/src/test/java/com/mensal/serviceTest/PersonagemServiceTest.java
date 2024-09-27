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
import com.mensal.entity.Personagem;
import com.mensal.repository.JogadorRep;
import com.mensal.repository.PersonagemRep;
import com.mensal.service.PersonagemService;

class PersonagemServiceTest {

    @InjectMocks
    private PersonagemService personagemService;

    @Mock
    private PersonagemRep personagemRep;

    @Mock
    private JogadorRep jogadorRep;

    private Personagem personagem;
    private Jogador jogador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jogador = new Jogador();
        personagem = new Personagem();
    }

    @Test
    void testSavePersonagem() {
    	jogador.setId(1L);
        personagem.setJogadores(List.of(jogador));
        when(jogadorRep.findById(anyLong())).thenReturn(Optional.of(jogador));
        when(personagemRep.save(any(Personagem.class))).thenReturn(personagem);
        Personagem resultado = personagemService.savePersonagem(personagem);
        assertEquals(personagem, resultado);
        verify(jogadorRep, times(1)).findById(anyLong());
        verify(personagemRep, times(1)).save(personagem);
    }


    @Test
    void testFindById() {
        when(personagemRep.findById(anyLong())).thenReturn(Optional.of(personagem));
        Personagem resultado = personagemService.findById(1L);
        assertEquals(personagem, resultado);
        verify(personagemRep, times(1)).findById(1L);
    }


    @Test
    void testFindAll() {
        List<Personagem> personagens = new ArrayList<>();
        personagens.add(personagem);
        when(personagemRep.findAll()).thenReturn(personagens);

        List<Personagem> resultado = personagemService.findAll();

        assertEquals(personagens, resultado);
        verify(personagemRep, times(1)).findAll();
    }

    @Test
    void testAtualizarPersonagem() {
        Personagem personagemAtualizado = new Personagem();
        when(personagemRep.findById(anyLong())).thenReturn(Optional.of(personagem));
        when(personagemRep.save(any(Personagem.class))).thenReturn(personagemAtualizado);

        Personagem resultado = personagemService.atualizarPersonagem(1L, personagemAtualizado);

        assertEquals(personagemAtualizado.getNome(), resultado.getNome());
        assertEquals(personagemAtualizado.getClasse(), resultado.getClasse());
        assertEquals(personagemAtualizado.getRaca(), resultado.getRaca());
        verify(personagemRep, times(1)).findById(1L);
        verify(personagemRep, times(1)).save(personagem);
    }

    @Test
    void testAtualizarAuto() {
        personagem.setAtivo(true);
        when(personagemRep.save(any(Personagem.class))).thenReturn(personagem);

        personagemService.atualizarAuto(personagem);

        assertEquals(2, personagem.getNivel());
        assertEquals(15, personagem.getAtaque());
        assertEquals(110, personagem.getVida());
        assertEquals(8, personagem.getDefesa());
        verify(personagemRep, times(1)).save(personagem);
    }

    @Test
    void testDesativarDerrota() {
        personagem.setAtivo(true);
        when(personagemRep.save(any(Personagem.class))).thenReturn(personagem);

        personagemService.desativarDerrota(personagem);

        assertEquals(false, personagem.getAtivo());
        verify(personagemRep, times(1)).save(personagem);
    }

    @Test
    void testDeletarPersonagem() {
        when(personagemRep.findById(anyLong())).thenReturn(Optional.of(personagem));

        personagemService.deletarPersonagem(1L);

        verify(personagemRep, times(1)).findById(1L);
        verify(personagemRep, times(1)).delete(personagem);
    }
}
