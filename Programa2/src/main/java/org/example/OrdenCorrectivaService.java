package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenCorrectivaService {

    private List<OrdenCorrectiva> ordenesCorrectivas;

    // Constructor
    public OrdenCorrectivaService() {
        this.ordenesCorrectivas = new ArrayList<>();
    }

    // Crear/agregar una orden correctiva
    public boolean agregarOrdenCorrectiva(OrdenCorrectiva orden) {
        for (OrdenCorrectiva o : ordenesCorrectivas) {
            if (o.getIdOrdenCorrectiva() == orden.getIdOrdenCorrectiva()) {
                return false; // Ya existe una orden con ese ID
            }
        }
        ordenesCorrectivas.add(orden);
        return true;
    }

    // Buscar por ID
    public OrdenCorrectiva buscarOrdenCorrectivaPorId(int idOrden) {
        for (OrdenCorrectiva o : ordenesCorrectivas) {
            if (o.getIdOrdenCorrectiva() == idOrden) {
                return o;
            }
        }
        return null;
    }

    // Eliminar por ID
    public boolean eliminarOrdenCorrectiva(int idOrden) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden != null) {
            ordenesCorrectivas.remove(orden);
            return true;
        }
        return false;
    }

    // Iniciar atención de una orden
    public boolean iniciarAtencion(int idOrden, LocalDate fechaAtencion) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return false;

        // Validación de fechas
        if (fechaAtencion.isBefore(orden.getFechaReporte())) {
            return false;
        }

        orden.iniciarAtencion(fechaAtencion);
        return true;
    }

    // Finalizar la orden correctiva
    public boolean finalizarOrdenCorrectiva(int idOrden,
                                            LocalDate fechaFinalizacion,
                                            String accionesRealizadas,
                                            double costoTotal) {

        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return false;

        // Validaciones de fechas según el PDF
        if (orden.getFechaAtencion() == null) {
            return false; // No se puede finalizar sin inicia atención
        }

        if (fechaFinalizacion.isBefore(orden.getFechaAtencion())) {
            return false; // No puede terminar antes de que comenzó
        }

        // Finaliza la orden con datos básicos (los demás valores son llenados desde el controller)
        orden.setFechaFinalizacion(fechaFinalizacion);
        orden.setAccionesRealizadas(accionesRealizadas);
        orden.setCostoReparacion(costoTotal);
        orden.setEstado(OrdenCorrectiva.EstadoOrden.COMPLETADA);

        return true;
    }

    // Marcar como NO reparada
    public boolean marcarNoReparada(int idOrden, String motivo) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return false;

        orden.setEstado(OrdenCorrectiva.EstadoOrden.NO_REPARADA);
        orden.setObservacionesFinales("NO REPARADA: " + motivo);

        return true;
    }

    // Registrar un material usado
    public boolean agregarMaterial(int idOrden, String material) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return false;

        orden.agregarMaterial(material);
        return true;
    }

    // Registrar tiempo real empleado
    public boolean registrarTiempo(int idOrden, double horas) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null || horas < 0) return false;

        orden.setTiempoEmpleadoHoras(horas);
        return true;
    }

    // Obtener todas las órdenes
    public List<OrdenCorrectiva> obtenerOrdenesCorrectivas() {
        return ordenesCorrectivas;
    }
}
