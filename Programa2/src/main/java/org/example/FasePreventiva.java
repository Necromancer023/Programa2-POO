package org.example;

import java.util.ArrayList;
import java.util.List;

public class FasePreventiva {

    public enum Frecuencia {
        DIARIA,
        SEMANAL,
        MENSUAL,
        BIMESTRAL,
        TRIMESTRAL,
        SEMESTRAL,
        ANUAL
    }

    private int numeroFase;
    private String descripcion;
    private Frecuencia frecuencia;
    private int intervaloDias;
    private List<String> tareas;
    private List<String> recursosNecesarios;
    private double tiempoEstimadoHoras;
    private String observaciones;

    // -- Constructor --

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

    // -- Getters y Setters --

    public int getNumeroFase() {
        return numeroFase;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Frecuencia getFrecuencia() {
        return frecuencia;
    }
    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }
    public int getIntervaloDias() {
        return intervaloDias;
    }
    public void setIntervaloDias(int intervaloDias) {
        this.intervaloDias = intervaloDias;
    }
    public List<String> getTareas() {
        return tareas;
    }
    public List<String> getRecursosNecesarios() {
        return recursosNecesarios;
    }
    public double getTiempoEstimadoHoras() {
        return tiempoEstimadoHoras;
    }
    public void setTiempoEstimadoHoras(double tiempoEstimadoHoras) {
        this.tiempoEstimadoHoras = tiempoEstimadoHoras;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // -- Métodos --

    public void agregarTarea(String tarea) {
        this.tareas.add(tarea);
    }
    public void eliminarTarea(String tarea) {
        this.tareas.remove(tarea);
    }
    public void agregarRecurso(String recurso) {
        this.recursosNecesarios.add(recurso);
    }
    public void eliminarRecurso(String recurso) {
        this.recursosNecesarios.remove(recurso);
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
