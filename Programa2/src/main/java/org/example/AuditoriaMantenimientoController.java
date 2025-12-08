package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador encargado del almacenamiento y consulta de eventos
 * de auditoría dentro del sistema.
 *
 * Permite registrar nuevas acciones auditables y realizar filtros
 * por usuario o entidad involucrada en los eventos.
 */
public class AuditoriaMantenimientoController {

    /** Lista donde se almacenan los registros de auditoría. */
    private final List<AuditoriaMantenimiento> registros = new ArrayList<>();

    /**
     * Registra un nuevo movimiento de auditoría dentro del sistema.
     *
     * @param usuario usuario que realizó la acción
     * @param entidad entidad afectada
     * @param accion tipo de acción efectuada
     * @param detalle información adicional descriptiva
     */
    public void registrarMovimiento(String usuario, String entidad, String accion, String detalle) {
        registros.add(new AuditoriaMantenimiento(usuario, entidad, accion, detalle));
    }

    /**
     * Obtiene todos los registros de auditoría almacenados.
     *
     * @return lista de eventos registrados
     */
    public List<AuditoriaMantenimiento> obtenerAuditoria() {
        return new ArrayList<>(registros);
    }

    /**
     * Obtiene registros donde el campo usuario coincida con el filtrado.
     *
     * @param usuario nombre de usuario buscado
     * @return registros asociados al usuario solicitado
     */
    public List<AuditoriaMantenimiento> obtenerPorUsuario(String usuario) {
        List<AuditoriaMantenimiento> res = new ArrayList<>();
        for (AuditoriaMantenimiento a : registros) {
            if (a.getUsuario().equalsIgnoreCase(usuario)) {
                res.add(a);
            }
        }
        return res;
    }

    /**
     * Obtiene registros donde la entidad coincida con el filtrado.
     *
     * @param entidad entidad buscada
     * @return registros asociados a la entidad indicada
     */
    public List<AuditoriaMantenimiento> obtenerPorEntidad(String entidad) {
        List<AuditoriaMantenimiento> res = new ArrayList<>();
        for (AuditoriaMantenimiento a : registros) {
            if (a.getEntidad().equalsIgnoreCase(entidad)) {
                res.add(a);
            }
        }
        return res;
    }
}




