package com.projectvvv.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.projectvvv.domain.controller.RotaDaReservaController;
import com.projectvvv.domain.dto.RotaDaReservaDTO;
import com.projectvvv.domain.model.RotaDaReserva;
import com.projectvvv.domain.service.RotaDaReservaService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(RotaDaReservaController.class)
class RotaDaReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RotaDaReservaService rotaDaReservaService;

    @Test
    @DisplayName("POST /api/rota-da-reserva - Deve vincular uma rota a uma reserva com base no DTO")
    void deveCriarRotaDaReservaComSucesso() throws Exception {
        RotaDaReservaDTO dto = new RotaDaReservaDTO();

        RotaDaReserva retornoSalvo = new RotaDaReserva();
        retornoSalvo.setId(1L);

        Mockito.when(rotaDaReservaService.salvar(any(RotaDaReservaDTO.class))).thenReturn(retornoSalvo);

        mockMvc.perform(post("/api/rota-da-reserva")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/rota-da-reserva - Deve listar todos os vínculos de rotas e reservas")
    void deveListarTodasAsRotasDaReserva() throws Exception {
        RotaDaReserva r1 = new RotaDaReserva();
        RotaDaReserva r2 = new RotaDaReserva();
        Mockito.when(rotaDaReservaService.listarTodas()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/api/rota-da-reserva"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/rota-da-reserva/{id} - Deve encontrar um vínculo específico pelo seu ID")
    void deveBuscarRotaDaReservaPorId() throws Exception {
        Long id = 1L;
        RotaDaReserva registro = new RotaDaReserva();
        registro.setId(id);

        Mockito.when(rotaDaReservaService.buscarPorId(id)).thenReturn(registro);

        mockMvc.perform(get("/api/rota-da-reserva/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/rota-da-reserva/reserva/{reservaId} - Deve buscar todas as rotas vinculadas a uma Reserva específica")
    void deveBuscarRotasPorIdDaReserva() throws Exception {
        Long reservaId = 10L;
        RotaDaReserva vinculo = new RotaDaReserva();
        
        Mockito.when(rotaDaReservaService.buscarPorReserva(reservaId)).thenReturn(List.of(vinculo));

        mockMvc.perform(get("/api/rota-da-reserva/reserva/{reservaId}", reservaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("PUT /api/rota-da-reserva/{id} - Deve atualizar o vínculo da rota a partir do ID e do DTO")
    void deveAtualizarRotaDaReservaComSucesso() throws Exception {
        Long id = 1L;
        RotaDaReservaDTO dto = new RotaDaReservaDTO();
        
        RotaDaReserva atualizada = new RotaDaReserva();
        atualizada.setId(id);

        Mockito.when(rotaDaReservaService.atualizar(eq(id), any(RotaDaReservaDTO.class))).thenReturn(atualizada);

        mockMvc.perform(put("/api/rota-da-reserva/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("DELETE /api/rota-da-reserva/{id} - Deve deletar o vínculo e retornar status 200")
    void deveDeletarRotaDaReservaComSucesso() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/rota-da-reserva/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(rotaDaReservaService, Mockito.times(1)).deletar(id);
    }
}