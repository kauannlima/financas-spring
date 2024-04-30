package com.klima.financasspring.controller;

import com.klima.financasspring.domain.Receita;
import com.klima.financasspring.domain.Usuario;
import com.klima.financasspring.dto.UsuarioDetailDTO;
import com.klima.financasspring.dto.UsuarioIdDTO;
import com.klima.financasspring.dto.UsuarioLoginDTO;
import com.klima.financasspring.dto.UsuarioRequestDTO;
import com.klima.financasspring.exception.EmailDuplicadoException;
import com.klima.financasspring.exception.EmailNaoEncontradoException;
import com.klima.financasspring.exception.SenhaIncorretaException;
import com.klima.financasspring.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioRequestDTO body, UriComponentsBuilder uriComp) {
        try {
            UsuarioIdDTO usuarioIdDTO = this.usuarioService.createUsuario(body);

            var uri = uriComp.path("/usuarios/{id}").buildAndExpand(usuarioIdDTO.Id()).toUri();

            return ResponseEntity.created(uri).body(usuarioIdDTO);
        } catch (EmailDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> fazerLogin(@RequestBody UsuarioLoginDTO loginRequest) {
        try {
            Usuario usuario = usuarioService.fazerLogin(loginRequest.email(), loginRequest.senha());
            return ResponseEntity.ok("Login bem-sucedido!");
              } catch (EmailNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado.");
        } catch (SenhaIncorretaException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao fazer login.");
        }
    }




    @GetMapping("/{usuarioId}/receitas")
    public ResponseEntity<List<Receita>> getReceitasByUsuarioId(@PathVariable Long usuarioId) {
        List<Receita> receitas = usuarioService.findReceitasByUsuarioId(usuarioId);
        return ResponseEntity.ok(receitas);
    }
}
