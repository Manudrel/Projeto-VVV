package com.projectvvv.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectvvv.domain.model.Modal;

@Repository
public interface ModalRepository extends JpaRepository<Modal, Long> {
}