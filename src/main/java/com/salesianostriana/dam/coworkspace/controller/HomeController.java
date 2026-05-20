package com.salesianostriana.dam.coworkspace.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.coworkspace.model.Espacio;
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
	public String index(Model model) {

		List<Espacio> destacados = List.of(

				espacioService.findAll().stream().filter(e -> e.getNombre().equalsIgnoreCase("Oficina individual"))
						.findFirst().orElse(null),

				espacioService.findAll().stream().filter(e -> e.getNombre().equalsIgnoreCase("Zona de equipos"))
						.findFirst().orElse(null),

				espacioService.findAll().stream().filter(e -> e.getNombre().equalsIgnoreCase("Sala de reuniones"))
						.findFirst().orElse(null)

		).stream().filter(e -> e != null).toList();

		model.addAttribute("espacios", destacados);

		model.addAttribute("totalEspacios", espacioService.findAll().size());
		model.addAttribute("totalReservas", reservaService.findAll().size());

		return "index";
	}

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