package com.projectvvv.domain.service;

import com.projectvvv.domain.model.Cargo;
import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.model.PontosDeVendaDoFuncionario;
import com.projectvvv.domain.repository.FuncionarioRepository;
import com.projectvvv.domain.repository.PontoDeVendaRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PontoDeVendaRepository pontoDeVendaRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            PontoDeVendaRepository pontoDeVendaRepository,
            PasswordEncoder passwordEncoder) {

        this.funcionarioRepository = funcionarioRepository;
        this.pontoDeVendaRepository = pontoDeVendaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Criação livre — usada apenas por GERENTE.
     * Respeita o Cargo enviado no corpo da requisição
     * (permite cadastrar outro GERENTE ou um FUNCIONARIO comum).
     */
    public Funcionario criar(Funcionario funcionario) {

        if (funcionarioRepository.existsByCpf(funcionario.getCpf())) {
            throw new RuntimeException("CPF já cadastrado.");
        }

        if (funcionario.getCargo() == null) {
            funcionario.setCargo(Cargo.FUNCIONARIO);
        }

        validarPontos(funcionario);

        funcionario.setSenha(passwordEncoder.encode(funcionario.getSenha()));

        return funcionarioRepository.save(funcionario);
    }

    /**
     * Criação restrita — usada por qualquer FUNCIONARIO autenticado
     * (ex: na tela de cadastro pública, ou um funcionário cadastrando outro).
     * Ignora qualquer Cargo enviado no corpo e força sempre FUNCIONARIO,
     * para impedir que um funcionário comum se promova ou crie um gerente.
     */
    public Funcionario criarFuncionarioComum(Funcionario funcionario) {

        funcionario.setCargo(Cargo.FUNCIONARIO);

        return criar(funcionario);
    }

    private void validarPontos(Funcionario funcionario) {

        List<PontosDeVendaDoFuncionario> pontos = funcionario.getPontosDeVenda();

        if (pontos == null) {
            return;
        }

        if (pontos.size() > 2) {
            throw new RuntimeException(
                    "Funcionário pode trabalhar em no máximo 2 pontos.");
        }

        Set<Short> dias = new HashSet<>();

        for (PontosDeVendaDoFuncionario ponto : pontos) {

            ponto.setFuncionario(funcionario);

            if (ponto.getDiaTrabalho() == null) {
                throw new RuntimeException("Dia de trabalho é obrigatório.");
            }

            if (ponto.getDiaTrabalho() < 1 || ponto.getDiaTrabalho() > 7) {
                throw new RuntimeException("Dia deve estar entre 1 e 7.");
            }

            if (!dias.add(ponto.getDiaTrabalho())) {
                throw new RuntimeException("Os dias devem ser diferentes.");
            }
        }
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado."));
    }

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    public void deletar(Long id) {

        if (!funcionarioRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado.");
        }

        funcionarioRepository.deleteById(id);
    }
}