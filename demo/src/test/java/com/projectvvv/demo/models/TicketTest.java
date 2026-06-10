package com.projectvvv.demo.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.projectvvv.domain.model.Ticket;
import com.projectvvv.domain.model.TipoPassagem;

public class TicketTest {
    
    @Test
    void testTicketCreation() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setCodigoTicket("ABC123");
        ticket.setTipoPassagem(TipoPassagem.LEITO);
        ticket.setIdReserva(10L);
        ticket.setIdCliente(5L);

        assertEquals(1L, ticket.getId());
        assertEquals("ABC123", ticket.getCodigoTicket());
        assertEquals(TipoPassagem.LEITO, ticket.getTipoPassagem());
        assertEquals(10L, ticket.getIdReserva());
        assertEquals(5L, ticket.getIdCliente());
    }
}
