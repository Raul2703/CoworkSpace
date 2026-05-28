package com.salesianostriana.dam.coworkspace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesianostriana.dam.coworkspace.model.Espacio;

public interface EspacioRepository extends JpaRepository<Espacio, Long> {

	@Query(value = """
			SELECT *
			FROM espacio
			WHERE (:busqueda IS NULL OR LOWER(nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')))
			AND (:capacidadMinima IS NULL OR capacidad >= :capacidadMinima)
			AND (:precioMaximo IS NULL OR precio <= :precioMaximo)
			""", nativeQuery = true)
	List<Espacio> filtrarEspacios(@Param("busqueda") String busqueda,
			@Param("capacidadMinima") Integer capacidadMinima,
			@Param("precioMaximo") Double precioMaximo);

}
