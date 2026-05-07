package com.salesianostriana.dam.coworkspace.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reserva {

	@Id
	private Long id;

	private String codigo;
	private LocalDate fecha;
	private int duracion;
	private double precioTotal;

}