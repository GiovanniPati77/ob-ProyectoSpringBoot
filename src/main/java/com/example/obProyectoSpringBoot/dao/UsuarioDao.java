package com.example.obProyectoSpringBoot.dao;

import com.example.obProyectoSpringBoot.playload.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombre(String nombre);

    Optional<Usuario> findByEmail(String Email);

    Boolean existsByNombre(String nombre);

    Boolean existsByEmail(String email);

}
