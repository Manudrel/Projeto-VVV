package com.projectvvv.demo.model;

import org.junit.jupiter.api.Test;

import com.projectvvv.domain.model.EstadoModal;
import com.projectvvv.domain.model.Modal;
import com.projectvvv.domain.model.TipoModal;

public class ModalTest {
    
    @Test
    void ModalConstructorTest(){
        long id = 1L;
        String codigoModal = "MOD123";
        TipoModal tipo = TipoModal.NAVIO;
        int ano = 2026;
        String modelo = "ModeloX";
        int capacidade = 1000;
        EstadoModal estado = EstadoModal.ATIVO;
        long idTransportadora = 2L;

        Modal modal = new Modal(id, codigoModal, tipo, ano, modelo, capacidade, estado, idTransportadora);

        assert modal.getId().equals(id);
        assert modal.getCodigoModal().equals(codigoModal);
        assert modal.getTipoModal() == tipo;
        assert modal.getAno().equals(ano);
        assert modal.getModelo().equals(modelo);
        assert modal.getCapacidade().equals(capacidade);
        assert modal.getEstadoModal() == estado;
        assert modal.getIdTrasnportadora().equals(idTransportadora);
   
    }
}
