package com.projectvvv.demo.models;

import org.junit.jupiter.api.Test;

import com.projectvvv.domain.model.Pagamento;
import com.projectvvv.domain.model.StatusPagamento;
import com.projectvvv.domain.model.TipoPagamento;

public class PagamentoTest {
    
    @Test
    void PagamentoConstructorTest(){
        long id = 1L;
        TipoPagamento tipoPagamento = TipoPagamento.CARTAO_CREDITO;
        int parcelas = 3;
        StatusPagamento statusPagamento = StatusPagamento.FINALIZADO;
        float valorPago = 150.75f;
        long idTicket = 2L;
        long idCliente = 3L;

        Pagamento pagamento = new Pagamento(id, tipoPagamento, parcelas, statusPagamento, valorPago, idTicket, idCliente);

        assert pagamento.getId().equals(id);
        assert pagamento.getTipoPagamento() == tipoPagamento;
        assert pagamento.getParcelas().equals(parcelas);
        assert pagamento.getStatusPagamento() == statusPagamento;
        assert pagamento.getValorPago().equals(valorPago);
        assert pagamento.getIdTicket().equals(idTicket);
        assert pagamento.getIdCliente().equals(idCliente);
   
    }
}
