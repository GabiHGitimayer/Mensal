package com.mensal.controllerTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

import com.mensal.controller.NPCControl;
import com.mensal.entity.NPC;
import com.mensal.service.NPCService;

class NPCControlTest {

  @InjectMocks
    private NPCControl npcControl;

    @Mock
    private NPCService npcService;

    private NPC npc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        npc = new NPC(1L, "Orc", 100L, 15L, 50L, 10L, new ArrayList<>());
    }

    @Test
    void testSaveNPC() {
        when(npcService.saveNPC(any(NPC.class))).thenReturn(npc);

        ResponseEntity<NPC> response = npcControl.saveNPC(npc);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(npc, response.getBody());
        verify(npcService, times(1)).saveNPC(npc);
    }

    @Test
    void testFindById() {
        when(npcService.findById(1L)).thenReturn(npc);

        ResponseEntity<NPC> response = npcControl.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(npc, response.getBody());
        verify(npcService, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<NPC> npcs = new ArrayList<>();
        npcs.add(npc);
        when(npcService.findAll()).thenReturn(npcs);

        List<NPC> resultado = npcControl.findAll();

        assertEquals(npcs, resultado);
        verify(npcService, times(1)).findAll();
    }

    @Test
    void testAtualizarNPC() {
        NPC npcAtualizado = new NPC(1L, "Goblin", 80L, 20L, 30L, 5L, new ArrayList<>());
        when(npcService.atualizarNPC(1L, npcAtualizado)).thenReturn(npcAtualizado);

        ResponseEntity<NPC> response = npcControl.atualizarNPC(1L, npcAtualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(npcAtualizado, response.getBody());
        verify(npcService, times(1)).atualizarNPC(1L, npcAtualizado);
    }

    @Test
    void testDeletarNPC() {
        ResponseEntity<String> response = npcControl.deletarNPC(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(npcService, times(1)).deletarNPC(1L); 
    }
}
