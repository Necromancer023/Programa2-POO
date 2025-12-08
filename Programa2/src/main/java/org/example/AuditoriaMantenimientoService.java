package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado de administrar los registros de auditoría del sistema.
 * 
 * Esta clase actúa como capa lógica para almacenar eventos de auditoría,
 * consultarlos y filtrarlos según parámetros determinados como usuario
 * o entidad afectada.
 */
public class AuditoriaMantenimientoService {

    /** Lista donde se almacenan todos los registros generados por el sistema. */
    private List<AuditoriaMantenimiento> registros;

    /**
     * Construye el servicio inicializando la colección de auditorías.
     */
    public AuditoriaMantenimientoService() {
        this.registros = new ArrayList<>();
    }

    /**
     * Registra un nuevo evento de auditoría con información asociada al usuario,
     * entidad afectada, acción realizada y descripción detallada.
     *
     * @param usuario usuario que ejecutó la acción
     * @param entidad área o elemento del sistema afectado
     * @param accion tipo de cambio realizado
     * @param detalle información adicional relacionada con el evento
     */
    public void registrar(String usuario, String entidad, String accion, String detalle) {
        AuditoriaMantenimiento nuevo = new AuditoriaMantenimiento(usuario, entidad, accion, detalle);
        registros.add(nuevo);
    }

    /**
     * Obtiene todos los registros almacenados en la auditoría.
     *
     * @return lista completa de eventos registrados
     */
    public List<AuditoriaMantenimiento> obtenerTodos() {
        return registros;
    }

    /**
     * Filtra los registros de auditoría devolviendo únicamente aquellos
     * que fueron generados por un usuario específico.
     *
     * @param usuario nombre del usuario buscado
     * @return lista de auditorías asociadas a dicho usuario
     */
    public List<AuditoriaMantenimiento> buscarPorUsuario(String usuario) {
        return registros.stream()
                .filter(r -> r.getUsuario().equalsIgnoreCase(usuario))
                .collect(Collectors.toList());
    }

    /**
     * Filtra los registros de auditoría devolviendo únicamente aquellos
     * relacionados con una entidad determinada del sistema
     * (por ejemplo, USUARIO, EQUIPO o ORDEN).
     *
     * @param entidad entidad sobre la cual se consultan los registros
     * @return lista de auditorías vinculadas a dicha entidad
     */
    public List<AuditoriaMantenimiento> buscarPorEntidad(String entidad) {
        return registros.stream()
                .filter(r -> r.getEntidad().equalsIgnoreCase(entidad))
                .collect(Collectors.toList());
    }
}



