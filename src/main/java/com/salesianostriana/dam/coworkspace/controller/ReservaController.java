package com.salesianostriana.dam.coworkspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.coworkspace.model.Reserva;
import com.salesianostriana.dam.coworkspace.service.EspacioService;
import com.salesianostriana.dam.coworkspace.service.ReservaService;
import com.salesianostriana.dam.coworkspace.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservaController {

	private final ReservaService reservaService;
	private final UsuarioService usuarioService;
	private final EspacioService espacioService;
	

	@GetMapping("/reservas")
	public String getReservas(Model model) {

		model.addAttribute("reservas", reservaService.findAll());

		return "reservas";
	}

	@GetMapping("/reservas/nuevo")
	public String nuevaReserva(Model model) {

		model.addAttribute("reserva", new Reserva());
		model.addAttribute("usuarios", usuarioService.findAll());
		model.addAttribute("espacios", espacioService.findAll());

		return "form-reserva";
	}

	@PostMapping("/reservas/guardar")
	public String guardarReserva(@Valid @ModelAttribute Reserva reserva, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("usuarios", usuarioService.findAll());
			model.addAttribute("espacios", espacioService.findAll());
			return "form-reserva";
		}

		reservaService.save(reserva);

		return "redirect:/reservas";
	}

}