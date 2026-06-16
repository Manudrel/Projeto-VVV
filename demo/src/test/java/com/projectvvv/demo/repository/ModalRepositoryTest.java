package com.projectvvv.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.projectvvv.domain.model.EstadoModal;
import com.projectvvv.domain.model.Modal;
import com.projectvvv.domain.model.TipoModal;
import com.projectvvv.domain.repository.ModalRepository;

@DataJpaTest
class ModalRepositoryTest {

    @Autowired
    private ModalRepository modalRepository;

    private Modal criarModalValido() {
        Modal modal = new Modal();
        
        modal.setCodigoModal("MOD-12345");
        modal.setTipoModal(TipoModal.NAVIO); 
        modal.setAno(2026);
        modal.setModelo("Marcopolo Paradiso G8");
        modal.setCapacidade(44);
        modal.setEstadoModal(EstadoModal.ATIVO); 
        modal.setIdTrasnportadora(1L); 

        return modal;
    }

    @Test
    @DisplayName("save - Deve persistir o modal preenchido com sucesso no H2")
    void deveSalvarModalComSucesso() {
        Modal modal = criarModalValido();

        Modal modalSalvo = modalRepository.save(modal);

        assertNotNull(modalSalvo.getId(), "O ID não deveria ser nulo após salvar");
        assertEquals("MOD-12345", modalSalvo.getCodigoModal());
        assertEquals(2026, modalSalvo.getAno());
        assertEquals(44, modalSalvo.getCapacidade());
        assertEquals(TipoModal.NAVIO, modalSalvo.getTipoModal());
        assertEquals(EstadoModal.ATIVO, modalSalvo.getEstadoModal());
    }

    @Test
    @DisplayName("findById - Deve recuperar um modal por ID com os dados íntegros")
    void deveBuscarModalPorId() {
        Modal modalSalvo = modalRepository.save(criarModalValido());
        Long idGerado = modalSalvo.getId();

        Optional<Modal> resultado = modalRepository.findById(idGerado);

        assertTrue(resultado.isPresent(), "O modal deveria ser localizado");
        assertEquals(idGerado, resultado.get().getId());
        assertEquals("Marcopolo Paradiso G8", resultado.get().getModelo());
    }

    @Test
    @DisplayName("deleteById - Deve remover o modal do banco sem deixar órfãos")
    void deveDeletarModalComSucesso() {
        Modal modalSalvo = modalRepository.save(criarModalValido());
        Long idGerado = modalSalvo.getId();

        modalRepository.deleteById(idGerado);
        Optional<Modal> resultado = modalRepository.findById(idGerado);

        assertTrue(resultado.isEmpty(), "O registro deveria ter sido deletado");
    }
}