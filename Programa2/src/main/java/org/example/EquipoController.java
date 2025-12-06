package org.example;

import java.time.LocalDate;
import java.util.List;

public class EquipoController {

    private EquipoService equipoService;

    public EquipoController() {
        this.equipoService = new EquipoService();
    }

    public String crearEquipo(int id, String descripcion, String tipo, String ubicacion,
                              String fabricante, String serie, LocalDate fechaAdquisicion,
                              LocalDate fechaPuestaEnServicio, int mesesVidaUtil,
                              double costoInicial, Equipo.EstadoEquipo estado,
                              String modelo, String dimensiones, double peso) {

        if (id <= 0) return "El ID debe ser mayor que cero.";

        if (descripcion.isBlank() || tipo.isBlank() || ubicacion.isBlank() ||
            fabricante.isBlank() || serie.isBlank()) {
            return "Ningún campo requerido puede estar vacío.";
        }

        if (mesesVidaUtil <= 0) return "La vida útil debe ser mayor que cero.";
        if (fechaAdquisicion == null || fechaPuestaEnServicio == null)
            return "Las fechas no pueden ser nulas.";
        if (fechaPuestaEnServicio.isBefore(fechaAdquisicion))
            return "La fecha puesta en servicio no puede ser anterior.";
        if (modelo == null || modelo.isBlank()) return "Debe indicar modelo.";
        if (dimensiones == null || dimensiones.isBlank()) return "Debe indicar dimensiones.";
        if (peso <= 0) return "El peso debe ser mayor que cero.";

        Equipo nuevo = new Equipo(
                id, descripcion, tipo, ubicacion, fabricante, serie,
                fechaAdquisicion, fechaPuestaEnServicio, mesesVidaUtil,
                costoInicial, estado, modelo, dimensiones, peso
        );

        boolean agregado = equipoService.agregarEquipo(nuevo);

        return agregado ? "Equipo creado exitosamente."
                        : "Ya existe un equipo con ese ID.";
    }

    public Equipo buscarEquipo(int id) {
        return equipoService.buscarEquipoPorId(id);
    }

    public String eliminarEquipo(int id) {

        int preventivas = equipoService.contarOrdenesPreventivas(id);
        int correctivas = equipoService.contarOrdenesCorrectivas(id);

        if (preventivas > 0 || correctivas > 0) {
            return "No puede eliminarse — El equipo tiene "
                    + preventivas + " preventivas y "
                    + correctivas + " correctivas asociadas.";
        }

        boolean eliminado = equipoService.eliminarEquipoPorId(id);

        return eliminado ? "Equipo eliminado exitosamente."
                          : "No se encontró el equipo.";
    }

    public String actualizarUbicacion(int idEquipo, String nuevaUbicacion) {
        return equipoService.actualizarUbicacion(idEquipo, nuevaUbicacion)
                ? "Ubicación actualizada."
                : "Equipo no encontrado.";
    }

    public String actualizarEstado(int idEquipo, Equipo.EstadoEquipo nuevoEstado) {

        if (equipoService.tieneOrdenesEnProceso(idEquipo)) {
            return "No puede cambiar estado — el equipo tiene órdenes en proceso.";
        }

        return equipoService.actualizarEstado(idEquipo, nuevoEstado)
                ? "Estado actualizado."
                : "Equipo no encontrado.";
    }

    public List<Equipo> obtenerEquipos() {
        return equipoService.obtenerEquipos();
    }

    public String asignarProgramaPreventivo(int idEquipo, ProgramaPreventivo programa) {
        return equipoService.asignarProgramaPreventivo(idEquipo, programa)
                ? "Programa preventivo asignado con éxito."
                : "No se pudo asignar el programa.";
    }

    public String agregarComponente(int idEquipo, Equipo componente) {
        Equipo principal = equipoService.buscarEquipoPorId(idEquipo);

        if (principal == null) {
            return "El equipo principal no existe.";
        }

        principal.agregarComponente(componente);
        return "Componente añadido correctamente.";
    }
}



