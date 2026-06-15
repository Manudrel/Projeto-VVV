package com.projectvvv.domain.service;

import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.model.PontosDeVendaDoFuncionario;
import com.projectvvv.domain.repository.FuncionarioRepository;
import com.projectvvv.domain.repository.PontoDeVendaRepository;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PontoDeVendaRepository pontoDeVendaRepository;
    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            PontoDeVendaRepository pontoDeVendaRepository) {

        this.funcionarioRepository = funcionarioRepository;
        this.pontoDeVendaRepository = pontoDeVendaRepository;
    }

    public Funcionario criar(
            Funcionario funcionario) {

        if (funcionarioRepository.existsByCpf(
                funcionario.getCpf())) {

            throw new RuntimeException(
                    "CPF já cadastrado.");
        }

        validarPontos(funcionario);

        return funcionarioRepository.save(funcionario);
    }

    private void validarPontos(
            Funcionario funcionario) {

        List<PontosDeVendaDoFuncionario> pontos =
                funcionario.getPontosDeVenda();

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
                throw new RuntimeException(
                    "Dia de trabalho é obrigatório.");
            }

            if (ponto.getDiaTrabalho() < 1 ||
                    ponto.getDiaTrabalho() > 7) {

                throw new RuntimeException(
                        "Dia deve estar entre 1 e 7.");
            }

            if (!dias.add(
                    ponto.getDiaTrabalho())) {

                throw new RuntimeException(
                        "Os dias devem ser diferentes.");
            }
        }
    }

    public Funcionario buscarPorId(Long id) {

        return funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Funcionário não encontrado."));
    }

    public List<Funcionario> listarTodos() {

        return funcionarioRepository.findAll();
    }

    public void deletar(Long id) {

        if (!funcionarioRepository.existsById(id)) {

            throw new RuntimeException(
                    "Funcionário não encontrado.");
        }

        funcionarioRepository.deleteById(id);
    }
}