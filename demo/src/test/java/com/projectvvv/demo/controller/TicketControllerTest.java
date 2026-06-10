package com.projectvvv.demo.controller;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.projectvvv.domain.controller.TicketController;
import com.projectvvv.domain.model.Ticket;
import com.projectvvv.domain.service.TicketService;

import tools.jackson.databind.ObjectMapper;


@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TicketService ticketService;

    @Test
    @DisplayName("GET /api/tickets - Deve retornar lista de tickets e status 200")
    void deveListarTodosOsTickets() throws Exception {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Mockito.when(ticketService.listarTodos()).thenReturn(List.of(ticket1, ticket2));

        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/tickets/{id} - Deve retornar um ticket específico e status 200")
    void deveBuscarTicketPorId() throws Exception {
        Long id = 1L;
        Ticket ticket = new Ticket();
        Mockito.when(ticketService.buscarPorId(id)).thenReturn(ticket);

        mockMvc.perform(get("/api/tickets/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/tickets/{id} - Deve atualizar o ticket e retornar status 200")
    void deveAtualizarTicket() throws Exception {
        Long id = 1L;
        Ticket dadosAtualizacao = new Ticket();
        Ticket ticketAtualizado = new Ticket();

        Mockito.when(ticketService.atualizar(eq(id), any(Ticket.class))).thenReturn(ticketAtualizado);

        mockMvc.perform(patch("/api/tickets/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dadosAtualizacao)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/tickets/{id} - Deve deletar o ticket e retornar status 204")
    void deveDeletarTicket() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/tickets/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(ticketService, Mockito.times(1)).deletar(id);
    }
}