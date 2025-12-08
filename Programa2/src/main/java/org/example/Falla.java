package org.example;

/**
 * Representa un tipo de falla registrada en el sistema.
 * Se utiliza como catálogo base para asignación a órdenes correctivas
 * y análisis estadísticos sobre ocurrencias.
 */
public class Falla {

    /** Identificador único de la falla. */
    private int idFalla;

    /** Descripción explicativa o nombre de la falla. */
    private String descripcion;

    /** Indicador de cuántas veces se ha reportado esta falla. */
    private int frecuenciaReportes; // opcional: para análisis gráfico

    /**
     * Construye una nueva falla con identificador y descripción asignados.
     *
     * @param idFalla identificador único de la falla
     * @param descripcion detalle o nombre de la falla
     */
    public Falla(int idFalla, String descripcion) {
        this.idFalla = idFalla;
        this.descripcion = descripcion;
        this.frecuenciaReportes = 0;
    }

    /**
     * Obtiene el identificador asignado a la falla.
     *
     * @return código numérico de la falla
     */
    public int getIdFalla() {
        return idFalla;
    }

    /**
     * Devuelve la descripción asociada a la falla.
     *
     * @return texto descriptivo
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Incrementa el contador de frecuencia de reportes,
     * utilizado para análisis estadístico y priorización.
     */
    public void incrementarReporte() {
        this.frecuenciaReportes++;
    }

    /**
     * Obtiene la cantidad acumulada de reportes registrados para esta falla.
     *
     * @return número de veces reportada
     */
    public int getFrecuenciaReportes() {
        return frecuenciaReportes;
    }

    /**
     * Representación textual estructurada de la falla
     * usada para visualización y reportes.
     *
     * @return cadena con ID, descripción y frecuencia de reportes
     */
    @Override
    public String toString() {
        return "Falla{" +
                "id=" + idFalla +
                ", descripcion='" + descripcion + '\'' +
                ", frecuenciaReportes=" + frecuenciaReportes +
                '}';
    }
}


