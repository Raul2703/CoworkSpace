package com.salesianostriana.dam.coworkspace.model;

public enum EstadoReserva {
	
	PENDIENTE,
	CONFIRMADA,
	OCUPADO,
	CANCELADA,
	FINALIZADA;

	public String getTexto() {
		return switch (this) {
		case PENDIENTE -> "Pendiente";
		case CONFIRMADA -> "Confirmada";
		case OCUPADO -> "Ocupado";
		case CANCELADA -> "Cancelada";
		case FINALIZADA -> "Finalizada";
		};
	}

	public String getBadgeClass() {
		return switch (this) {
		case PENDIENTE -> "text-bg-warning";
		case CONFIRMADA -> "text-bg-success";
		case OCUPADO -> "text-bg-primary";
		case CANCELADA -> "text-bg-danger";
		case FINALIZADA -> "text-bg-secondary";
		};
	}

}
