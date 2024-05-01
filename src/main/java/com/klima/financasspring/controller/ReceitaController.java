package com.klima.financasspring.controller;

import com.klima.financasspring.domain.Receita;
import com.klima.financasspring.exception.ReceitaNaoEncontradaException;
import com.klima.financasspring.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PutMapping("/{usuarioId}/{receitaId}")
    public ResponseEntity<?> atualizarReceita(@PathVariable Long usuarioId, @PathVariable Long receitaId, @RequestBody Receita receitaAtualizada) {
        try {
            receitaService.atualizarReceita(usuarioId, receitaId, receitaAtualizada);
            return ResponseEntity.ok("Receita atualizada com sucesso!");
        } catch (ReceitaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receita não encontrada.");
        }
    }

    @DeleteMapping("/{usuarioId}/{receitaId}")
    public ResponseEntity<?> excluirReceita(@PathVariable Long usuarioId, @PathVariable Long receitaId) {
        try {
            receitaService.excluirReceita(usuarioId, receitaId);
            return ResponseEntity.ok("Receita excluída com sucesso!");
        } catch (ReceitaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receita não encontrada.");
        }
    }

}
