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

import com.projectvvv.domain.controller.ClienteController;
import com.projectvvv.domain.model.Cliente;
import com.projectvvv.domain.service.ClienteService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService clienteService;

    @Test
    @DisplayName("GET /api/clientes - Deve retornar lista de clientes e status 200")
    void deveListarTodosOsClientes() throws Exception {
        Cliente c1 = new Cliente();
        Cliente c2 = new Cliente();
        Mockito.when(clienteService.listarTodos()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/clientes/{id} - Deve retornar um cliente específico e status 200")
    void deveBuscarClientePorId() throws Exception {
        Long id = 1L;
        Cliente cliente = new Cliente();
        Mockito.when(clienteService.buscarPorId(id)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /api/clientes - Deve salvar um novo cliente e retornar status 201")
    void deveCriarCliente() throws Exception {
        Cliente novoCliente = new Cliente();
        Cliente clienteSalvo = new Cliente();

        Mockito.when(clienteService.salvar(any(Cliente.class))).thenReturn(clienteSalvo);

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoCliente)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PATCH /api/clientes/{id} - Deve atualizar o cliente e retornar status 200")
    void deveAtualizarCliente() throws Exception {
        Long id = 1L;
        Cliente dadosAtualizacao = new Cliente();
        Cliente clienteAtualizado = new Cliente();

        Mockito.when(clienteService.atualizar(eq(id), any(Cliente.class))).thenReturn(clienteAtualizado);

        mockMvc.perform(patch("/api/clientes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dadosAtualizacao)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/clientes/{id} - Deve excluir o cliente e retornar status 204")
    void deveDeletarCliente() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/clientes/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(clienteService, Mockito.times(1)).excluir(id);
    }
}