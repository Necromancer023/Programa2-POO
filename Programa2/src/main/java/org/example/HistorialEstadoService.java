package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorialEstadoService {

    // Mapa: ID del equipo → lista de cambios
    private Map<Integer, List<HistorialEstado>> historialPorEquipo;

    public HistorialEstadoService() {
        this.historialPorEquipo = new HashMap<>();
    }

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

    public List<HistorialEstado> obtenerHistorial(int idEquipo) {
        return historialPorEquipo.getOrDefault(idEquipo, new ArrayList<>());
    }
}

