package com.salesianostriana.dam.coworkspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.service.EspacioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EspacioController {

	private final EspacioService espacioService;

	@GetMapping("/espacios")
	public String getEspacios(@RequestParam(required = false) String busqueda,
			@RequestParam(required = false) Integer capacidadMinima,
			@RequestParam(required = false) Double precioMaximo, Model model) {

		model.addAttribute("espacios", espacioService.filtrar(busqueda, capacidadMinima, precioMaximo));
		model.addAttribute("busqueda", busqueda);
		model.addAttribute("capacidadMinima", capacidadMinima);
		model.addAttribute("precioMaximo", precioMaximo);

		return "espacios";
	}

	@GetMapping("/espacios/nuevo")
	public String nuevoEspacio(Model model) {

		model.addAttribute("espacio", new Espacio());

		return "form-espacio";
	}

	@PostMapping("/espacios/guardar")
	public String guardarEspacio(@Valid @ModelAttribute Espacio espacio, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {

			return "form-espacio";
		}

		espacioService.save(espacio);
		redirectAttributes.addFlashAttribute("mensajeExito", "Espacio guardado correctamente.");

		return "redirect:/espacios";
	}

	@GetMapping("/espacios/editar/{id}")
	public String editarEspacio(@PathVariable Long id, Model model) {

		model.addAttribute("espacio", espacioService.findById(id).orElse(null));

		return "form-espacio";
	}

	@GetMapping("/espacios/borrar/{id}")
	public String borrarEspacio(@PathVariable Long id, RedirectAttributes redirectAttributes) {

		espacioService.deleteById(id);
		redirectAttributes.addFlashAttribute("mensajeExito", "Espacio eliminado correctamente.");

		return "redirect:/espacios";
	}

}
