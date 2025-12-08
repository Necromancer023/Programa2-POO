package org.example;

import java.util.List;

/**
 * Controlador encargado de gestionar las fallas registradas en el sistema.
 * Actúa como puente entre la interfaz de usuario y el servicio de fallas,
 * validando entradas y delegando operaciones de creación, consulta y eliminación.
 */
public class FallaController {

    /** Servicio responsable de almacenar y administrar fallas. */
    private FallaService fallaService;

    /**
     * Constructor por defecto.
     * Inicializa el servicio asociado.
     */
    public FallaController() {
        this.fallaService = new FallaService();
    }

    /**
     * Crear una nueva falla validando ID y descripción.
     *
     * @param id identificador numérico de la falla
     * @param descripcion texto descriptivo de la falla
     * @return mensaje informativo sobre el resultado del registro
     */
    public String crearFalla(int id, String descripcion) {

        if (id <= 0) return "El ID debe ser mayor a cero.";
        if (descripcion == null || descripcion.isBlank()) return "Debe indicar una descripción.";

        Falla nueva = new Falla(id, descripcion);

        boolean ok = fallaService.registrarFalla(nueva);

        return ok ? "Falla registrada." : "Ya existe una falla con ese ID.";
    }

    /**
     * Busca una falla existente por su identificador.
     *
     * @param id código numérico de la falla
     * @return objeto {@link Falla} si se encuentra, o null si no existe
     */
    public Falla buscarFalla(int id) {
        return fallaService.buscarFalla(id);
    }

    /**
     * Elimina una falla seleccionada por identificador.
     *
     * @param id código numérico de la falla a eliminar
     * @return mensaje textual indicando el resultado del proceso
     */
    public String eliminarFalla(int id) {
        return fallaService.eliminarFalla(id) ?
                "Falla eliminada." :
                "No se encontró la falla.";
    }

    /**
     * Obtiene el listado completo de fallas registradas en el sistema.
     *
     * @return lista de objetos {@link Falla}
     */
    public List<Falla> obtenerFallas() {
        return fallaService.obtenerFallas();
    }
}


