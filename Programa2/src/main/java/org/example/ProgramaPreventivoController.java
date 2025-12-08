package org.example;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones sobre
 * Programas Preventivos desde la capa de presentación (UI).
 *
 * Este controlador valida datos ingresados por el usuario,
 * construye objetos de dominio y delega la persistencia y lógica
 * a la capa de servicios (ProgramaPreventivoService).
 */
public class ProgramaPreventivoController {

    /** Servicio encargado de la lógica de negocio sobre programas preventivos */
    private ProgramaPreventivoService programaService;

    /**
     * Instancia un controlador inicializando su servicio asociado.
     */
    public ProgramaPreventivoController() {
        this.programaService = new ProgramaPreventivoService();
    }

    /**
     * Crea un nuevo programa preventivo realizando validaciones previas.
     *
     * @param idPrograma identificador del programa
     * @param nombre nombre descriptivo del programa
     * @param objetivo propósito del mantenimiento preventivo
     * @param fechaCreacion fecha en que se registra el programa
     * @param responsable persona encargada o autor
     * @return mensaje informativo del resultado de registro
     */
    public String crearProgramaPreventivo(int idPrograma,
                                          String nombre,
                                          String objetivo,
                                          LocalDate fechaCreacion,
                                          String responsable) {

        if (idPrograma <= 0) return "El ID debe ser mayor que cero.";
        if (nombre == null || nombre.isBlank()) return "El nombre no puede estar vacío.";
        if (objetivo == null || objetivo.isBlank()) return "Debe definir un objetivo.";
        if (fechaCreacion == null) return "Debe ingresar una fecha válida.";
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

    /**
     * Obtiene un programa preventivo según su ID.
     *
     * @param idPrograma identificador buscado
     * @return instancia del programa o null si no existe
     */
    public ProgramaPreventivo buscarPrograma(int idPrograma) {
        return programaService.buscarProgramaPreventivo(idPrograma);
    }

    /**
     * Elimina un programa preventivo si no tiene órdenes asociadas.
     *
     * @param idPrograma identificador del programa a eliminar
     * @return mensaje indicando si la operación fue exitosa o rechazada
     */
    public String eliminarPrograma(int idPrograma) {

        // Regla técnica: no puede eliminarse si hay órdenes preventivas generadas
        if (programaService.tieneOrdenesGeneradas(idPrograma)) {
            return "No puede eliminarse — El programa tiene órdenes preventivas ya generadas.";
        }

        boolean eliminado = programaService.eliminarProgramaPreventivo(idPrograma);

        return eliminado ? "Programa eliminado correctamente."
                         : "No se encontró el programa.";
    }

    /**
     * Agrega una fase preventiva a un programa existente.
     *
     * @param idPrograma ID del programa objetivo
     * @param fase objeto fase a vincular
     * @return mensaje del resultado
     */
    public String agregarFaseAPrograma(int idPrograma, FasePreventiva fase) {

        if (fase == null) return "Debe seleccionar una fase válida.";

        boolean agregado = programaService.agregarFaseAPrograma(idPrograma, fase);

        return agregado ? "Fase agregada correctamente."
                        : "No se pudo agregar la fase (Programa no encontrado).";
    }

    /**
     * Elimina una fase asociada a un programa por número de fase.
     *
     * @param idPrograma programa origen
     * @param numeroFase identificador de fase dentro del programa
     * @return mensaje del estado de eliminación
     */
    public String eliminarFaseDePrograma(int idPrograma, int numeroFase) {

        boolean eliminado = programaService.eliminarFaseDePrograma(idPrograma, numeroFase);

        return eliminado ? "Fase eliminada correctamente."
                         : "No se pudo eliminar la fase.";
    }

    /**
     * Retorna la lista de programas preventivos registrados.
     *
     * @return lista de instancias ProgramaPreventivo
     */
    public List<ProgramaPreventivo> obtenerProgramas() {
        return programaService.obtenerProgramasPreventivos();
    }
}



