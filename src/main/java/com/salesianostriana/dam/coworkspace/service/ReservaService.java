package com.salesianostriana.dam.coworkspace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.model.Reserva;
import com.salesianostriana.dam.coworkspace.repository.ReservaRepository;
import com.salesianostriana.dam.coworkspace.service.base.BaseServiceImpl;

@Service
public class ReservaService extends BaseServiceImpl<Reserva, Long, ReservaRepository> {

	private static final double PRECIO_HORA = 30.0;

	@Override
	public Reserva save(Reserva reserva) {

		calcularPrecioTotal(reserva);

		return super.save(reserva);
	}

	public boolean existeSolapamiento(Reserva nuevaReserva) {

		List<Reserva> reservas = findAll();

		int nuevaInicio = obtenerHora(nuevaReserva.getHoraInicio());
		int nuevaFin = obtenerHora(nuevaReserva.getHoraFin());

		for (Reserva reserva : reservas) {

			if (nuevaReserva.getId() != null && nuevaReserva.getId().equals(reserva.getId())) {
				continue;
			}

			boolean mismoEspacio = reserva.getEspacio() != null && nuevaReserva.getEspacio() != null
					&& reserva.getEspacio().getId().equals(nuevaReserva.getEspacio().getId());

			boolean mismaFecha = reserva.getFecha() != null && reserva.getFecha().equals(nuevaReserva.getFecha());

			int inicioExistente = obtenerHora(reserva.getHoraInicio());
			int finExistente = obtenerHora(reserva.getHoraFin());

			boolean seSolapa = nuevaInicio < finExistente && nuevaFin > inicioExistente;

			if (mismoEspacio && mismaFecha && seSolapa) {
				return true;
			}
		}

		return false;
	}

	private void calcularPrecioTotal(Reserva reserva) {

		int horaInicio = obtenerHora(reserva.getHoraInicio());
		int horaFin = obtenerHora(reserva.getHoraFin());

		int horasReservadas = horaFin - horaInicio;

		reserva.setPrecioTotal(horasReservadas * PRECIO_HORA);
	}

	private int obtenerHora(String hora) {
		return Integer.parseInt(hora.substring(0, 2));
	}

}