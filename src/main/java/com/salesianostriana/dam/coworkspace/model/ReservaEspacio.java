package com.salesianostriana.dam.coworkspace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaEspacio {
	
	private Long id;
	private Reserva reserva;
	private Espacio espacio;
	private EstadoReserva estado;
	private String observaciones;

}
