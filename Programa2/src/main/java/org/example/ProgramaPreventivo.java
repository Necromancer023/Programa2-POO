package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgramaPreventivo {

    private int idPrograma;
    private String nombrePrograma;
    private String objetivo;
    private LocalDate fechaCreacion;
    private String responsable;
    private List<FasePreventiva> fases;

    // --- Constructor correcto ---
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

    // Métodos funcionales
    public void agregarFase(FasePreventiva fase) {
        this.fases.add(fase);
    }

    public void eliminarFase(FasePreventiva fase) {
        this.fases.remove(fase);
    }

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


