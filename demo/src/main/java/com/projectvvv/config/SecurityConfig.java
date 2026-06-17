package com.projectvvv.config;

import com.projectvvv.domain.service.FuncionarioUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final FuncionarioUserDetailsService userDetailsService;

    public SecurityConfig(FuncionarioUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/css/**"
                        ).permitAll()

                        // apenas gerente
                        .requestMatchers(
                                "/gerente/**",
                                "/api/funcionarios/**"
                        ).hasRole("GERENTE")

                        .anyRequest().authenticated()
                )


                .formLogin(form -> form

                        .loginPage("/auth/login")

                        // POST que o Spring Security intercepta
                        .loginProcessingUrl("/auth/login")

                        // nomes dos campos HTML
                        .usernameParameter("cpf")
                        .passwordParameter("senha")


                        // após login vai para sua API
                        .defaultSuccessUrl(
                                "/dashboard",
                                true
                        )


                        .failureUrl(
                                "/auth/login?erro=true"
                        )

                        .permitAll()
                )


                .logout(logout -> logout

                        .logoutUrl("/auth/logout")

                        .logoutSuccessUrl(
                                "/auth/login?logout=true"
                        )
                );


        return http.build();
    }
}