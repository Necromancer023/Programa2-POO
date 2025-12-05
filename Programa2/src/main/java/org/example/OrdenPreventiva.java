package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenPreventiva {

    // ---------------------- ENUMS ----------------------
    public enum EstadoOrden {
        PROGRAMADA,
        EN_PROCESO,
        COMPLETADA,
        CANCELADA
    }

    // ---------------------- ATRIBUTOS ----------------------
    private int idOrden;

    private LocalDate fechaProgramada;
    private LocalDate fechaEjecucion;        // fecha real
    private LocalDate fechaCancelacion;

    private EstadoOrden estado;

    private Equipo equipoAsociado;
    private FasePreventiva fase;

    private String tecnicoAsignado;
    private String firmaDigitalTecnico;

    private String observaciones;
    private String diagnosticoFinal;

    private double tiempoRealHoras;   // tiempo real trabajado

    private List<String> materialesUtilizados;

    // ---------------------- CONSTRUCTOR ----------------------

    public OrdenPreventiva(int idOrden,
                           LocalDate fechaProgramada,
                           Equipo equipoAsociado,
                           FasePreventiva fase,
                           String tecnicoAsignado) {

        this.idOrden = idOrden;
        this.fechaProgramada = fechaProgramada;
        this.equipoAsociado = equipoAsociado;
        this.fase = fase;
        this.tecnicoAsignado = tecnicoAsignado;

        this.estado = EstadoOrden.PROGRAMADA;
        this.materialesUtilizados = new ArrayList<>();

        this.observaciones = "";
        this.diagnosticoFinal = "";
        this.firmaDigitalTecnico = "";
        this.tiempoRealHoras = 0;
    }

    // ---------------------- GETTERS & SETTERS ----------------------

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

    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
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

    public String getDiagnosticoFinal() {
        return diagnosticoFinal;
    }

    public void setDiagnosticoFinal(String diagnosticoFinal) {
        this.diagnosticoFinal = diagnosticoFinal;
    }

    public double getTiempoRealHoras() {
        return tiempoRealHoras;
    }

    public void setTiempoRealHoras(double tiempoRealHoras) {
        this.tiempoRealHoras = tiempoRealHoras;
    }

    public List<String> getMaterialesUtilizados() {
        return materialesUtilizados;
    }

    public void agregarMaterial(String material) {
        this.materialesUtilizados.add(material);
    }

    public String getFirmaDigitalTecnico() {
        return firmaDigitalTecnico;
    }

    public void firmarOrden(String firma) {
        this.firmaDigitalTecnico = firma;
    }

    // ---------------------- MÉTODOS FUNCIONALES ----------------------

    /** Marca la orden como iniciada **/
    public void iniciarOrden(LocalDate fechaEjecucion) {
        this.estado = EstadoOrden.EN_PROCESO;
        this.fechaEjecucion = fechaEjecucion;
    }

    /** Marca la orden como completada **/
    public void completarOrden(LocalDate fechaEjecucionReal,
                               double tiempoReal,
                               String diagnostico,
                               String firmaTecnico) {

        this.estado = EstadoOrden.COMPLETADA;
        this.fechaEjecucion = fechaEjecucionReal;
        this.tiempoRealHoras = tiempoReal;
        this.diagnosticoFinal = diagnostico;
        this.firmaDigitalTecnico = firmaTecnico;
    }

    /** Marca la orden como cancelada **/
    public void cancelarOrden(String motivo) {
        this.estado = EstadoOrden.CANCELADA;
        this.fechaCancelacion = LocalDate.now();
        this.observaciones = motivo;
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
                ", diagnosticoFinal='" + diagnosticoFinal + '\'' +
                ", tiempoRealHoras=" + tiempoRealHoras +
                ", materialesUtilizados=" + materialesUtilizados +
                '}';
    }
}


