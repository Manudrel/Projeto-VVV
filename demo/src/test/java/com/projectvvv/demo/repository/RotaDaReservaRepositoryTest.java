package com.projectvvv.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.Rota;
import com.projectvvv.domain.model.RotaDaReserva;
import com.projectvvv.domain.repository.RotaDaReservaRepository;

@DataJpaTest
class RotaDaReservaRepositoryTest {

    @Autowired
    private RotaDaReservaRepository rotaDaReservaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("findByReservaCodigoReserva - Deve retornar as rotas quando a reserva possuir trechos vinculados")
    void deveBuscarRotasPorCodigoReservaComSucesso() {
        Rota rota = new Rota();
        Reserva reserva = new Reserva();
        Long codigoReservaGerado = 1L;
        reserva.setCodigoReserva(codigoReservaGerado);
       
        RotaDaReserva primeiroTrecho = new RotaDaReserva();
        primeiroTrecho.setReserva(reserva);
        primeiroTrecho.setRota(rota);
        primeiroTrecho.setOrdem(1); // Campo obrigatório (ordem)

        RotaDaReserva segundoTrecho = new RotaDaReserva();
        segundoTrecho.setReserva(reserva);
        segundoTrecho.setRota(rota);
        segundoTrecho.setOrdem(2); // Campo obrigatório (ordem)
        
        rotaDaReservaRepository.saveAll(List.of(primeiroTrecho, segundoTrecho));

        // 4. Execução do método customizado da sua interface
        List<RotaDaReserva> resultado = rotaDaReservaRepository.findByReservaCodigoReserva(codigoReservaGerado);

        // 5. Validações
        assertEquals(2, resultado.size(), "Deveria encontrar os 2 trechos de rota associados a essa reserva");
        assertEquals(1, resultado.get(0).getOrdem(), "O primeiro trecho deve possuir a ordem de número 1");
    }

    @Test
    @DisplayName("findByReservaCodigoReserva - Deve retornar lista vazia quando o código da reserva não possuir nenhuma rota cadastrada")
    void deveRetornarVazioParaCodigoReservaSemVinculos() {
        // Execução usando um ID arbitrário que não possui registros no banco em memória
        List<RotaDaReserva> resultado = rotaDaReservaRepository.findByReservaCodigoReserva(999L);

        // Validação
        assertTrue(resultado.isEmpty(), "A lista resultante deveria estar completamente vazia");
    }
}