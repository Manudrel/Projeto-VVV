package com.projectvvv.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.model.TipoModal;
import com.projectvvv.domain.model.TipoPassagem;
import com.projectvvv.domain.model.TipoViagem;

public class ReservaTest {

    @Test
    void ReservaConstructorTest() {

        Long codigoReserva = 1L;
        LocalDate data = LocalDate.of(2026, 6, 20);
        LocalTime horaPartida = LocalTime.of(10, 0);

        StatusReserva statusReserva = StatusReserva.CONCLUIDO;
        TipoViagem tipoViagem = TipoViagem.DIRETA;
        TipoModal tipoModal = TipoModal.NAVIO;

        String localizador = "ABC123";

        TipoPassagem tipoPassagem = TipoPassagem.LEITO;

        Long idModal = 3L;

        Reserva reserva = new Reserva(
                codigoReserva,
                data,
                horaPartida,
                statusReserva,
                tipoViagem,
                tipoModal,
                localizador,
                tipoPassagem,
                idModal, null
        );

        assert reserva.getCodigoReserva().equals(codigoReserva);
        assert reserva.getData().equals(data);
        assert reserva.getHoraPartida().equals(horaPartida);
        assert reserva.getStatusReserva() == statusReserva;
        assert reserva.getTipoViagem() == tipoViagem;
        assert reserva.getTipoModal() == tipoModal;
        assert reserva.getLocalizador().equals(localizador);
        assert reserva.getTipoPassagem() == tipoPassagem;
        assert reserva.getIdModal().equals(idModal);
    }
}