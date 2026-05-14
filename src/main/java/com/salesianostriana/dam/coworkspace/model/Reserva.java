package com.salesianostriana.dam.coworkspace.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nombreReserva;

	@NotNull
	private LocalDate fecha;

	@NotBlank
	private String horaInicio;

	@NotBlank
	private String horaFin;

	private Double precioTotal;

	@ManyToOne
	private Usuario usuario;

	@ManyToOne
	private Espacio espacio;

}