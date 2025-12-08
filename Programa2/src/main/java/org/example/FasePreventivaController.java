package org.example;

/**
 * Controlador encargado de gestionar la creación, modificación y asignación
 * de fases preventivas dentro de programas de mantenimiento.
 *
 * Ofrece métodos para crear fases, asignarlas a programas, eliminarlas
 * y modificar su contenido interno (tareas, recursos y parámetros).
 */
public class FasePreventivaController {

    /** Servicio de programas preventivos utilizado para operaciones de asignación. */
    private ProgramaPreventivoService programaService;

    /**
     * Constructor que inicializa el controlador con su servicio asociado.
     */
    public FasePreventivaController() {
        this.programaService = new ProgramaPreventivoService();
    }

    /**
     * Crea una nueva fase preventiva validando los datos de entrada, pero
     * no la almacena directamente. Solo devuelve el resultado de validación.
     *
     * @param numeroFase identificador interno de la fase dentro del programa
     * @param descripcion descripción textual de la fase
     * @param frecuencia frecuencia programada de ejecución de la fase
     * @param intervaloDias cantidad de días entre repeticiones
     * @param tiempoEstimadoHoras duración estimada de ejecución en horas
     * @return mensaje indicando éxito o error según validaciones
     */
    public String crearFase(int numeroFase, String descripcion,
                            FasePreventiva.Frecuencia frecuencia, int intervaloDias,
                            double tiempoEstimadoHoras) {

        // Validaciones
        if (numeroFase <= 0) return "El número de fase debe ser mayor a 0.";
        if (descripcion == null || descripcion.isBlank()) return "La descripción no puede estar vacía.";
        if (intervaloDias < 0) return "El intervalo en días no puede ser negativo.";
        if (tiempoEstimadoHoras <= 0) return "El tiempo estimado debe ser mayor a 0.";

        return "Fase creada correctamente.";
    }

    /**
     * Agrega una fase a un programa preventivo ya existente.
     *
     * @param idPrograma identificador del programa preventivo destino
     * @param fase instancia de fase a agregar
     * @return mensaje indicando éxito o error por programa inexistente
     */
    public String agregarFaseAPrograma(int idPrograma, FasePreventiva fase) {
        boolean agregado = programaService.agregarFaseAPrograma(idPrograma, fase);
        return agregado ? "Fase agregada al programa." : "No se encontró el programa preventivo.";
    }

    /**
     * Elimina una fase perteneciente a un programa.
     *
     * @param idPrograma identificador del programa al cual pertenece
     * @param numeroFase número secuencial de fase a eliminar
     * @return mensaje indicando éxito o fallo por no encontrarse
     */
    public String eliminarFaseDePrograma(int idPrograma, int numeroFase) {
        boolean eliminado = programaService.eliminarFaseDePrograma(idPrograma, numeroFase);
        return eliminado ? "Fase eliminada." : "No se encontró la fase o el programa.";
    }

    // --- Métodos para modificar contenido interno de la fase ---

    /**
     * Agrega una tarea a la fase especificada.
     *
     * @param fase instancia donde agregar la tarea
     * @param tarea descripción textual de la tarea
     * @return mensaje descriptivo del resultado
     */
    public String agregarTarea(FasePreventiva fase, String tarea) {
        if (tarea == null || tarea.isBlank()) return "La tarea no puede estar vacía.";
        fase.agregarTarea(tarea);
        return "Tarea agregada.";
    }

    /**
     * Elimina una tarea asociada a una fase.
     *
     * @param fase instancia afectada
     * @param tarea identificación textual de la tarea
     * @return mensaje informativo
     */
    public String eliminarTarea(FasePreventiva fase, String tarea) {
        fase.eliminarTarea(tarea);
        return "Tarea eliminada.";
    }

    /**
     * Agrega un recurso registrado dentro de la fase.
     *
     * @param fase instancia a modificar
     * @param recurso texto del recurso requerido
     * @return mensaje indicando el resultado
     */
    public String agregarRecurso(FasePreventiva fase, String recurso) {
        if (recurso == null || recurso.isBlank()) return "El recurso no puede estar vacío.";
        fase.agregarRecurso(recurso);
        return "Recurso agregado.";
    }

    /**
     * Elimina un recurso asignado a la fase.
     *
     * @param fase instancia de fase a modificar
     * @param recurso recurso que debe eliminarse
     * @return mensaje informativo
     */
    public String eliminarRecurso(FasePreventiva fase, String recurso) {
        fase.eliminarRecurso(recurso);
        return "Recurso eliminado.";
    }

    /**
     * Modifica la descripción textual asociada a una fase.
     *
     * @param fase instancia a editar
     * @param nuevaDescripcion descripción actualizada
     * @return resultado de la operación
     */
    public String actualizarDescripcion(FasePreventiva fase, String nuevaDescripcion) {
        if (nuevaDescripcion.isBlank()) return "La descripción no puede estar vacía.";
        fase.setDescripcion(nuevaDescripcion);
        return "Descripción actualizada.";
    }

    /**
     * Establece una nueva frecuencia de ejecución para la fase.
     *
     * @param fase instancia de fase
     * @param nuevaFrecuencia valor de frecuencia a asignar
     * @return mensaje de actualización
     */
    public String actualizarFrecuencia(FasePreventiva fase, FasePreventiva.Frecuencia nuevaFrecuencia) {
        fase.setFrecuencia(nuevaFrecuencia);
        return "Frecuencia actualizada.";
    }

    /**
     * Cambia el intervalo operacional de la fase.
     *
     * @param fase instancia a modificar
     * @param nuevoIntervalo cantidad de días
     * @return mensaje con el resultado
     */
    public String actualizarIntervaloDias(FasePreventiva fase, int nuevoIntervalo) {
        if (nuevoIntervalo < 0) return "El intervalo no puede ser negativo.";
        fase.setIntervaloDias(nuevoIntervalo);
        return "Intervalo actualizado.";
    }

    /**
     * Modifica el tiempo estimado de ejecución de una fase.
     *
     * @param fase instancia sobre la que aplicar el cambio
     * @param nuevoTiempo cantidad de horas
     * @return mensaje con resultado de validación
     */
    public String actualizarTiempoEstimado(FasePreventiva fase, double nuevoTiempo) {
        if (nuevoTiempo <= 0) return "El tiempo estimado debe ser mayor a 0.";
        fase.setTiempoEstimadoHoras(nuevoTiempo);
        return "Tiempo estimado actualizado.";
    }

    /**
     * Actualiza las observaciones internas registradas en la fase.
     *
     * @param fase instancia destino
     * @param observaciones texto de observaciones
     * @return mensaje informativo
     */
    public String actualizarObservaciones(FasePreventiva fase, String observaciones) {
        fase.setObservaciones(observaciones);
        return "Observaciones actualizadas.";
    }

}

