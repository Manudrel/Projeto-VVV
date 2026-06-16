package com.projectvvv.domain.service;

import com.projectvvv.domain.model.Cargo;
import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.repository.FuncionarioRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioUserDetailsService implements UserDetailsService {

    private final FuncionarioRepository funcionarioRepository;



    public FuncionarioUserDetailsService(
            FuncionarioRepository funcionarioRepository) {

        this.funcionarioRepository = funcionarioRepository;

    }


    @Override
    public UserDetails loadUserByUsername(String cpf)
            throws UsernameNotFoundException {


        Funcionario funcionario =
                funcionarioRepository.findByCpf(cpf)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Funcionário não encontrado com CPF: "
                                                + cpf));


        String role;

        if (funcionario.getCargo() == Cargo.GERENTE) {
            role = "ROLE_GERENTE";
        } else {
            role = "ROLE_FUNCIONARIO";
        }


        return new User(
                funcionario.getCpf(),
                funcionario.getSenha(),
                List.of(
                        new SimpleGrantedAuthority(role)
                )
        );
    }




}