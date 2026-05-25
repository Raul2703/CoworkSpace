package com.salesianostriana.dam.coworkspace.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.repository.EspacioRepository;

@Configuration
public class DataSeed {

	@Bean
	CommandLineRunner initData(EspacioRepository espacioRepository) {

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
		};
	}

}