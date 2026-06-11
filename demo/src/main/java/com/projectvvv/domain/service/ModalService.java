package com.projectvvv.domain.service;

import com.projectvvv.domain.repository.HistoricoManutencaoRepository;
import com.projectvvv.domain.repository.ModalRepository;
import com.projectvvv.domain.model.Modal;
import com.projectvvv.domain.model.EstadoModal;
import com.projectvvv.domain.model.HistoricoManutencao;
import com.projectvvv.domain.model.TipoModal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.UUID;

@Service
public class ModalService {

    private final ModalRepository modalRepository;
    private final HistoricoManutencaoRepository historicoRepository;

    public ModalService(
        ModalRepository modalRepository,
        HistoricoManutencaoRepository historicoRepository) {

        this.modalRepository = modalRepository;
        this.historicoRepository = historicoRepository;
    }

    public Modal buscarPorId(Long id) {
        return modalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modal não encontrado."));
    }

    public List<Modal> listarTodos() {
        return modalRepository.findAll();
    }
    public Modal criar(Modal modal, Long idTransportadora, TipoModal tipoModal, Integer ano, String modelo, Integer capacidade) {
        modal.setCodigoModal(
            "MD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()
        );

        modal.setEstadoModal(EstadoModal.ATIVO);
        modal.setIdTrasnportadora(idTransportadora);
        modal.setTipoModal(tipoModal);
        modal.setAno(ano);
        modal.setModelo(modelo);
        modal.setCapacidade(capacidade);

        return modalRepository.save(modal);
    }

    public Modal manutencao(Long id, String descricao) {

        Modal modal = buscarPorId(id);

        modal.setEstadoModal(EstadoModal.MANUTENCAO);

        Modal salvo = modalRepository.save(modal);

        HistoricoManutencao historico =
                new HistoricoManutencao();

        historico.setModalId(id);
        historico.setDataRegistro(LocalDateTime.now());
        historico.setDescricao(descricao);

        historicoRepository.save(historico);

        return salvo;
    }


    public Modal ativar(Long id) {

        Modal modal = buscarPorId(id);

        modal.setEstadoModal(EstadoModal.ATIVO);

        return modalRepository.save(modal);
    }

    public Modal inativar(Long id) {

        Modal modal = buscarPorId(id);

        modal.setEstadoModal(EstadoModal.INATIVO);

        return modalRepository.save(modal);
    }

    public Modal atualizar(Long id, Modal modalAtualizado) {
        Modal modalExistente = buscarPorId(id);

        if (modalAtualizado.getCodigoModal() != null) {
            modalExistente.setCodigoModal(modalAtualizado.getCodigoModal());
        }
        if (modalAtualizado.getTipoModal() != null) {
            modalExistente.setTipoModal(modalAtualizado.getTipoModal());
        }
        if (modalAtualizado.getAno() != null) {
            modalExistente.setAno(modalAtualizado.getAno());
        }
        if (modalAtualizado.getModelo() != null) {
            modalExistente.setModelo(modalAtualizado.getModelo());
        }
        if (modalAtualizado.getCapacidade() != null) {
            modalExistente.setCapacidade(modalAtualizado.getCapacidade());
        }
        if (modalAtualizado.getEstadoModal() != null) {
            modalExistente.setEstadoModal(modalAtualizado.getEstadoModal());
        }
        if (modalAtualizado.getIdTrasnportadora() != null) {
            modalExistente.setIdTrasnportadora(modalAtualizado.getIdTrasnportadora());
        }

        return modalRepository.save(modalExistente);
    }

    public void deletar(Long id) {
        if (!modalRepository.existsById(id)) {
            throw new RuntimeException("Modal não encontrado para exclusão.");
        }
        modalRepository.deleteById(id);
    }
}