package org.example;

import java.time.LocalDate;
import java.util.List;

public class EquipoController {

    private EquipoService equipoService;

    // Constructor
    public EquipoController() {
        this.equipoService = new EquipoService();
    }

    // Crear equipo
    public String crearEquipo(int id, String descripcion, String tipo, String ubicacion,
                              String fabricante, String serie, LocalDate fechaAdquisicion,
                              LocalDate fechaPuestaEnServicio, int mesesVidaUtil,
                              double costoInicial, Equipo.EstadoEquipo estado,
                              String modelo, String dimensiones, double peso) {

        // ----- VALIDACIONES -----
        if (id <= 0) return "El ID debe ser mayor que cero.";

        if (descripcion.isBlank() || tipo.isBlank() || ubicacion.isBlank() ||
            fabricante.isBlank() || serie.isBlank()) {
            return "Ningún campo requerido puede estar vacío.";
        }

        if (mesesVidaUtil <= 0) return "La vida útil debe ser mayor que cero.";

        if (fechaAdquisicion == null || fechaPuestaEnServicio == null)
            return "Las fechas no pueden ser nulas.";

        if (fechaPuestaEnServicio.isBefore(fechaAdquisicion))
            return "La fecha de puesta en servicio no puede ser anterior a la adquisición.";

        if (modelo == null || modelo.isBlank()) return "Debe indicar un modelo.";
        if (dimensiones == null || dimensiones.isBlank()) return "Debe indicar dimensiones.";
        if (peso <= 0) return "El peso debe ser mayor que cero.";

        // Crear el objeto equipo
        Equipo nuevo = new Equipo(
                id, descripcion, tipo, ubicacion, fabricante, serie,
                fechaAdquisicion, fechaPuestaEnServicio, mesesVidaUtil,
                costoInicial, estado,
                modelo, dimensiones, peso
        );

        boolean agregado = equipoService.agregarEquipo(nuevo);

        return agregado ?
                "Equipo creado exitosamente." :
                "Ya existe un equipo con ese ID.";
    }

    // Buscar por ID
    public Equipo buscarEquipo(int id) {
        return equipoService.buscarEquipoPorId(id);
    }

    // Eliminar equipo
    public String eliminarEquipo(int id) {
        boolean eliminado = equipoService.eliminarEquipoPorId(id);
        return eliminado ? "Equipo eliminado exitosamente." : "No se encontró el equipo.";
    }

    // Actualizar ubicación
    public String actualizarUbicacion(int idEquipo, String nuevaUbicacion) {
        return equipoService.actualizarUbicacion(idEquipo, nuevaUbicacion)
                ? "Ubicación actualizada."
                : "Equipo no encontrado.";
    }

    // Actualizar estado
    public String actualizarEstado(int idEquipo, Equipo.EstadoEquipo nuevoEstado) {
        return equipoService.actualizarEstado(idEquipo, nuevoEstado)
                ? "Estado actualizado."
                : "Equipo no encontrado.";
    }

    // Actualizar fabricante
    public String actualizarFabricante(int idEquipo, String nuevoFabricante) {
        return equipoService.actualizarFabricante(idEquipo, nuevoFabricante)
                ? "Fabricante actualizado."
                : "Equipo no encontrado.";
    }

    // Obtener lista de equipos
    public List<Equipo> obtenerEquipos() {
        return equipoService.obtenerEquipos();
    }

    // Asignar programa preventivo
    public String asignarProgramaPreventivo(int idEquipo, ProgramaPreventivo programa) {
        return equipoService.asignarProgramaPreventivo(idEquipo, programa)
                ? "Programa preventivo asignado con éxito."
                : "No se pudo asignar el programa.";
    }

    // Agregar componente a un equipo
    public String agregarComponente(int idEquipo, Equipo componente) {
        Equipo principal = equipoService.buscarEquipoPorId(idEquipo);

        if (principal == null) {
            return "El equipo principal no existe.";
        }

        principal.agregarComponente(componente);
        return "Componente añadido correctamente.";
    }
}


