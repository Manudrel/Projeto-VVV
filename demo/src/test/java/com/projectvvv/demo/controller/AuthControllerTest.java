package com.projectvvv.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.projectvvv.domain.controller.AuthController;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /auth/login - Deve carregar a página de login com status 200")
    void deveRetornarViewDeLogin() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk()) 
                .andExpect(view().name("auth/login")); 
    }

    @Test
    @DisplayName("GET /auth/cadastro - Deve carregar a página de cadastro com status 200")
    void deveRetornarViewDeCadastro() throws Exception {
        mockMvc.perform(get("/auth/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/cadastro"));
    }
}