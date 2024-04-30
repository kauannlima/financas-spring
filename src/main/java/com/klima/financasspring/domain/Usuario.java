package com.klima.financasspring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    private BigDecimal saldo = BigDecimal.ZERO;

    @OneToMany(mappedBy = "usuario")
    private List<Receita> receitas;

    @OneToMany(mappedBy = "usuario")
    private List<Despesa> despesas;
}
