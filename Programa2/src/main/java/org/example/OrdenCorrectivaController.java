package org.example;

import java.time.LocalDate;
import java.util.List;

public class OrdenCorrectivaController {

    private OrdenCorrectivaService ordenService;

    // Constructor
    public OrdenCorrectivaController() {
        this.ordenService = new OrdenCorrectivaService();
    }

    // Crear nueva orden
    public String crearOrdenCorrectiva(int idOrden,
                                       LocalDate fechaReporte,
                                       Equipo equipoAsociado,
                                       String descripcionFalla,
                                       String causaFalla,
                                       OrdenCorrectiva.Prioridad prioridad,
                                       String diagnosticoInicial) {

        if (idOrden <= 0) return "El ID debe ser mayor que cero.";
        if (fechaReporte == null) return "Debe ingresar fecha de reporte.";
        if (equipoAsociado == null) return "Debe seleccionar un equipo.";
        if (descripcionFalla == null || descripcionFalla.isBlank()) return "Debe indicar la falla.";
        if (causaFalla == null || causaFalla.isBlank()) return "Debe indicar causa.";
        if (prioridad == null) return "Debe indicar prioridad.";
        if (diagnosticoInicial == null) diagnosticoInicial = "";

        OrdenCorrectiva nueva = new OrdenCorrectiva(
                idOrden,
                fechaReporte,
                equipoAsociado,
                descripcionFalla,
                causaFalla,
                prioridad,
                diagnosticoInicial
        );

        boolean ok = ordenService.agregarOrdenCorrectiva(nueva);

        return ok ? "Orden registrada con éxito."
                  : "Ya existe una orden con ese ID.";
    }

    // Iniciar atención
    public String iniciarAtencion(int idOrden, LocalDate fechaAtencion) {
        if (fechaAtencion == null) return "Ingrese fecha válida.";

        boolean ok = ordenService.iniciarAtencion(idOrden, fechaAtencion);

        return ok ? "Atención iniciada correctamente."
                  : "No se pudo iniciar la atención.";
    }

    // Finalizar orden  
    public String finalizarOrden(int idOrden,
                                 LocalDate fechaFinalizacion,
                                 String accionesRealizadas,
                                 String observacionesFinales,
                                 double costo,
                                 double horasTrabajadas) {

        if (fechaFinalizacion == null) return "Debe ingresar fecha válida.";
        if (accionesRealizadas == null || accionesRealizadas.isBlank())
            return "Debe indicar acciones realizadas.";
        if (costo < 0) return "El costo no puede ser negativo.";
        if (horasTrabajadas < 0) return "Las horas trabajadas no pueden ser negativas.";

        boolean ok = ordenService.finalizarOrdenCorrectiva(
                idOrden,
                fechaFinalizacion,
                accionesRealizadas,
                observacionesFinales,
                costo,
                horasTrabajadas
        );

        return ok ? "Orden finalizada correctamente."
                  : "No se pudo finalizar la orden.";
    }

    // Marcar como no reparada
    public String marcarNoReparada(int idOrden, String motivo) {
        if (motivo == null || motivo.isBlank()) return "Debe indicar motivo.";

        boolean ok = ordenService.marcarNoReparada(idOrden, motivo);

        return ok ? "Orden marcada como no reparada."
                  : "No se pudo actualizar.";
    }

    public List<OrdenCorrectiva> obtenerOrdenes() {
        return ordenService.obtenerOrdenesCorrectivas();
    }
}



