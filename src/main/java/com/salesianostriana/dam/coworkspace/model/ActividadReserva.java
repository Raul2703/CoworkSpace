package com.salesianostriana.dam.coworkspace.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActividadReserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime fechaHora;

	private String accion;

	private Long reservaId;

	private String reservaNombre;

	private String usuario;

	private String detalle;

	@PrePersist
	public void prePersist() {
		if (fechaHora == null) {
			fechaHora = LocalDateTime.now();
		}
	}

}
