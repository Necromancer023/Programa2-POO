package org.example;

import java.time.LocalDate;
import java.util.List;

public class ProgramaPreventivoController {

    private ProgramaPreventivoService programaService;

    // Constructor
    public ProgramaPreventivoController() {
        this.programaService = new ProgramaPreventivoService();
    }

    // Crear un nuevo programa preventivo
    public String crearProgramaPreventivo(int idPrograma, LocalDate fechaCreacion, String responsable) {

        // Validaciones
        if (idPrograma <= 0) {
            return "El ID del programa debe ser mayor que cero.";
        }
        if (fechaCreacion == null) {
            return "Debe ingresar una fecha válida.";
        }
        if (responsable == null || responsable.isBlank()) {
            return "El responsable no puede estar vacío.";
        }

        ProgramaPreventivo nuevo = new ProgramaPreventivo(idPrograma, fechaCreacion, responsable);

        boolean agregado = programaService.agregarProgramaPreventivo(nuevo);

        return agregado ? "Programa preventivo creado exitosamente."
                        : "Ya existe un programa con ese ID.";
    }

    // Buscar un programa por ID
    public ProgramaPreventivo buscarPrograma(int idPrograma) {
        return programaService.buscarProgramaPreventivo(idPrograma);
    }

    // Eliminar un programa preventivo
    public String eliminarPrograma(int idPrograma) {
        boolean eliminado = programaService.eliminarProgramaPreventivo(idPrograma);
        return eliminado ? "Programa eliminado correctamente."
                         : "No se encontró el programa.";
    }

    // Agregar fase a un programa existente
    public String agregarFase(int idPrograma, FasePreventiva fase) {
        if (fase == null) {
            return "Debe enviar una fase válida.";
        }

        boolean agregado = programaService.agregarFaseAPrograma(idPrograma, fase);

        return agregado ? "Fase agregada correctamente."
                        : "No se pudo agregar la fase al programa.";
    }

    // Eliminar una fase por número de fase
    public String eliminarFase(int idPrograma, int numeroFase) {
        boolean eliminada = programaService.eliminarFaseDePrograma(idPrograma, numeroFase);
        return eliminada ? "Fase eliminada correctamente."
                         : "No se pudo eliminar la fase.";
    }

    // Listar todos los programas preventivos
    public List<ProgramaPreventivo> obtenerProgramas() {
        return programaService.obtenerProgramasPreventivos();
    }

}
