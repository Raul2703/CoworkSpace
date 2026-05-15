package com.salesianostriana.dam.coworkspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.coworkspace.model.Usuario;
import com.salesianostriana.dam.coworkspace.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService usuarioService;

	@GetMapping("/usuarios")
	public String getUsuarios(Model model) {
		model.addAttribute("usuarios", usuarioService.findAll());
		return "usuarios";
	}

	@GetMapping("/usuarios/nuevo")
	public String nuevoUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "form-usuario";
	}

	@PostMapping("/usuarios/guardar")
	public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result) {

		if (result.hasErrors()) {
			return "form-usuario";
		}

		usuarioService.save(usuario);
		return "redirect:/usuarios";
	}

	@GetMapping("/usuarios/editar/{id}")
	public String editarUsuario(@PathVariable Long id, Model model) {
		model.addAttribute("usuario", usuarioService.findById(id).orElse(null));
		return "form-usuario";
	}

	@GetMapping("/usuarios/borrar/{id}")
	public String borrarUsuario(@PathVariable Long id) {
		usuarioService.deleteById(id);
		return "redirect:/usuarios";
	}

}