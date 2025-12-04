package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenCorrectivaService {

    List<OrdenCorrectiva> ordenesCorrectivas;

    // Constructor
    public OrdenCorrectivaService() {
        this.ordenesCorrectivas = new ArrayList<>();
    }

    // Método para agregar una orden correctiva a la lista
    public boolean agregarOrdenCorrectiva(OrdenCorrectiva orden) {
        for (OrdenCorrectiva ordenExistente : ordenesCorrectivas) {
             if (ordenExistente.getIdOrdenCorrectiva() == orden.getIdOrdenCorrectiva()) {
            return false;
        }
    }
    ordenesCorrectivas.add(orden);
    return true;
    }

    // Método para obtener todas las órdenes correctivas
    public List<OrdenCorrectiva> obtenerOrdenesCorrectivas() {
        return ordenesCorrectivas;
    }

    // Método para buscar órdenes correctivas por ID
    public OrdenCorrectiva buscarOrdenCorrectivaPorId(int id) {
        for (OrdenCorrectiva orden : ordenesCorrectivas) {
            if (orden.getIdOrdenCorrectiva() == id) {
                return orden;
            }
        }
        return null; // Retorna null si no se encuentra la orden
    }

    // Método para eliminar una orden correctiva por ID
    public boolean eliminarOrdenCorrectiva(int id) {
        for (OrdenCorrectiva orden : ordenesCorrectivas) {
            if (orden.getIdOrdenCorrectiva() == id) {
                ordenesCorrectivas.remove(orden);
                return true; // Retorna true si se elimina la orden
            }
        }
        return false; // Retorna false si no se encuentra la orden
    }

    // Método para iniciar atención de una orden correctiva
    public boolean iniciarAtencion(int idOrden, LocalDate fechaAtencion ) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden != null) {
            orden.setEstado(OrdenCorrectiva.EstadoOrden.EN_PROCESO);
            orden.setFechaAtencion(fechaAtencion);
            return true; // Retorna true si se inicia la atención
        }
        return false; 
    }

    // Método para finalizar una orden correctiva
    public boolean finalizarOrdenCorrectiva(int idOrden, LocalDate fechaFinalizacion, 
                                            String accionesRealizadas, double costo) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden != null) {
            orden.setEstado(OrdenCorrectiva.EstadoOrden.COMPLETADA);
            orden.setFechaFinalizacion(fechaFinalizacion);
            orden.setAccionesRealizadas(accionesRealizadas);
            orden.setCostoReparacion(costo);
            return true; // Retorna true si se finaliza la orden
        }
        return false; // Retorna false si no se encuentra la orden
    }

    // Método para marcar una orden como no reparada
    public boolean marcarNoReparada(int idOrden, String motivo) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden != null) {
            orden.setEstado(OrdenCorrectiva.EstadoOrden.NO_REPARADA);
            orden.setAccionesRealizadas(motivo);
            return true; // Retorna true si se marca la orden como no reparada
        }
        return false; // Retorna false si no se encuentra la orden
    }

}