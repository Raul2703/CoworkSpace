package com.salesianostriana.dam.coworkspace.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.exception.DuracionInvalidaException;
import com.salesianostriana.dam.coworkspace.exception.ReservaSolapadaException;
import com.salesianostriana.dam.coworkspace.model.Reserva;
import com.salesianostriana.dam.coworkspace.model.ReservaEspacio;
import com.salesianostriana.dam.coworkspace.repository.ReservaRepository;
import com.salesianostriana.dam.coworkspace.service.base.BaseServiceImpl;

@Service
public class ReservaService extends BaseServiceImpl<Reserva, Long, ReservaRepository> {

	@Override
	public Reserva save(Reserva reserva) {

		validarFecha(reserva);
		validarDuracion(reserva);
		validarSolapamientos(reserva);
		calcularPrecioTotal(reserva);

		for (ReservaEspacio reservaEspacio : reserva.getReservasEspacios()) {
			reservaEspacio.setReserva(reserva);
		}

		return super.save(reserva);
	}

	public List<Reserva> findByUsuarioNombre(String nombre) {
		return repository.findByUsuario_NombreIgnoreCase(nombre);
	}

	private void validarFecha(Reserva reserva) {

		if (reserva.getFecha() != null && reserva.getFecha().isBefore(LocalDate.now())) {
			throw new DuracionInvalidaException("No puedes hacer una reserva en una fecha pasada");
		}
	}

	private void validarDuracion(Reserva reserva) {

		int horaInicio = obtenerHora(reserva.getHoraInicio());
		int horaFin = obtenerHora(reserva.getHoraFin());

		if (horaFin <= horaInicio) {
			throw new DuracionInvalidaException("La hora de fin debe ser posterior a la hora de inicio");
		}
	}

	private void validarSolapamientos(Reserva nuevaReserva) {

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

			for (ReservaEspacio nuevoRe : nuevaReserva.getReservasEspacios()) {

				for (ReservaEspacio existenteRe : reserva.getReservasEspacios()) {

					boolean mismoEspacio = nuevoRe.getEspacio() != null && existenteRe.getEspacio() != null
							&& nuevoRe.getEspacio().getId().equals(existenteRe.getEspacio().getId());

					if (mismoEspacio) {
						throw new ReservaSolapadaException(
								"El espacio " + nuevoRe.getEspacio().getNombre() + " ya está reservado en ese horario");
					}
				}
			}
		}
	}

	private void calcularPrecioTotal(Reserva reserva) {

		int horaInicio = obtenerHora(reserva.getHoraInicio());
		int horaFin = obtenerHora(reserva.getHoraFin());
		int horasReservadas = horaFin - horaInicio;

		double totalPorHora = reserva.getReservasEspacios().stream().filter(re -> re.getEspacio() != null)
				.mapToDouble(re -> re.getEspacio().getPrecio()).sum();

		reserva.setPrecioTotal(horasReservadas * totalPorHora);
	}

	public int obtenerHora(String hora) {
		return Integer.parseInt(hora.substring(0, 2));
	}

}