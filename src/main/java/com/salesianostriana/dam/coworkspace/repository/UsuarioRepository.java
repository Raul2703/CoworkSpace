package com.salesianostriana.dam.coworkspace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.coworkspace.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByNombreIgnoreCase(String nombre);

	Optional<Usuario> findByEmailIgnoreCase(String email);

	boolean existsByNombreIgnoreCase(String nombre);

	boolean existsByEmailIgnoreCase(String email);

}
