package com.salesianostriana.dam.coworkspace.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.salesianostriana.dam.coworkspace.exception.EspacioNoSeleccionadoException;
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
	public String guardarReserva(@Valid @ModelAttribute Reserva reservaForm, BindingResult result,
			@RequestParam(name = "espacioIds", required = false) List<Long> espacioIds, Model model,
			Authentication authentication) {

		if (result.hasErrors()) {
			cargarUsuariosYEspacios(model);
			return "form-reserva";
		}

		if (espacioIds == null || espacioIds.isEmpty()) {
			throw new EspacioNoSeleccionadoException("Debes seleccionar al menos un espacio");
		}

		Reserva reserva;

		if (reservaForm.getId() != null) {

			reserva = reservaService.findById(reservaForm.getId()).orElseThrow();

			reserva.setNombreReserva(reservaForm.getNombreReserva());
			reserva.setFecha(reservaForm.getFecha());
			reserva.setHoraInicio(reservaForm.getHoraInicio());
			reserva.setHoraFin(reservaForm.getHoraFin());
			reserva.setUsuario(reservaForm.getUsuario());

			reserva.getReservasEspacios().clear();

		} else {
			reserva = reservaForm;
		}

		for (Long espacioId : espacioIds) {

			Espacio espacio = espacioService.findById(espacioId).orElse(null);

			if (espacio != null) {

				ReservaEspacio reservaEspacio = ReservaEspacio.builder().reserva(reserva).espacio(espacio)
						.estado(EstadoReserva.PENDIENTE).observaciones("").build();

				reserva.getReservasEspacios().add(reservaEspacio);
			}
		}

		reservaService.save(reserva);

		boolean esAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

		if (esAdmin) {
			return "redirect:/reservas";
		}

		return "redirect:/reservas/confirmada";
	}

	@GetMapping("/reservas/confirmada")
	public String reservaConfirmada() {
		return "reserva-confirmada";
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