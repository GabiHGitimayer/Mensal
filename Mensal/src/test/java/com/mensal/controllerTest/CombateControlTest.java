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

import com.mensal.controller.CombateControl;
import com.mensal.entity.Combate;
import com.mensal.entity.NPC;
import com.mensal.entity.Personagem;
import com.mensal.service.CombateService;

class CombateControlTest {

    @InjectMocks
    private CombateControl combateControl;

    @Mock
    private CombateService combateService;

    private Personagem atacante;
    private NPC defensor;
    private Combate combate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        atacante = new Personagem(1L, "Val", "Ladino", "Elfo", 10L, 100L, 20L, 20L, 10L, null, null, null);
        defensor = new NPC(2L, "Orc", 20L, 20L, 20L, 20L, null);
        combate = new Combate(1L, atacante, defensor, "Vitória");
    }

    @Test
    void testIniciarCombate() { //testes de integração
        when(combateService.iniciarCombate(1L, 2L)).thenReturn(combate);

        Combate request = new Combate();
        request.setAtacante(atacante);
        request.setDefensor(defensor);

        ResponseEntity<Combate> response = combateControl.iniciarCombate(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(combate, response.getBody());
        verify(combateService, times(1)).iniciarCombate(1L, 2L);
    }

    @Test
    void testObterCombatesPorPersonagem() {
        List<Combate> combates = new ArrayList<>();
        combates.add(combate);
        when(combateService.obterCombatesPorPersonagem(any(Personagem.class))).thenReturn(combates);

        ResponseEntity<List<Combate>> response = combateControl.obterCombatesPorPersonagem(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(combate, response.getBody().get(0));
        verify(combateService, times(1)).obterCombatesPorPersonagem(any(Personagem.class));
    }

    @Test
    void testObterCombatesPorNpc() {
        List<Combate> combates = new ArrayList<>();
        combates.add(combate);
        when(combateService.obterCombatesPorNpc(any(NPC.class))).thenReturn(combates);

        ResponseEntity<List<Combate>> response = combateControl.obterCombatesPorNpc(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(combate, response.getBody().get(0));
        verify(combateService, times(1)).obterCombatesPorNpc(any(NPC.class));
    }
}
