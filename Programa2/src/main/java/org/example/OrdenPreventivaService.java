package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de administrar las operaciones relacionadas con
 * órdenes preventivas.
 *
 * Provee funciones CRUD (crear, buscar, eliminar) y acciones operativas:
 * iniciar, completar, cancelar, agregar material, validar estados y
 * obtener estadísticas.
 */
public class OrdenPreventivaService {

    /** Lista interna que almacena las órdenes preventivas registradas */
    private List<OrdenPreventiva> ordenesPreventivas;

    /** Constructor: inicializa la estructura de almacenamiento */
    public OrdenPreventivaService() {
        this.ordenesPreventivas = new ArrayList<>();
    }

    // --------------------------------------------------------------------
    // ------------------------- CRUD BÁSICO ------------------------------
    // --------------------------------------------------------------------

    /**
     * Registra una nueva orden preventiva.
     *
     * @param orden objeto de OrdenPreventiva
     * @return true si se agregó correctamente, false si ya existe un ID igual
     */
    public boolean agregarOrdenPreventiva(OrdenPreventiva orden) {
        for (OrdenPreventiva o : ordenesPreventivas) {
            if (o.getIdOrden() == orden.getIdOrden()) {
                return false; // Ya existe una orden con ese ID
            }
        }
        ordenesPreventivas.add(orden);
        return true;
    }

    /**
     * Busca una orden preventiva por su ID.
     *
     * @param idOrden identificador buscado
     * @return objeto OrdenPreventiva si existe, null si no se encuentra
     */
    public OrdenPreventiva buscarOrdenPreventivaPorId(int idOrden) {
        for (OrdenPreventiva o : ordenesPreventivas) {
            if (o.getIdOrden() == idOrden) {
                return o;
            }
        }
        return null;
    }

