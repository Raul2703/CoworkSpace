package com.salesianostriana.dam.coworkspace.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.service.EspacioService;

@Configuration
public class DataSeed {

	@Bean
	CommandLineRunner initData(EspacioService espSer) {

		return args -> {

			if (espSer.findAll().isEmpty()) {

				espSer.save(Espacio.builder().nombre("Oficina individual").capacidad(1).precio(24.0).build());
				espSer.save(Espacio.builder().nombre("Oficina individual").capacidad(1).precio(24.0).build());
				espSer.save(Espacio.builder().nombre("Oficina individual").capacidad(1).precio(24.0).build());
				espSer.save(Espacio.builder().nombre("Zona de equipos").capacidad(6).precio(16.0).build());
				espSer.save(Espacio.builder().nombre("Zona de equipos").capacidad(8).precio(18.0).build());
				espSer.save(Espacio.builder().nombre("Zona de equipos").capacidad(10).precio(20.0).build());
				espSer.save(Espacio.builder().nombre("Sala de reuniones").capacidad(8).precio(35.0).build());
				espSer.save(Espacio.builder().nombre("Sala de reuniones").capacidad(10).precio(40.0).build());
				espSer.save(Espacio.builder().nombre("Sala de reuniones").capacidad(12).precio(45.0).build());

			}

		};

	}

}