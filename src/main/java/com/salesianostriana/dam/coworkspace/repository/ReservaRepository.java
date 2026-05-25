package com.salesianostriana.dam.coworkspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianostriana.dam.coworkspace.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
	@Query("""
			SELECT re.espacio.nombre, COUNT(re)
			FROM ReservaEspacio re
			GROUP BY re.espacio.nombre
			ORDER BY COUNT(re) DESC
			""")
	List<Object[]> obtenerEspaciosMasReservados();
}