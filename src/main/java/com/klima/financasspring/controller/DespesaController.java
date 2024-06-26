package com.klima.financasspring.controller;

import com.klima.financasspring.domain.Despesa;
import com.klima.financasspring.domain.Receita;
import com.klima.financasspring.exception.DespesaNaoEncontradaException;
import com.klima.financasspring.exception.ReceitaNaoEncontradaException;
import com.klima.financasspring.exception.SaldoInsuficienteException;
import com.klima.financasspring.exception.UsuarioNaoEncontradoException;
import com.klima.financasspring.service.DespesaService;
import com.klima.financasspring.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    DespesaService despesaService ;

    @PostMapping("/{usuarioId}")
    public ResponseEntity<?> adicionarDespesa(@PathVariable Long usuarioId, @RequestBody Despesa despesa) {
        try {
            despesaService.adicionarDespesa(usuarioId, despesa);
            return ResponseEntity.ok("Despesa adicionada com sucesso!");
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UsuarioNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao processar a solicitação");
        }
    }


     @DeleteMapping("/{usuarioId}/{despesaId}")
    public ResponseEntity<?> excluirDespesa(@PathVariable Long usuarioId, @PathVariable Long despesaId) {
        try {
            despesaService.excluirDespesa(usuarioId, despesaId);
            return ResponseEntity.ok("Despesa excluída com sucesso!");
        } catch (DespesaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Despesa não encontrada.");
        }
    }

    
    @PutMapping("/{usuarioId}/{despesaId}")
    public ResponseEntity<?> atualizarDespesa(@PathVariable Long usuarioId, @PathVariable Long despesaId, @RequestBody Receita despesaAtualizada) {
        try {
            despesaService.atualizarDespesa(usuarioId, despesaId, despesaAtualizada);
            return ResponseEntity.ok("Despesa atualizada com sucesso!");
        } catch (DespesaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Despesa não encontrada.");
        }
    }

}
