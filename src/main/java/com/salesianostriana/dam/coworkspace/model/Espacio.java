package com.salesianostriana.dam.coworkspace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Espacio {
	
	private Long id;
	private String nombre;
	private Integer capacidad;
	private Double precio;

}
