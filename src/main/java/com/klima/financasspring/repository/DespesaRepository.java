package com.klima.financasspring.repository;

import com.klima.financasspring.domain.Despesa;
import com.klima.financasspring.domain.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
