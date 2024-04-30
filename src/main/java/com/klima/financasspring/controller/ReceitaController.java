package com.klima.financasspring.controller;

import com.klima.financasspring.domain.Receita;
import com.klima.financasspring.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired ReceitaService receitaService;

    @PostMapping("/{usuarioId}")
    public ResponseEntity<?> adicionarReceita(@PathVariable Long usuarioId, @RequestBody Receita receita) {
        receitaService.adicionarReceita(usuarioId, receita);
        return ResponseEntity.ok("Receita adicionada com sucesso!");
    }
}
