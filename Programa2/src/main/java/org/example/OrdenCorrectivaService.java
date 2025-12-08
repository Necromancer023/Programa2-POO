package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar las operaciones sobre
 * órdenes correctivas: creación, búsqueda, actualización
 * de estados, materiales, tiempos y consultas.
 *
 * Actúa como capa de negocio para {@link OrdenCorrectivaController}.
 */
public class OrdenCorrectivaService {

    private List<OrdenCorrectiva> ordenesCorrectivas;

    /**
     * Constructor — inicializa la lista interna de órdenes correctivas.
     */
    public OrdenCorrectivaService() {
        this.ordenesCorrectivas = new ArrayList<>();
    }

    /**
     * Registra una orden correctiva si su ID no existe previamente.
     *
     * @param orden nueva orden a añadir
     * @return true si se agregó, false si existe otra con mismo ID
     */
    public boolean agregarOrdenCorrectiva(OrdenCorrectiva orden) {
        for (OrdenCorrectiva o : ordenesCorrectivas) {
            if (o.getIdOrdenCorrectiva() == orden.getIdOrdenCorrectiva()) {
                return false; // Ya existe una orden con ese ID
            }
        }
        ordenesCorrectivas.add(orden);
        return true;
    }

    /**
     * Busca y retorna una orden correctiva por su ID.
     *
     * @param idOrden identificador de la orden
     * @return instancia encontrada o null si no existe
     */
    public OrdenCorrectiva buscarOrdenCorrectivaPorId(int idOrden) {
        for (OrdenCorrectiva o : ordenesCorrectivas) {
            if (o.getIdOrdenCorrectiva() == idOrden) {
                return o;
            }
        }
        return null;
    }

    /**
     * Elimina una orden correctiva existente.
     *
     * @param idOrden ID de la orden a eliminar
     * @return true si se eliminó, false si no existe
     */
    public boolean eliminarOrdenCorrectiva(int idOrden) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden != null) {
            ordenesCorrectivas.remove(orden);
            return true;
        }
        return false;
    }

    /**
     * Cambia el estado de una orden a EN_PROCESO,
     * validando coherencia temporal.
     *
     * @param idOrden ID de la orden
     * @param fechaAtencion fecha en que inicia su atención
     * @return true si se realizó el cambio; false si falla validación
     */
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

    /**
     * Finaliza una orden correctiva, registrando costos, horas,
     * resultados y observaciones internas.
     *
     * @param idOrden ID de la orden
     * @param fechaFinalizacion fecha de cierre
     * @param accionesRealizadas acciones ejecutadas
     * @param observacionesFinales texto final de resumen
     * @param costo costo económico
     * @param horasTrabajadas tiempo invertido
     * @return true si la orden fue finalizada correctamente
     */
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

    /**
     * Cambia el estado de una orden a NO_REPARADA
     * guardando el motivo correspondiente.
     *
     * @param idOrden ID de la orden
     * @param motivo texto explicativo
     * @return true si se aplicó el cambio
     */
    public boolean marcarNoReparada(int idOrden, String motivo) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return false;

        orden.setEstado(OrdenCorrectiva.EstadoOrden.NO_REPARADA);
        orden.setObservacionesFinales("NO REPARADA: " + motivo);

        return true;
    }

    /**
     * Registra material utilizado en una orden.
     *
     * @param idOrden ID de la orden
     * @param material descripción del material
     * @return true si se agregó, false si la orden no existe
     */
    public boolean agregarMaterial(int idOrden, String material) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return false;

        orden.agregarMaterial(material);
        return true;
    }

    /**
     * Guarda el tiempo empleado en la reparación.
     *
     * @param idOrden ID de la orden
     * @param horas cantidad de horas trabajadas
     * @return true si se registró, false si orden inexistente o valor inválido
     */
    public boolean registrarTiempo(int idOrden, double horas) {
        OrdenCorrectiva orden = buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null || horas < 0) return false;

        orden.setTiempoEmpleadoHoras(horas);
        return true;
    }

    /**
     * Devuelve todas las órdenes registradas.
     *
     * @return lista completa de órdenes correctivas
     */
    public List<OrdenCorrectiva> obtenerOrdenesCorrectivas() {
        return ordenesCorrectivas;
    }

    /**
     * Cuenta cuántas órdenes correctivas están asociadas a un equipo.
     *
     * @param idEquipo ID del equipo
     * @return número de órdenes asociadas
     */
    public long contarOrdenesCorrectivasPorEquipo(int idEquipo) {
        return obtenerOrdenesCorrectivas().stream()
                .filter(oc -> oc.getEquipoAsociado().getId() == idEquipo)
                .count();
    }
}


