package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenCorrectiva {

    // --- Enums ---
    public enum EstadoOrden {
        REPORTADA,
        EN_PROCESO,
        COMPLETADA,
        NO_REPARADA
    }

    public enum Prioridad {
        ALTA,
        MEDIA,
        BAJA
    }

    // --- Atributos principales ---
    private int idOrdenCorrectiva;
    private LocalDate fechaReporte;
    private LocalDate fechaAtencion;
    private LocalDate fechaFinalizacion;

    private EstadoOrden estado;
    private Prioridad prioridad;

    private Equipo equipoAsociado;

    // Información técnica
    private String descripcionFalla;
    private String causaFalla;
    private String diagnosticoInicial;     
    private List<String> materialesUtilizados; 
    private double tiempoEmpleadoHoras;    

    private String accionesRealizadas;
    private String observacionesFinales;   
    private double costoReparacion;

    // --- Constructor ---

    public OrdenCorrectiva(int idOrden, LocalDate fechaReporte,
                           Equipo equipoAsociado, String descripcionFalla,
                           String causaFalla, Prioridad prioridad,
                           String diagnosticoInicial) {

        this.idOrdenCorrectiva = idOrden;
        this.fechaReporte = fechaReporte;
        this.equipoAsociado = equipoAsociado;
        this.descripcionFalla = descripcionFalla;
        this.causaFalla = causaFalla;
        this.prioridad = prioridad;
        this.diagnosticoInicial = diagnosticoInicial;

        this.estado = EstadoOrden.REPORTADA;
        this.accionesRealizadas = "";
        this.observacionesFinales = "";
        this.materialesUtilizados = new ArrayList<>();
        this.tiempoEmpleadoHoras = 0;
        this.costoReparacion = 0.0;
    }

    // --- Getters y Setters ---
    public int getIdOrdenCorrectiva() {
        return idOrdenCorrectiva;
    }

    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }
    public void setFechaAtencion(LocalDate fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
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

    public Prioridad getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public String getDiagnosticoInicial() {
        return diagnosticoInicial;
    }
    public void setDiagnosticoInicial(String diagnosticoInicial) {
        this.diagnosticoInicial = diagnosticoInicial;
    }

    public List<String> getMaterialesUtilizados() {
        return materialesUtilizados;
    }
    public void agregarMaterial(String material) {
        this.materialesUtilizados.add(material);
    }

    public double getTiempoEmpleadoHoras() {
        return tiempoEmpleadoHoras;
    }
    public void setTiempoEmpleadoHoras(double horas) {
        this.tiempoEmpleadoHoras = horas;
    }

    public String getDescripcionFalla() {
        return descripcionFalla;
    }

    public String getCausaFalla() {
        return causaFalla;
    }

    public String getAccionesRealizadas() {
        return accionesRealizadas;
    }
    public void setAccionesRealizadas(String accionesRealizadas) {
        this.accionesRealizadas = accionesRealizadas;
    }

    public String getObservacionesFinales() {
        return observacionesFinales;
    }
    public void setObservacionesFinales(String observacionesFinales) {
        this.observacionesFinales = observacionesFinales;
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

    // --- Acciones principales ---
    public void iniciarAtencion(LocalDate fechaAtencion) {
        this.estado = EstadoOrden.EN_PROCESO;
        this.fechaAtencion = fechaAtencion;
    }

    public void finalizarOrden(LocalDate fechaFinalizacion,
                               String accionesRealizadas,
                               String observacionesFinales,
                               double costo,
                               double horas) {

        this.estado = EstadoOrden.COMPLETADA;
        this.fechaFinalizacion = fechaFinalizacion;
        this.accionesRealizadas = accionesRealizadas;
        this.observacionesFinales = observacionesFinales;
        this.costoReparacion = costo;
        this.tiempoEmpleadoHoras = horas;
    }

    @Override
    public String toString() {
        return "OrdenCorrectiva{" +
                "idOrdenCorrectiva=" + idOrdenCorrectiva +
                ", fechaReporte=" + fechaReporte +
                ", fechaAtencion=" + fechaAtencion +
                ", fechaFinalizacion=" + fechaFinalizacion +
                ", estado=" + estado +
                ", prioridad=" + prioridad +
                ", diagnosticoInicial='" + diagnosticoInicial + '\'' +
                ", materialesUtilizados=" + materialesUtilizados +
                ", tiempoEmpleadoHoras=" + tiempoEmpleadoHoras +
                ", descripcionFalla='" + descripcionFalla + '\'' +
                ", causaFalla='" + causaFalla + '\'' +
                ", accionesRealizadas='" + accionesRealizadas + '\'' +
                ", observacionesFinales='" + observacionesFinales + '\'' +
                ", costoReparacion=" + costoReparacion +
                '}';
    }
}
