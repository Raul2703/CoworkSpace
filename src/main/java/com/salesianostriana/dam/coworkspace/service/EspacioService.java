package com.salesianostriana.dam.coworkspace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.repository.EspacioRepository;
import com.salesianostriana.dam.coworkspace.service.base.BaseServiceImpl;

@Service
public class EspacioService extends BaseServiceImpl<Espacio, Long, EspacioRepository> {

	public List<Espacio> filtrar(String busqueda, Integer capacidadMinima, Double precioMaximo) {

		String texto = busqueda != null && !busqueda.isBlank() ? busqueda.trim() : null;

		return repository.filtrarEspacios(texto, capacidadMinima, precioMaximo);
	}

	public List<Object[]> espaciosMasUsados() {
		return repository.espaciosMasUsados();
	}

}
