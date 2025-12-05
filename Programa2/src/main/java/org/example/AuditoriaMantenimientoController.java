package org.example;

import java.time.LocalDate;
import java.util.List;

public class AuditoriaMantenimientoController {

    private AuditoriaMantenimientoService auditoriaService;

    public AuditoriaMantenimientoController() {
        this.auditoriaService = new AuditoriaMantenimientoService();
    }

    public String crearAuditoria(int id, LocalDate fecha, String responsable,
                                 ProgramaPreventivo programa) {

        if (id <= 0) return "ID inválido.";
        if (fecha == null) return "Fecha inválida.";
        if (responsable == null || responsable.isBlank()) return "Debe indicar un responsable.";
        if (programa == null) return "Debe seleccionar un programa preventivo.";

        AuditoriaMantenimiento nueva = new AuditoriaMantenimiento(id, fecha, responsable, programa);

        boolean creada = auditoriaService.agregarAuditoria(nueva);

        return creada ? "Auditoría registrada correctamente."
                      : "Ya existe una auditoría con ese ID.";
    }

    public String agregarHallazgo(int idAuditoria, String hallazgo) {
        if (hallazgo == null || hallazgo.isBlank()) {
            return "El hallazgo no puede estar vacío.";
        }

        boolean ok = auditoriaService.agregarHallazgo(idAuditoria, hallazgo);

        return ok ? "Hallazgo añadido." : "Auditoría no encontrada.";
    }

    public List<AuditoriaMantenimiento> obtenerAuditorias() {
        return auditoriaService.obtenerTodas();
    }

    public String eliminarAuditoria(int id) {
        return auditoriaService.eliminarAuditoria(id)
                ? "Auditoría eliminada."
                : "No se encontró la auditoría.";
    }
}

