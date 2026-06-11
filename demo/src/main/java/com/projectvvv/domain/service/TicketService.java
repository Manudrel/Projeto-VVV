package com.projectvvv.domain.service;

import com.projectvvv.domain.model.Pagamento;
import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.Ticket;
import com.projectvvv.domain.model.Cliente;
import com.projectvvv.domain.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket buscarPorId(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado."));
    }

    public Ticket criar(Reserva reserva, Pagamento pagamento, Cliente cliente) {
        Ticket ticket = new Ticket();

        ticket.setCodigoTicket(
            "TK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()
        );
        ticket.setTipoPassagem(
                reserva.getTipoPassagem()
        );
        ticket.setIdCliente(cliente.getId());
        ticket.setIdReserva(reserva.getCodigoReserva());

        return ticketRepository.save(ticket);
    }

    public void deletar(Long id){
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Ticket não encontrado para exclusão.");
        }
        ticketRepository.deleteById(id);
    }

    public Ticket atualizar(Long id, Ticket ticketAtualizado) {
        Ticket ticketExistente = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado para atualização."));

        if (!ticketExistente.getId().equals(ticketAtualizado.getId())) {
            throw new RuntimeException("ID do ticket atualizado não corresponde ao ID do ticket existente.");
        }

        if (ticketAtualizado.getCodigoTicket() != null ){
            ticketExistente.setCodigoTicket(ticketAtualizado.getCodigoTicket());
        }
        if (ticketAtualizado.getTipoPassagem() != null) {
            ticketExistente.setTipoPassagem(ticketAtualizado.getTipoPassagem());
        }
        if (ticketAtualizado.getIdCliente() != null) {
            ticketExistente.setIdCliente(ticketAtualizado.getIdCliente());
        }
        if (ticketAtualizado.getIdReserva() != null) {
            ticketExistente.setIdReserva(ticketAtualizado.getIdReserva());
        }

        return ticketRepository.save(ticketExistente);
    }

    public List<Ticket> listarTodos() {
        return ticketRepository.findAll();
    }
}