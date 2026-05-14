package com.salesianostriana.dam.coworkspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

		int horaInicio = Integer.parseInt(reserva.getHoraInicio().substring(0, 2));
		int horaFin = Integer.parseInt(reserva.getHoraFin().substring(0, 2));

		if (horaFin <= horaInicio) {

			model.addAttribute("errorHora", "La hora de fin debe ser posterior a la hora de inicio");

			model.addAttribute("usuarios", usuarioService.findAll());
			model.addAttribute("espacios", espacioService.findAll());

			return "form-reserva";
		}

		reservaService.save(reserva);

		return "redirect:/reservas";
	}

	@GetMapping("/reservas/editar/{id}")
	public String editarReserva(@PathVariable Long id, Model model) {

		Reserva reserva = reservaService.findById(id).orElse(null);

		model.addAttribute("reserva", reserva);
		model.addAttribute("usuarios", usuarioService.findAll());
		model.addAttribute("espacios", espacioService.findAll());

		return "form-reserva";
	}

	@GetMapping("/reservas/borrar/{id}")
	public String borrarReserva(@PathVariable Long id) {

		reservaService.deleteById(id);

		return "redirect:/reservas";
	}

}