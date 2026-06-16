package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.ClienteForm;
import com.projectvvv.domain.dto.FuncionarioForm;
import com.projectvvv.domain.model.Cargo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.service.FuncionarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final FuncionarioService funcionarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(FuncionarioService funcionarioService,
                          PasswordEncoder passwordEncoder) {
        this.funcionarioService = funcionarioService;
        this.passwordEncoder = passwordEncoder;
    }

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
        form.setCargo(Cargo.GERENTE);
        model.addAttribute("funcionarioForm", form);
        return "auth/cadastro-funcionario";
    }

    @PostMapping("/cadastro-funcionario")
    public String salvarFuncionario(@ModelAttribute FuncionarioForm form) {
        // TODO: salvar funcionário com cargo definido pelo form
        return "redirect:/auth/login";
    }

    @PostMapping("/cadastro")
    public String cadastrar(Funcionario funcionario,
                            BindingResult result,
                            RedirectAttributes attrs) {
        funcionario.setSenha(passwordEncoder.encode(funcionario.getSenha()));
        funcionarioService.criar(funcionario);
        return "redirect:/auth/login";
    }
}