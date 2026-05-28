package com.salesianostriana.dam.coworkspace.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.coworkspace.model.Reserva;

@Service
public class ReservaPdfService {

	public byte[] generarMisReservasPdf(List<Reserva> reservas, String usuario) throws IOException {

		try (PDDocument document = new PDDocument(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {

			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);

			PDPageContentStream content = new PDPageContentStream(document, page);
			float y = 760;

			escribir(content, "CoworkSpace", 50, y, PDType1Font.HELVETICA_BOLD, 20);
			y -= 28;
			escribir(content, "Reservas de " + usuario + " - " + LocalDate.now(), 50, y, PDType1Font.HELVETICA, 11);
			y -= 32;

			if (reservas.isEmpty()) {
				escribir(content, "No hay reservas registradas.", 50, y, PDType1Font.HELVETICA, 12);
			}

			for (Reserva reserva : reservas) {

				if (y < 120) {
					content.close();
					page = new PDPage(PDRectangle.A4);
					document.addPage(page);
					content = new PDPageContentStream(document, page);
					y = 760;
				}

				escribir(content, reserva.getNombreReserva(), 50, y, PDType1Font.HELVETICA_BOLD, 14);
				y -= 20;
				escribir(content, "Fecha: " + reserva.getFecha() + " | Horario: " + reserva.getHoraInicio() + " - "
						+ reserva.getHoraFin(), 50, y, PDType1Font.HELVETICA, 11);
				y -= 18;
				escribir(content, "Espacios: " + obtenerEspacios(reserva), 50, y, PDType1Font.HELVETICA, 11);
				y -= 18;
				escribir(content, "Estado: " + reserva.getEstadoActual().getTexto() + " | Precio: "
						+ reserva.getPrecioTotal() + " EUR", 50, y, PDType1Font.HELVETICA, 11);
				y -= 28;
			}

			content.close();
			document.save(output);

			return output.toByteArray();
		}
	}

	private String obtenerEspacios(Reserva reserva) {
		return reserva.getReservasEspacios().stream().filter(reservaEspacio -> reservaEspacio.getEspacio() != null)
				.map(reservaEspacio -> reservaEspacio.getEspacio().getNombre()).collect(Collectors.joining(", "));
	}

	private void escribir(PDPageContentStream content, String texto, float x, float y, PDType1Font font, float size)
			throws IOException {

		content.beginText();
		content.setFont(font, size);
		content.newLineAtOffset(x, y);
		content.showText(limpiarTexto(texto));
		content.endText();
	}

	private String limpiarTexto(String texto) {
		if (texto == null) {
			return "";
		}

		String normalizado = Normalizer.normalize(texto.replace("€", "EUR"), Normalizer.Form.NFD);

		return normalizado.replaceAll("\\p{M}", "").replaceAll("[^\\x20-\\x7E]", "");
	}

}
