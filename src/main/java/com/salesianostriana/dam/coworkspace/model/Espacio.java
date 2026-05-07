package com.salesianostriana.dam.coworkspace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Espacio {

	@Id
	private Long id;

	private String nombre;
	private Integer capacidad;
	private Double precio;

}
