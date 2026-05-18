package com.salesianostriana.dam.coworkspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.coworkspace.service.EspacioService;
import com.salesianostriana.dam.coworkspace.service.ReservaService;
import com.salesianostriana.dam.coworkspace.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final UsuarioService usuarioService;
	private final EspacioService espacioService;
	private final ReservaService reservaService;

	@GetMapping({ "/", "/index" })
	public String index() {
		return "index";
	}

// 
	@GetMapping("/admin")
	public String admin(Model model) {

		model.addAttribute("totalUsuarios", usuarioService.findAll().size());
		model.addAttribute("totalEspacios", espacioService.findAll().size());
		model.addAttribute("totalReservas", reservaService.findAll().size());

		double ingresosTotales = reservaService.findAll().stream().filter(r -> r.getPrecioTotal() != null)
				.mapToDouble(r -> r.getPrecioTotal()).sum();

		model.addAttribute("ingresosTotales", ingresosTotales);

		model.addAttribute("ultimasReservas",
				reservaService.findAll().stream().sorted((a, b) -> b.getId().compareTo(a.getId())).limit(5).toList());

		return "admin";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

}