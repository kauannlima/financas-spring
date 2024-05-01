package com.klima.financasspring.service;

import com.klima.financasspring.domain.Receita;
import com.klima.financasspring.domain.Usuario;
import com.klima.financasspring.dto.UsuarioLoginDTO;
import com.klima.financasspring.exception.ReceitaNaoEncontradaException;
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

            usuarioRepository.save(usuario);
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com o ID fornecido: " + usuarioId);
        }
    }


    public void atualizarReceita(Long usuarioId, Long receitaId, Receita receitaAtualizada) {

        Receita receitaExistente = receitaRepository.findByIdAndUsuarioId(receitaId, usuarioId);
if(receitaExistente != null){

    Usuario usuario = receitaExistente.getUsuario();
    BigDecimal valorReceita = receitaExistente.getValor();

    String descricaoAtualizada = receitaAtualizada.getDescricao();
    BigDecimal valorAtualizado = receitaAtualizada.getValor();

    int comparacao  = valorReceita.compareTo(valorAtualizado);

    if (comparacao > 0) {
       BigDecimal diferenca = valorReceita.subtract(valorAtualizado);
       BigDecimal novoSaldo = usuario.getSaldo().subtract(diferenca);
usuario.setSaldo(novoSaldo);

    } else if (comparacao < 0){
         BigDecimal diferenca = valorAtualizado.subtract(valorReceita);
         BigDecimal novoSaldo = usuario.getSaldo().add(diferenca);
  usuario.setSaldo(novoSaldo);
    }

    receitaExistente.setDescricao(descricaoAtualizada);
    receitaExistente.setValor(valorAtualizado);

    receitaRepository.save(receitaExistente);
}else{
    throw new ReceitaNaoEncontradaException("Receita não encontrada.");
}
            
    }

    public void excluirReceita(Long usuarioId, Long receitaId) {

        Receita receitaExistente = receitaRepository.findByIdAndUsuarioId(receitaId, usuarioId);

        if (receitaExistente != null) {

            Usuario usuario = receitaExistente.getUsuario();
            BigDecimal valorReceita = receitaExistente.getValor();
            BigDecimal novoSaldo = usuario.getSaldo().subtract(valorReceita);
            usuario.setSaldo(novoSaldo);
           
            usuarioRepository.save(usuario);
            
            receitaRepository.delete(receitaExistente);
        } else {

            throw new ReceitaNaoEncontradaException("Receita não encontrada.");
        }
    }
    

}