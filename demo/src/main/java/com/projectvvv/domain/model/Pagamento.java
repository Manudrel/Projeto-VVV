package com.projectvvv.domain.model;

import jakarta.persistence.*;


@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    @Column(nullable = false)
    private Integer parcelas;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @Column(nullable = false, length = 100)
    private Float valorPago;

    @Column(nullable = false, length = 100)
    private Long idTicket;

    @Column(nullable = false, length = 100)
    private Long idCliente;

    // Construtores
    private Pagamento() {}

    public Pagamento(Long id, TipoPagamento tipoPagamento, Integer parcelas, StatusPagamento statusPagamento, Float valorPago, Long idTicket, Long idCliente) {
        this.id = id;
        this.tipoPagamento = tipoPagamento;
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

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
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