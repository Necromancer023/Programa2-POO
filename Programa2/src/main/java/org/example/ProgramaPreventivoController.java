package org.example;

import java.time.LocalDate;
import java.util.List;

public class ProgramaPreventivoController {

    private ProgramaPreventivoService programaService;

    public ProgramaPreventivoController() {
        this.programaService = new ProgramaPreventivoService();
    }

    // Crear programa preventivo
    public String crearProgramaPreventivo(int idPrograma,
                                          String nombre,
                                          String objetivo,
                                          LocalDate fechaCreacion,
                                          String responsable) {

        if (idPrograma <= 0) return "El ID debe ser mayor que cero.";
        if (nombre == null || nombre.isBlank()) return "El nombre no puede estar vacío.";
        if (objetivo == null || objetivo.isBlank()) return "Debe definir un objetivo.";
        if (fechaCreacion == null) return "Debe ingresar una fecha de creación válida.";
        if (responsable == null || responsable.isBlank()) return "Debe ingresar un responsable.";

        ProgramaPreventivo nuevo = new ProgramaPreventivo(
                idPrograma,
                nombre,
                objetivo,
                fechaCreacion,
                responsable
        );

        boolean creado = programaService.agregarProgramaPreventivo(nuevo);

        return creado ? "Programa creado exitosamente."
                      : "Ya existe un programa con ese ID.";
    }

    // Buscar programa
    public ProgramaPreventivo buscarPrograma(int idPrograma) {
        return programaService.buscarProgramaPreventivo(idPrograma);
    }

    // Eliminar programa
    public String eliminarPrograma(int idPrograma) {
        boolean eliminado = programaService.eliminarProgramaPreventivo(idPrograma);
        return eliminado ? "Programa eliminado correctamente."
                         : "No se encontró el programa.";
    }

    // Agregar fase a un programa
    public String agregarFaseAPrograma(int idPrograma, FasePreventiva fase) {

        if (fase == null) return "Debe seleccionar una fase válida.";

        boolean agregado = programaService.agregarFaseAPrograma(idPrograma, fase);

        return agregado ? "Fase agregada correctamente."
                        : "No se pudo agregar la fase (Programa no encontrado).";
    }

    // Eliminar fase
    public String eliminarFaseDePrograma(int idPrograma, int numeroFase) {

        boolean eliminado = programaService.eliminarFaseDePrograma(idPrograma, numeroFase);

        return eliminado ? "Fase eliminada correctamente."
                         : "No se pudo eliminar la fase.";
    }

    // Obtener listado de programas
    public List<ProgramaPreventivo> obtenerProgramas() {
        return programaService.obtenerProgramasPreventivos();
    }
}

