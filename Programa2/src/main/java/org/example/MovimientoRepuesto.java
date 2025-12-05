package org.example;

import java.time.LocalDate;

public class MovimientoRepuesto {

    public enum TipoMovimiento {
        ENTRADA,
        SALIDA,
        AJUSTE
    }

    private LocalDate fecha;
    private Repuesto repuesto;
    private int cantidad;          // siempre positiva
    private TipoMovimiento tipo;
    private String motivo;         // compra, uso en orden, ajuste inventario, etc.
    private String referencia;     // ID de orden, factura, etc.

    // --- Constructor ---

    public MovimientoRepuesto(LocalDate fecha,
                              Repuesto repuesto,
                              int cantidad,
                              TipoMovimiento tipo,
                              String motivo,
                              String referencia) {

        this.fecha = fecha;
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.motivo = motivo;
        this.referencia = referencia;
    }

    // --- Getters ---

    public LocalDate getFecha() {
        return fecha;
    }

    public Repuesto getRepuesto() {
        return repuesto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getReferencia() {
        return referencia;
    }

    @Override
    public String toString() {
        return "[" + fecha + "] " +
                tipo + " | " +
                "Repuesto: " + repuesto.getNombre() +
                " | Cant: " + cantidad +
                " | Motivo: " + motivo +
                " | Ref: " + referencia;
    }
}

