package org.example;

public class Falla {

    private int idFalla;
    private String descripcion;
    private int frecuenciaReportes; // opcional: para análisis gráfico

    public Falla(int idFalla, String descripcion) {
        this.idFalla = idFalla;
        this.descripcion = descripcion;
        this.frecuenciaReportes = 0;
    }

    public int getIdFalla() {
        return idFalla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void incrementarReporte() {
        this.frecuenciaReportes++;
    }

    public int getFrecuenciaReportes() {
        return frecuenciaReportes;
    }

    @Override
    public String toString() {
        return "Falla{" +
                "id=" + idFalla +
                ", descripcion='" + descripcion + '\'' +
                ", frecuenciaReportes=" + frecuenciaReportes +
                '}';
    }
}

