package org.example;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador encargado de gestionar operaciones sobre órdenes preventivas.
 *
 * Actúa como intermediario entre la capa UI (frames) y la capa de servicio,
 * validando datos de entrada y delegando acciones en {@link OrdenPreventivaService}.
 */
public class OrdenPreventivaController {

    private OrdenPreventivaService ordenService;

    /**
     * Inicializa el controlador instanciando el servicio.
     */
    public OrdenPreventivaController() {
        this.ordenService = new OrdenPreventivaService();
    }

    /**
     * Registra una nueva orden preventiva desde una interfaz de usuario.
     *
     * @param idOrden identificador único
     * @param fecha fecha programada
     * @param equipo equipo asociado
     * @param fase fase preventiva asignada
     * @param tecnico técnico responsable
     * @return mensaje de resultado de operación
     */
    public String crearOrdenPreventiva(int idOrden,
                                       LocalDate fecha,
                                       Equipo equipo,
                                       FasePreventiva fase,
                                       Tecnico tecnico) {

        if (idOrden <= 0) return "ID inválido.";
        if (fecha == null) return "Debe ingresar fecha válida.";
        if (equipo == null) return "Debe seleccionar un equipo.";
        if (fase == null) return "Debe seleccionar una fase.";

        boolean ok = ordenService.crearOrdenPreventiva(idOrden, fecha, equipo, fase, tecnico);

        return ok ? "Orden registrada con éxito."
                  : "No se pudo registrar (ID duplicado).";
    }

    /**
     * Registra una orden preventiva creada automáticamente desde programación interna.
     *
     * @param idOrden identificador único
     * @param fecha fecha generada
     * @param equipo equipo asociado
     * @param fase fase preventiva correspondiente
     * @param tecnico técnico asignado
     * @return mensaje de resultado
     */
    public String crearOrdenDesdeAutoGeneracion(int idOrden,
                                                LocalDate fecha,
                                                Equipo equipo,
                                                FasePreventiva fase,
                                                Tecnico tecnico) {

        boolean ok = ordenService.crearOrdenPreventiva(idOrden, fecha, equipo, fase, tecnico);

        return ok ? "Orden preventiva generada automáticamente."
                  : "La orden ya existe.";
    }

    /**
     * Marca una orden como iniciada.
     *
     * @param idOrden identificador de la orden
     * @param fecha fecha real de inicio
     * @return mensaje de resultado
     */
    public String iniciarOrden(int idOrden, LocalDate fecha) {

        boolean ok = ordenService.iniciarOrden(idOrden, fecha);

        return ok ? "Orden iniciada correctamente."
                  : "No se pudo iniciar la orden.";
    }

    /**
     * Completa una orden preventiva registrado diagnóstico, tiempo y técnico.
     *
     * @param idOrden id de la orden
     * @param fecha fecha de finalización
     * @param tiempo horas reales trabajadas
     * @param diagFinal diagnóstico final
     * @param tecnico técnico ejecutante
     * @return mensaje de resultado
     */
    public String completarOrden(int idOrden,
                                 LocalDate fecha,
                                 double tiempo,
                                 String diagFinal,
                                 Tecnico tecnico) {

        if (diagFinal == null || diagFinal.isBlank()) return "Debe ingresar diagnóstico final.";
        if (tiempo < 0) return "Tiempo inválido.";

        boolean ok = ordenService.completarOrden(idOrden, fecha, tiempo, diagFinal, tecnico);

        return ok ? "Orden completada correctamente."
                  : "No se pudo completar (¿Estado incorrecto?).";
    }

    /**
     * Cancela una orden preventiva registrando observación.
     *
     * @param idOrden identificador de la orden
     * @param motivo texto de cancelación
     * @return mensaje de resultado
     */
    public String cancelarOrden(int idOrden, String motivo) {

        if (motivo == null || motivo.isBlank()) return "Debe ingresar motivo.";

        boolean ok = ordenService.cancelarOrden(idOrden, motivo);

        return ok ? "Orden cancelada correctamente."
                  : "No se pudo cancelar.";
    }

    /**
     * Registra material consumido en la ejecución de una orden preventiva.
     *
     * @param idOrden id de la orden
     * @param material material asociado
     * @return mensaje de resultado
     */
    public String agregarMaterial(int idOrden, String material) {

        if (material == null || material.isBlank()) return "Debe ingresar material.";

        boolean ok = ordenService.agregarMaterial(idOrden, material);

        return ok ? "Material registrado."
                  : "No se pudo registrar material.";
    }

    /**
     * Obtiene listado completo de órdenes preventivas registradas.
     *
     * @return lista de {@link OrdenPreventiva}
     */
    public List<OrdenPreventiva> obtenerOrdenes() {
        return ordenService.obtenerOrdenes();
    }
}







