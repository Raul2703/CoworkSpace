package com.salesianostriana.dam.coworkspace.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.model.Usuario;
import com.salesianostriana.dam.coworkspace.repository.EspacioRepository;
import com.salesianostriana.dam.coworkspace.repository.UsuarioRepository;

@Configuration
public class DataSeed {

	@Bean
	CommandLineRunner initData(EspacioRepository espacioRepository, UsuarioRepository usuarioRepository,
			PasswordEncoder passwordEncoder) {

		return args -> {

			if (espacioRepository.count() == 0) {

				espacioRepository.save(Espacio.builder().nombre("Puesto flexible").capacidad(1).precio(4.50).build());

				espacioRepository
						.save(Espacio.builder().nombre("Sala pequeña reuniones").capacidad(4).precio(12.00).build());

				espacioRepository
						.save(Espacio.builder().nombre("Oficina individual").capacidad(2).precio(18.50).build());

				espacioRepository.save(Espacio.builder().nombre("Oficina premium").capacidad(4).precio(25.00).build());

				espacioRepository
						.save(Espacio.builder().nombre("Sala conferencias").capacidad(12).precio(35.00).build());

				espacioRepository.save(Espacio.builder().nombre("Zona podcast").capacidad(3).precio(22.00).build());

				espacioRepository.save(Espacio.builder().nombre("Estudio creativo").capacidad(6).precio(28.00).build());

				espacioRepository.save(Espacio.builder().nombre("Zona equipos").capacidad(8).precio(15.50).build());
			}

			crearUsuarioSiNoExiste(usuarioRepository, passwordEncoder, "admin", "admin@coworkspace.local",
					"600000000", "admin", "ADMIN");

			crearUsuarioSiNoExiste(usuarioRepository, passwordEncoder, "user", "user@coworkspace.local",
					"600000001", "user", "USER");
		};
	}

	private void crearUsuarioSiNoExiste(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
			String nombre, String email, String telefono, String password, String rol) {

		if (usuarioRepository.existsByNombreIgnoreCase(nombre)) {
			return;
		}

		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setEmail(email);
		usuario.setTelefono(telefono);
		usuario.setPassword(passwordEncoder.encode(password));
		usuario.setRol(rol);

		usuarioRepository.save(usuario);
	}

}
