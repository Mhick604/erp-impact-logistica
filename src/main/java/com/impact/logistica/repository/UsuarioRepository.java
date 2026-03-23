package com.impact.logistica.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.impact.logistica.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}