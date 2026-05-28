package com.salesianostriana.dam.coworkspace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.model.ActividadReserva;
import com.salesianostriana.dam.coworkspace.model.Reserva;
import com.salesianostriana.dam.coworkspace.repository.ActividadReservaRepository;
import com.salesianostriana.dam.coworkspace.service.base.BaseServiceImpl;

@Service
public class ActividadReservaService
		extends BaseServiceImpl<ActividadReserva, Long, ActividadReservaRepository> {

	public void registrar(String accion, Reserva reserva, String usuario, String detalle) {

		ActividadReserva actividad = new ActividadReserva();
		actividad.setAccion(accion);
		actividad.setReservaId(reserva != null ? reserva.getId() : null);
		actividad.setReservaNombre(reserva != null ? reserva.getNombreReserva() : "Sin reserva");
		actividad.setUsuario(usuario);
		actividad.setDetalle(detalle);

		save(actividad);
	}

	public List<ActividadReserva> findUltimas() {
		return repository.findTop6ByOrderByFechaHoraDesc();
	}

}
