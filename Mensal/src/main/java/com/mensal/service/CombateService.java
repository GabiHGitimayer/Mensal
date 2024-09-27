package com.mensal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mensal.entity.Combate;
import com.mensal.entity.NPC;
import com.mensal.entity.Personagem;
import com.mensal.repository.CombateRep;
import com.mensal.repository.NPCRep;
import com.mensal.repository.PersonagemRep;

@Service
public class CombateService {

    @Autowired
    private CombateRep combateRepository;

    @Autowired
    private PersonagemRep personagemRepository;

    @Autowired
    private NPCRep npcRepository;
    
    @Autowired
    private PersonagemService personagemService;

    @Transactional
    public Combate iniciarCombate(Long atacanteId, Long defensorId) {

        Personagem atacante = personagemRepository.findById(atacanteId)
                .orElseThrow(() -> new RuntimeException("Personagem atacante não encontrado"));

        NPC defensor = npcRepository.findById(defensorId)
                .orElseThrow(() -> new RuntimeException("NPC defensor não encontrado"));

        System.out.println("Iniciando combate entre: " + atacante.getNome() + " e " + defensor.getNome());
        System.out.println("Atacante - Ataque: " + atacante.getAtaque() + ", Vida: " + atacante.getVida());
        System.out.println("Defensor - Defesa: " + defensor.getDefesa() + ", Vida: " + defensor.getVida());
        

        Combate combate = new Combate();
        combate.setAtacante(atacante);
        combate.setDefensor(defensor);

        long danoAtacante = atacante.getAtaque() - defensor.getDefesa();
        System.out.println("Dano do atacante ao defensor: " + danoAtacante);

        if (danoAtacante > 0) {
            defensor.setVida(defensor.getVida() - danoAtacante);
            System.out.println("Vida do defensor após dano: " + defensor.getVida());
        }

        long danoDefensor = defensor.getAtaque() - atacante.getDefesa();
        System.out.println("Dano do defensor ao atacante: " + danoDefensor);

        if (danoDefensor > 0) {
            atacante.setVida(atacante.getVida() - danoDefensor);
            System.out.println("Vida do atacante após dano: " + atacante.getVida());
        }

        String resultado = null;
        if (defensor.getVida() <= 0) {
            resultado = "Vitória";
            System.out.println("Resultado: Vitória do atacante");

            personagemRepository.save(atacante);
            combateRepository.save(combate);
            
        } else if (atacante.getVida() <= 0) {
            resultado = "Derrota";
            System.out.println("Resultado: Derrota do atacante");

            personagemRepository.save(atacante);
            combateRepository.save(combate);
        } 

        combate.setResultado(resultado);



        return combate;
    }
    
    public void finalizarCombate(Personagem personagem, boolean venceu) {
        if (venceu) {
            personagemService.atualizarAuto(personagem);
        } else {
            personagemService.desativarDerrota(personagem);
        }
    }

    public List<Combate> obterCombatesPorPersonagem(Personagem personagem) {
        return combateRepository.findByAtacante(personagem);
    }

    public List<Combate> obterCombatesPorNpc(NPC npc) {
        return combateRepository.findByDefensor(npc);
    }
}
