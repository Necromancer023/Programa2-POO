package org.example;

import java.time.LocalDate;

public class EntradaBitacora {

    public enum TipoRegistro {
        MANTENIMIENTO_PREVENTIVO,
        MANTENIMIENTO_CORRECTIVO,
        CAMBIO_ESTADO,
        OBSERVACION
    }

    private LocalDate fecha;
    private TipoRegistro tipo;
    private String descripcion;

    // -- Constructor --
    public EntradaBitacora(LocalDate fecha, TipoRegistro tipo, String descripcion) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    // -- Getters --
    public LocalDate getFecha() {
        return fecha;
    }

    public TipoRegistro getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "[" + fecha + "][" + tipo + "] " + descripcion;
    }
}