package org.example;

import java.time.LocalDateTime;

public class AuditoriaMantenimiento {

    private LocalDateTime fechaRegistro;
    private String usuario;
    private String entidad;
    private String accion;
    private String detalle;

    public AuditoriaMantenimiento(String usuario, String entidad, String accion, String detalle) {
        this.fechaRegistro = LocalDateTime.now();
        this.usuario = usuario;
        this.entidad = entidad;
        this.accion = accion;
        this.detalle = detalle;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEntidad() {
        return entidad;
    }

    public String getAccion() {
        return accion;
    }

    public String getDetalle() {
        return detalle;
    }

    @Override
    public String toString() {
        return "[" + fechaRegistro + "] Usuario=" + usuario +
                ", Entidad=" + entidad +
                ", Acción=" + accion +
                ", Detalle=" + detalle;
    }
}


