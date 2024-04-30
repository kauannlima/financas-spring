package com.klima.financasspring.service;

import com.klima.financasspring.domain.Receita;
import com.klima.financasspring.domain.Usuario;
import com.klima.financasspring.dto.UsuarioIdDTO;
import com.klima.financasspring.dto.UsuarioRequestDTO;
import com.klima.financasspring.exception.EmailDuplicadoException;
import com.klima.financasspring.exception.EmailNaoEncontradoException;
import com.klima.financasspring.exception.SenhaIncorretaException;
import com.klima.financasspring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioIdDTO createUsuario(UsuarioRequestDTO body) {
        Usuario newUsuario = new Usuario();
        newUsuario.setNome(body.nome());
        newUsuario.setEmail(body.email());
        newUsuario.setSenha(body.senha());

        try {
            this.usuarioRepository.save(newUsuario);
        } catch (DataIntegrityViolationException e) {
            throw new EmailDuplicadoException("Já existe um usuário com o mesmo e-mail.");
        }

        return new UsuarioIdDTO(newUsuario.getId());
    }


    public Usuario fazerLogin(String email, String senha) {

        Usuario logarUsuario = usuarioRepository.findByEmail(email);
        if (logarUsuario == null){
            throw new EmailNaoEncontradoException("Email não encontrado.");
        }

        if (!logarUsuario.getSenha().equals(senha)) {
            throw new SenhaIncorretaException("Senha incorreta.");
        }
        return logarUsuario;
    }

    public List<Receita> findReceitasByUsuarioId(Long id) {
        return usuarioRepository.findReceitasByUsuarioId(id);
    }
}
