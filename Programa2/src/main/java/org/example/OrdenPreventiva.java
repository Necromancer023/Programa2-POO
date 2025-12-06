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
    private LocalDate fechaEjecucion;     // fecha real
    private LocalDate fechaCancelacion;

    private EstadoOrden estado;

    private Equipo equipoAsociado;
    private FasePreventiva fase;

    private Tecnico tecnicoAsignado;     // Técnico REAL
    private String firmaDigitalTecnico;  // Firma generada del técnico

    private String observaciones;
    private String diagnosticoFinal;

    private double tiempoRealHoras;

    private List<String> materialesUtilizados;

    // ---------------------- CONSTRUCTOR ----------------------
    public OrdenPreventiva(int idOrden,
                           LocalDate fechaProgramada,
                           Equipo equipoAsociado,
                           FasePreventiva fase,
                           Tecnico tecnicoAsignado) {

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
    public int getIdOrden() { return idOrden; }

    public LocalDate getFechaProgramada() { return fechaProgramada; }
    public void setFechaProgramada(LocalDate fechaProgramada) { this.fechaProgramada = fechaProgramada; }

    public LocalDate getFechaEjecucion() { return fechaEjecucion; }
    public void setFechaEjecucion(LocalDate fechaEjecucion) { this.fechaEjecucion = fechaEjecucion; }
    public LocalDate getFechaCancelacion() { return fechaCancelacion; }

    public EstadoOrden getEstado() { return estado; }
    public void setEstado(EstadoOrden estado) { this.estado = estado; }

    public Equipo getEquipoAsociado() { return equipoAsociado; }

    public FasePreventiva getFase() { return fase; }

    public Tecnico getTecnicoAsignado() { return tecnicoAsignado; }
    public void setTecnicoAsignado(Tecnico tecnicoAsignado) { this.tecnicoAsignado = tecnicoAsignado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getDiagnosticoFinal() { return diagnosticoFinal; }
    public void setDiagnosticoFinal(String diagnosticoFinal) { this.diagnosticoFinal = diagnosticoFinal; }

    public double getTiempoRealHoras() { return tiempoRealHoras; }
    public void setTiempoRealHoras(double tiempoRealHoras) { this.tiempoRealHoras = tiempoRealHoras; }

    public List<String> getMaterialesUtilizados() { return materialesUtilizados; }
    public void agregarMaterial(String material) { this.materialesUtilizados.add(material); }

    public String getFirmaDigitalTecnico() { return firmaDigitalTecnico; }

    // ---------------------- MÉTODOS FUNCIONALES ----------------------

    /** Iniciar la orden preventiva */
    public void iniciarOrden(LocalDate fechaEjecucion) {
        this.estado = EstadoOrden.EN_PROCESO;
        this.fechaEjecucion = fechaEjecucion;
    }

    /** Completar la orden preventiva */
    public void completarOrden(LocalDate fechaEjecucionReal,
                               double tiempoReal,
                               String diagnostico,
                               Tecnico tecnico) {

        this.estado = EstadoOrden.COMPLETADA;
        this.fechaEjecucion = fechaEjecucionReal;
        this.tiempoRealHoras = tiempoReal;
        this.diagnosticoFinal = diagnostico;

        // Firma digital basada en el técnico
        if (tecnico != null) {
            this.firmaDigitalTecnico = tecnico.getNombreCompleto();
        } else {
            this.firmaDigitalTecnico = "TÉCNICO NO REGISTRADO";
        }
    }

    /** Cancelar la orden preventiva */
    public void cancelarOrden(String motivo) {
        this.estado = EstadoOrden.CANCELADA;
        this.fechaCancelacion = LocalDate.now();
        this.observaciones = motivo;
    }

    // ---------------------- REPRESENTACIÓN ----------------------
    @Override
    public String toString() {
        return "OrdenPreventiva{" +
                "idOrden=" + idOrden +
                ", fechaProgramada=" + fechaProgramada +
                ", tecnicoAsignado=" + (tecnicoAsignado != null ? tecnicoAsignado.getNombreCompleto() : "SIN ASIGNAR") +
                ", estado=" + estado +
                '}';
    }
}




