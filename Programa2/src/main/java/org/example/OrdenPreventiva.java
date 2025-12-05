package org.example;

import java.time.LocalDate;

public class OrdenPreventiva {

    public enum EstadoOrden {
        PROGRAMADA,
        EN_PROCESO,
        COMPLETADA,
        CANCELADA
    }

    public enum Prioridad {
        ALTA,
        MEDIA,
        BAJA
    }

    private int idOrden;
    private LocalDate fechaProgramada;
    private LocalDate fechaEjecucion;

    private EstadoOrden estado;
    private Prioridad prioridad;             
    private String observacionInicial;       

    private Equipo equipoAsociado;
    private FasePreventiva fase;

    private String tecnicoAsignado;
    private String observacionesFinales;

    // --- Constructor ---

    public OrdenPreventiva(int idOrden, LocalDate fechaProgramada,
                           Equipo equipoAsociado, FasePreventiva fase,
                           String tecnicoAsignado, Prioridad prioridad,
                           String observacionInicial) {

        this.idOrden = idOrden;
        this.fechaProgramada = fechaProgramada;
        this.equipoAsociado = equipoAsociado;
        this.fase = fase;
        this.tecnicoAsignado = tecnicoAsignado;
        this.prioridad = prioridad;
        this.observacionInicial = observacionInicial;

        this.estado = EstadoOrden.PROGRAMADA;
        this.observacionesFinales = "";
    }

    // --- Getters y Setters ---

    public int getIdOrden() {
        return idOrden;
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }
    public void setFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public LocalDate getFechaEjecucion() {
        return fechaEjecucion;
    }
    public void setFechaEjecucion(LocalDate fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public EstadoOrden getEstado() {
        return estado;
    }
    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public String getObservacionInicial() {
        return observacionInicial;
    }
    public void setObservacionInicial(String observacionInicial) {
        this.observacionInicial = observacionInicial;
    }

    public Equipo getEquipoAsociado() {
        return equipoAsociado;
    }

    public FasePreventiva getFase() {
        return fase;
    }

    public String getTecnicoAsignado() {
        return tecnicoAsignado;
    }
    public void setTecnicoAsignado(String tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public String getObservacionesFinales() {
        return observacionesFinales;
    }
    public void setObservacionesFinales(String obs) {
        this.observacionesFinales = obs;
    }

    // --- Métodos adicionales ---
    public void marcarComoCompletada(LocalDate fechaEjecucionReal, String observacionesFinales) {
        this.estado = EstadoOrden.COMPLETADA;
        this.fechaEjecucion = fechaEjecucionReal;
        this.observacionesFinales = observacionesFinales;
    }

    @Override
    public String toString() {
        return "OrdenPreventiva{" +
                "idOrden=" + idOrden +
                ", fechaProgramada=" + fechaProgramada +
                ", fechaEjecucion=" + fechaEjecucion +
                ", estado=" + estado +
                ", prioridad=" + prioridad +
                ", observacionInicial='" + observacionInicial + '\'' +
                ", equipoAsociado=" + equipoAsociado.getDescripcion() +
                ", fase=" + fase.getDescripcion() +
                ", tecnicoAsignado='" + tecnicoAsignado + '\'' +
                ", observacionesFinales='" + observacionesFinales + '\'' +
                '}';
    }
}
