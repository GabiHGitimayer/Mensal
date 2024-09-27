package com.mensal.serviceTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mensal.entity.Combate;
import com.mensal.entity.NPC;
import com.mensal.entity.Personagem;
import com.mensal.repository.CombateRep;
import com.mensal.repository.NPCRep;
import com.mensal.repository.PersonagemRep;
import com.mensal.service.CombateService;

class CombateServiceTest {

    @InjectMocks
    private CombateService combateService;

    @Mock
    private CombateRep combateRepository;

    @Mock
    private PersonagemRep personagemRepository;

    @Mock
    private NPCRep npcRepository;

    private Personagem atacante;
    private NPC defensor;
    private Combate combate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        atacante = new Personagem();
        defensor = new NPC(2L, "Orc", 50L, 15L, 20L, 5L, null);
        combate = new Combate(1L, atacante, defensor, "Vitória");
    }

    @Test
    public void testIniciarCombate_Vitoria() { //testes unitarios

       
        Personagem atacante = new Personagem(1L, "Link", "Paladino", "Elfo", 5l, 100L, 150L, 30L, 5L, null, null, null);
        NPC defensor = new NPC(2L, "Defensor", 50L, 20L, 10L, 30L, null);
        
        Mockito.when(personagemRepository.findById(1L)).thenReturn(Optional.of(atacante));
        Mockito.when(npcRepository.findById(2L)).thenReturn(Optional.of(defensor));
        
        Combate combate = combateService.iniciarCombate(1L, 2L);
        assertEquals("Vitória", combate.getResultado());
    }

    @Test 
    public void testIniciarCombate_Derrota() {

        
        Personagem atacante = new Personagem(1L, "Vox", "Ladino", "Drow", 5l, 30L, 50L, 10L, 5L, null, null, null);
        NPC defensor = new NPC(2L, "Defensor", 100L, 60L, 20L, 30L, null);
        
        Mockito.when(personagemRepository.findById(1L)).thenReturn(Optional.of(atacante));
        Mockito.when(npcRepository.findById(2L)).thenReturn(Optional.of(defensor));
        
        Combate combate = combateService.iniciarCombate(1L, 2L);
        assertEquals("Derrota", combate.getResultado());
    }



    @Test
    void testObterCombatesPorPersonagem() {
        List<Combate> combates = new ArrayList<>();
        combates.add(combate);
        when(combateRepository.findByAtacante(any(Personagem.class))).thenReturn(combates);

        List<Combate> resultado = combateService.obterCombatesPorPersonagem(atacante);

        assertEquals(1, resultado.size());
        assertEquals(combate, resultado.get(0));
        verify(combateRepository, times(1)).findByAtacante(any(Personagem.class));
    }

    @Test
    void testObterCombatesPorNpc() {
        List<Combate> combates = new ArrayList<>();
        combates.add(combate);
        when(combateRepository.findByDefensor(any(NPC.class))).thenReturn(combates);

        List<Combate> resultado = combateService.obterCombatesPorNpc(defensor);

        assertEquals(1, resultado.size());
        assertEquals(combate, resultado.get(0));
        verify(combateRepository, times(1)).findByDefensor(any(NPC.class));
    }
}
