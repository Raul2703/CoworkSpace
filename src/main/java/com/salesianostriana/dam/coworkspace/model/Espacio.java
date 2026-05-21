package com.salesianostriana.dam.coworkspace.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Espacio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nombre;

	@NotNull
	@Min(1)
	private Integer capacidad;

	@NotNull
	@Min(0)
	private Double precio;

	@Builder.Default
	@OneToMany(mappedBy = "espacio", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReservaEspacio> reservasEspacios = new ArrayList<>();

}