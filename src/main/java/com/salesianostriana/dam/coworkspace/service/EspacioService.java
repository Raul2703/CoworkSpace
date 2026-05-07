package com.salesianostriana.dam.coworkspace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.repository.EspacioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspacioService {

	private final EspacioRepository espacioRepository;

	public List<Espacio> findAll() {
		return espacioRepository.findAll();
	}

	public Optional<Espacio> findById(Long id) {
		return espacioRepository.findById(id);
	}

	public Espacio save(Espacio espacio) {
		return espacioRepository.save(espacio);
	}

	public void deleteById(Long id) {
		espacioRepository.deleteById(id);
	}

}
