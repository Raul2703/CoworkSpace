package com.salesianostriana.dam.coworkspace.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianostriana.dam.coworkspace.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByNombreIgnoreCase(String nombre);

	Optional<Usuario> findByEmailIgnoreCase(String email);

	boolean existsByNombreIgnoreCase(String nombre);

	boolean existsByEmailIgnoreCase(String email);

	@Query(value = """
			SELECT u.nombre, COUNT(r.id) AS total
			FROM usuario u
			LEFT JOIN reserva r ON u.id = r.usuario_id
			GROUP BY u.id, u.nombre
			ORDER BY total DESC
			LIMIT 5
			""", nativeQuery = true)
	List<Object[]> usuariosFrecuentes();

}
