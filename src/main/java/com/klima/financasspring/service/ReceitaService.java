package com.klima.financasspring.service;

import com.klima.financasspring.domain.Receita;
import com.klima.financasspring.domain.Usuario;
import com.klima.financasspring.dto.UsuarioLoginDTO;
import com.klima.financasspring.exception.UsuarioNaoEncontradoException;
import com.klima.financasspring.repository.ReceitaRepository;
import com.klima.financasspring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public void adicionarReceita(Long usuarioId, Receita receita) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            receita.setUsuario(usuario);
            receitaRepository.save(receita);

            if (usuario.getSaldo() == null) {
                usuario.setSaldo(BigDecimal.ZERO);
            }

            BigDecimal novoSaldo = usuario.getSaldo().add(receita.getValor());
            usuario.setSaldo(novoSaldo);

            // Salvar o usuário atualizado de volta no banco de dados
            usuarioRepository.save(usuario);
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com o ID fornecido: " + usuarioId);
        }
    }
}
