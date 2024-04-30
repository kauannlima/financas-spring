package com.klima.financasspring.service;

import com.klima.financasspring.domain.Despesa;
import com.klima.financasspring.domain.Receita;
import com.klima.financasspring.domain.Usuario;
import com.klima.financasspring.exception.SaldoInsuficienteException;
import com.klima.financasspring.exception.UsuarioNaoEncontradoException;
import com.klima.financasspring.repository.DespesaRepository;
import com.klima.financasspring.repository.ReceitaRepository;
import com.klima.financasspring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public void adicionarDespesa(Long usuarioId, Despesa despesa) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            despesa.setUsuario(usuario);
            despesaRepository.save(despesa);

            if (usuario.getSaldo() == null) {
                usuario.setSaldo(BigDecimal.ZERO);
            }

            BigDecimal saldoAtual = usuario.getSaldo();

            if (despesa.getValor().compareTo(saldoAtual) > 0) {
                throw new SaldoInsuficienteException("A despesa é maior do que o saldo atual");
            }
            despesa.setUsuario(usuario);
            despesaRepository.save(despesa);
            BigDecimal novoSaldo = saldoAtual.subtract(despesa.getValor());
            usuario.setSaldo(novoSaldo);

            usuarioRepository.save(usuario);

        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com o ID fornecido: " + usuarioId);
        }
    }
}
