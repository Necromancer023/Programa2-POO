package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de administrar el historial de cambios de estado
 * realizados sobre los equipos.
 * Utiliza una estructura en memoria basada en un mapa que asocia cada
 * equipo con su lista de registros históricos.
 */
public class HistorialEstadoService {

    /** Estructura que relaciona un equipo (por ID) con su lista de cambios de estado. */
    private Map<Integer, List<HistorialEstado>> historialPorEquipo;

    /**
     * Inicializa el servicio y crea la estructura base donde se almacenarán
     * los historiales de cambio por equipo.
     */
    public HistorialEstadoService() {
        this.historialPorEquipo = new HashMap<>();
    }

    /**
     * Registra un nuevo cambio de estado para un equipo.
     * Si el equipo aún no tiene historial, se crea automáticamente
     * una nueva lista asociada en el mapa.
     *
     * @param equipo          equipo cuyo estado ha cambiado
     * @param estadoAnterior  estado previo del equipo
     * @param estadoNuevo     nuevo estado asignado
     * @param responsable     persona que autorizó el cambio
     * @param motivo          justificación del cambio
     * @return {@code true} si el registro se almacenó correctamente;
     *         {@code false} si el equipo no es válido
     */
    public boolean registrarCambioEstado(Equipo equipo,
                                         Equipo.EstadoEquipo estadoAnterior,
                                         Equipo.EstadoEquipo estadoNuevo,
                                         String responsable,
                                         String motivo) {

        if (equipo == null) return false;

        List<HistorialEstado> lista = historialPorEquipo
                .computeIfAbsent(equipo.getId(), k -> new ArrayList<>());

        HistorialEstado registro = new HistorialEstado(
                java.time.LocalDate.now(),
                estadoAnterior,
                estadoNuevo,
                responsable,
                motivo
        );

        lista.add(registro);
        return true;
    }

    /**
     * Obtiene el listado de cambios de estado registrados para un equipo específico.
     * Si no existe historial previo, se retorna una lista vacía.
     *
     * @param idEquipo identificador del equipo consultado
     * @return lista de cambios registrados para el equipo
     */
    public List<HistorialEstado> obtenerHistorial(int idEquipo) {
        return historialPorEquipo.getOrDefault(idEquipo, new ArrayList<>());
    }
}


