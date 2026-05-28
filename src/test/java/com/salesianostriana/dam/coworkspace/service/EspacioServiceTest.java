package com.salesianostriana.dam.coworkspace.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.salesianostriana.dam.coworkspace.model.Espacio;

@SpringBootTest
@Transactional
class EspacioServiceTest {

	@Autowired
	private EspacioService espacioService;

	@Test
	void filtraEspaciosPorNombreCapacidadYPrecio() {

		Espacio espacio = espacioService.save(Espacio.builder()
				.nombre("Sala filtro " + System.nanoTime())
				.capacidad(8)
				.precio(18.0)
				.build());

		assertThat(espacioService.filtrar("sala filtro", 6, 20.0)).contains(espacio);
		assertThat(espacioService.filtrar("sala filtro", 10, 20.0)).doesNotContain(espacio);
		assertThat(espacioService.filtrar("sala filtro", 6, 10.0)).doesNotContain(espacio);
	}

}
