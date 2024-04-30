package com.klima.financasspring.repository;

import com.klima.financasspring.domain.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
