package com.projectvvv.domain.controller;

import com.projectvvv.domain.dto.FuncionarioForm;
import com.projectvvv.domain.model.Cargo;
import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.repository.FuncionarioRepository;
import com.projectvvv.domain.repository.PontoDeVendaRepository;
import com.projectvvv.domain.service.FuncionarioService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/gerente/funcionarios")
@PreAuthorize("hasRole('GERENTE')")
public class FuncionarioViewController {

    private final FuncionarioService funcionarioService;
    private final PontoDeVendaRepository pontoDeVendaRepository;
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioViewController(
            FuncionarioService funcionarioService,
            PontoDeVendaRepository pontoDeVendaRepository,
            FuncionarioRepository funcionarioRepository) {

        this.funcionarioService = funcionarioService;
        this.pontoDeVendaRepository = pontoDeVendaRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    // GET /gerente/funcionarios
    @GetMapping
    public String listar(Model model, Authentication authentication) {
        model.addAttribute("funcionarios", funcionarioService.listarTodos());
        model.addAttribute("funcionarioLogado", buscarLogado(authentication));
        return "funcionarios/listar";
    }

    // GET /gerente/funcionarios/cadastro
    @GetMapping("/cadastro")
    public String formulario(Model model, Authentication authentication) {
        model.addAttribute("funcionarioForm", new FuncionarioForm());
        model.addAttribute("pontosDeVenda", pontoDeVendaRepository.findAll());
        model.addAttribute("funcionarioLogado", buscarLogado(authentication));
        return "auth/cadastro-funcionario";
    }

    // POST /gerente/funcionarios/cadastro
    @PostMapping("/cadastro")
    public String cadastrar(
            @Valid @ModelAttribute("funcionarioForm") FuncionarioForm form,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (form.getSenha() != null
                && !form.getSenha().equals(form.getConfirmarSenha())) {
            result.rejectValue("confirmarSenha", "senha.invalida", "As senhas não coincidem.");
        }

        if (result.hasErrors()) {
            model.addAttribute("pontosDeVenda", pontoDeVendaRepository.findAll());
            return "gerente/funcionarios/cadastro";
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(form.getNome());
        funcionario.setCpf(form.getCpf());
        funcionario.setTelefone(form.getTelefone());
        funcionario.setDataNascimento(form.getDataNascimento());
        funcionario.setEndereco(form.getEndereco());
        funcionario.setSenha(form.getSenha());
        funcionario.setCargo(form.getCargo() != null ? form.getCargo() : Cargo.FUNCIONARIO);

        // Criação livre: o gerente pode definir o cargo (FUNCIONARIO ou GERENTE).
        funcionarioService.criar(funcionario);

        redirectAttributes.addFlashAttribute(
                "mensagem", "Funcionário cadastrado com sucesso.");

        return "redirect:/funcionarios";
    }

    private Funcionario buscarLogado(Authentication authentication) {
        String cpf = authentication.getName();
        return funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));
    }
}