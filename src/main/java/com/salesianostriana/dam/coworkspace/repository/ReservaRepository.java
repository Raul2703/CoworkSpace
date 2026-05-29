package com.salesianostriana.dam.coworkspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.coworkspace.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

	List<Reserva> findByUsuario_NombreIgnoreCase(String nombre);

	@Query(value = """
			SELECT fecha, COUNT(*) AS total
			FROM reserva
			GROUP BY fecha
			ORDER BY fecha DESC
			LIMIT 5
			""", nativeQuery = true)
	List<Object[]> reservasPorFecha();

}
