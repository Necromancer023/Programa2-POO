package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar el inventario de repuestos y
 * los movimientos relacionados (entradas, salidas y ajustes).
 * Funciona como capa lógica entre los controladores y los modelos.
 */
public class InventarioRepuestosService {

    /** Lista interna de repuestos registrados en el sistema. */
    private List<Repuesto> repuestos;

    /** Historial de movimientos registrados sobre los repuestos. */
    private List<MovimientoRepuesto> movimientos;

    /**
     * Constructor que inicializa listas internas de inventario y movimientos.
     */
    public InventarioRepuestosService() {
        this.repuestos = new ArrayList<>();
        this.movimientos = new ArrayList<>();
    }

    // ----------------- CRUD REPUESTOS -----------------

    /**
     * Registra un nuevo repuesto si no existe otro con el mismo ID.
     *
     * @param repuesto objeto a registrar
     * @return true si se agregó correctamente, false si ya existe un repuesto con ese ID
     */
    public boolean agregarRepuesto(Repuesto repuesto) {
        for (Repuesto r : repuestos) {
            if (r.getId() == repuesto.getId()) {
                return false; // ya existe
            }
        }
        repuestos.add(repuesto);
        return true;
    }

    /**
     * Busca un repuesto según su ID.
     *
     * @param id identificador del repuesto
     * @return instancia encontrada o null si no existe
     */
    public Repuesto buscarRepuestoPorId(int id) {
        for (Repuesto r : repuestos) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    /**
     * Elimina un repuesto de la colección si existe.
     *
     * @param id identificador del repuesto a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminarRepuesto(int id) {
        Repuesto r = buscarRepuestoPorId(id);
        if (r != null) {
            repuestos.remove(r);
            return true;
        }
        return false;
    }

    /**
     * Obtiene la lista completa de repuestos.
     */
    public List<Repuesto> obtenerRepuestos() {
        return repuestos;
    }

    /**
     * Devuelve el historial de movimientos registrados.
     */
    public List<MovimientoRepuesto> obtenerMovimientos() {
        return movimientos;
    }

    // ----------------- MOVIMIENTOS DE INVENTARIO -----------------

    /**
     * Registra una entrada de inventario (compra, devolución, etc.).
     *
     * @param idRepuesto ID del repuesto
     * @param cantidad cantidad ingresada
     * @param motivo causa del ingreso
     * @param referencia identificador externo (factura, documento, etc.)
     * @return true si fue válido y se registró, false si hubo error
     */
    public boolean registrarEntrada(int idRepuesto, int cantidad,
                                    String motivo, String referencia) {

        if (cantidad <= 0) return false;

        Repuesto r = buscarRepuestoPorId(idRepuesto);
        if (r == null) return false;

        // Incremento de stock
        r.ajustarStock(cantidad);

        // Registro del movimiento
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

    /**
     * Registra una salida de inventario (uso en orden, baja, etc.).
     *
     * @param idRepuesto ID del repuesto
     * @param cantidad cantidad a descontar
     * @param motivo razón del consumo
     * @param referencia dato de referencia externa
     * @return true si se registró correctamente o false si el stock fue insuficiente
     */
    public boolean registrarSalida(int idRepuesto, int cantidad,
                                   String motivo, String referencia) {

        if (cantidad <= 0) return false;

        Repuesto r = buscarRepuestoPorId(idRepuesto);
        if (r == null) return false;

        // Validar que haya stock suficiente
        if (r.getStockActual() < cantidad) {
            return false;
        }

        // Descuento de inventario
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

    /**
     * Ajuste directo de inventario basado en conteos físicos.
     *
     * @param idRepuesto ID del repuesto
     * @param nuevoStock cantidad corregida
     * @param motivo razón del ajuste
     * @return true si se aplicó el ajuste, false si el repuesto no existe o stock negativo
     */
    public boolean registrarAjuste(int idRepuesto, int nuevoStock,
                                   String motivo) {

        if (nuevoStock < 0) return false;

        Repuesto r = buscarRepuestoPorId(idRepuesto);
        if (r == null) return false;

        int stockAnterior = r.getStockActual();
        int diferencia = nuevoStock - stockAnterior;

        // Reemplazo directo del stock
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
     * Método auxiliar para descontar un repuesto automáticamente desde
     * módulos de órdenes. Internamente se registra como salida.
     *
     * @param idRepuesto identificador del repuesto
     * @param cantidad cantidad consumida
     * @param motivo motivo del uso
     * @param idOrdenReferencia número o ID de orden asociado
     * @return true si se registró correctamente
     */
    public boolean usarRepuestoEnOrden(int idRepuesto,
                                       int cantidad,
                                       String motivo,
                                       String idOrdenReferencia) {

        // Internamente es una salida registrada como consumo de orden
        return registrarSalida(idRepuesto, cantidad, motivo, "ORDEN " + idOrdenReferencia);
    }
}


