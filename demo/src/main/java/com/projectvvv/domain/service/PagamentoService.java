package com.projectvvv.domain.service;

import com.projectvvv.domain.model.*;
import com.projectvvv.domain.repository.PagamentoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    private final ReservaService reservaService;
    private final ModalService modalService;
    private final TicketService ticketService;
    private final ClienteService clienteService;

    public PagamentoService(
            PagamentoRepository pagamentoRepository,
            ReservaService reservaService,
            ModalService modalService,
            TicketService ticketService,
            ClienteService clienteService) {

        this.pagamentoRepository = pagamentoRepository;
        this.reservaService = reservaService;
        this.modalService = modalService;
        this.ticketService = ticketService;
        this.clienteService = clienteService;
    }

    @Transactional
    public Pagamento realizarPagamento(
            Long reservaId,
            Long clienteId,
            Pagamento pagamento) {

        Reserva reserva =
                reservaService.buscarPorId(reservaId);

        Cliente cliente =
                clienteService.buscarPorId(clienteId);

        pagamento.setStatusPagamento(
                StatusPagamento.PENDENTE);

        Float valorFinal = pagamento.getValorPago();

        /*
         * REGRA DE JUROS
         */

        if (pagamento.getTipoPagamento()
                == TipoPagamento.CARTAO_CREDITO
                &&
                pagamento.getParcelas() > 4) {

            valorFinal = valorFinal * 1.05f;
        }

        pagamento.setValorPago(valorFinal);

        /*
         * APROVA PAGAMENTO
         */

        pagamento.setStatusPagamento(
                StatusPagamento.FINALIZADO);

        Pagamento pagamentoSalvo =
                pagamentoRepository.save(pagamento);

        /*
         * CONFIRMA RESERVA
         */

        reserva.setStatusReserva(
                StatusReserva.CONCLUIDO);

        reservaService.atualizar(
                reserva.getCodigoReserva(),
                reserva);

        /*
         * ATUALIZA INVENTÁRIO DO MODAL
         */

        Modal modal =
                modalService.buscarPorId(
                        reserva.getIdModal());

        modal.setCapacidade(
                modal.getCapacidade() - 1);

        modalService.atualizar(
                modal.getId(),
                modal);

        /*
         * GERA TICKET
         */

        ticketService.criar(
                reserva,
                pagamentoSalvo,
                cliente);

        return pagamentoSalvo;
    }

    public Pagamento cancelarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado para cancelamento."));

        if (pagamento.getStatusPagamento() != StatusPagamento.FINALIZADO) {
            throw new RuntimeException("Apenas pagamentos finalizados podem ser cancelados.");
        }

        pagamento.setStatusPagamento(StatusPagamento.CANCELADO);
        return pagamentoRepository.save(pagamento);
    }
}