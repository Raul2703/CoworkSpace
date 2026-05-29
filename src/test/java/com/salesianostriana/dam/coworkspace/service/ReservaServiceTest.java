package com.salesianostriana.dam.coworkspace.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.salesianostriana.dam.coworkspace.exception.DuracionInvalidaException;
import com.salesianostriana.dam.coworkspace.exception.ReservaSolapadaException;
import com.salesianostriana.dam.coworkspace.model.Espacio;
import com.salesianostriana.dam.coworkspace.model.EstadoReserva;
import com.salesianostriana.dam.coworkspace.model.Reserva;
import com.salesianostriana.dam.coworkspace.model.ReservaEspacio;
import com.salesianostriana.dam.coworkspace.model.Usuario;

@SpringBootTest
@Transactional
class ReservaServiceTest {

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private EspacioService espacioService;

	@Autowired
	private UsuarioService usuarioService;

	@Test
	void calculaDuracionYPrecioAlGuardarReserva() {

		Espacio espacio = crearEspacio("Sala test precio", 10.0);
		Usuario usuario = crearUsuario();
		Reserva reserva = crearReserva("Reserva precio", "09:00", "12:00", usuario, espacio);

		Reserva guardada = reservaService.save(reserva);

		assertThat(guardada.getDuracion()).isEqualTo(3);
		assertThat(guardada.getPrecioTotal()).isEqualTo(30.0);
		assertThat(guardada.getCodigo()).startsWith("RES-");
		assertThat(guardada.getReservasEspacios()).allMatch(re -> re.getReserva().equals(guardada));
	}

	@Test
	void rechazaReservaSolapadaEnElMismoEspacio() {

		Espacio espacio = crearEspacio("Sala test solape", 12.0);
		Usuario usuario = crearUsuario();

		reservaService.save(crearReserva("Reserva inicial", "10:00", "12:00", usuario, espacio));

		Reserva reservaSolapada = crearReserva("Reserva solapada", "11:00", "13:00", usuario, espacio);

		assertThatThrownBy(() -> reservaService.save(reservaSolapada))
				.isInstanceOf(ReservaSolapadaException.class);
	}

	@Test
	void rechazaHoraFinAnteriorALaHoraInicio() {

		Espacio espacio = crearEspacio("Sala test duracion", 8.0);
		Usuario usuario = crearUsuario();
		Reserva reserva = crearReserva("Reserva invalida", "13:00", "11:00", usuario, espacio);

		assertThatThrownBy(() -> reservaService.save(reserva)).isInstanceOf(DuracionInvalidaException.class);
	}

	private Reserva crearReserva(String nombre, String horaInicio, String horaFin, Usuario usuario, Espacio espacio) {

		Reserva reserva = new Reserva();
		reserva.setNombreReserva(nombre);
		reserva.setDni("12345678A");
		reserva.setFecha(LocalDate.now().plusDays(1));
		reserva.setHoraInicio(horaInicio);
		reserva.setHoraFin(horaFin);
		reserva.setUsuario(usuario);

		reserva.getReservasEspacios().add(ReservaEspacio.builder()
				.espacio(espacio)
				.estado(EstadoReserva.PENDIENTE)
				.observaciones("")
				.build());

		return reserva;
	}

	private Espacio crearEspacio(String nombre, double precio) {

		return espacioService.save(Espacio.builder()
				.nombre(nombre + " " + System.nanoTime())
				.capacidad(4)
				.precio(precio)
				.build());
	}

	private Usuario crearUsuario() {

		String valorUnico = String.valueOf(System.nanoTime());

		Usuario usuario = new Usuario();
		usuario.setNombre("test" + valorUnico);
		usuario.setEmail("test" + valorUnico + "@coworkspace.local");
		usuario.setTelefono("600000000");
		usuario.setPassword("test");
		usuario.setRol("USER");

		return usuarioService.save(usuario);
	}

}
