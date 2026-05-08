package com.salesianostriana.dam.coworkspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EspacioController {
	
	@GetMapping("/espacios")
	public String getEspacios() {
		return "espacios";
	}

}
