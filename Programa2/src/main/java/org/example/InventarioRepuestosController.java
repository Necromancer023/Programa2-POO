package org.example;

import java.util.List;

/**
 * Controlador responsable de gestionar las operaciones sobre
 * el inventario de repuestos. Actúa como intermediario entre
 * la interfaz de usuario y el servicio de almacenamiento de datos.
 */
public class InventarioRepuestosController {

    /** Servicio encargado de almacenar, actualizar y consultar repuestos. */
    private InventarioRepuestosService inventarioService;

    /**
     * Inicializa el controlador y su servicio asociado.
     */
    public InventarioRepuestosController() {
        this.inventarioService = new InventarioRepuestosService();
    }

    /**
     * Registra un nuevo repuesto en el inventario, previa validación
     * de los parámetros ingresados.
     *
     * @param id             identificador del repuesto
     * @param nombre         nombre del repuesto
     * @param descripcion    información adicional
     * @param stockInicial   cantidad disponible al ingresar
     * @param stockMinimo    mínimo requerido para reposición
     * @param ubicacion      ubicación física del repuesto
     * @param costoUnitario  costo individual del repuesto
     * @return mensaje indicando éxito o razón del rechazo
     */
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

    /**
     * Consulta un repuesto existente usando su identificador.
     *
     * @param id identificador del repuesto
     * @return objeto {@link Repuesto} si existe; de lo contrario {@code null}
     */
    public Repuesto buscarRepuesto(int id) {
        return inventarioService.buscarRepuestoPorId(id);
    }

    /**
     * Recupera todos los repuestos almacenados en el inventario.
     *
     * @return lista de repuestos registrados
     */
    public List<Repuesto> obtenerRepuestos() {
        return inventarioService.obtenerRepuestos();
    }

    /**
     * Registra una entrada física de repuestos al inventario.
     * Se utiliza para compras, devoluciones o ajustes positivos.
     *
     * @param idRepuesto identificador del repuesto
     * @param cantidad   unidades ingresadas
     * @param motivo     descripción del movimiento
     * @param referencia documento o registro asociado
     * @return mensaje indicando el resultado de la operación
     */
    public String registrarEntrada(int idRepuesto, int cantidad,
                                   String motivo, String referencia) {

        if (cantidad <= 0) return "La cantidad debe ser mayor a cero.";

        boolean ok = inventarioService.registrarEntrada(idRepuesto, cantidad, motivo, referencia);

        return ok ? "Entrada registrada."
                  : "No se pudo registrar la entrada. Verifique ID y cantidad.";
    }

    /**
     * Registra la salida o consumo de repuestos, normalmente
     * asociada a trabajos de mantenimiento correctivo o preventivo.
     *
     * @param idRepuesto identificador del repuesto
     * @param cantidad   unidades consumidas
     * @param motivo     causa de la salida
     * @param referencia documento o número de orden asociado
     * @return mensaje de confirmación o error
     */
    public String registrarSalida(int idRepuesto, int cantidad,
                                  String motivo, String referencia) {

        if (cantidad <= 0) return "La cantidad debe ser mayor a cero.";

        boolean ok = inventarioService.registrarSalida(idRepuesto, cantidad, motivo, referencia);

        return ok ? "Salida registrada."
                  : "Stock insuficiente o ID no válido.";
    }

    /**
     * Aplica un ajuste de inventario derivado de verificaciones físicas,
     * auditorías o corrección de errores de registro.
     *
     * @param idRepuesto identificador del repuesto
     * @param nuevoStock nivel actualizado de stock
     * @param motivo     razón del ajuste
     * @return mensaje indicando éxito o fallo
     */
    public String registrarAjuste(int idRepuesto, int nuevoStock, String motivo) {

        if (nuevoStock < 0) return "El stock no puede ser negativo.";

        boolean ok = inventarioService.registrarAjuste(idRepuesto, nuevoStock, motivo);

        return ok ? "Ajuste aplicado."
                  : "No se pudo registrar el ajuste.";
    }

    /**
     * Recupera el historial de movimientos de inventario que se han registrado,
     * como entradas, salidas y ajustes.
     *
     * @return lista de movimientos con detalle de cada operación
     */
    public List<MovimientoRepuesto> obtenerMovimientos() {
        return inventarioService.obtenerMovimientos();
    }
}


