package com.projectvvv.domain.controller;

import com.projectvvv.domain.service.FuncionarioService;
import com.projectvvv.domain.model.Funcionario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/gerente/funcionarios")
public class FuncionarioViewController {

    private final FuncionarioService funcionarioService;

    public FuncionarioViewController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public String listarFuncionarios(Model model) {
        model.addAttribute("usuarioLogado", new UsuarioLogadoMock());

        List<Funcionario> listaFuncionarios = funcionarioService.listarTodos();

        model.addAttribute("funcionarios", listaFuncionarios);

        return "funcionarios/listar";
    }
}