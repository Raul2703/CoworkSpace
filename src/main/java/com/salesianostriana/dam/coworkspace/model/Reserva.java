	package com.salesianostriana.dam.coworkspace.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
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

	@OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReservaEspacio> reservasEspacios = new ArrayList<>();

}