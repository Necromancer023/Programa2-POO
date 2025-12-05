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

        // Regla: no puede iniciar una orden cancelada o completada
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
                                            String firmaTecnico) {

        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);

        if (orden == null) return false;
        if (orden.getEstado() == OrdenPreventiva.EstadoOrden.CANCELADA) return false;

        orden.completarOrden(fechaReal, tiempoRealHoras, diagnosticoFinal, firmaTecnico);

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
}


