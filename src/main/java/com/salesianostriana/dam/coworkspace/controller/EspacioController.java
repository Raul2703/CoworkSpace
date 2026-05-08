package com.salesianostriana.dam.coworkspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.coworkspace.service.EspacioService;

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

}
