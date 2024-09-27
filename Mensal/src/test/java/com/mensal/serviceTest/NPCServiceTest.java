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

import com.mensal.entity.NPC;
import com.mensal.repository.NPCRep;
import com.mensal.service.NPCService;

class NPCServiceTest {

    @InjectMocks
    private NPCService npcService;

    @Mock
    private NPCRep npcRep;

    private NPC npc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        npc = new NPC(1L, "Orc", 120L, 15L, 50L, 10L, new ArrayList<>());
    }

    @Test
    void testSaveNPC() {
        when(npcRep.save(any(NPC.class))).thenReturn(npc);

        NPC resultado = npcService.saveNPC(npc);

        assertEquals(npc, resultado);
        verify(npcRep, times(1)).save(npc);
    }

    @Test
    void testFindById() {
        when(npcRep.findById(anyLong())).thenReturn(Optional.of(npc));

        NPC resultado = npcService.findById(1L);

        assertEquals(npc, resultado);
        verify(npcRep, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<NPC> npcs = new ArrayList<>();
        npcs.add(npc);
        when(npcRep.findAll()).thenReturn(npcs);

        List<NPC> resultado = npcService.findAll();

        assertEquals(npcs, resultado);
        verify(npcRep, times(1)).findAll();
    }

    @Test
    void testAtualizarNPC() {
        NPC npcAtualizado = new NPC(1L, "Goblin", 80L, 20L, 40L, 15L, new ArrayList<>());        
        when(npcRep.findById(anyLong())).thenReturn(Optional.of(npc));
        when(npcRep.save(any(NPC.class))).thenReturn(npcAtualizado);        
        NPC resultado = npcService.atualizarNPC(1L, npcAtualizado);        
        assertEquals(npcAtualizado.getNome(), resultado.getNome());
        assertEquals(npcAtualizado.getVida(), resultado.getVida());
        assertEquals(npcAtualizado.getAtaque(), resultado.getAtaque());
        assertEquals(npcAtualizado.getDefesa(), resultado.getDefesa());
        assertEquals(npcAtualizado.getMana(), resultado.getMana());       
        verify(npcRep, times(1)).findById(1L);
        verify(npcRep, times(1)).save(npc);
    }

    @Test
    void testDeletarNPC() {
        when(npcRep.findById(1L)).thenReturn(Optional.of(npc));

        npcService.deletarNPC(1L);

        verify(npcRep, times(1)).delete(npc);
    }
}

