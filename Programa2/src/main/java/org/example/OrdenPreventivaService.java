package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenPreventivaService {

    private List<OrdenPreventiva> ordenesPreventivas;

    // Constructor
    public OrdenPreventivaService() {
        this.ordenesPreventivas = new ArrayList<>();
    }

    // ------------------- CRUD -------------------

    /** Agregar nueva orden preventiva */
    public boolean agregarOrdenPreventiva(OrdenPreventiva orden) {
        for (OrdenPreventiva o : ordenesPreventivas) {
            if (o.getIdOrden() == orden.getIdOrden()) {
                return false; // Ya existe
            }
        }
        ordenesPreventivas.add(orden);
        return true;
    }

    /** Buscar orden por ID */
    public OrdenPreventiva buscarOrdenPreventivaPorId(int idOrden) {
        for (OrdenPreventiva o : ordenesPreventivas) {
            if (o.getIdOrden() == idOrden) {
                return o;
            }
        }
        return null;
    }

    /** Eliminar por ID */
    public boolean eliminarOrdenPreventiva(int idOrden) {
        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);
        if (orden != null) {
            ordenesPreventivas.remove(orden);
            return true;
        }
        return false;
    }

    /** Obtener lista de órdenes */
    public List<OrdenPreventiva> obtenerOrdenesPreventivas() {
        return ordenesPreventivas;
    }

    // ------------------- OPERACIONES DE MANTENIMIENTO -------------------

    /** Iniciar una orden preventiva */
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

    /** Completar una orden preventiva */
    public boolean completarOrdenPreventiva(int idOrden,
                                            LocalDate fechaReal,
                                            double tiempoRealHoras,
                                            String diagnosticoFinal,
                                            Tecnico tecnico) {

        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);

        if (orden == null) return false;
        if (orden.getEstado() == OrdenPreventiva.EstadoOrden.CANCELADA) return false;

        // Validación de fecha
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

    /** Cancelar una orden preventiva */
    public boolean cancelarOrdenPreventiva(int idOrden, String motivo) {
        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);

        if (orden == null) return false;
        if (orden.getEstado() == OrdenPreventiva.EstadoOrden.COMPLETADA) return false;

        orden.cancelarOrden(motivo);
        return true;
    }

    // ------------------- AGREGAR MATERIALES -------------------

    /** Agregar materiales utilizados a la orden */
    public boolean agregarMaterialAOrden(int idOrden, String material) {
        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);

        if (orden == null || material == null || material.isBlank()) {
            return false;
        }

        orden.agregarMaterial(material);
        return true;
    }

    // ============================================
    // 🔹 Validación: verificar si puede finalizar
    // ============================================
    public boolean puedeFinalizar(int idOrden) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(idOrden);

        if (op == null) return false;

        // Solo se permite finalizar si está programada o en ejecución
        return op.getEstado() == OrdenPreventiva.EstadoOrden.PROGRAMADA ||
            op.getEstado() == OrdenPreventiva.EstadoOrden.EN_PROCESO;
    }

    // ============================================
    //  Finalizar Orden Preventiva
    // ============================================
    public boolean finalizarOrdenPreventiva(int idOrden, LocalDate fechaRealizacion, String resultado) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(idOrden);
        if (op == null) return false;

        op.setEstado(OrdenPreventiva.EstadoOrden.COMPLETADA);
        op.setFechaEjecucion(fechaRealizacion);
        op.setDiagnosticoFinal(resultado);
        return true;
    }

    // Conteo de órdenes por equipo
    public long contarOrdenesPorEquipo(int idEquipo) {
        return obtenerOrdenesPreventivas().stream()
                .filter(op -> op.getEquipoAsociado().getId() == idEquipo)
                .count();
    }

    // Generar nuevo ID de orden preventiva
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

    public boolean iniciarOrden(int id, LocalDate fecha) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(id);
        if (op == null) return false;
        if (op.getEstado() != OrdenPreventiva.EstadoOrden.PROGRAMADA) return false;

        op.iniciarOrden(fecha);
        return true;
    }

    public boolean completarOrden(int id, LocalDate fecha, double tiempo, String diag, Tecnico tecnico) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(id);
        if (op == null) return false;
        if (op.getEstado() != OrdenPreventiva.EstadoOrden.EN_PROCESO) return false;

        op.completarOrden(fecha, tiempo, diag, tecnico);
        return true;
    }

    public boolean cancelarOrden(int id, String motivo) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(id);
        if (op == null) return false;
        if (op.getEstado() == OrdenPreventiva.EstadoOrden.COMPLETADA) return false;

        op.cancelarOrden(motivo);
        return true;
    }

    public boolean agregarMaterial(int id, String material) {
        OrdenPreventiva op = buscarOrdenPreventivaPorId(id);
        if (op == null) return false;
        op.agregarMaterial(material);
        return true;
    }

    public List<OrdenPreventiva> obtenerOrdenes() {
        return ordenesPreventivas;
    }
}



