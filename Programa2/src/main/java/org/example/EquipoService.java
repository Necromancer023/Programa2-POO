package org.example;

import java.util.ArrayList;
import java.util.List;

public class EquipoService {

    private List<Equipo> equipos;


    // Constructor
    public EquipoService() {
        this.equipos = new ArrayList<>();
    }

    // Método para agregar un equipo
    public boolean agregarEquipo(Equipo equipo) {
        //Validar que no exista un equipo con el mismo ID
        for (Equipo e : equipos) {
            if (e.getIdEquipo() == equipo.getIdEquipo()) {
                return false; // No se agrega el equipo porque el ID ya existe
                
            }    
        }

        equipos.add(equipo);
        return true;
    }

    // Método para obtener la lista de equipos
    public List<Equipo> obtenerEquipos() {
        return equipos;
    }

    // Método para buscar un equipo por ID
    public Equipo buscarEquipoPorId(int id) {
        for (Equipo e: equipos) {
            if (e.getIdEquipo() == id) {
                return e;
            }
        }
        return null; // No se encontró el equipo
    }

    // Método para eliminar un equipo por ID

    public boolean eliminarEquipoPorId(int id) {
        Equipo equipoAEliminar = buscarEquipoPorId(id);
        if (equipoAEliminar != null) {
            equipos.remove(equipoAEliminar);
            return true; // Equipo eliminado exitosamente
        }
        return false; // No se encontró el equipo para eliminar
    }

    // Método para actualizar la ubicación de un equipo por ID
    public boolean actualizarUbicacionEquipo(int idEquipo, String nuevaUbicacion) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setUbicacion(nuevaUbicacion);
            return true; // Ubicación actualizada exitosamente
        }
        return false; // No se encontró el equipo para actualizar
    }

    // Método para actualizar el estado de un equipo por ID
    public boolean actualizarEstadoEquipo(int idEquipo, Equipo.EstadoEquipo nuevoEstado) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setEstado(nuevoEstado);
            return true; // Estado actualizado exitosamente
        }
        return false; // No se encontró el equipo para actualizar
    }

    // Método para actualizar el fabricante de un equipo por ID
    public boolean actualizarFabricanteEquipo(int idEquipo, String nuevoFabricante) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setFabricante(nuevoFabricante);
            return true; // Fabricante actualizado exitosamente
        }
        return false; // No se encontró el equipo para actualizar
    }

    // Método para asignar promgrama preventivo
    public boolean asignarProgramaPreventivo(int idEquipo, ProgramaPreventivo programa) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setProgramaPreventivo(programa);
            return true; // Programa preventivo asignado exitosamente
        }
        return false; // No se encontró el equipo para asignar el programa
    }
}
