package org.example;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador responsable de gestionar operaciones relacionadas con los equipos.
 * Actúa como intermediario entre la lógica de negocio del {@link EquipoService}
 * y las interfaces de usuario o capas superiores del sistema.
 */
public class EquipoController {

    /** Servicio encargado de la manipulación y persistencia de equipos. */
    private EquipoService equipoService;

    /**
     * Inicializa el controlador creando una instancia interna del servicio
     * encargado de gestionar los equipos registrados.
     */
    public EquipoController() {
        this.equipoService = new EquipoService();
    }

    /**
     * Crea un nuevo equipo validando previamente los datos requeridos.
     *
     * @param id identificador único del equipo
     * @param descripcion descripción del equipo
     * @param tipo categoría o tipo del equipo
     * @param ubicacion ubicación física en donde se instala
     * @param fabricante nombre del fabricante
     * @param serie número de serie
     * @param fechaAdquisicion fecha de adquisición del equipo
     * @param fechaPuestaEnServicio fecha de puesta en operación
     * @param mesesVidaUtil vida útil estimada en meses
     * @param costoInicial valor de compra o inversión del recurso
     * @param estado condición operativa inicial
     * @param modelo modelo del equipo
     * @param dimensiones dimensiones físicas del dispositivo
     * @param peso peso físico del equipo
     * @return mensaje con resultado de la operación
     */
    public String crearEquipo(int id, String descripcion, String tipo, String ubicacion,
                              String fabricante, String serie, LocalDate fechaAdquisicion,
                              LocalDate fechaPuestaEnServicio, int mesesVidaUtil,
                              double costoInicial, Equipo.EstadoEquipo estado,
                              String modelo, String dimensiones, double peso) {

        if (id <= 0) return "El ID debe ser mayor que cero.";

        if (descripcion.isBlank() || tipo.isBlank() || ubicacion.isBlank() ||
            fabricante.isBlank() || serie.isBlank()) {
            return "Ningún campo requerido puede estar vacío.";
        }

        if (mesesVidaUtil <= 0) return "La vida útil debe ser mayor que cero.";
        if (fechaAdquisicion == null || fechaPuestaEnServicio == null)
            return "Las fechas no pueden ser nulas.";
        if (fechaPuestaEnServicio.isBefore(fechaAdquisicion))
            return "La fecha puesta en servicio no puede ser anterior.";
        if (modelo == null || modelo.isBlank()) return "Debe indicar modelo.";
        if (dimensiones == null || dimensiones.isBlank()) return "Debe indicar dimensiones.";
        if (peso <= 0) return "El peso debe ser mayor que cero.";

        Equipo nuevo = new Equipo(
                id, descripcion, tipo, ubicacion, fabricante, serie,
                fechaAdquisicion, fechaPuestaEnServicio, mesesVidaUtil,
                costoInicial, estado, modelo, dimensiones, peso
        );

        boolean agregado = equipoService.agregarEquipo(nuevo);

        return agregado ? "Equipo creado exitosamente."
                        : "Ya existe un equipo con ese ID.";
    }

    /**
     * Busca y obtiene un equipo por su identificador.
     *
     * @param id ID del equipo a localizar
     * @return instancia del equipo encontrado o null si no existe
     */
    public Equipo buscarEquipo(int id) {
        return equipoService.buscarEquipoPorId(id);
    }

    /**
     * Elimina un equipo verificando que no posea órdenes preventivas
     * o correctivas asociadas antes de su eliminación.
     *
     * @param id identificador del equipo a eliminar
     * @return mensaje indicando éxito o motivo de falla
     */
    public String eliminarEquipo(int id) {

        int preventivas = equipoService.contarOrdenesPreventivas(id);
        int correctivas = equipoService.contarOrdenesCorrectivas(id);

        if (preventivas > 0 || correctivas > 0) {
            return "No puede eliminarse — El equipo tiene "
                    + preventivas + " preventivas y "
                    + correctivas + " correctivas asociadas.";
        }

        boolean eliminado = equipoService.eliminarEquipoPorId(id);

        return eliminado ? "Equipo eliminado exitosamente."
                          : "No se encontró el equipo.";
    }

    /**
     * Actualiza la ubicación física registrada del equipo.
     *
     * @param idEquipo identificador del equipo
     * @param nuevaUbicacion texto con la nueva ubicación
     * @return mensaje indicando resultado de la operación
     */
    public String actualizarUbicacion(int idEquipo, String nuevaUbicacion) {
        return equipoService.actualizarUbicacion(idEquipo, nuevaUbicacion)
                ? "Ubicación actualizada."
                : "Equipo no encontrado.";
    }

    /**
     * Modifica el estado operativo del equipo si no posee solicitudes
     * de mantenimiento en proceso.
     *
     * @param idEquipo identificador del equipo
     * @param nuevoEstado estado operativo a establecer
     * @return mensaje indicando éxito o restricción
     */
    public String actualizarEstado(int idEquipo, Equipo.EstadoEquipo nuevoEstado) {

        if (equipoService.tieneOrdenesEnProceso(idEquipo)) {
            return "No puede cambiar estado — el equipo tiene órdenes en proceso.";
        }

        return equipoService.actualizarEstado(idEquipo, nuevoEstado)
                ? "Estado actualizado."
                : "Equipo no encontrado.";
    }

    /**
     * Obtiene un listado de todos los equipos registrados en el sistema.
     *
     * @return colección de equipos almacenados
     */
    public List<Equipo> obtenerEquipos() {
        return equipoService.obtenerEquipos();
    }

    /**
     * Asigna un programa preventivo a un equipo determinado.
     *
     * @param idEquipo identificador del equipo
     * @param programa instancia del programa preventivo a asociar
     * @return mensaje con resultado de la asociación
     */
    public String asignarProgramaPreventivo(int idEquipo, ProgramaPreventivo programa) {
        return equipoService.asignarProgramaPreventivo(idEquipo, programa)
                ? "Programa preventivo asignado con éxito."
                : "No se pudo asignar el programa.";
    }

    /**
     * Añade un componente subordinado a un equipo principal existente.
     *
     * @param idEquipo identificador del equipo principal
     * @param componente equipo que será registrado como componente
     * @return mensaje indicando éxito o falla de la operación
     */
    public String agregarComponente(int idEquipo, Equipo componente) {
        Equipo principal = equipoService.buscarEquipoPorId(idEquipo);

        if (principal == null) {
            return "El equipo principal no existe.";
        }

        principal.agregarComponente(componente);
        return "Componente añadido correctamente.";
    }
}




