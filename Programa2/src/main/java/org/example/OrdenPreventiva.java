package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una orden de mantenimiento preventivo emitida
 * sobre un equipo y una fase dentro de un programa de mantenimiento.
 *
 * Contiene información de ejecución, estado, técnico asignado,
 * materiales usados y resultado del servicio.
 */
public class OrdenPreventiva {

    // ---------------------- ENUMS ----------------------

    /**
     * Posibles estados de una orden preventiva.
     */
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

    /**
     * Construye una orden preventiva lista para ser programada y ejecutada.
     *
     * @param idOrden identificador único
     * @param fechaProgramada fecha en la que se planea ejecutar
     * @param equipoAsociado equipo objeto del mantenimiento
     * @param fase etapa dentro del programa preventivo
     * @param tecnicoAsignado responsable designado
     */
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

    /**
     * Registra material utilizado en la ejecución de la orden.
     * @param material descripción del repuesto o insumo
     */
    public void agregarMaterial(String material) { this.materialesUtilizados.add(material); }

    public String getFirmaDigitalTecnico() { return firmaDigitalTecnico; }

    // ---------------------- MÉTODOS FUNCIONALES ----------------------

    /**
     * Marca la orden como iniciada y registra fecha real de ejecución.
     * @param fechaEjecucion fecha en que comenzó el trabajo
     */
    public void iniciarOrden(LocalDate fechaEjecucion) {
        this.estado = EstadoOrden.EN_PROCESO;
        this.fechaEjecucion = fechaEjecucion;
    }

    /**
     * Completa la orden, almacenando diagnóstico, firma digital del técnico y tiempo real.
     *
     * @param fechaEjecucionReal fecha real de conclusión
     * @param tiempoReal horas empleadas
     * @param diagnostico resultado o análisis final
     * @param tecnico técnico que ejecutó la orden
     */
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

    /**
     * Cancela la orden y guarda motivo de la cancelación.
     * @param motivo texto explicativo
     */
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





