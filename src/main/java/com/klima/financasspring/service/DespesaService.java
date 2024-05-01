package com.klima.financasspring.service;

import com.klima.financasspring.domain.Despesa;
import com.klima.financasspring.domain.Receita;
import com.klima.financasspring.domain.Usuario;
import com.klima.financasspring.exception.DespesaNaoEncontradaException;
import com.klima.financasspring.exception.ReceitaNaoEncontradaException;
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

            despesa.setUsuario(usuario);
            despesaRepository.save(despesa);
            BigDecimal novoSaldo = saldoAtual.subtract(despesa.getValor());
            usuario.setSaldo(novoSaldo);

            usuarioRepository.save(usuario);

        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com o ID fornecido: " + usuarioId);
        }
    }


    public void excluirDespesa(Long usuarioId, Long despesaId) {
       Despesa despesaExistente = despesaRepository.findByIdAndUsuarioId(despesaId, usuarioId);

        if (despesaExistente != null) {

            Usuario usuario = despesaExistente.getUsuario();
            BigDecimal valorDespesa= despesaExistente.getValor();
            BigDecimal novoSaldo = usuario.getSaldo().add(valorDespesa);
            usuario.setSaldo(novoSaldo);
           
            usuarioRepository.save(usuario);
            
            despesaRepository.delete(despesaExistente);
        } else {

            throw new DespesaNaoEncontradaException("Despesa não encontrada.");
        }
    }


    public void atualizarDespesa(Long usuarioId, Long despesaId, Receita despesaAtualizada) {

            Despesa despesaExistente = despesaRepository.findByIdAndUsuarioId(despesaId, usuarioId);
    if(despesaExistente != null){
    
        Usuario usuario = despesaExistente.getUsuario();
        BigDecimal valorReceita = despesaExistente.getValor();
    
        String descricaoAtualizada = despesaAtualizada.getDescricao();
        BigDecimal valorAtualizado = despesaAtualizada.getValor();
    
        int comparacao  = valorReceita.compareTo(valorAtualizado);
    
        if (comparacao > 0) {
           BigDecimal diferenca = valorReceita.subtract(valorAtualizado);
           BigDecimal novoSaldo = usuario.getSaldo().add(diferenca);
    usuario.setSaldo(novoSaldo);
    
        } else if (comparacao < 0){
             BigDecimal diferenca = valorAtualizado.subtract(valorReceita);
             BigDecimal novoSaldo = usuario.getSaldo().subtract(diferenca);
      usuario.setSaldo(novoSaldo);
        }
    
        despesaExistente.setDescricao(descricaoAtualizada);
        despesaExistente.setValor(valorAtualizado);
    
        despesaRepository.save(despesaExistente);
    }else{
        throw new ReceitaNaoEncontradaException("Receita não encontrada.");
    }
                
        }
    }
