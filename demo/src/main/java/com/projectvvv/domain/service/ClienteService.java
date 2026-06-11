package com.projectvvv.domain.service;

import com.projectvvv.domain.model.Cliente;
import com.projectvvv.domain.repository.ClienteRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Cliente atualizar(
            Long id,
            Cliente clienteAtualizado) {

        Cliente clienteExistente =
                buscarPorId(id);

        if (clienteAtualizado.getNome() != null) {
            clienteExistente.setNome(
                    clienteAtualizado.getNome());
        }

        if (clienteAtualizado.getCpf() != null) {
            clienteExistente.setCpf(
                    clienteAtualizado.getCpf());
        }

        if (clienteAtualizado.getEndereco() != null) {
            clienteExistente.setEndereco(
                    clienteAtualizado.getEndereco());
        }

        if (clienteAtualizado.getTelefone() != null) {
            clienteExistente.setTelefone(
                    clienteAtualizado.getTelefone());
        }

        if (clienteAtualizado.getDataNascimento() != null) {
            clienteExistente.setDataNascimento(
                    clienteAtualizado.getDataNascimento());
        }

        return repository.save(
                clienteExistente);
    }
}