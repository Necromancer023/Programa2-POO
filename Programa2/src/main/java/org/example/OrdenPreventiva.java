package org.example;

import java.time.LocalDate;

public class OrdenPreventiva {

    private int idOrden;
    private LocalDate fechaProgramada;
    private LocalDate fechaEjecucion;
    public enum EstadoOrden {
        PROGRAMADA,
        EN_PROCESO,
        COMPLETADA,
        CANCELADA
    }
    private EstadoOrden estado;

    private Equipo equipoAsociado;
    private FasePreventiva fase;

    private String tecnicoAsignado;
    private String observaciones;

    // -- Constructor --

    public OrdenPreventiva(int idOrden, LocalDate fechaProgramada, Equipo equipoAsociado,
                           FasePreventiva fase, String tecnicoAsignado) {
        this.idOrden = idOrden;
        this.fechaProgramada = fechaProgramada;
        this.equipoAsociado = equipoAsociado;
        this.fase = fase;
        this.tecnicoAsignado = tecnicoAsignado;
        this.estado = EstadoOrden.PROGRAMADA;
        this.observaciones = "";
    }

    // -- Getters y Setters --
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
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // -- Métodos --

    public void marcarComoCompletada(LocalDate fechaEjecucionReal) {
        this.estado = EstadoOrden.COMPLETADA;
        this.fechaEjecucion = fechaEjecucionReal;
    }

    @Override
    public String toString() {
        return "OrdenPreventiva{" +
                "idOrden=" + idOrden +
                ", fechaProgramada=" + fechaProgramada +
                ", fechaEjecucion=" + fechaEjecucion +
                ", estado=" + estado +
                ", equipoAsociado=" + equipoAsociado.getDescripcion() +
                ", fase=" + fase.getDescripcion() +
                ", tecnicoAsignado='" + tecnicoAsignado + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
