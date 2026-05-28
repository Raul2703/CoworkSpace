package com.salesianostriana.dam.coworkspace.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.model.Usuario;
import com.salesianostriana.dam.coworkspace.repository.UsuarioRepository;
import com.salesianostriana.dam.coworkspace.service.base.BaseServiceImpl;

@Service
public class UsuarioService extends BaseServiceImpl<Usuario, Long, UsuarioRepository> {

	private final PasswordEncoder passwordEncoder;

	public UsuarioService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Usuario save(Usuario usuario) {

		if (usuario.getRol() == null || usuario.getRol().isBlank()) {
			usuario.setRol("USER");
		}

		if (usuario.getId() != null && (usuario.getPassword() == null || usuario.getPassword().isBlank())) {
			findById(usuario.getId()).ifPresent(usuarioGuardado -> usuario.setPassword(usuarioGuardado.getPassword()));
		} else if (usuario.getPassword() != null && !usuario.getPassword().startsWith("{")) {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		}

		return super.save(usuario);
	}

	public Optional<Usuario> findByNombreIgnoreCase(String nombre) {
		return repository.findByNombreIgnoreCase(nombre);
	}

	public Optional<Usuario> findByEmailIgnoreCase(String email) {
		return repository.findByEmailIgnoreCase(email);
	}

	public boolean existsByNombreIgnoreCase(String nombre) {
		return repository.existsByNombreIgnoreCase(nombre);
	}

	public boolean existsByEmailIgnoreCase(String email) {
		return repository.existsByEmailIgnoreCase(email);
	}

}
