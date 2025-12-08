package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una orden de mantenimiento correctivo realizada
 * sobre un equipo determinado. Incluye información sobre fechas,
 * costos, diagnóstico, materiales utilizados y acciones realizadas.
 */
public class OrdenCorrectiva {

    // --- Enums ---

    /**
     * Enum que describe el estado de avance de una orden correctiva.
     */
    public enum EstadoOrden {
        REPORTADA,
        EN_PROCESO,
        COMPLETADA,
        NO_REPARADA
    }

    /**
     * Enum que define la prioridad asignada a una orden correctiva.
     */
    public enum Prioridad {
        ALTA,
        MEDIA,
        BAJA
    }

    // --- Atributos principales ---

    private int idOrdenCorrectiva;             // Identificador único de la orden
    private LocalDate fechaReporte;            // Fecha en que se reportó la falla
    private LocalDate fechaAtencion;           // Fecha en que se comenzó la atención
    private LocalDate fechaFinalizacion;       // Fecha de finalización de trabajo

    private EstadoOrden estado;                // Estado actual de la orden
    private Prioridad prioridad;               // Nivel de prioridad asignado

    private Equipo equipoAsociado;             // Equipo sobre el cual se ejecuta la orden

    // Información técnica de mantenimiento
    private String descripcionFalla;           // Descripción del problema reportado
    private String causaFalla;                 // Causa identificada
    private String diagnosticoInicial;         // Resultado inicial de evaluación
    private List<String> materialesUtilizados; // Lista de materiales consumidos
    private double tiempoEmpleadoHoras;        // Tiempo invertido en mantenimiento

    private String accionesRealizadas;         // Acciones ejecutadas en reparación
    private String observacionesFinales;       // Comentarios o resultados al cierre
    private double costoReparacion;            // Costo económico asociado a la reparación

    /**
     * Constructor principal para crear una orden correctiva reportada.
     *
     * @param idOrden              identificador único de orden
     * @param fechaReporte         fecha del reporte de falla
     * @param equipoAsociado       equipo asociado a la orden
     * @param descripcionFalla     descripción inicial de falla
     * @param causaFalla           posible causa sugerida
     * @param prioridad            prioridad de trabajo asignada
     * @param diagnosticoInicial   diagnóstico preliminar registrado
     */
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

        // Valores iniciales por defecto al registrar una orden
        this.estado = EstadoOrden.REPORTADA;
        this.accionesRealizadas = "";
        this.observacionesFinales = "";
        this.materialesUtilizados = new ArrayList<>();
        this.tiempoEmpleadoHoras = 0;
        this.costoReparacion = 0.0;
    }

    // --- Getters y Setters ---

    /** @return id de la orden correctiva */
    public int getIdOrdenCorrectiva() {
        return idOrdenCorrectiva;
    }

    /** @return fecha de reporte de falla */
    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    /** @return fecha de inicio de atención */
    public LocalDate getFechaAtencion() {
        return fechaAtencion;
    }

    /** Establece la fecha en la que se comienza a trabajar en la orden. */
    public void setFechaAtencion(LocalDate fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    /** @return fecha de finalización de la orden */
    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    /** Registra la fecha en que se finaliza la orden. */
    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    /** @return estado actual de la orden */
    public EstadoOrden getEstado() {
        return estado;
    }

    /** Modifica el estado de la orden. */
    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    /** @return prioridad asignada a la orden */
    public Prioridad getPrioridad() {
        return prioridad;
    }

    /** Establece una nueva prioridad. */
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    /** @return diagnóstico inicial registrado */
    public String getDiagnosticoInicial() {
        return diagnosticoInicial;
    }

    /** Establece nueva información diagnóstica. */
    public void setDiagnosticoInicial(String diagnosticoInicial) {
        this.diagnosticoInicial = diagnosticoInicial;
    }

    /** @return lista de materiales usados */
    public List<String> getMaterialesUtilizados() {
        return materialesUtilizados;
    }

    /** Agrega un material consumido dentro de la orden. */
    public void agregarMaterial(String material) {
        this.materialesUtilizados.add(material);
    }

    /** @return tiempo total de trabajo invertido */
    public double getTiempoEmpleadoHoras() {
        return tiempoEmpleadoHoras;
    }

    /** Establece tiempo invertido en atención. */
    public void setTiempoEmpleadoHoras(double horas) {
        this.tiempoEmpleadoHoras = horas;
    }

    /** @return descripción de falla reportada */
    public String getDescripcionFalla() {
        return descripcionFalla;
    }

    /** @return causa detectada de la falla */
    public String getCausaFalla() {
        return causaFalla;
    }

    /** @return acciones realizadas en mantenimiento */
    public String getAccionesRealizadas() {
        return accionesRealizadas;
    }

    /** Establece detalle de acciones realizadas. */
    public void setAccionesRealizadas(String accionesRealizadas) {
        this.accionesRealizadas = accionesRealizadas;
    }

    /** @return observaciones finales al cierre de orden */
    public String getObservacionesFinales() {
        return observacionesFinales;
    }

    /** Establece conclusión y observaciones al finalizar. */
    public void setObservacionesFinales(String observacionesFinales) {
        this.observacionesFinales = observacionesFinales;
    }

    /** @return costo económico de reparación */
    public double getCostoReparacion() {
        return costoReparacion;
    }

    /** Registra el costo final asociado a la orden. */
    public void setCostoReparacion(double costoReparacion) {
        this.costoReparacion = costoReparacion;
    }

    /** @return equipo asociado a la orden correctiva */
    public Equipo getEquipoAsociado() {
        return equipoAsociado;
    }

    // --- Acciones principales ---

    /**
     * Marca el inicio de atención técnica de la orden y cambia el estado a EN_PROCESO.
     *
     * @param fechaAtencion fecha de inicio de atención
     */
    public void iniciarAtencion(LocalDate fechaAtencion) {
        this.estado = EstadoOrden.EN_PROCESO;
        this.fechaAtencion = fechaAtencion;
    }

    /**
     * Marca el cierre de la orden y registra información final.
     *
     * @param fechaFinalizacion    fecha de conclusión
     * @param accionesRealizadas   acciones aplicadas
     * @param observacionesFinales notas y conclusiones
     * @param costo                costo total registrado
     * @param horas                horas invertidas en atención
     */
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

    /**
     * Representación textual detallada para informes o depuración.
     */
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

