package com.salesianostriana.dam.coworkspace.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {

	private String codigo;
	private LocalDate fecha;
	private Integer duracion;
	private Double precioTotal;
}
