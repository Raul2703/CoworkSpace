package com.salesianostriana.dam.coworkspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.coworkspace.service.ReservaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservaController {

	private final ReservaService reservaService;

	@GetMapping("/reservas")
	public String getReservas(Model model) {
		model.addAttribute("reservas", reservaService.findAll());
		return "reservas";
	}

}
