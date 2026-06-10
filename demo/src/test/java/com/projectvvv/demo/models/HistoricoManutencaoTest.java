package com.projectvvv.demo.models;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import com.projectvvv.domain.model.HistoricoManutencao;

public class HistoricoManutencaoTest {
    
    @Test
    void HistoricoManutencaoConstructorTest(){
        long id = 1L;
        String descricao = "Manutenção preventiva";
        LocalDateTime dataRegistro = LocalDateTime.now();
        long idModal = 2L;

        HistoricoManutencao historicoManutencao = new HistoricoManutencao(id, idModal, dataRegistro, descricao);

        assert historicoManutencao.getId() == id;
        assert historicoManutencao.getModalId() == idModal;
        assert historicoManutencao.getDataRegistro().equals(dataRegistro);
        assert historicoManutencao.getDescricao().equals(descricao);
    }
}
