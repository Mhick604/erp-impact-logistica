package com.impact.logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.impact.logistica.model.Motorista;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

}