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
            if (e.getId() == equipo.getId()) {
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
            if (e.getId() == id) {
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
}
