package com.salesianostriana.dam.coworkspace.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.salesianostriana.dam.coworkspace.error.EspacioNoSeleccionadoException;
import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.model.EstadoReserva;
import com.salesianostriana.dam.coworkspace.model.Reserva;
import com.salesianostriana.dam.coworkspace.model.ReservaEspacio;
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

		cargarUsuariosYEspacios(model);

		return "form-reserva";
	}

	@PostMapping("/reservas/guardar")
	public String guardarReserva(@Valid @ModelAttribute Reserva reserva, BindingResult result,
			@RequestParam(name = "espacioIds", required = false) List<Long> espacioIds, Model model) {

		if (result.hasErrors()) {

			cargarUsuariosYEspacios(model);

			return "form-reserva";
		}

		if (espacioIds == null || espacioIds.isEmpty()) {

			throw new EspacioNoSeleccionadoException("Debes seleccionar al menos un espacio");
		}

		reserva.getReservasEspacios().clear();

		for (Long espacioId : espacioIds) {

			Espacio espacio = espacioService.findById(espacioId).orElse(null);

			if (espacio != null) {

				ReservaEspacio reservaEspacio = new ReservaEspacio();

				reservaEspacio.setReserva(reserva);
				reservaEspacio.setEspacio(espacio);
				reservaEspacio.setEstado(EstadoReserva.PENDIENTE);
				reservaEspacio.setObservaciones("");

				reserva.getReservasEspacios().add(reservaEspacio);
			}
		}

		reservaService.save(reserva);

		return "redirect:/reservas";
	}

	@GetMapping("/reservas/editar/{id}")
	public String editarReserva(@PathVariable Long id, Model model) {

		model.addAttribute("reserva", reservaService.findById(id).orElse(null));

		cargarUsuariosYEspacios(model);

		return "form-reserva";
	}

	@GetMapping("/reservas/borrar/{id}")
	public String borrarReserva(@PathVariable Long id) {

		reservaService.deleteById(id);

		return "redirect:/reservas";
	}

	private void cargarUsuariosYEspacios(Model model) {

		model.addAttribute("usuarios", usuarioService.findAll());

		model.addAttribute("espacios", espacioService.findAll());
	}

}