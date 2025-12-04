package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenPreventivaService {

    List<OrdenPreventiva> ordenesPreventivas;

    // Constructor
    public OrdenPreventivaService() {
        this.ordenesPreventivas = new ArrayList<>();
    }

    // Método para agregar una orden preventiva a la lista
    public boolean agregarOrdenPreventiva(OrdenPreventiva orden) {
        for (OrdenPreventiva ordenExistente : ordenesPreventivas) {
             if (ordenExistente.getIdOrden() == orden.getIdOrden()) {
            return false;
        }
    }
    ordenesPreventivas.add(orden);
    return true;
    }

    // Método para obtener todas las órdenes preventivas
    public List<OrdenPreventiva> obtenerOrdenesPreventivas() {
        return ordenesPreventivas;
    }

    // Método para buscar órdenes preventivas por ID
    public OrdenPreventiva buscarOrdenPreventivaPorId(int idOrden) {
        for (OrdenPreventiva orden : ordenesPreventivas) {
            if (orden.getIdOrden() == idOrden) {
                return orden;
            }
        }
        return null; // Retorna null si no se encuentra la orden
    }

    // Método para eliminar una orden preventiva por ID
    public boolean eliminarOrdenPreventiva(int idOrden) {
        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);
        if (orden != null) {
            ordenesPreventivas.remove(orden);
            return true; // Retorna true si se elimina la orden
        }
        return false; // Retorna false si no se encuentra la orden
    }

    // Método para iniciar una orden preventiva
    public boolean iniciarOrdenPreventiva(int idOrden, LocalDate fechaEjecucion) {
        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);
        if (orden != null) {
            orden.setEstado(OrdenPreventiva.EstadoOrden.EN_PROCESO);
            orden.setFechaEjecucion(fechaEjecucion);
            return true; // Retorna true si se inicia la orden
        }
        return false; // Retorna false si no se puede iniciar la orden
    }

    // Método para completar una orden preventiva
    public boolean completarOrdenPreventiva(int idOrden, LocalDate fechaEjecucion, String observaciones) {
        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);
        if (orden != null) {
            orden.marcarComoCompletada(fechaEjecucion);
            orden.setObservaciones(observaciones);
            return true; // Retorna true si se completa la orden
        }
        return false; // Retorna false si no se puede completar la orden
    }

    // Método para cancelar una orden preventiva
    public boolean cancelarOrdenPreventiva(int idOrden, String observaciones) {
        OrdenPreventiva orden = buscarOrdenPreventivaPorId(idOrden);
        if (orden != null) {
            orden.setEstado(OrdenPreventiva.EstadoOrden.CANCELADA);
            orden.setObservaciones(observaciones);
            return true; // Retorna true si se cancela la orden
        }
        return false; // Retorna false si no se puede cancelar la orden
    }
}
