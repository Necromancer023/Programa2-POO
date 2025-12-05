package org.example;

import java.time.LocalDate;
import java.util.List;

public class EquipoController {

    private EquipoService equipoService;

    // Constructor
    public EquipoController() {
        this.equipoService = new EquipoService();
    }
    
    // Crear un nuevo equipo
    public String crearEquipo(int id, String descripcion, String tipo, String ubicacion,
                              String fabricante, String serie, LocalDate fechaAdquisicion,
                              LocalDate fechaPuestaEnServicio, int mesesVidaUtil,
                              double costoInicial, Equipo.EstadoEquipo estado) {

        // Validaciones 
        if (mesesVidaUtil <= 0) {
            return "La vida útil debe ser mayor a cero meses.";
        }

        if (fechaAdquisicion == null || fechaPuestaEnServicio == null) {
            return "Las fechas no pueden ser nulas.";
        }

        if (fechaPuestaEnServicio.isBefore(fechaAdquisicion)) {
            return "La fecha de puesta en servicio no puede ser anterior a la de adquisición.";
        }

        if (descripcion.isBlank() || tipo.isBlank() || ubicacion.isBlank()
                || fabricante.isBlank() || serie.isBlank()) {
            return "Ningún campo de texto puede estar vacío.";
        }

        Equipo nuevoEquipo = new Equipo(id, descripcion, tipo, ubicacion, fabricante,
                                        serie, fechaAdquisicion, fechaPuestaEnServicio,
                                        mesesVidaUtil, costoInicial, estado);
        
        boolean agregado = equipoService.agregarEquipo(nuevoEquipo);
        return agregado ? "Equipo creado exitosamente." : "Ya existe un equipo con el mismo ID.";
    }

    // Buscar un equipo por ID
    public Equipo buscarEquipoPorId(int id) {
        return equipoService.buscarEquipoPorId(id);
    }

    // Eliminar equipo
    public String eliminarEquipo(int id) {
        boolean eliminado = equipoService.eliminarEquipoPorId(id);
        return eliminado ? "Equipo eliminado." : "Error: No existe el equipo.";
    }

    // Actualizar ubicación
    public String actualizarUbicacion(int idEquipo, String nuevaUbicacion) {
        boolean actualizado = equipoService.actualizarUbicacionEquipo(idEquipo, nuevaUbicacion);
        return actualizado ? "Ubicación actualizada." : "No se encontró el equipo.";
    }

    // Actualizar estado
    public String actualizarEstado(int idEquipo, Equipo.EstadoEquipo nuevoEstado) {
        boolean estadoActualizado = equipoService.actualizarEstadoEquipo(idEquipo, nuevoEstado);
        return estadoActualizado ? "Estado actualizado." : "No se encontró el equipo.";
    }

    // Actualizar fabricante
    public String actualizarFabricante(int idEquipo, String nuevoFabricante) {
        boolean fabricanteActualizado = equipoService.actualizarFabricanteEquipo(idEquipo, nuevoFabricante);
        return fabricanteActualizado ? "Fabricante actualizado." : "No se encontró el equipo.";
    }

    // Listar equipos
    public List<Equipo> obtenerEquipos() {
        return equipoService.obtenerEquipos();
    }

    // Asignar programa preventivo
    public String asignarProgramaPreventivo(int idEquipo, ProgramaPreventivo programa) {
        boolean programaAsignado = equipoService.asignarProgramaPreventivo(idEquipo, programa);
        return programaAsignado ? "Programa asignado." : "No se pudo asignar el programa.";
    }

    // Agregar componente a un equipo
    public String agregarComponente(int idEquipo, Equipo componente) {
        Equipo equipo = equipoService.buscarEquipoPorId(idEquipo);
        if (equipo == null) return "Equipo principal no encontrado.";

        equipo.agregarComponente(componente);
        return "Componente añadido correctamente.";
    }
}