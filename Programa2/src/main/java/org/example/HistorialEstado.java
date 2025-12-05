package org.example;

import java.time.LocalDate;

public class HistorialEstado {

    private LocalDate fechaCambio;
    private Equipo.EstadoEquipo estadoAnterior;
    private Equipo.EstadoEquipo estadoNuevo;
    private String responsable;
    private String motivo;

    public HistorialEstado(LocalDate fechaCambio,
                           Equipo.EstadoEquipo estadoAnterior,
                           Equipo.EstadoEquipo estadoNuevo,
                           String responsable,
                           String motivo) {

        this.fechaCambio = fechaCambio;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.responsable = responsable;
        this.motivo = motivo;
    }

    public LocalDate getFechaCambio() {
        return fechaCambio;
    }

    public Equipo.EstadoEquipo getEstadoAnterior() {
        return estadoAnterior;
    }

    public Equipo.EstadoEquipo getEstadoNuevo() {
        return estadoNuevo;
    }

    public String getResponsable() {
        return responsable;
    }

    public String getMotivo() {
        return motivo;
    }

    @Override
    public String toString() {
        return "[" + fechaCambio + "] "
                + "Estado: " + estadoAnterior + " → " + estadoNuevo
                + " | Responsable: " + responsable
                + " | Motivo: " + motivo;
    }
}

