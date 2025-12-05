package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuditoriaMantenimientoService {

    private List<AuditoriaMantenimiento> registros;

    public AuditoriaMantenimientoService() {
        this.registros = new ArrayList<>();
    }

    public void registrar(String usuario, String entidad, String accion, String detalle) {
        AuditoriaMantenimiento nuevo = new AuditoriaMantenimiento(usuario, entidad, accion, detalle);
        registros.add(nuevo);
    }

    public List<AuditoriaMantenimiento> obtenerTodos() {
        return registros;
    }

    public List<AuditoriaMantenimiento> buscarPorUsuario(String usuario) {
        return registros.stream()
                .filter(r -> r.getUsuario().equalsIgnoreCase(usuario))
                .collect(Collectors.toList());
    }

    public List<AuditoriaMantenimiento> buscarPorEntidad(String entidad) {
        return registros.stream()
                .filter(r -> r.getEntidad().equalsIgnoreCase(entidad))
                .collect(Collectors.toList());
    }
}


