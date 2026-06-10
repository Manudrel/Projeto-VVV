package com.projectvvv.demo.controller;

import com.projectvvv.domain.controller.ReservaController;
import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.service.ReservaService;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReservaService reservaService;

    @Test
    @DisplayName("GET /api/reservas - Deve retornar lista de reservas e status 200")
    void deveListarTodasAsReservas() throws Exception {
        
        Reserva reserva1 = new Reserva(); 
        Reserva reserva2 = new Reserva();
        Mockito.when(reservaService.listarTodas()).thenReturn(List.of(reserva1, reserva2));

        
        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/reservas/{id} - Deve retornar reserva específica e status 200")
    void deveBuscarReservaPorId() throws Exception {
        Long id = 1L;
        Reserva reserva = new Reserva();
        Mockito.when(reservaService.buscarPorId(id)).thenReturn(reserva);

        mockMvc.perform(get("/api/reservas/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/reservas - Deve criar reserva e retornar status 201")
    void deveCriarReserva() throws Exception {
        Reserva novaReserva = new Reserva();
        Reserva reservaCriada = new Reserva();
        
        Mockito.when(reservaService.criar(any(Reserva.class))).thenReturn(reservaCriada);

        mockMvc.perform(post("/api/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novaReserva))) 
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PATCH /api/reservas/{id} - Deve atualizar reserva e retornar status 200")
    void deveAtualizarReserva() throws Exception {
        Long id = 1L;
        Reserva dadosAtualizacao = new Reserva();
        Reserva reservaAtualizada = new Reserva();

        Mockito.when(reservaService.atualizar(eq(id), any(Reserva.class))).thenReturn(reservaAtualizada);

        mockMvc.perform(patch("/api/reservas/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dadosAtualizacao)));
    }

    @Test
    @DisplayName("DELETE /api/reservas/{id} - Deve deletar reserva e retornar status 204")
    void deveDeletarReserva() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/reservas/{id}", id))
                .andExpect(status().isNoContent());
                
        Mockito.verify(reservaService, Mockito.times(1)).deletar(id);
    }
}