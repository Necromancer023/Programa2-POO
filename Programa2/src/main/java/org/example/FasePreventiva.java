package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una fase dentro de un programa preventivo de mantenimiento.
 * Cada fase incluye una descripción, frecuencia, tareas asociadas, recursos
 * requeridos y parámetros operativos como duración estimada y ciclos.
 */
public class FasePreventiva {

    /**
     * Enumeración que define la frecuencia de ejecución de una fase preventiva.
     */
    public enum Frecuencia {
        DIARIA,
        SEMANAL,
        MENSUAL,
        BIMESTRAL,
        TRIMESTRAL,
        SEMESTRAL,
        ANUAL
    }

    /** Número identificador o secuencial de la fase dentro del programa. */
    private int numeroFase;

    /** Descripción general de la fase y su propósito. */
    private String descripcion;

    /** Frecuencia estimada de ejecución. */
    private Frecuencia frecuencia;

    /** Intervalo en días entre ejecuciones de la fase. */
    private int intervaloDias;

    /** Cantidad de ciclos programados para repetición de la fase. */
    private int cantidadCiclos;

    /** Lista de tareas que deben ejecutarse durante la fase. */
    private List<String> tareas;

    /** Lista de recursos necesarios para completar la fase. */
    private List<String> recursosNecesarios;

    /** Tiempo estimado de ejecución expresado en horas. */
    private double tiempoEstimadoHoras;

    /** Observaciones adicionales asociadas a la fase. */
    private String observaciones;

    /** Programa preventivo al cual pertenece la fase. */
    private ProgramaPreventivo programa;

    // -- Constructor --

    /**
     * Construye una fase preventiva especificando parámetros principales.
     *
     * @param numeroFase número o identificador interno de la fase
     * @param descripcion texto descriptivo de la fase
     * @param frecuencia frecuencia con que se repite
     * @param intervaloDias días entre repeticiones
     * @param tiempoEstimadoHoras duración estimada en horas
     */
    public FasePreventiva(int numeroFase, String descripcion, Frecuencia frecuencia, int intervaloDias,
                          double tiempoEstimadoHoras) {
        this.numeroFase = numeroFase;
        this.descripcion = descripcion;
        this.frecuencia = frecuencia;
        this.intervaloDias = intervaloDias;
        this.tiempoEstimadoHoras = tiempoEstimadoHoras;
        this.tareas = new ArrayList<>();
        this.recursosNecesarios = new ArrayList<>();
        this.observaciones = "";
    }

    /**
     * Constructor alternativo utilizado principalmente en interfaces gráficas,
     * asignando valores por defecto para frecuencia y otros atributos.
     *
     * @param numeroFase número asignado a la fase
     * @param descripcion texto con la descripción resumida
     * @param intervaloDias días entre cada ejecución
     * @param cantidadCiclos cantidad total de ciclos programados
     */
    public FasePreventiva(int numeroFase, String descripcion, int intervaloDias, int cantidadCiclos) {
        this.numeroFase = numeroFase;
        this.descripcion = descripcion;
        this.frecuencia = Frecuencia.MENSUAL; // Valor por defecto
        this.intervaloDias = intervaloDias;
        this.cantidadCiclos = cantidadCiclos;
        this.tareas = new ArrayList<>();
        this.recursosNecesarios = new ArrayList<>();
        this.tiempoEstimadoHoras = 0;
        this.observaciones = "";
    }

    // -- Getters y Setters --

    /** @return el número de fase asignado */
    public int getNumeroFase() {
        return numeroFase;
    }

    /** @return descripción de la fase */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Actualiza la descripción de la fase.
     *
     * @param descripcion nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** @return frecuencia de ejecución de la fase */
    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    /**
     * Establece una nueva frecuencia programada.
     *
     * @param frecuencia valor de enumeración Frecuencia
     */
    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    /** @return intervalo programado en días */
    public int getIntervaloDias() {
        return intervaloDias;
    }

    /**
     * Modifica el intervalo programado de ejecución.
     *
     * @param intervaloDias cantidad de días
     */
    public void setIntervaloDias(int intervaloDias) {
        this.intervaloDias = intervaloDias;
    }

    /** @return lista de tareas asignadas a la fase */
    public List<String> getTareas() {
        return tareas;
    }

    /** @return lista de recursos necesarios */
    public List<String> getRecursosNecesarios() {
        return recursosNecesarios;
    }

    /** @return tiempo estimado de ejecución expresado en horas */
    public double getTiempoEstimadoHoras() {
        return tiempoEstimadoHoras;
    }

    /**
     * Define un nuevo tiempo estimado para la fase.
     *
     * @param tiempoEstimadoHoras valor en horas
     */
    public void setTiempoEstimadoHoras(double tiempoEstimadoHoras) {
        this.tiempoEstimadoHoras = tiempoEstimadoHoras;
    }

    /** @return observaciones registradas */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Actualiza las observaciones de la fase.
     *
     * @param observaciones texto descriptivo adicional
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /** @return número de ciclos definidos para la fase */
    public int getCantidadCiclos() {
        return cantidadCiclos;
    }

    // -- Métodos --

    /**
     * Agrega una tarea operativa asociada a la fase.
     *
     * @param tarea texto representando la tarea
     */
    public void agregarTarea(String tarea) {
        this.tareas.add(tarea);
    }

    /**
     * Elimina una tarea registrada.
     *
     * @param tarea texto de la tarea a eliminar
     */
    public void eliminarTarea(String tarea) {
        this.tareas.remove(tarea);
    }

    /**
     * Agrega un recurso requerido para ejecutar la fase.
     *
     * @param recurso descripción del recurso
     */
    public void agregarRecurso(String recurso) {
        this.recursosNecesarios.add(recurso);
    }

    /**
     * Elimina un recurso previamente agregado.
     *
     * @param recurso recurso a retirar
     */
    public void eliminarRecurso(String recurso) {
        this.recursosNecesarios.remove(recurso);
    }

    /**
     * @return programa preventivo al cual pertenece esta fase
     */
    public ProgramaPreventivo getPrograma() {
        return programa;
    }

    /**
     * Establece el programa preventivo al que pertenece esta fase.
     *
     * @param programa instancia del programa que contiene la fase
     */
    public void setPrograma(ProgramaPreventivo programa) {
        this.programa = programa;
    }

    @Override
    public String toString() {
        return "FasePreventiva{" +
                "numeroFase=" + numeroFase +
                ", descripcion='" + descripcion + '\'' +
                ", frecuencia=" + frecuencia +
                ", intervaloDias=" + intervaloDias +
                ", tareas=" + tareas +
                ", recursosNecesarios=" + recursosNecesarios +
                ", tiempoEstimadoHoras=" + tiempoEstimadoHoras +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
    
}
