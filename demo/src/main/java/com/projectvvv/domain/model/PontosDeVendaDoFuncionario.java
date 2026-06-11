package com.projectvvv.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pontos_de_venda_do_funcionario")
public class PontosDeVendaDoFuncionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "id_funcionario",
        nullable = false
    )
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(
        name = "id_ponto_de_venda",
        nullable = false
    )
    private PontoDeVenda pontoDeVenda;

    public PontosDeVendaDoFuncionario() {
    }

    public Long getId() {
        return id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public PontoDeVenda getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(PontoDeVenda pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }
}