package com.klima.financasspring.repository;

import com.klima.financasspring.domain.Despesa;
import com.klima.financasspring.domain.Receita;
import com.klima.financasspring.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmailAndSenha(String email, String senha);

    @Query("SELECT r FROM Receita r WHERE r.usuario.id = :userId")
    List<Receita> findReceitasByUsuarioId(Long userId);

    Usuario findByEmail(String email);

    @Query("SELECT d FROM Despesa d WHERE d.usuario.id = :userId")
    List<Despesa> findDespesasByUsuarioId(Long userId);
}
