package com.impact.logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.impact.logistica.model.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}