package org.example;

import java.util.ArrayList;
import java.util.List;

public class EquipoService {

    private List<Equipo> equipos;

    // Constructor
    public EquipoService() {
        this.equipos = new ArrayList<>();
    }

    // Agregar equipo
    public boolean agregarEquipo(Equipo equipo) {
        for (Equipo e : equipos) {
            if (e.getId() == equipo.getId()) {
                return false; // ID duplicado
            }
        }
        equipos.add(equipo);
        return true;
    }

    // Obtener todos los equipos
    public List<Equipo> obtenerEquipos() {
        return equipos;
    }

    // Buscar un equipo por ID
    public Equipo buscarEquipoPorId(int id) {
        for (Equipo e : equipos) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    // Eliminar un equipo por ID
    public boolean eliminarEquipoPorId(int id) {
        Equipo equipo = buscarEquipoPorId(id);
        if (equipo != null) {
            equipos.remove(equipo);
            return true;
        }
        return false;
    }

    // Actualizar ubicación
    public boolean actualizarUbicacion(int idEquipo, String nuevaUbicacion) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setUbicacion(nuevaUbicacion);
            return true;
        }
        return false;
    }

    // Actualizar fabricante
    public boolean actualizarFabricante(int idEquipo, String nuevoFabricante) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setFabricante(nuevoFabricante);
            return true;
        }
        return false;
    }

    // Actualizar estado (sin historial)
    public boolean actualizarEstado(int idEquipo, Equipo.EstadoEquipo nuevoEstado) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    // Asignar programa preventivo
    public boolean asignarProgramaPreventivo(int idEquipo, ProgramaPreventivo programa) {
        Equipo equipo = buscarEquipoPorId(idEquipo);
        if (equipo != null) {
            equipo.setProgramaPreventivo(programa);
            return true;
        }
        return false;
    }
}


