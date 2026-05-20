package com.salesianostriana.dam.coworkspace.config;

import java.util.ArrayList;

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

				espSer.save(new Espacio(null, "Oficina individual", 1, 24.0, new ArrayList<>()));
				espSer.save(new Espacio(null, "Oficina individual", 1, 24.0, new ArrayList<>()));
				espSer.save(new Espacio(null, "Oficina individual", 1, 24.0, new ArrayList<>()));

				espSer.save(new Espacio(null, "Zona de equipos", 6, 16.0, new ArrayList<>()));
				espSer.save(new Espacio(null, "Zona de equipos", 8, 18.0, new ArrayList<>()));
				espSer.save(new Espacio(null, "Zona de equipos", 10, 20.0, new ArrayList<>()));

				espSer.save(new Espacio(null, "Sala de reuniones", 10, 35.0, new ArrayList<>()));
				espSer.save(new Espacio(null, "Sala de reuniones", 20, 40.0, new ArrayList<>()));
				espSer.save(new Espacio(null, "Sala de reuniones", 30, 45.0, new ArrayList<>()));

			}

		};

	}

}