package com.salesianostriana.dam.coworkspace.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.model.Usuario;
import com.salesianostriana.dam.coworkspace.repository.UsuarioRepository;
import com.salesianostriana.dam.coworkspace.service.base.BaseServiceImpl;

@Service
public class UsuarioService extends BaseServiceImpl<Usuario, Long, UsuarioRepository> {

	public Optional<Usuario> findByNombreIgnoreCase(String nombre) {
		return repository.findByNombreIgnoreCase(nombre);
	}

}
