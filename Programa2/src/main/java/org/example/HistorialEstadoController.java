package org.example;

import java.util.List;

public class HistorialEstadoController {

    private HistorialEstadoService historialService;

    public HistorialEstadoController() {
        this.historialService = new HistorialEstadoService();
    }

    public String registrarCambioEstado(Equipo equipo,
                                        Equipo.EstadoEquipo estadoAnterior,
                                        Equipo.EstadoEquipo estadoNuevo,
                                        String responsable,
                                        String motivo) {

        if (equipo == null) return "Equipo no válido.";
        if (estadoAnterior == estadoNuevo) return "El estado no ha cambiado.";
        if (motivo == null || motivo.isBlank()) return "Debe indicar un motivo.";
        if (responsable == null || responsable.isBlank())
            responsable = "No especificado";

        boolean ok = historialService.registrarCambioEstado(
                equipo, estadoAnterior, estadoNuevo, responsable, motivo
        );

        return ok ? "Cambio de estado registrado."
                  : "No se pudo registrar el cambio.";
    }

    public List<HistorialEstado> obtenerHistorial(int idEquipo) {
        return historialService.obtenerHistorial(idEquipo);
    }
}

