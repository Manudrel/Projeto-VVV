package com.projectvvv.config;

import com.projectvvv.domain.model.Cargo;
import com.projectvvv.domain.model.Funcionario;
import com.projectvvv.domain.repository.FuncionarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataSeeder implements ApplicationRunner {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(FuncionarioRepository funcionarioRepository,
                      PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {

        boolean gerenteExiste = funcionarioRepository
                .existsByCargo(Cargo.GERENTE);

        if (!gerenteExiste) {

            Funcionario gerente = new Funcionario();

            gerente.setNome("Administrador");
            gerente.setCpf("00000000000");
            gerente.setTelefone("11999999999");
            gerente.setDataNascimento(LocalDate.of(1990, 1, 1));
            gerente.setEndereco("Rua Principal, 100");
            gerente.setCargo(Cargo.GERENTE);
            gerente.setSenha(passwordEncoder.encode("admin123"));

            funcionarioRepository.save(gerente);

            System.out.println(">>> Gerente padrão criado: CPF 00000000000 / senha admin123");
        }
    }
}