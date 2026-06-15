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

import com.projectvvv.domain.controller.CidadeController;
import com.projectvvv.domain.model.Cidade;
import com.projectvvv.domain.service.CidadeService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(CidadeController.class)
class CidadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CidadeService cidadeService;

    @Test
    @DisplayName("POST /api/cidade - Deve salvar uma cidade e retornar status 200")
    void deveCriarCidadeComSucesso() throws Exception {
        Cidade novaCidade = new Cidade();
        novaCidade.setCidade("Rio de Janeiro");

        Cidade cidadeSalva = new Cidade();
        cidadeSalva.setId(1L);
        cidadeSalva.setCidade("Rio de Janeiro");

        Mockito.when(cidadeService.salvar(any(Cidade.class))).thenReturn(cidadeSalva);

        mockMvc.perform(post("/api/cidade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novaCidade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cidade").value("Rio de Janeiro"));
    }

    @Test
    @DisplayName("GET /api/cidade - Deve retornar a lista de todas as cidades")
    void deveListarTodasAsCidades() throws Exception {
        Cidade c1 = new Cidade();
        Cidade c2 = new Cidade();
        Mockito.when(cidadeService.listarTodas()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/cidade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/cidade/{id} - Deve retornar uma cidade por ID")
    void deveBuscarCidadePorId() throws Exception {
        Long id = 1L;
        Cidade cidade = new Cidade();
        cidade.setId(id);
        cidade.setCidade("Salvador");

        Mockito.when(cidadeService.buscarPorId(id)).thenReturn(cidade);

        mockMvc.perform(get("/api/cidade/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidade").value("Salvador"));
    }

    @Test
    @DisplayName("PUT /api/cidade/{id} - Deve atualizar os dados da cidade")
    void deveAtualizarCidadeComSucesso() throws Exception {
        Long id = 1L;
        Cidade dadosAtualizacao = new Cidade();
        dadosAtualizacao.setCidade("São Paulo");

        Cidade cidadeAtualizada = new Cidade();
        cidadeAtualizada.setId(id);
        cidadeAtualizada.setCidade("São Paulo");

        Mockito.when(cidadeService.atualizar(eq(id), any(Cidade.class))).thenReturn(cidadeAtualizada);

        mockMvc.perform(put("/api/cidade/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dadosAtualizacao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cidade").value("São Paulo"));
    }

    @Test
    @DisplayName("DELETE /api/cidade/{id} - Deve invocar a exclusão do service")
    void deveDeletarCidadeComSucesso() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/cidade/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(cidadeService, Mockito.times(1)).deletar(id);
    }
}