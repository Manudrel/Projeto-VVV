package com.projectvvv.domain.service;

import com.projectvvv.domain.model.*;
import com.projectvvv.domain.repository.PagamentoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

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

        // Regra do Juros
        
        if (pagamento.getStatusPagamento() == StatusPagamento.FINALIZADO){
            throw new RuntimeException("Pagamento já finalizado. Não é possível realizar o pagamento novamente.");
        }
        
        if (pagamento.getTipoPagamento()
                == TipoPagamento.CARTAO_CREDITO
                &&
                pagamento.getParcelas() > 4) {

            valorFinal = valorFinal * 1.05f;
        }

        pagamento.setValorPago(valorFinal);

        // Aprova o pagamento

        pagamento.setStatusPagamento(
                StatusPagamento.FINALIZADO);

        pagamento.setCliente(cliente);
        

        Pagamento pagamentoSalvo =
                pagamentoRepository.save(pagamento);

        // Confirma a reserva

        reserva.setStatusReserva(
                StatusReserva.CONCLUIDO);

        reservaService.atualizar(
                reserva.getCodigoReserva(),
                reserva);

        // Atualiza a Capacidade do Modal

        Modal modal =
                modalService.buscarPorId(
                        reserva.getIdModal());

        modal.setCapacidade(
                modal.getCapacidade() - 1);

        modalService.atualizar(
                modal.getId(),
                modal);

    
        // GERAR TICKET

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

    public Pagamento buscarPorId(Long id) {
        return pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado."));
    }

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public void deletar(Long id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new RuntimeException("Pagamento não encontrado para exclusão.");
        }
        pagamentoRepository.deleteById(id);
    }
}