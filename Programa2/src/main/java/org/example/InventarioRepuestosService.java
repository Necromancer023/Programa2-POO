package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventarioRepuestosService {

    private List<Repuesto> repuestos;
    private List<MovimientoRepuesto> movimientos;

    public InventarioRepuestosService() {
        this.repuestos = new ArrayList<>();
        this.movimientos = new ArrayList<>();
    }

    // ----------------- CRUD REPUESTOS -----------------

    public boolean agregarRepuesto(Repuesto repuesto) {
        for (Repuesto r : repuestos) {
            if (r.getId() == repuesto.getId()) {
                return false; // ya existe
            }
        }
        repuestos.add(repuesto);
        return true;
    }

    public Repuesto buscarRepuestoPorId(int id) {
        for (Repuesto r : repuestos) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public boolean eliminarRepuesto(int id) {
        Repuesto r = buscarRepuestoPorId(id);
        if (r != null) {
            repuestos.remove(r);
            return true;
        }
        return false;
    }

    public List<Repuesto> obtenerRepuestos() {
        return repuestos;
    }

    public List<MovimientoRepuesto> obtenerMovimientos() {
        return movimientos;
    }

    // ----------------- MOVIMIENTOS DE INVENTARIO -----------------

    /** Registrar una ENTRADA (compra, devolución, etc.) */
    public boolean registrarEntrada(int idRepuesto, int cantidad,
                                    String motivo, String referencia) {

        if (cantidad <= 0) return false;

        Repuesto r = buscarRepuestoPorId(idRepuesto);
        if (r == null) return false;

        r.ajustarStock(cantidad);

        MovimientoRepuesto mov = new MovimientoRepuesto(
                LocalDate.now(),
                r,
                cantidad,
                MovimientoRepuesto.TipoMovimiento.ENTRADA,
                motivo,
                referencia
        );
        movimientos.add(mov);
        return true;
    }

    /** Registrar una SALIDA (uso en orden, baja, etc.) */
    public boolean registrarSalida(int idRepuesto, int cantidad,
                                   String motivo, String referencia) {

        if (cantidad <= 0) return false;

        Repuesto r = buscarRepuestoPorId(idRepuesto);
        if (r == null) return false;

        // Validar que haya stock suficiente
        if (r.getStockActual() < cantidad) {
            return false;
        }

        r.ajustarStock(-cantidad);

        MovimientoRepuesto mov = new MovimientoRepuesto(
                LocalDate.now(),
                r,
                cantidad,
                MovimientoRepuesto.TipoMovimiento.SALIDA,
                motivo,
                referencia
        );
        movimientos.add(mov);
        return true;
    }

    /** Ajuste directo de inventario (conteo físico) */
    public boolean registrarAjuste(int idRepuesto, int nuevoStock,
                                   String motivo) {

        if (nuevoStock < 0) return false;

        Repuesto r = buscarRepuestoPorId(idRepuesto);
        if (r == null) return false;

        int stockAnterior = r.getStockActual();
        int diferencia = nuevoStock - stockAnterior;

        r.setStockActual(nuevoStock);

        MovimientoRepuesto mov = new MovimientoRepuesto(
                LocalDate.now(),
                r,
                Math.abs(diferencia),
                MovimientoRepuesto.TipoMovimiento.AJUSTE,
                motivo,
                "AJUSTE INV."
        );
        movimientos.add(mov);

        return true;
    }

    // ----------------- INTEGRACIÓN CON ÓRDENES -----------------
    /**
     * Método de ayuda para usar un repuesto en una orden de mantenimiento.
     * No modifica la orden, solo descuenta inventario y registra movimiento.
     */
    public boolean usarRepuestoEnOrden(int idRepuesto,
                                       int cantidad,
                                       String motivo,
                                       String idOrdenReferencia) {

        // Internamente es una salida
        return registrarSalida(idRepuesto, cantidad, motivo, "ORDEN " + idOrdenReferencia);
    }
}

