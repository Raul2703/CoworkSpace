package com.salesianostriana.dam.coworkspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservaController {
	
	@GetMapping("/reservas")
	public String getReservas() {
		return "reservas";
	}

}
