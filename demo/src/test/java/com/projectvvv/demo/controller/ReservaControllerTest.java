package com.projectvvv.demo.controller;

import com.projectvvv.domain.controller.ReservaController;
import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.service.ReservaService;

import com.projectvvv.domain.dto.ReservaDTO;
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
    @DisplayName("GET /api/reservas")
    void deveListarTodasAsReservas() throws Exception {

        Mockito.when(reservaService.listarTodas())
                .thenReturn(List.of(new Reserva(), new Reserva()));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/reservas/{id}")
    void deveBuscarReservaPorId() throws Exception {

        Mockito.when(reservaService.buscarPorId(1L))
                .thenReturn(new Reserva());

        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/reservas")
    void deveCriarReserva() throws Exception {

        ReservaDTO dto = new ReservaDTO();

        Mockito.when(reservaService.criar(any(ReservaDTO.class)))
                .thenReturn(new Reserva());

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PATCH /api/reservas/{id}")
    void deveAtualizarReserva() throws Exception {

        ReservaDTO dto = new ReservaDTO();

        Mockito.when(reservaService.atualizar(
                        eq(1L),
                        any(ReservaDTO.class)))
                .thenReturn(new Reserva());

        mockMvc.perform(patch("/api/reservas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/reservas/{id}")
    void deveDeletarReserva() throws Exception {

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(reservaService)
                .deletar(1L);
    }
}