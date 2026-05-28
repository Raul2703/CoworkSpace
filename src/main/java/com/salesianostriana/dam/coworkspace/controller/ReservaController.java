package com.salesianostriana.dam.coworkspace.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.coworkspace.exception.EspacioNoSeleccionadoException;
import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.model.EstadoReserva;
import com.salesianostriana.dam.coworkspace.model.Reserva;
import com.salesianostriana.dam.coworkspace.model.ReservaEspacio;
import com.salesianostriana.dam.coworkspace.model.Usuario;
import com.salesianostriana.dam.coworkspace.service.ActividadReservaService;
import com.salesianostriana.dam.coworkspace.service.EspacioService;
import com.salesianostriana.dam.coworkspace.service.ReservaPdfService;
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
	private final ActividadReservaService actividadReservaService;
	private final ReservaPdfService reservaPdfService;

	@GetMapping("/reservas")
	public String getReservas(Model model) {
		model.addAttribute("reservas", reservaService.findAll());
		model.addAttribute("estadosReserva", EstadoReserva.values());
		return "reservas";
	}

	@GetMapping("/mis-reservas")
	public String misReservas(Authentication authentication, Model model) {
		model.addAttribute("reservas", reservaService.findByUsuarioNombre(authentication.getName()));
		return "mis-reservas";
	}

	@GetMapping("/mis-reservas/pdf")
	public ResponseEntity<byte[]> exportarMisReservas(Authentication authentication) throws IOException {

		List<Reserva> reservas = reservaService.findByUsuarioNombre(authentication.getName());
		byte[] pdf = reservaPdfService.generarMisReservasPdf(reservas, authentication.getName());

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=mis-reservas.pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(pdf);
	}

	@GetMapping("/reservas/nuevo")
	public String nuevaReserva(Model model) {
		model.addAttribute("reserva", new Reserva());
		cargarEspacios(model);
		return "form-reserva";
	}

	@PostMapping("/reservas/guardar")
	public String guardarReserva(@Valid @ModelAttribute Reserva reservaForm, BindingResult result,
			@RequestParam(name = "espacioIds", required = false) List<Long> espacioIds, Model model,
			Authentication authentication, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			cargarEspacios(model);
			return "form-reserva";
		}

		if (espacioIds == null || espacioIds.isEmpty()) {
			throw new EspacioNoSeleccionadoException("Debes seleccionar al menos un espacio");
		}

		Reserva reserva;
		Usuario usuarioAutenticado = obtenerUsuarioAutenticado(authentication);

		boolean esEdicion = reservaForm.getId() != null;

		if (esEdicion) {

			reserva = reservaService.findById(reservaForm.getId()).orElseThrow();

			reserva.setNombreReserva(reservaForm.getNombreReserva());
			reserva.setFecha(reservaForm.getFecha());
			reserva.setHoraInicio(reservaForm.getHoraInicio());
			reserva.setHoraFin(reservaForm.getHoraFin());
			reserva.setUsuario(usuarioAutenticado);
			reserva.getReservasEspacios().clear();

		} else {
			reserva = reservaForm;
			reserva.setUsuario(usuarioAutenticado);
		}

		for (Long espacioId : espacioIds) {

			Espacio espacio = espacioService.findById(espacioId).orElse(null);

			if (espacio != null) {

				ReservaEspacio reservaEspacio = ReservaEspacio.builder().reserva(reserva).espacio(espacio)
						.estado(EstadoReserva.PENDIENTE).observaciones("").build();

				reserva.getReservasEspacios().add(reservaEspacio);
			}
		}

		Reserva reservaGuardada = reservaService.save(reserva);
		actividadReservaService.registrar(esEdicion ? "Actualizacion" : "Creacion", reservaGuardada,
				obtenerNombreUsuario(authentication),
				esEdicion ? "Se actualizaron los datos de la reserva" : "Se creo una nueva reserva");

		boolean esAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

		if (esAdmin) {
			redirectAttributes.addFlashAttribute("mensajeExito", "Reserva guardada correctamente.");
			return "redirect:/reservas";
		}

		redirectAttributes.addFlashAttribute("mensajeExito", "Reserva registrada correctamente.");
		return "redirect:/reservas/confirmada";
	}

	@GetMapping("/reservas/confirmada")
	public String reservaConfirmada() {
		return "reserva-confirmada";
	}

	@GetMapping("/reservas/editar/{id}")
	public String editarReserva(@PathVariable Long id, Model model) {
		model.addAttribute("reserva", reservaService.findById(id).orElse(null));
		cargarEspacios(model);
		return "form-reserva";
	}

	@GetMapping("/reservas/borrar/{id}")
	public String borrarReserva(@PathVariable Long id, Authentication authentication,
			RedirectAttributes redirectAttributes) {

		Reserva reserva = reservaService.findById(id).orElseThrow();
		actividadReservaService.registrar("Borrado", reserva, obtenerNombreUsuario(authentication),
				"Se elimino la reserva");
		reservaService.deleteById(id);
		redirectAttributes.addFlashAttribute("mensajeExito", "Reserva eliminada correctamente.");

		return "redirect:/reservas";
	}

	@PostMapping("/reservas/{id}/estado")
	public String actualizarEstado(@PathVariable Long id, @RequestParam EstadoReserva estado,
			@RequestParam(required = false) String observaciones, Authentication authentication,
			RedirectAttributes redirectAttributes) {

		Reserva reserva = reservaService.actualizarEstado(id, estado, observaciones);
		actividadReservaService.registrar("Estado", reserva, obtenerNombreUsuario(authentication),
				"Estado actualizado a " + estado.getTexto());
		redirectAttributes.addFlashAttribute("mensajeExito", "Estado actualizado correctamente.");

		return "redirect:/reservas";
	}

	private void cargarEspacios(Model model) {
		model.addAttribute("espacios", espacioService.findAll());
	}

	private Usuario obtenerUsuarioAutenticado(Authentication authentication) {

		String nombre = authentication.getName();

		return usuarioService.findByNombreIgnoreCase(nombre).orElseThrow();
	}

	private String obtenerNombreUsuario(Authentication authentication) {
		return authentication != null ? authentication.getName() : "sistema";
	}

}
