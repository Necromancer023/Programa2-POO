package org.example;

public class FasePreventivaController {

    private ProgramaPreventivoService programaService;

    // Constructor
    public FasePreventivaController() {
        this.programaService = new ProgramaPreventivoService();
    }

    // Crear una nueva fase preventiva
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

    // Agregar una fase a un programa preventivo
    public String agregarFaseAPrograma(int idPrograma, FasePreventiva fase) {
        boolean agregado = programaService.agregarFaseAPrograma(idPrograma, fase);
        return agregado ? "Fase agregada al programa." : "No se encontró el programa preventivo.";
    }

    // Eliminar una fase de un programa preventivo
    public String eliminarFaseDePrograma(int idPrograma, int numeroFase) {
        boolean eliminado = programaService.eliminarFaseDePrograma(idPrograma, numeroFase);
        return eliminado ? "Fase eliminada." : "No se encontró la fase o el programa.";
    }

    // --- Métodos para modificar contenido interno de la fase ---

    public String agregarTarea(FasePreventiva fase, String tarea) {
        if (tarea == null || tarea.isBlank()) return "La tarea no puede estar vacía.";
        fase.agregarTarea(tarea);
        return "Tarea agregada.";
    }

    public String eliminarTarea(FasePreventiva fase, String tarea) {
        fase.eliminarTarea(tarea);
        return "Tarea eliminada.";
    }

    public String agregarRecurso(FasePreventiva fase, String recurso) {
        if (recurso == null || recurso.isBlank()) return "El recurso no puede estar vacío.";
        fase.agregarRecurso(recurso);
        return "Recurso agregado.";
    }

    public String eliminarRecurso(FasePreventiva fase, String recurso) {
        fase.eliminarRecurso(recurso);
        return "Recurso eliminado.";
    }

    // Actualizar descripción
    public String actualizarDescripcion(FasePreventiva fase, String nuevaDescripcion) {
        if (nuevaDescripcion.isBlank()) return "La descripción no puede estar vacía.";
        fase.setDescripcion(nuevaDescripcion);
        return "Descripción actualizada.";
    }

    // Actualizar frecuencia
    public String actualizarFrecuencia(FasePreventiva fase, FasePreventiva.Frecuencia nuevaFrecuencia) {
        fase.setFrecuencia(nuevaFrecuencia);
        return "Frecuencia actualizada.";
    }

    // Actualizar intervalo
    public String actualizarIntervaloDias(FasePreventiva fase, int nuevoIntervalo) {
        if (nuevoIntervalo < 0) return "El intervalo no puede ser negativo.";
        fase.setIntervaloDias(nuevoIntervalo);
        return "Intervalo actualizado.";
    }

    // Actualizar tiempo estimado
    public String actualizarTiempoEstimado(FasePreventiva fase, double nuevoTiempo) {
        if (nuevoTiempo <= 0) return "El tiempo estimado debe ser mayor a 0.";
        fase.setTiempoEstimadoHoras(nuevoTiempo);
        return "Tiempo estimado actualizado.";
    }

    // Actualizar observaciones
    public String actualizarObservaciones(FasePreventiva fase, String observaciones) {
        fase.setObservaciones(observaciones);
        return "Observaciones actualizadas.";
    }

}
