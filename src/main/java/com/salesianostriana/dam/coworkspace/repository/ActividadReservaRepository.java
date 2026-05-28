package com.salesianostriana.dam.coworkspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.coworkspace.model.ActividadReserva;

public interface ActividadReservaRepository extends JpaRepository<ActividadReserva, Long> {

	List<ActividadReserva> findTop6ByOrderByFechaHoraDesc();

}
