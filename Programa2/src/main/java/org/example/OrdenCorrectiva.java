package org.example;

import java.time.LocalDate;

public class OrdenCorrectiva {

    private int idOrdenCorrectiva;
    private LocalDate fechaReporte;
    private LocalDate fechaAtencion;
    private LocalDate fechaFinalizacion;
    public enum EstadoOrden {
        REPORTADA,
        EN_PROCESO,
        COMPLETADA,
        NO_REPARADA
    }
    private EstadoOrden estado;
    private Equipo equipoAsociado;

    private String descripcionFalla;
    private String causaFalla;
    private String accionesRealizadas;
    private double costoReparacion;

    // -- Constructor --

    public OrdenCorrectiva(int idOrden, LocalDate fechaReporte, Equipo equipoAsociado, 
                           String descripcionFalla, String causaFalla) {
        this.idOrdenCorrectiva = idOrden;
        this.fechaReporte = fechaReporte;
        this.equipoAsociado = equipoAsociado;
        this.descripcionFalla = descripcionFalla;
        this.causaFalla = causaFalla;
        this.estado = EstadoOrden.REPORTADA;
        this.accionesRealizadas = "";
        this.costoReparacion = 0.0;
    }

    // -- Getters y Setters --
    public int getIdOrdenCorrectiva() {
        return idOrdenCorrectiva;
    }
    public LocalDate getFechaReporte() {
        return fechaReporte;
    }
    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }
    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
    public EstadoOrden getEstado() {
        return estado;
    }
    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }
    public String getDescripcionFalla() {
        return descripcionFalla;
    }
    public void setDescripcionFalla(String descripcionFalla) {
        this.descripcionFalla = descripcionFalla;
    }
    public String getCausaFalla() {
        return causaFalla;
    }
    public void setCausaFalla(String causaFalla) {
        this.causaFalla = causaFalla;
    }
    public String getAccionesRealizadas() {
        return accionesRealizadas;
    }
    public void setAccionesRealizadas(String accionesRealizadas) {
        this.accionesRealizadas = accionesRealizadas;
    }
    public double getCostoReparacion() {
        return costoReparacion;
    }
    public void setCostoReparacion(double costoReparacion) {
        this.costoReparacion = costoReparacion;
    }
    public Equipo getEquipoAsociado() {
        return equipoAsociado;
    }
    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }
    public void setFechaAtencion(LocalDate fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }


    @Override
    public String toString() {
        return "OrdenCorrectiva{" +
                "idOrdenCorrectiva=" + idOrdenCorrectiva +
                ", fechaReporte=" + fechaReporte +
                ", fechaAtencion=" + fechaAtencion +
                ", fechaFinalizacion=" + fechaFinalizacion +
                ", estado=" + estado +
                ", equipoAsociado=" + equipoAsociado.getDescripcion() +
                ", descripcionFalla='" + descripcionFalla + '\'' +
                ", causaFalla='" + causaFalla + '\'' +
                ", accionesRealizadas='" + accionesRealizadas + '\'' +
                ", costoReparacion=" + costoReparacion +
                '}';
    }

}