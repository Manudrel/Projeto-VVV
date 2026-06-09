package com.projectvvv.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectvvv.domain.model.Modal;

public interface ModalRepository extends JpaRepository<Modal, Long> {
}