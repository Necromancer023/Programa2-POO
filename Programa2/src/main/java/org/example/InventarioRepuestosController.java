package org.example;

import java.util.List;

public class InventarioRepuestosController {

    private InventarioRepuestosService inventarioService;

    public InventarioRepuestosController() {
        this.inventarioService = new InventarioRepuestosService();
    }

    // ----------------------------------------------------------
    // AGREGAR NUEVO REPUESTO
    // ----------------------------------------------------------
    public String agregarRepuesto(int id,
                                  String nombre,
                                  String descripcion,
                                  int stockInicial,
                                  int stockMinimo,
                                  String ubicacion,
                                  double costoUnitario) {

        if (id <= 0) return "El ID debe ser mayor a cero.";
        if (nombre == null || nombre.isBlank()) return "Debe indicar un nombre.";
        if (stockInicial < 0) return "El stock inicial no puede ser negativo.";
        if (stockMinimo < 0) return "El stock mínimo no puede ser negativo.";
        if (costoUnitario < 0) return "El costo unitario no puede ser negativo.";

        Repuesto nuevo = new Repuesto(
                id,
                nombre,
                descripcion,
                stockInicial,
                stockMinimo,
                ubicacion,
                costoUnitario
        );

        boolean ok = inventarioService.agregarRepuesto(nuevo);
        return ok ? "Repuesto registrado correctamente."
                  : "Ya existe un repuesto con ese ID.";
    }

    // ----------------------------------------------------------
    // CONSULTAR REPUESTO
    // ----------------------------------------------------------
    public Repuesto buscarRepuesto(int id) {
        return inventarioService.buscarRepuestoPorId(id);
    }

    // ----------------------------------------------------------
    // LISTAR REPUESTOS
    // ----------------------------------------------------------
    public List<Repuesto> obtenerRepuestos() {
        return inventarioService.obtenerRepuestos();
    }

    // ----------------------------------------------------------
    // REGISTRAR ENTRADA
    // ----------------------------------------------------------
    public String registrarEntrada(int idRepuesto, int cantidad,
                                   String motivo, String referencia) {

        if (cantidad <= 0) return "La cantidad debe ser mayor a cero.";

        boolean ok = inventarioService.registrarEntrada(idRepuesto, cantidad, motivo, referencia);

        return ok ? "Entrada registrada."
                  : "No se pudo registrar la entrada. Verifique ID y cantidad.";
    }

    // ----------------------------------------------------------
    // REGISTRAR SALIDA / USO EN ÓRDENES
    // ----------------------------------------------------------
    public String registrarSalida(int idRepuesto, int cantidad,
                                  String motivo, String referencia) {

        if (cantidad <= 0) return "La cantidad debe ser mayor a cero.";

        boolean ok = inventarioService.registrarSalida(idRepuesto, cantidad, motivo, referencia);

        return ok ? "Salida registrada."
                  : "Stock insuficiente o ID no válido.";
    }

    // ----------------------------------------------------------
    // AJUSTE POR INVENTARIO FÍSICO
    // ----------------------------------------------------------
    public String registrarAjuste(int idRepuesto, int nuevoStock, String motivo) {

        if (nuevoStock < 0) return "El stock no puede ser negativo.";

        boolean ok = inventarioService.registrarAjuste(idRepuesto, nuevoStock, motivo);

        return ok ? "Ajuste aplicado."
                  : "No se pudo registrar el ajuste.";
    }

    // ----------------------------------------------------------
    // CONSULTAR MOVIMIENTOS
    // ----------------------------------------------------------
    public List<MovimientoRepuesto> obtenerMovimientos() {
        return inventarioService.obtenerMovimientos();
    }
}

