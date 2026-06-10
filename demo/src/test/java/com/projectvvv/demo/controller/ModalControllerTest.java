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

import com.projectvvv.domain.controller.ModalController;
import com.projectvvv.domain.model.Modal;
import com.projectvvv.domain.service.ModalService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ModalController.class)
class ModalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ModalService modalService;

    @Test
    @DisplayName("GET /api/modais - Deve retornar lista de modais e status 200")
    void deveListarTodosOsModais() throws Exception {
        Modal modal1 = new Modal();
        Modal modal2 = new Modal();
        Mockito.when(modalService.listarTodos()).thenReturn(List.of(modal1, modal2));

        mockMvc.perform(get("/api/modais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/modais/{id} - Deve retornar um modal específico e status 200")
    void deveBuscarModalPorId() throws Exception {
        Long id = 1L;
        Modal modal = new Modal();
        Mockito.when(modalService.buscarPorId(id)).thenReturn(modal);

        mockMvc.perform(get("/api/modais/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/modais - Deve criar um modal com múltiplos parâmetros e retornar status 201")
    void deveCriarModal() throws Exception {
        Modal novoModal = new Modal();
        Modal modalCriado = new Modal();

        Mockito.when(modalService.criar(any(), any(), any(), any(), any(), any()))
                .thenReturn(modalCriado);

        mockMvc.perform(post("/api/modais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoModal)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PATCH /api/modais/{id} - Deve atualizar o modal e retornar status 200")
    void deveAtualizarModal() throws Exception {
        Long id = 1L;
        Modal dadosAtualizacao = new Modal();
        Modal modalAtualizado = new Modal();

        Mockito.when(modalService.atualizar(eq(id), any(Modal.class))).thenReturn(modalAtualizado);

        mockMvc.perform(patch("/api/modais/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dadosAtualizacao)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/modais/{id}/manutencao - Deve colocar em manutenção usando RequestParam e retornar status 200")
    void deveColocarEmManutencao() throws Exception {
        Long id = 1L;
        String descricao = "Troca de óleo preventiva";
        Modal modalEmManutencao = new Modal();

        Mockito.when(modalService.manutencao(eq(id), eq(descricao))).thenReturn(modalEmManutencao);

        mockMvc.perform(patch("/api/modais/{id}/manutencao", id)
                .param("descricao", descricao))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/modais/{id}/ativar - Deve ativar o modal e retornar status 200")
    void deveAtivarModal() throws Exception {
        Long id = 1L;
        Modal modalAtivo = new Modal();

        Mockito.when(modalService.ativar(id)).thenReturn(modalAtivo);

        mockMvc.perform(patch("/api/modais/{id}/ativar", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/modais/{id}/inativar - Deve inativar o modal e retornar status 200")
    void deveInativarModal() throws Exception {
        Long id = 1L;
        Modal modalInativo = new Modal();

        Mockito.when(modalService.inativar(id)).thenReturn(modalInativo);

        mockMvc.perform(patch("/api/modais/{id}/inativar", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/modais/{id} - Deve deletar o modal e retornar status 204")
    void deveDeletarModal() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/modais/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(modalService, Mockito.times(1)).deletar(id);
    }
}