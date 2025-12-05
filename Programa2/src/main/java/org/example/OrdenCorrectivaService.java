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

    // FINALIZAR ORDEN — versión completa
    public boolean finalizarOrdenCorrectiva(int idOrden,
                                            LocalDate fechaFinalizacion,
                                            String accionesRealizadas,
                                            String observacionesFinales,
                                            double costo,
                                            double horasTrabajadas) {

        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return false;

        // No se puede finalizar si no se inició
        if (orden.getFechaAtencion() == null) return false;

        // La finalización no puede ser antes que la fecha de atención
        if (fechaFinalizacion.isBefore(orden.getFechaAtencion())) return false;

        orden.finalizarOrden(
                fechaFinalizacion,
                accionesRealizadas,
                observacionesFinales,
                costo,
                horasTrabajadas
        );

        return true;
    }

    // MARCAR COMO NO REPARADA
    public boolean marcarNoReparada(int idOrden, String motivo) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return false;

        orden.setEstado(OrdenCorrectiva.EstadoOrden.NO_REPARADA);
        orden.setObservacionesFinales("NO REPARADA: " + motivo);

        return true;
    }

    // REGISTRAR MATERIAL UTILIZADO
    public boolean agregarMaterial(int idOrden, String material) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return false;

        orden.agregarMaterial(material);
        return true;
    }

    // REGISTRAR TIEMPO EMPLEADO
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

