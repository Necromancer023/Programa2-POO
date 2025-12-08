package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un Programa de Mantenimiento Preventivo asignable a un equipo.
 *
 * Un programa preventivo está compuesto por:
 *  - Identificador único
 *  - Nombre y propósito (objetivo técnico)
 *  - Fecha de creación y responsable
 *  - Conjunto de fases preventivas, cada una con tareas, intervalos y criterios
 *
 * Cada programa puede contener múltiples fases, las cuales modelan
 * actividades periódicas de mantenimiento para un equipo.
 */
public class ProgramaPreventivo {

    /** Identificador único del programa preventivo */
    private int idPrograma;

    /** Nombre o título del programa (ej: "Plan de mantenimiento CNC") */
    private String nombrePrograma;

    /** Objetivo o finalidad del programa */
    private String objetivo;

    /** Fecha en la que se registró o creó el programa */
    private LocalDate fechaCreacion;

    /** Persona responsable o autor del programa */
    private String responsable;

    /** Lista ordenada de fases preventivas asociadas al programa */
    private List<FasePreventiva> fases;

    // --- Constructor correcto ---

    /**
     * Crea un programa preventivo con información inicial
     * pero sin fases cargadas aún.
     *
     * @param idPrograma identificador único
     * @param nombrePrograma nombre del programa
     * @param objetivo objetivo o propósito del mantenimiento
     * @param fechaCreacion fecha de registro
     * @param responsable persona que elaboró o aprobó el programa
     */
    public ProgramaPreventivo(int idPrograma, String nombrePrograma, String objetivo,
                              LocalDate fechaCreacion, String responsable) {

        this.idPrograma = idPrograma;
        this.nombrePrograma = nombrePrograma;
        this.objetivo = objetivo;
        this.fechaCreacion = fechaCreacion;
        this.responsable = responsable;
        this.fases = new ArrayList<>();
    }

    // --- Getters y Setters ---

    public int getIdPrograma() {
        return idPrograma;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }
    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public String getObjetivo() {
        return objetivo;
    }
    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public String getResponsable() {
        return responsable;
    }
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public List<FasePreventiva> getFases() {
        return fases;
    }

    // --- Métodos funcionales ---

    /**
     * Agrega una fase preventiva al programa.
     *
     * @param fase instancia de fase preventiva
     */
    public void agregarFase(FasePreventiva fase) {
        this.fases.add(fase);
    }

    /**
     * Elimina una fase previamente registrada.
     *
     * @param fase fase a eliminar
     */
    public void eliminarFase(FasePreventiva fase) {
        this.fases.remove(fase);
    }

    /**
     * Busca una fase dentro del programa por número de fase.
     *
     * @param numeroFase identificador de la fase dentro del programa
     * @return fase encontrada o null si no existe
     */
    public FasePreventiva obtenerFase(int numeroFase) {
        for (FasePreventiva f : fases) {
            if (f.getNumeroFase() == numeroFase) {
                return f;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ProgramaPreventivo{" +
                "idPrograma=" + idPrograma +
                ", nombre='" + nombrePrograma + '\'' +
                ", objetivo='" + objetivo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", responsable='" + responsable + '\'' +
                ", fases=" + fases.size() +
                '}';
    }
}



