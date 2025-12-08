package org.example;

import java.time.LocalDateTime;

/**
 * Representa un evento registrado en el sistema de auditoría.
 * Cada registro almacena información relativa a una acción realizada
 * por un usuario sobre una determinada entidad, junto con una marca
 * de tiempo y detalles del evento.
 */
public class AuditoriaMantenimiento {

    /** Fecha y hora exacta en que se registró el evento. */
    private LocalDateTime fechaRegistro;

    /** Nombre o identificador del usuario que ejecutó la acción. */
    private String usuario;

    /** Tipo de entidad afectada por la acción (EQUIPO, ORDEN, USUARIO, etc.). */
    private String entidad;

    /** Nombre o descripción breve de la acción realizada. */
    private String accion;

    /** Información adicional que describe la acción ejecutada. */
    private String detalle;

    /**
     * Crea un nuevo evento de auditoría con los datos indicados.
     * La fecha de registro se asigna automáticamente con la hora actual del sistema.
     *
     * @param usuario  usuario responsable de la acción
     * @param entidad  entidad afectada por el evento
     * @param accion   tipo de acción realizada
     * @param detalle  detalle descriptivo del evento
     */
    public AuditoriaMantenimiento(String usuario, String entidad, String accion, String detalle) {
        this.fechaRegistro = LocalDateTime.now();
        this.usuario = usuario;
        this.entidad = entidad;
        this.accion = accion;
        this.detalle = detalle;
    }

    /**
     * Obtiene la fecha y hora del registro.
     *
     * @return instante de creación del evento de auditoría
     */
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Obtiene el nombre del usuario que originó el registro.
     *
     * @return nombre o identificador de usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Obtiene el nombre de la entidad afectada por la acción.
     *
     * @return entidad asociada al registro
     */
    public String getEntidad() {
        return entidad;
    }

    /**
     * Obtiene la descripción breve de la acción registrada.
     *
     * @return tipo de acción ejecutada
     */
    public String getAccion() {
        return accion;
    }

    /**
     * Obtiene información detallada sobre el evento ocurrido.
     *
     * @return descripción detallada de la acción
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * Retorna una representación textual del evento en el formato:
     * [fecha] Usuario=..., Entidad=..., Acción=..., Detalle=...
     *
     * @return cadena descriptiva del registro de auditoría
     */
    @Override
    public String toString() {
        return "[" + fechaRegistro + "] Usuario=" + usuario +
                ", Entidad=" + entidad +
                ", Acción=" + accion +
                ", Detalle=" + detalle;
    }
}



