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

import com.projectvvv.domain.dto.RotaDTO;
import com.projectvvv.domain.model.Rota;
import com.projectvvv.domain.controller.RotaController;
import com.projectvvv.domain.service.RotaService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(RotaController.class)
class RotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RotaService rotaService;

    @Test
    @DisplayName("POST /api/rota - Deve criar uma nova rota com base no DTO")
    void deveCriarRotaComSucesso() throws Exception {
        RotaDTO dto = new RotaDTO();
        // preencha campos do DTO se necessário (ex: dto.setOrigem("Rio"))

        Rota rotaSalva = new Rota();
        rotaSalva.setId(1L);

        Mockito.when(rotaService.salvar(any(RotaDTO.class))).thenReturn(rotaSalva);

        mockMvc.perform(post("/api/rota")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/rota - Deve retornar a lista de todas as rotas cadastradas")
    void deveListarTodasAsRotas() throws Exception {
        Rota r1 = new Rota();
        Rota r2 = new Rota();
        Mockito.when(rotaService.listarTodas()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/api/rota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/rota/{id} - Deve encontrar e retornar uma rota pelo seu ID")
    void deveBuscarRotaPorId() throws Exception {
        Long id = 1L;
        Rota rota = new Rota();
        rota.setId(id);

        Mockito.when(rotaService.buscarPorId(id)).thenReturn(rota);

        mockMvc.perform(get("/api/rota/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("PUT /api/rota/{id} - Deve atualizar os dados da rota a partir de um ID e um DTO")
    void deveAtualizarRotaComSucesso() throws Exception {
        Long id = 1L;
        RotaDTO dto = new RotaDTO();
        
        Rota rotaAtualizada = new Rota();
        rotaAtualizada.setId(id);

        Mockito.when(rotaService.atualizar(eq(id), any(RotaDTO.class))).thenReturn(rotaAtualizada);

        mockMvc.perform(put("/api/rota/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("DELETE /api/rota/{id} - Deve invocar a remoção da rota no service")
    void deveDeletarRotaComSucesso() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/rota/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(rotaService, Mockito.times(1)).deletar(id);
    }
}