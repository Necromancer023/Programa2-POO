package org.example;

import java.time.LocalDate;

/**
 * Representa un movimiento de inventario relacionado con un repuesto.
 * Cada movimiento corresponde a una acción realizada sobre el stock:
 * entrada, salida o ajuste de inventario.
 */
public class MovimientoRepuesto {

    /**
     * Tipos posibles de movimientos de inventario.
     * ENTRADA: incremento del stock.
     * SALIDA: consumo o baja del stock.
     * AJUSTE: corrección manual del inventario por diferencias físicas.
     */
    public enum TipoMovimiento {
        ENTRADA,
        SALIDA,
        AJUSTE
    }

    private LocalDate fecha;      // Fecha en que ocurrió el movimiento
    private Repuesto repuesto;   // Repuesto afectado por el movimiento
    private int cantidad;        // Magnitud del movimiento (siempre positiva)
    private TipoMovimiento tipo; // Tipo de operación realizada
    private String motivo;       // Razón del movimiento (compra, uso, ajuste, etc.)
    private String referencia;   // Documento o entidad relacionada (orden, factura, etc.)

    /**
     * Constructor que permite registrar un movimiento en inventario.
     *
     * @param fecha      fecha en que se registró el movimiento
     * @param repuesto   repuesto afectado
     * @param cantidad   cantidad aplicada (siempre positiva)
     * @param tipo       tipo de movimiento realizado
     * @param motivo     descripción o justificación de la acción
     * @param referencia referencia documental o código asociado
     */
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

    /** @return fecha del movimiento */
    public LocalDate getFecha() {
        return fecha;
    }

    /** @return repuesto involucrado en el movimiento */
    public Repuesto getRepuesto() {
        return repuesto;
    }

    /** @return cantidad aplicada al inventario */
    public int getCantidad() {
        return cantidad;
    }

    /** @return tipo del movimiento realizado */
    public TipoMovimiento getTipo() {
        return tipo;
    }

    /** @return motivo explicado del movimiento */
    public String getMotivo() {
        return motivo;
    }

    /** @return referencia documental asociada al movimiento */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Representación textual del movimiento para fines de auditoría y listado.
     */
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


