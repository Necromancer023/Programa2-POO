package org.example;

import java.util.List;

public class AuditoriaMantenimientoController {

    private AuditoriaMantenimientoService auditoriaService;

    public AuditoriaMantenimientoController() {
        this.auditoriaService = new AuditoriaMantenimientoService();
    }

    public void registrarEvento(String usuario, String entidad, String accion, String detalle) {
        auditoriaService.registrar(usuario, entidad, accion, detalle);
    }

    public List<AuditoriaMantenimiento> obtenerAuditoria() {
        return auditoriaService.obtenerTodos();
    }

    public List<AuditoriaMantenimiento> obtenerPorUsuario(String usuario) {
        return auditoriaService.buscarPorUsuario(usuario);
    }

    public List<AuditoriaMantenimiento> obtenerPorEntidad(String entidad) {
        return auditoriaService.buscarPorEntidad(entidad);
    }

    public void registrarAccion(String usuario, String accion, String descripcion) {
    auditoriaService.registrar(usuario, "SISTEMA", accion, descripcion);
    }
}


