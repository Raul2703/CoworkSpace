package com.salesianostriana.dam.coworkspace.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.model.Reserva;
import com.salesianostriana.dam.coworkspace.model.ReservaEspacio;
import com.salesianostriana.dam.coworkspace.repository.ReservaRepository;
import com.salesianostriana.dam.coworkspace.service.base.BaseServiceImpl;

@Service
public class ReservaService extends BaseServiceImpl<Reserva, Long, ReservaRepository> {

	@Override
	public Reserva save(Reserva reserva) {

		calcularPrecioTotal(reserva);

		for (ReservaEspacio reservaEspacio : reserva.getReservasEspacios()) {
			reservaEspacio.setReserva(reserva);
		}

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

			boolean mismaFecha = reserva.getFecha() != null && reserva.getFecha().equals(nuevaReserva.getFecha());

			if (!mismaFecha) {
				continue;
			}

			int inicioExistente = obtenerHora(reserva.getHoraInicio());
			int finExistente = obtenerHora(reserva.getHoraFin());

			boolean seSolapa = nuevaInicio < finExistente && nuevaFin > inicioExistente;

			if (!seSolapa) {
				continue;
			}

			for (ReservaEspacio nuevaRE : nuevaReserva.getReservasEspacios()) {
				for (ReservaEspacio existenteRE : reserva.getReservasEspacios()) {

					boolean mismoEspacio = nuevaRE.getEspacio() != null && existenteRE.getEspacio() != null
							&& nuevaRE.getEspacio().getId().equals(existenteRE.getEspacio().getId());

					if (mismoEspacio) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private void calcularPrecioTotal(Reserva reserva) {

		int horaInicio = obtenerHora(reserva.getHoraInicio());
		int horaFin = obtenerHora(reserva.getHoraFin());

		int horasReservadas = horaFin - horaInicio;

		double precioTotal = 0.0;

		for (ReservaEspacio reservaEspacio : reserva.getReservasEspacios()) {
			if (reservaEspacio.getEspacio() != null) {
				precioTotal += horasReservadas * reservaEspacio.getEspacio().getPrecio();
			}
		}

		reserva.setPrecioTotal(precioTotal);
	}

	public int obtenerHora(String hora) {
		return Integer.parseInt(hora.substring(0, 2));
	}

}