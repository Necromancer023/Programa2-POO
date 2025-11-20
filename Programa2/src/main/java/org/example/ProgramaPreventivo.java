package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgramaPreventivo {

    private int idPrograma;
    private LocalDate fechaCreacion;
    private String responsable;
    private List<FasePreventiva> fases;

    // -- Constructor --

    public ProgramaPreventivo(int idPrograma, LocalDate fechaCreacion, String responsable) {
        this.idPrograma = idPrograma;
        this.fechaCreacion = fechaCreacion;
        this.responsable = responsable;
        this.fases = new ArrayList<>();
    }

    // -- Getters y Setters --

    public int getIdPrograma() {
        return idPrograma;
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

    // -- Métodos --

    public void agregarFase(FasePreventiva fase) {
        this.fases.add(fase);
    }

    public void eliminarFase(FasePreventiva fase) {
        this.fases.remove(fase);
    }

    public FasePreventiva obtenerFase(int numeroFase) {
        for (FasePreventiva fase : fases) {
            if (fase.getNumeroFase() == numeroFase) {
                return fase;
            }
        }
        return null;
    }

    public FasePreventiva obtenerProximaFase(int faseActual) {
        for (FasePreventiva fase : fases) {
            if (fase.getNumeroFase() == faseActual + 1) {
                return fase;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ProgramaPreventivo{" +
                "idPrograma=" + idPrograma +
                ", fechaCreacion=" + fechaCreacion +
                ", responsable='" + responsable + '\'' +
                ", fases=" + fases +
                '}';
    }
}
