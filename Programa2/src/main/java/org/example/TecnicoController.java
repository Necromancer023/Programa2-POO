package org.example;

import java.util.List;

/**
 * Controlador responsable de gestionar las operaciones relacionadas
 * con los técnicos dentro del sistema.
 *
 * Valida entradas desde la interfaz de usuario antes de delegar
 * operaciones al servicio correspondiente.
 */
public class TecnicoController {

    private TecnicoService tecnicoService;

    /**
     * Constructor que inicializa el servicio asociado.
     */
    public TecnicoController() {
        this.tecnicoService = new TecnicoService();
    }

    /**
     * Registra un nuevo técnico en el sistema tras validar los datos recibidos.
     *
     * @param id identificador único del técnico
     * @param nombre nombre completo
     * @param especialidad área de especialización
     * @param telefono número telefónico (opcional)
     * @param email correo electrónico (opcional)
     * @return mensaje indicando éxito o motivo de fallo
     */
    public String crearTecnico(int id, String nombre,
                               String especialidad, String telefono,
                               String email) {

        if (id <= 0) return "El ID debe ser mayor que cero.";
        if (nombre == null || nombre.isBlank()) return "El nombre no puede estar vacío.";
        if (especialidad == null || especialidad.isBlank()) return "Debe indicar una especialidad.";

        if (telefono == null) telefono = "";
        if (email == null) email = "";

        Tecnico nuevo = new Tecnico(id, nombre, especialidad, telefono, email);

        boolean agregado = tecnicoService.agregarTecnico(nuevo);

        return agregado ? "Técnico registrado correctamente."
                        : "Ya existe un técnico con ese ID.";
    }

    /**
     * Busca un técnico por su identificador.
     *
     * @param id identificador del técnico
     * @return objeto Tecnico correspondiente o null si no existe
     */
    public Tecnico buscarTecnico(int id) {
        return tecnicoService.buscarTecnicoPorId(id);
    }

    /**
     * Cambia el estado activo o inactivo de un técnico.
     *
     * @param id identificador del técnico
     * @param activo nuevo estado a aplicar
     * @return mensaje indicando si se pudo realizar la operación
     */
    public String cambiarEstado(int id, boolean activo) {
        boolean ok = tecnicoService.cambiarEstado(id, activo);
        return ok ? "Estado actualizado." : "No se encontró el técnico.";
    }

    /**
     * Agrega una certificación a un técnico.
     *
     * @param id identificador del técnico
     * @param cert descripción de la certificación
     * @return mensaje indicando resultado de la operación
     */
    public String agregarCertificacion(int id, String cert) {
        if (cert == null || cert.isBlank()) return "Debe indicar una certificación.";

        boolean ok = tecnicoService.agregarCertificacion(id, cert);
        return ok ? "Certificación agregada." : "No se encontró el técnico.";
    }

    /**
     * Elimina un técnico del sistema.
     *
     * @param id identificador del técnico
     * @return mensaje de confirmación o error según resultado
     */
    public String eliminarTecnico(int id) {
        boolean ok = tecnicoService.eliminarTecnico(id);
        return ok ? "Técnico eliminado." : "No se encontró el técnico.";
    }

    /**
     * Obtiene una lista con todos los técnicos registrados.
     *
     * @return lista de técnicos
     */
    public List<Tecnico> listarTecnicos() {
        return tecnicoService.obtenerTecnicos();
    }
}


