package org.example;

import java.time.LocalDate;
import java.util.List;

public class OrdenCorrectivaController {

    private OrdenCorrectivaService ordenService;

    // Constructor
    public OrdenCorrectivaController() {
        this.ordenService = new OrdenCorrectivaService();
    }

    // Crear una nueva orden correctiva
    public String crearOrdenCorrectiva(int idOrden, LocalDate fechaReporte,
                                       Equipo equipoAsociado, String descripcionFalla,
                                       String causaFalla,
                                       OrdenCorrectiva.Prioridad prioridad,
                                       String diagnosticoInicial) {

        // Validaciones
        if (idOrden <= 0) return "El ID debe ser mayor que cero.";
        if (fechaReporte == null) return "Debe ingresar una fecha de reporte válida.";
        if (equipoAsociado == null) return "Debe seleccionar un equipo asociado.";
        if (descripcionFalla == null || descripcionFalla.isBlank())
            return "La descripción de la falla no puede estar vacía.";
        if (causaFalla == null || causaFalla.isBlank())
            return "La causa de la falla no puede estar vacía.";
        if (prioridad == null) return "Debe seleccionar una prioridad.";
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

        boolean creada = ordenService.agregarOrdenCorrectiva(nueva);

        return creada ? "Orden correctiva creada exitosamente."
                : "Ya existe una orden con ese ID.";
    }

    // Buscar orden correctiva por ID
    public OrdenCorrectiva buscarOrden(int idOrden) {
        return ordenService.buscarOrdenCorrectivaPorId(idOrden);
    }

    // Eliminar orden correctiva por ID
    public String eliminarOrden(int idOrden) {
        boolean eliminada = ordenService.eliminarOrdenCorrectiva(idOrden);
        return eliminada ? "Orden eliminada correctamente."
                : "No se encontró la orden.";
    }

    // Iniciar atención de la orden
    public String iniciarAtencion(int idOrden, LocalDate fechaAtencion) {
        if (fechaAtencion == null)
            return "Debe ingresar una fecha de atención válida.";

        OrdenCorrectiva orden = ordenService.buscarOrdenCorrectivaPorId(idOrden);
        if (orden == null)
            return "No se encontró la orden.";

        if (fechaAtencion.isBefore(orden.getFechaReporte()))
            return "La fecha de atención no puede ser anterior a la fecha del reporte.";

        boolean iniciada = ordenService.iniciarAtencion(idOrden, fechaAtencion);

        return iniciada ? "Atención iniciada correctamente."
                : "No se pudo iniciar la atención.";
    }

    // Finalizar una orden correctiva (versión completa con PDF)
    public String finalizarOrden(int idOrden,
                                 LocalDate fechaFinalizacion,
                                 String accionesRealizadas,
                                 String observacionesFinales,
                                 double costo,
                                 double horasTrabajadas) {

        if (fechaFinalizacion == null)
            return "Debe ingresar una fecha de finalización válida.";

        if (accionesRealizadas == null || accionesRealizadas.isBlank())
            return "Debe indicar las acciones realizadas.";

        if (observacionesFinales == null)
            observacionesFinales = "";

        if (costo < 0)
            return "El costo no puede ser negativo.";

        if (horasTrabajadas < 0)
            return "El tiempo trabajado no puede ser negativo.";

        boolean finalizada = ordenService.finalizarOrdenCorrectiva(
                idOrden,
                fechaFinalizacion,
                accionesRealizadas,
                observacionesFinales,
                costo,
                horasTrabajadas
        );

        return finalizada ? "Orden finalizada exitosamente."
                : "No se pudo finalizar la orden.";
    }

    // Registrar material usado en la orden
    public String agregarMaterial(int idOrden, String material) {
        if (material == null || material.isBlank())
            return "Debe ingresar un material válido.";

        boolean agregado = ordenService.agregarMaterial(idOrden, material);
        return agregado ? "Material registrado." :
                "No se pudo registrar el material.";
    }

    // Registrar tiempo empleado
    public String registrarTiempo(int idOrden, double horas) {
        if (horas < 0) return "El tiempo no puede ser negativo.";

        boolean registrado = ordenService.registrarTiempo(idOrden, horas);
        return registrado ? "Tiempo registrado."
                : "No se pudo registrar el tiempo.";
    }

    // Marcar una orden como NO reparada
    public String marcarNoReparada(int idOrden, String motivo) {
        if (motivo == null || motivo.isBlank())
            return "Debe indicar el motivo.";

        boolean marcada = ordenService.marcarNoReparada(idOrden, motivo);

        return marcada ? "La orden fue marcada como no reparada."
                : "No se pudo actualizar la orden.";
    }

    // Listar todas las órdenes correctivas
    public List<OrdenCorrectiva> obtenerOrdenes() {
        return ordenService.obtenerOrdenesCorrectivas();
    }
}



