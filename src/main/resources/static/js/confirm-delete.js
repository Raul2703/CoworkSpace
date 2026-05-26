document.addEventListener("DOMContentLoaded", () => {
	document.querySelectorAll(".js-confirm-delete").forEach((button) => {
		button.addEventListener("click", (event) => {
			const message = button.dataset.confirmMessage || "Seguro que quieres borrar este elemento?";

			if (!confirm(message)) {
				event.preventDefault();
			}
		});
	});
});
