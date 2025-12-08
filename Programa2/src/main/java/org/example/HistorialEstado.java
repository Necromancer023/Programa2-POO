package org.example;

import java.time.LocalDate;

/**
 * Representa un registro histórico de cambio de estado en un equipo.
 * Cada instancia almacena información sobre cuándo ocurrió el cambio,
 * cuál era el estado previo, cuál es el nuevo estado, quién lo autorizó
 * y el motivo del ajuste.
 */
public class HistorialEstado {

    /** Fecha en que se realizó el cambio de estado. */
    private LocalDate fechaCambio;

    /** Estado anterior del equipo antes del cambio. */
    private Equipo.EstadoEquipo estadoAnterior;

    /** Nuevo estado asignado al equipo después del cambio. */
    private Equipo.EstadoEquipo estadoNuevo;

    /** Persona responsable que autorizó o ejecutó el cambio. */
    private String responsable;

    /** Justificación o causa del cambio de estado. */
    private String motivo;

    /**
     * Crea un nuevo registro histórico asociado a un cambio de estado.
     *
     * @param fechaCambio     fecha en que se produjo el cambio
     * @param estadoAnterior  estado previo del equipo
     * @param estadoNuevo     nuevo estado asignado al equipo
     * @param responsable     usuario responsable del cambio
     * @param motivo          razón documentada del cambio
     */
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

    /**
     * Obtiene la fecha registrada para el cambio de estado.
     *
     * @return fecha del cambio
     */
    public LocalDate getFechaCambio() {
        return fechaCambio;
    }

    /**
     * Obtiene el estado previo del equipo antes del cambio.
     *
     * @return estado anterior
     */
    public Equipo.EstadoEquipo getEstadoAnterior() {
        return estadoAnterior;
    }

    /**
     * Devuelve el nuevo estado asignado al equipo.
     *
     * @return estado nuevo
     */
    public Equipo.EstadoEquipo getEstadoNuevo() {
        return estadoNuevo;
    }

    /**
     * Obtiene el nombre del responsable del cambio de estado.
     *
     * @return responsable del cambio
     */
    public String getResponsable() {
        return responsable;
    }

    /**
     * Devuelve el motivo documentado del cambio de estado.
     *
     * @return motivo asociado
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Representación textual del registro histórico,
     * útil para imprimir o mostrar en interfaces.
     *
     * @return cadena con información detallada del evento
     */
    @Override
    public String toString() {
        return "[" + fechaCambio + "] "
                + "Estado: " + estadoAnterior + " → " + estadoNuevo
                + " | Responsable: " + responsable
                + " | Motivo: " + motivo;
    }
}


