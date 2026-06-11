package com.projectvvv.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.projectvvv.domain.controller.HomeController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(HomeController.class)
public class HomeControllerTest  {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve retornar a view index e status 200 ao acessar a rota raiz")
    void deveRetornarHomeComSucesso() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}

