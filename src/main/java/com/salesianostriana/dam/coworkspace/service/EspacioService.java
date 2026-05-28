package com.salesianostriana.dam.coworkspace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.repository.EspacioRepository;
import com.salesianostriana.dam.coworkspace.service.base.BaseServiceImpl;

@Service
public class EspacioService extends BaseServiceImpl<Espacio, Long, EspacioRepository> {

	public List<Espacio> filtrar(String busqueda, Integer capacidadMinima, Double precioMaximo) {

		String texto = busqueda != null ? busqueda.trim().toLowerCase() : "";

		return findAll().stream()
				.filter(espacio -> texto.isBlank() || espacio.getNombre().toLowerCase().contains(texto))
				.filter(espacio -> capacidadMinima == null || espacio.getCapacidad() >= capacidadMinima)
				.filter(espacio -> precioMaximo == null || espacio.getPrecio() <= precioMaximo)
				.toList();
	}

}
