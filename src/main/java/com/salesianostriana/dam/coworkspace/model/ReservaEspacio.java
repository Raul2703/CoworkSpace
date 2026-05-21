package com.salesianostriana.dam.coworkspace.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaEspacio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Reserva reserva;

	@ManyToOne
	private Espacio espacio;

	@Enumerated(EnumType.STRING)
	private EstadoReserva estado;

	private String observaciones;

}