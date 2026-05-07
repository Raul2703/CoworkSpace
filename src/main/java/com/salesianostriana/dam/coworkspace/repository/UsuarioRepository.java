package com.salesianostriana.dam.coworkspace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.coworkspace.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
