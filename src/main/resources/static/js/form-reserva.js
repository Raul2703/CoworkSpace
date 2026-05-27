class FormReserva {

	constructor() {
		this.horaInicio = document.querySelector('[name="horaInicio"]');
		this.horaFin = document.querySelector('[name="horaFin"]');
		this.espacio = document.querySelector('[name="espacioIds"]');
		this.precioEstimado = document.getElementById("precioEstimado");
	}

	init() {
		if (!this.horaInicio || !this.horaFin || !this.espacio || !this.precioEstimado) {
			return;
		}

		this.horaInicio.addEventListener("change", () => this.calcularPrecio());
		this.horaFin.addEventListener("change", () => this.calcularPrecio());
		this.espacio.addEventListener("change", () => this.calcularPrecio());

		this.calcularPrecio();
	}

	calcularPrecio() {
		const inicio = parseInt(this.horaInicio.value, 10);
		const fin = parseInt(this.horaFin.value, 10);

		if (isNaN(inicio) || isNaN(fin) || fin <= inicio || !this.espacio.value) {
			this.precioEstimado.textContent = "0.00";
			return;
		}

		const horas = fin - inicio;
		const precioHora = parseFloat(this.espacio.selectedOptions[0]?.dataset.precio) || 0;
		const total = horas * precioHora;

		this.precioEstimado.textContent = total.toFixed(2);
	}
}

document.addEventListener("DOMContentLoaded", () => {
	new FormReserva().init();
});
