package com.projectvvv.demo.model;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.projectvvv.domain.model.Reserva;
import com.projectvvv.domain.model.StatusReserva;
import com.projectvvv.domain.model.TipoModal;
import com.projectvvv.domain.model.TipoPassagem;

public class ReservaTest {
    
    @Test
    void ReservaConstructorTest(){
        
        Long codigoReserva = 1L;
        Date data = new Date();
        String horaPartida = "10:00";
        StatusReserva statusReserva = StatusReserva.CONCLUIDO;
        TipoModal tipoViagem = TipoModal.NAVIO;
        String localizador = "ABC123";
        TipoPassagem tipoPassagem = TipoPassagem.LEITO;
        Long idRota = 2L;
        Long idModal = 3L;
        
        Reserva reserva = new Reserva(codigoReserva, data, horaPartida, statusReserva, tipoViagem, localizador, tipoPassagem, idRota, idModal);

        assert reserva.getCodigoReserva().equals(codigoReserva);
        assert reserva.getData().equals(data);
        assert reserva.getHoraPartida().equals(horaPartida);
        assert reserva.getStatusReserva() == statusReserva;
        assert reserva.getTipoModal() == tipoViagem;
        assert reserva.getLocalizador().equals(localizador);
        assert reserva.getTipoPassagem() == tipoPassagem;
        assert reserva.getIdRota().equals(idRota);
        assert reserva.getIdModal().equals(idModal);
    }
}
