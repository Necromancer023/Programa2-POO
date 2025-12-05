package org.example;

import java.time.LocalDate;
import java.util.List;

public class OrdenCorrectivaController {

    private OrdenCorrectivaService ordenService;

    // Constructor
    public OrdenCorrectivaController() {
        this.ordenService = new OrdenCorrectivaService();
    }

    // Crear nueva orden correctiva
    public String crearOrdenCorrectiva(int idOrden, LocalDate fechaReporte, 
                                       Equipo equipoAsociado, String descripcionFalla,
                                       String causaFalla, String prioridad,
                                       String diagnosticoInicial) {

        // VALIDACIONES
        if (idOrden <= 0) return "El ID debe ser mayor que cero.";
        if (fechaReporte == null) return "Debe ingresar una fecha de reporte válida.";
        if (equipoAsociado == null) return "Debe seleccionar un equipo asociado.";
        if (descripcionFalla == null || descripcionFalla.isBlank()) return "La descripción no puede estar vacía.";
        if (causaFalla == null || causaFalla.isBlank()) return "La causa no puede estar vacía.";
        if (prioridad == null || prioridad.isBlank()) return "Debe ingresar la prioridad.";
        if (diagnosticoInicial == null) diagnosticoInicial = "";

        // Crear objeto
        OrdenCorrectiva nueva = new OrdenCorrectiva(
                idOrden, fechaReporte, equipoAsociado, descripcionFalla, causaFalla
        );

        // Asignar los nuevos datos requeridos por el PDF
        nueva.setPrioridad(prioridad);
        nueva.setDiagnosticoInicial(diagnosticoInicial);

        boolean creada = ordenService.agregarOrdenCorrectiva(nueva);

        return creada
                ? "Orden correctiva creada exitosamente."
                : "Ya existe una orden con ese ID.";
    }

    // Buscar orden
    public OrdenCorrectiva buscarOrden(int idOrden) {
        return ordenService.buscarOrdenCorrectivaPorId(idOrden);
    }

    // Eliminar orden
    public String eliminarOrden(int idOrden) {
        boolean eliminada = ordenService.eliminarOrdenCorrectiva(idOrden);
        return eliminada ? "Orden eliminada correctamente."
                         : "No se encontró la orden.";
    }

    // Iniciar atención
    public String iniciarAtencion(int idOrden, LocalDate fechaAtencion) {

        if (fechaAtencion == null) return "Debe ingresar una fecha válida.";

        OrdenCorrectiva orden = ordenService.buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return "No se encontró la orden.";

        if (fechaAtencion.isBefore(orden.getFechaReporte()))
            return "La fecha de atención no puede ser anterior al reporte.";

        boolean iniciada = ordenService.iniciarAtencion(idOrden, fechaAtencion);

        return iniciada
                ? "Atención iniciada correctamente."
                : "No se pudo iniciar la atención.";
    }

    // Registrar tiempo empleado
    public String registrarTiempo(int idOrden, double horas) {
        if (horas <= 0) return "Las horas deben ser mayores a cero.";

        boolean registrado = ordenService.registrarTiempo(idOrden, horas);

        return registrado
                ? "Tiempo registrado correctamente."
                : "No se pudo registrar el tiempo.";
    }

    // Agregar material
    public String agregarMaterial(int idOrden, String material) {
        if (material == null || material.isBlank())
            return "Debe ingresar un material válido.";

        boolean agregado = ordenService.agregarMaterial(idOrden, material);

        return agregado
                ? "Material agregado correctamente."
                : "No se pudo agregar el material.";
    }

    // Finalizar orden correctiva
    public String finalizarOrden(int idOrden, LocalDate fechaFinalizacion,
                                 String accionesRealizadas, double costo,
                                 String observacionesFinales) {

        if (fechaFinalizacion == null)
            return "Debe ingresar una fecha válida.";
        if (accionesRealizadas == null || accionesRealizadas.isBlank())
            return "Debe indicar las acciones realizadas.";
        if (costo < 0)
            return "El costo no puede ser negativo.";

        OrdenCorrectiva orden = ordenService.buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null) return "No se encontró la orden.";

        // Validación del PDF: no puede finalizar antes de iniciar atención
        if (orden.getFechaAtencion() == null)
            return "La orden debe iniciar atención antes de finalizar.";

        if (fechaFinalizacion.isBefore(orden.getFechaAtencion()))
            return "La fecha de finalización no puede ser antes de la atención.";

        boolean finalizada = ordenService.finalizarOrdenCorrectiva(
                idOrden, fechaFinalizacion, accionesRealizadas, costo
        );

        if (finalizada) {
            orden.setObservacionesFinales(observacionesFinales);
            return "Orden finalizada exitosamente.";
        }

        return "No se pudo finalizar la orden.";
    }

    // Marcar NO reparada
    public String marcarNoReparada(int idOrden, String motivo) {

        if (motivo == null || motivo.isBlank())
            return "Debe indicar el motivo.";

        boolean marcada = ordenService.marcarNoReparada(idOrden, motivo);

        return marcada
                ? "Orden marcada como NO REPARADA."
                : "No se pudo actualizar la orden.";
    }

    // Obtener todas las órdenes
    public List<OrdenCorrectiva> obtenerOrdenes() {
        return ordenService.obtenerOrdenesCorrectivas();
    }
}

