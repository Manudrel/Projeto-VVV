package com.projectvvv.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.projectvvv.domain.controller.FuncionarioController;
import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.service.FuncionarioService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(FuncionarioController.class)
class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FuncionarioService funcionarioService;

    @Test
    @DisplayName("POST /api/funcionarios - Deve criar um funcionário e retornar status 201 Created")
    void deveCriarFuncionarioComSucesso() throws Exception {
        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome("Felipe");

        Funcionario funcionarioSalvo = new Funcionario();
        funcionarioSalvo.setId(1L);
        funcionarioSalvo.setNome("Felipe");

        Mockito.when(funcionarioService.criar(any(Funcionario.class))).thenReturn(funcionarioSalvo);

        mockMvc.perform(post("/api/funcionarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoFuncionario)))
                .andExpect(status().isCreated()) // Valida status 201
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Felipe"));
    }

    @Test
    @DisplayName("GET /api/funcionarios/{id} - Deve buscar e retornar um funcionário por ID")
    void deveBuscarFuncionarioPorId() throws Exception {
        Long id = 1L;
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome("Rodrigo");

        Mockito.when(funcionarioService.buscarPorId(id)).thenReturn(funcionario);

        mockMvc.perform(get("/api/funcionarios/{id}", id))
                .andExpect(status().isOk()) // Valida status 200
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Rodrigo"));
    }

    @Test
    @DisplayName("GET /api/funcionarios - Deve listar todos os funcionários com status 200 OK")
    void deveListarTodosOsFuncionarios() throws Exception {
        Funcionario f1 = new Funcionario();
        Funcionario f2 = new Funcionario();
        Mockito.when(funcionarioService.listarTodos()).thenReturn(List.of(f1, f2));

        mockMvc.perform(get("/api/funcionarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("DELETE /api/funcionarios/{id} - Deve remover o funcionário e retornar status 204 No Content")
    void deveDeletarFuncionarioComSucesso() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/funcionarios/{id}", id))
                .andExpect(status().isNoContent()); // Valida status 204

        Mockito.verify(funcionarioService, Mockito.times(1)).deletar(id);
    }
}