package com.salesianostriana.dam.coworkspace.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
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

	@NotBlank(message = "El nombre de la reserva es obligatorio")
	private String nombreReserva;

	@Column(unique = true)
	private String codigo;

	@NotNull(message = "La fecha es obligatoria")
	@FutureOrPresent(message = "No puedes reservar en una fecha pasada")
	private LocalDate fecha;

	@NotBlank(message = "La hora de inicio es obligatoria")
	private String horaInicio;

	@NotBlank(message = "La hora de fin es obligatoria")
	private String horaFin;

	@Min(value = 1, message = "La duracion debe ser de al menos una hora")
	private Integer duracion;

	private Double precioTotal;

	@ManyToOne
	private Usuario usuario;

	@OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReservaEspacio> reservasEspacios = new ArrayList<>();

	@Transient
	public EstadoReserva getEstadoActual() {
		return reservasEspacios.stream().findFirst().map(ReservaEspacio::getEstado).orElse(EstadoReserva.PENDIENTE);
	}

}
