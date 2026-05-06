package com.salesianostriana.dam.coworkspace.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva {
	
	private Long id;
	private String codigo;
	private LocalDate fecha;
	private int duracion;
	private double precioTotal;

	private List<Espacio> espacios;
}