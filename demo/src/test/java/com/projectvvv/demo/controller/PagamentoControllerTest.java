package com.projectvvv.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.projectvvv.domain.controller.PagamentoController;
import com.projectvvv.domain.model.Pagamento;
import com.projectvvv.domain.service.PagamentoService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PagamentoService pagamentoService;

    @Test
    @DisplayName("POST /api/pagamentos/... - Deve realizar pagamento com múltiplas Path Variables e retornar 201")
    void deveRealizarPagamentoComSucesso() throws Exception {
        Long reservaId = 10L;
        Long clienteId = 20L;
        Pagamento dadosPagamento = new Pagamento();
        Pagamento pagamentoRealizado = new Pagamento();

        Mockito.when(pagamentoService.realizarPagamento(eq(reservaId), eq(clienteId), any(Pagamento.class)))
                .thenReturn(pagamentoRealizado);

        mockMvc.perform(post("/api/pagamentos/reserva/{reservaId}/cliente/{clienteId}", reservaId, clienteId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dadosPagamento)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/pagamentos/{id} - Deve retornar um pagamento específico e status 200")
    void deveBuscarPagamentoPorId() throws Exception {
        Long id = 1L;
        Pagamento pagamento = new Pagamento();
        Mockito.when(pagamentoService.buscarPorId(id)).thenReturn(pagamento);

        mockMvc.perform(get("/api/pagamentos/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/pagamentos - Deve retornar lista de pagamentos e status 200")
    void deveListarTodosOsPagamentos() throws Exception {
        Mockito.when(pagamentoService.listarTodos()).thenReturn(List.of(new Pagamento(), new Pagamento()));

        mockMvc.perform(get("/api/pagamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("DELETE /api/pagamentos/{id} - Deve deletar o pagamento e retornar status 204")
    void deveDeletarPagamento() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/pagamentos/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(pagamentoService, Mockito.times(1)).deletar(id);
    }

    @Test
    @DisplayName("PATCH /api/pagamentos/{id}/cancelar - Deve cancelar o pagamento e retornar status 204")
    void deveCancelarPagamento() throws Exception {
        Long id = 1L;

        mockMvc.perform(patch("/api/pagamentos/{id}/cancelar", id))
                .andExpect(status().isNoContent());

        Mockito.verify(pagamentoService, Mockito.times(1)).cancelarPagamento(id);
    }
}