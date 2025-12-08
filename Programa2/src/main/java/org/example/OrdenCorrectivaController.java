package org.example;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones de alto nivel
 * relacionadas con las órdenes correctivas. Aquí se validan entradas,
 * se aplican reglas de negocio y se delega la persistencia al servicio.
 */
public class OrdenCorrectivaController {

    private OrdenCorrectivaService ordenService;

    /**
     * Constructor que inicializa el servicio asociado
     * encargado de almacenar y manipular órdenes correctivas.
     */
    public OrdenCorrectivaController() {
        this.ordenService = new OrdenCorrectivaService();
    }

    /**
     * Registra una nueva orden correctiva en el sistema,
     * validando previamente la información ingresada.
     *
     * @param idOrden            identificador único de la orden
     * @param fechaReporte       fecha en que se reporta la falla
     * @param equipoAsociado     equipo afectado por la falla
     * @param descripcionFalla   descripción reportada de la falla
     * @param causaFalla         posible causa identificada
     * @param prioridad          prioridad asignada
     * @param diagnosticoInicial diagnóstico preliminar (opcional)
     *
     * @return mensaje indicando resultado de registro
     */
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

    /**
     * Inicia el proceso de atención de una orden existente
     * actualizando estado y fecha de inicio.
     *
     * @param idOrden       ID de la orden a actualizar
     * @param fechaAtencion fecha en la que se comienza la atención
     *
     * @return resultado de operación en formato texto
     */
    public String iniciarAtencion(int idOrden, LocalDate fechaAtencion) {
        if (fechaAtencion == null) return "Ingrese fecha válida.";

        boolean ok = ordenService.iniciarAtencion(idOrden, fechaAtencion);

        return ok ? "Atención iniciada correctamente."
                  : "No se pudo iniciar la atención.";
    }

    /**
     * Finaliza una orden asignando información técnica y económica.
     *
     * @param idOrden              orden a finalizar
     * @param fechaFinalizacion    fecha de cierre
     * @param accionesRealizadas   acciones aplicadas
     * @param observacionesFinales conclusiones del trabajo
     * @param costo                costo final de intervención
     * @param horasTrabajadas      horas invertidas en atención
     *
     * @return mensaje indicando resultado de actualización
     */
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

    /**
     * Marca una orden como no reparada, registrando motivo del resultado.
     *
     * @param idOrden ID de la orden asociada
     * @param motivo  explicación del por qué no fue reparada
     *
     * @return mensaje de retroalimentación
     */
    public String marcarNoReparada(int idOrden, String motivo) {
        if (motivo == null || motivo.isBlank()) return "Debe indicar motivo.";

        boolean ok = ordenService.marcarNoReparada(idOrden, motivo);

        return ok ? "Orden marcada como no reparada."
                  : "No se pudo actualizar.";
    }

    /**
     * Obtiene la lista completa de órdenes correctivas registradas..
     *
     * @return colección de órdenes existentes
     */
    public List<OrdenCorrectiva> obtenerOrdenes() {
        return ordenService.obtenerOrdenesCorrectivas();
    }

    /**
     * Busca y devuelve una orden correctiva según su ID.
     * Útil para validación y consultas desde la interfaz UI.
     *
     * @param id identificador buscado
     * @return orden encontrada o null si no existe
     */
    public OrdenCorrectiva buscarOrdenPorId(int id) {
        return ordenService.buscarOrdenCorrectivaPorId(id);
    }

}




