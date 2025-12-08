package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de administrar el catálogo de equipos del sistema.
 * Provee operaciones CRUD básicas, validaciones y consultas asociadas
 * al estado y relaciones del equipo dentro del proceso de mantenimiento.
 */
public class EquipoService {

    /** Lista interna que almacena los equipos registrados. */
    private List<Equipo> equipos;

    /**
     * Construye el servicio inicializando la colección de equipos.
     */
    public EquipoService() {
        this.equipos = new ArrayList<>();
    }

    /**
     * Agrega un equipo al sistema, validando que no exista otro
     * con el mismo identificador.
     *
     * @param equipo objeto a registrar
     * @return true si se agrega correctamente; false si el ID ya existe
     */
    public boolean agregarEquipo(Equipo equipo) {
        for (Equipo e : equipos) {
            if (e.getId() == equipo.getId()) {
                return false; // ID duplicado
            }
        }
        equipos.add(equipo);
        return true;
    }

    /**
     * Obtiene la lista de todos los equipos registrados.
     *
     * @return colección actual de equipos
     */
    public List<Equipo> obtenerEquipos() {
        return equipos;
    }

    /**
     * Busca un equipo según su identificador.
     *
     * @param id identificador del equipo
     * @return el equipo encontrado o null si no existe
     */
    public Equipo buscarEquipoPorId(int id) {
        for (Equipo e : equipos) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    /**
     * Elimina un equipo de la lista si existe.
     *
     * @param id identificador a eliminar
     * @return true si se eliminó; false si no se encontró
     */
    public boolean eliminarEquipoPorId(int id) {
        Equipo equipo = buscarEquipoPorId(id);
        if (equipo != null) {
            equipos.remove(equipo);
            return true;
        }
        return false;
    }

    /**
     * Actualiza la ubicación física de un equipo.
     *
     * @param idEquipo identificador del equipo
     * @param nuevaUbicacion nueva ubicación asignada
     * @return true si se actualizó; false si no se encontró
     */
    public boolean actualizarUbicacion(int idEquipo, String nuevaUbicacion) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setUbicacion(nuevaUbicacion);
            return true;
        }
        return false;
    }

    /**
     * Actualiza el fabricante almacenado para un equipo.
     *
     * @param idEquipo identificador del equipo
     * @param nuevoFabricante fabricante a registrar
     * @return true si se actualizó; false si no se encontró
     */
    public boolean actualizarFabricante(int idEquipo, String nuevoFabricante) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setFabricante(nuevoFabricante);
            return true;
        }
        return false;
    }

    /**
     * Cambia el estado del equipo sin registrar historial.
     *
     * @param idEquipo identificador del equipo
     * @param nuevoEstado estado a asignar
     * @return true si se actualizó; false si no existe
     */
    public boolean actualizarEstado(int idEquipo, Equipo.EstadoEquipo nuevoEstado) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    /**
     * Asigna un programa preventivo a un equipo.
     *
     * @param idEquipo identificador del equipo
     * @param programa instancia de programa preventivo
     * @return true si se asignó; false si no se encontró el equipo
     */
    public boolean asignarProgramaPreventivo(int idEquipo, ProgramaPreventivo programa) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setProgramaPreventivo(programa);
            return true;
        }
        return false;
    }

    // ============================================
    //      VALIDACIONES ESPECÍFICAS DEL PDF
    // ============================================

    /**
     * Cuenta cuántas órdenes preventivas tiene asociadas el equipo.
     *
     * @param idEquipo identificador del equipo
     * @return número de órdenes preventivas vinculadas
     */
    public int contarOrdenesPreventivas(int idEquipo) {
        return (int) SistemaMantenimiento.getInstancia()
                .getOrdenPreventivaController()
                .obtenerOrdenes()
                .stream()
                .filter(op -> op.getEquipoAsociado().getId() == idEquipo)
                .count();
    }

    /**
     * Cuenta cuántas órdenes correctivas tiene asociadas el equipo.
     *
     * @param idEquipo identificador del equipo
     * @return número de órdenes correctivas vinculadas
     */
    public int contarOrdenesCorrectivas(int idEquipo) {
        return (int) SistemaMantenimiento.getInstancia()
                .getOrdenCorrectivaController()
                .obtenerOrdenes()
                .stream()
                .filter(oc -> oc.getEquipoAsociado().getId() == idEquipo)
                .count();
    }

    /**
     * Verifica si el equipo posee órdenes correctivas que se encuentren activas
     * bajo el estado "EN_PROCESO".
     *
     * @param idEquipo identificador del equipo
     * @return true si tiene órdenes activas; false si no
     */
    public boolean tieneOrdenesEnProceso(int idEquipo) {
        return SistemaMantenimiento.getInstancia()
                .getOrdenCorrectivaController()
                .obtenerOrdenes()
                .stream()
                .anyMatch(oc -> oc.getEquipoAsociado().getId() == idEquipo &&
                             oc.getEstado() == OrdenCorrectiva.EstadoOrden.EN_PROCESO);
    }
}



