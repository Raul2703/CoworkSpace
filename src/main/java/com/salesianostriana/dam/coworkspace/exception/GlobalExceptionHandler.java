package com.salesianostriana.dam.coworkspace.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ReservaSolapadaException.class)
	public String handleReservaSolapada(ReservaSolapadaException ex, Model model) {

		model.addAttribute("error", ex.getMessage());

		return "error/error-personalizado";
	}

	@ExceptionHandler(DuracionInvalidaException.class)
	public String handleDuracionInvalida(DuracionInvalidaException ex, Model model) {

		model.addAttribute("error", ex.getMessage());

		return "error/error-personalizado";
	}

	@ExceptionHandler(EspacioNoSeleccionadoException.class)
	public String handleEspacioNoSeleccionado(EspacioNoSeleccionadoException ex, Model model) {

		model.addAttribute("error", ex.getMessage());

		return "error/error-personalizado";
	}

}