    /**
     * Elimina una orden preventiva registrada.
     *
     * @param idOrden identificador de la orden
     * @return true si se eliminó, false si no existe
     */
    public boolean eliminarOrdenPreventiva(int idOrden) {
        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);
        if (orden != null) {
            ordenesPreventivas.remove(orden);
            return true;
        }
        return false;
    }

    /** @return lista actual de órdenes preventivas */
    public List<OrdenPreventiva> obtenerOrdenesPreventivas() {
        return ordenesPreventivas;
    }

    // --------------------------------------------------------------------
    // --------------- OPERACIONES RELACIONADAS A ESTADOS ----------------
    // --------------------------------------------------------------------

    /**
     * Inicia una orden si está en estado válido.
     *
     * @param idOrden     ID de orden
     * @param fechaInicio fecha real de ejecución
     * @return true si se pudo iniciar, false si no aplica
     */
    public boolean iniciarOrdenPreventiva(int idOrden, LocalDate fechaInicio) {

        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);

        if (orden == null) return false;

        // Regla: no puede iniciar si está cancelada o completada
        if (orden.getEstado() == OrdenPreventiva.EstadoOrden.CANCELADA ||
            orden.getEstado() == OrdenPreventiva.EstadoOrden.COMPLETADA) {
            return false;
        }

        orden.iniciarOrden(fechaInicio);
        return true;
    }

    /**
     * Finaliza una orden preventiva registrando diagnóstico y técnico.
     *
     * @return true si la operación fue válida, false si no cumple reglas
     */
    public boolean completarOrdenPreventiva(int idOrden,
                                            LocalDate fechaReal,
                                            double tiempoRealHoras,
                                            String diagnosticoFinal,
                                            Tecnico tecnico) {

        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);

        if (orden == null) return false;
        if (orden.getEstado() == OrdenPreventiva.EstadoOrden.CANCELADA) return false;

        // Validación de fecha lógica
        if (fechaReal.isBefore(orden.getFechaProgramada())) {
            return false;
        }

        orden.completarOrden(
              fechaReal,
              tiempoRealHoras,
              diagnosticoFinal,
              tecnico
        );

        return true;
    }

    /**
     * Cancela una orden preventiva con un motivo.
     */
    public boolean cancelarOrdenPreventiva(int idOrden, String motivo) {
        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);

        if (orden == null) return false;
        if (orden.getEstado() == OrdenPreventiva.EstadoOrden.COMPLETADA) return false;

        orden.cancelarOrden(motivo);
        return true;
    }

    // --------------------------------------------------------------------
    // ---------------------- MATERIALES / INSUMOS ------------------------
    // --------------------------------------------------------------------

    /**
     * Registra un material utilizado en la orden.
     *
     * @return true si se agregó, false si orden no existe o material vacío
     */
    public boolean agregarMaterialAOrden(int idOrden, String material) {
        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);

        if (orden == null || material == null || material.isBlank()) {
            return false;
        }

        orden.agregarMaterial(material);
        return true;
    }

    // --------------------------------------------------------------------
    // ----------- Validación de condiciones de finalización --------------
    // --------------------------------------------------------------------

    /**
     * Determina si una orden podría ser finalizada según estado actual.
     */
    public boolean puedeFinalizar(int idOrden) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(idOrden);

        if (op == null) return false;

        // Solo programadas o en ejecución pueden finalizarse
        return op.getEstado() == OrdenPreventiva.EstadoOrden.PROGRAMADA ||
            op.getEstado() == OrdenPreventiva.EstadoOrden.EN_PROCESO;
    }

    /**
     * Marca la finalización de una orden sin registrar tiempo / técnico.
     */
    public boolean finalizarOrdenPreventiva(int idOrden, LocalDate fechaRealizacion, String resultado) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(idOrden);
        if (op == null) return false;

        op.setEstado(OrdenPreventiva.EstadoOrden.COMPLETADA);
        op.setFechaEjecucion(fechaRealizacion);
        op.setDiagnosticoFinal(resultado);
        return true;
    }

    // --------------------------------------------------------------------
    // -------------------------- Estadísticas ----------------------------
    // --------------------------------------------------------------------

    /**
     * Cuenta cuántas órdenes preventivas están asociadas a un equipo específico.
     */
    public long contarOrdenesPorEquipo(int idEquipo) {
        return obtenerOrdenesPreventivas().stream()
                .filter(op -> op.getEquipoAsociado().getId() == idEquipo)
                .count();
    }

    // --------------------------------------------------------------------
    // ------------------------ Creación simplificada ---------------------
    // --------------------------------------------------------------------

    /**
     * Crea una nueva orden preventiva.
     */
    public boolean crearOrdenPreventiva(int idOrden,
                                    LocalDate fecha,
                                    Equipo equipo,
                                    FasePreventiva fase,
                                    Tecnico tecnico) {

        // Validación: evitar repetir ID
        for (OrdenPreventiva op : ordenesPreventivas) {
            if (op.getIdOrden() == idOrden) {
                return false;
            }
        }

        OrdenPreventiva nueva = new OrdenPreventiva(
                idOrden,
                fecha,
                equipo,
                fase,
                tecnico
        );

        ordenesPreventivas.add(nueva);
        return true;
    }

    /**
     * Inicia una orden. Solo procede si está programada.
     */
    public boolean iniciarOrden(int id, LocalDate fecha) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(id);
        if (op == null) return false;
        if (op.getEstado() != OrdenPreventiva.EstadoOrden.PROGRAMADA) return false;

        op.iniciarOrden(fecha);
        return true;
    }

    /**
     * Completa una orden. Solo procede si estaba en ejecución.
     */
    public boolean completarOrden(int id, LocalDate fecha, double tiempo, String diag, Tecnico tecnico) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(id);
        if (op == null) return false;
        if (op.getEstado() != OrdenPreventiva.EstadoOrden.EN_PROCESO) return false;

        op.completarOrden(fecha, tiempo, diag, tecnico);
        return true;
    }

    /**
     * Cancela una orden si aún no ha sido finalizada.
     */
    public boolean cancelarOrden(int id, String motivo) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(id);
        if (op == null) return false;
        if (op.getEstado() == OrdenPreventiva.EstadoOrden.COMPLETADA) return false;

        op.cancelarOrden(motivo);
        return true;
    }

    /**
     * Agrega material consumido en la orden.
     */
    public boolean agregarMaterial(int id, String material) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(id);
        if (op == null) return false;
        op.agregarMaterial(material);
        return true;
    }

    /** Devuelve todas las órdenes preventivas registradas */
    public List<OrdenPreventiva> obtenerOrdenes() {
        return ordenesPreventivas;
    }
}




