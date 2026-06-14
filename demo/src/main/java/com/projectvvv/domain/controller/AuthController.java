package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.ClienteForm;
import com.projectvvv.domain.dto.FuncionarioForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/cadastro-cliente")
    public String cadastroCliente(Model model) {
        model.addAttribute("clienteForm", new ClienteForm());
        return "auth/cadastro-cliente";
    }

    @PostMapping("/cadastro-cliente")
    public String salvarCliente(@ModelAttribute ClienteForm form) {
        // TODO: salvar cliente via service
        return "redirect:/dashboard";
    }

    @GetMapping("/cadastro-funcionario")
    public String cadastroFuncionario(Model model) {
        FuncionarioForm form = new FuncionarioForm();
        form.setCargo("Gerente");
        model.addAttribute("funcionarioForm", form);
        return "auth/cadastro-funcionario";
    }

    @PostMapping("/cadastro-funcionario")
    public String salvarFuncionario(@ModelAttribute FuncionarioForm form) {
        // TODO: salvar funcionário com cargo definido pelo form
        return "redirect:/auth/login";
    }
}