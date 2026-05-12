package com.salesianostriana.dam.coworkspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.service.EspacioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EspacioController {

	private final EspacioService espacioService;

	@GetMapping("/espacios")
	public String getEspacios(Model model) {

		model.addAttribute("espacios", espacioService.findAll());

		return "espacios";
	}

	@GetMapping("/espacios/nuevo")
	public String nuevoEspacio(Model model) {

		model.addAttribute("espacio", new Espacio());

		return "form-espacio";
	}

	@PostMapping("/espacios/guardar")
	public String guardarEspacio(@Valid @ModelAttribute Espacio espacio, BindingResult result) {

		if (result.hasErrors()) {
			return "form-espacio";
		}

		espacioService.save(espacio);

		return "redirect:/espacios";
	}

}