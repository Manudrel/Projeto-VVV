package com.projectvvv.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Criar ENUM
    @Column(nullable = false)
    private String tipoPagamentoString;

    @Column(nullable = false)
    private Integer parcelas;

    // Criar ENUM
    @Column(nullable = false)
    private String statusPagamento;

    @Column(nullable = false, length = 100)
    private Float valorPago;

    @Column(nullable = false, length = 100)
    private Long idTicket;

    @Column(nullable = false, length = 100)
    private Long idCliente;

    // Construtores
    private Pagamento() {}

    public Pagamento(Long id, String tipoPagamentoString, Integer parcelas, String statusPagamento, Float valorPago, Long idTicket, Long idCliente) {
        this.id = id;
        this.tipoPagamentoString = tipoPagamentoString;
        this.parcelas = parcelas;
        this.statusPagamento = statusPagamento;
        this.valorPago = valorPago;
        this.idTicket = idTicket;
        this.idCliente = idCliente;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoPagamentoString() {
        return tipoPagamentoString;
    }

    public void setTipoPagamentoString(String tipoPagamentoString) {
        this.tipoPagamentoString = tipoPagamentoString;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Float getValorPago() {
        return valorPago;
    }

    public void setValorPago(Float valorPago) {
        this.valorPago = valorPago;
    }

    public Long getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Long idTicket) {
        this.idTicket = idTicket;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
}