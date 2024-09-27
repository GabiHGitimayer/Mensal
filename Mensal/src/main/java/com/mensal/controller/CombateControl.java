package com.mensal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mensal.entity.Combate;
import com.mensal.entity.NPC;
import com.mensal.entity.Personagem;
import com.mensal.service.CombateService;

@RestController
@RequestMapping("/api/combates")
public class CombateControl {
    @Autowired
    private CombateService combateService;

    @PostMapping(value = "/iniciar", consumes = "application/json")
    public ResponseEntity<Combate> iniciarCombate(@RequestBody Combate request) {
        try {
            Personagem atacante = request.getAtacante();
            NPC defensor = request.getDefensor();

            Combate combate = combateService.iniciarCombate(atacante.getId(), defensor.getId());
            return ResponseEntity.ok(combate);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/personagem/{personagemId}")
    public ResponseEntity<List<Combate>> obterCombatesPorPersonagem(
        @PathVariable Long personagemId
    ) {
        Personagem personagem = new Personagem();
        personagem.setId(personagemId);
        return ResponseEntity.ok(combateService.obterCombatesPorPersonagem(personagem));
    }

    @GetMapping("/npc/{npcId}")
    public ResponseEntity<List<Combate>> obterCombatesPorNpc(
        @PathVariable Long npcId
    ) {
        NPC npc = new NPC();
        npc.setId(npcId);
        return ResponseEntity.ok(combateService.obterCombatesPorNpc(npc));
    }
}
