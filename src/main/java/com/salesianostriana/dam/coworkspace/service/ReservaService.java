package com.salesianostriana.dam.coworkspace.service;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.model.Reserva;
import com.salesianostriana.dam.coworkspace.repository.ReservaRepository;
import com.salesianostriana.dam.coworkspace.service.base.BaseServiceImpl;

@Service
public class ReservaService extends BaseServiceImpl<Reserva, Long, ReservaRepository> {

	@Override
	public Reserva save(Reserva reserva) {

		calcularPrecioTotal(reserva);

		return super.save(reserva);
	}

	private void calcularPrecioTotal(Reserva reserva) {

		int horaInicio = Integer.parseInt(reserva.getHoraInicio().substring(0, 2));
		int horaFin = Integer.parseInt(reserva.getHoraFin().substring(0, 2));

		int horasReservadas = horaFin - horaInicio;

		reserva.setPrecioTotal(horasReservadas * 150.0);
	}

}