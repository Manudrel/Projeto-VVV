package com.projectvvv.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.projectvvv.domain.model.Pagamento;
import com.projectvvv.domain.model.StatusPagamento;
import com.projectvvv.domain.model.TipoPagamento;
import com.projectvvv.domain.repository.PagamentoRepository;

@DataJpaTest
class PagamentoRepositoryTest {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Test
    @DisplayName("Persistência - Deve salvar um pagamento com sucesso e gerar ID")
    void devePersistirPagamentoComSucesso() {
        Pagamento pagamento = new Pagamento();
        pagamento.setValorPago(150.0f);
        pagamento.setTipoPagamento(TipoPagamento.PIX);
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);

        assertNotNull(pagamentoSalvo.getId(), "O ID do pagamento não deveria ser nulo após salvar");
        assertEquals(150.0f, pagamentoSalvo.getValorPago());
        assertEquals(TipoPagamento.PIX, pagamentoSalvo.getTipoPagamento());
    }

    @Test
    @DisplayName("Consulta - Deve recuperar um pagamento salvo por ID")
    void deveBuscarPagamentoPorId() {
        Pagamento pagamento = new Pagamento();
        pagamento.setValorPago(250.0f);
        pagamento.setStatusPagamento(StatusPagamento.FINALIZADO);
        Pagamento salvo = pagamentoRepository.save(pagamento);

        Optional<Pagamento> encontrado = pagamentoRepository.findById(salvo.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(salvo.getId(), encontrado.get().getId());
        assertEquals(StatusPagamento.FINALIZADO, encontrado.get().getStatusPagamento());
    }
}