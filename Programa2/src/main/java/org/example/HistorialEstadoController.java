package org.example;

import java.util.List;

/**
 * Controlador encargado de gestionar el registro y consulta del
 * historial de cambios de estado de los equipos.
 * Interactúa con el servicio asociado para almacenar eventos
 * y recuperar registros históricos.
 */
public class HistorialEstadoController {

    /** Servicio que administra el almacenamiento del historial de estados. */
    private HistorialEstadoService historialService;

    /**
     * Construye el controlador e inicializa el servicio subyacente.
     */
    public HistorialEstadoController() {
        this.historialService = new HistorialEstadoService();
    }

    /**
     * Registra un cambio de estado para un equipo determinado.
     * Aplica validaciones básicas antes de delegar en el servicio.
     *
     * @param equipo          equipo cuyo estado fue modificado
     * @param estadoAnterior  estado previo del equipo
     * @param estadoNuevo     nuevo estado asignado
     * @param responsable     persona que autorizó el cambio
     * @param motivo          justificación del cambio
     * @return mensaje informativo indicando el resultado de la operación
     */
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

    /**
     * Recupera el historial completo de cambios de estado
     * asociados al equipo con el identificador indicado.
     *
     * @param idEquipo identificador del equipo a consultar
     * @return lista de registros históricos asociados
     */
    public List<HistorialEstado> obtenerHistorial(int idEquipo) {
        return historialService.obtenerHistorial(idEquipo);
    }
}


