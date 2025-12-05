package org.example;

import java.util.ArrayList;
import java.util.List;

public class AuditoriaMantenimientoService {

    private List<AuditoriaMantenimiento> auditorias;

    public AuditoriaMantenimientoService() {
        this.auditorias = new ArrayList<>();
    }

    public boolean agregarAuditoria(AuditoriaMantenimiento auditoria) {
        for (AuditoriaMantenimiento a : auditorias) {
            if (a.getIdAuditoria() == auditoria.getIdAuditoria()) {
                return false; // ID duplicado
            }
        }
        auditorias.add(auditoria);
        return true;
    }

    public AuditoriaMantenimiento buscarPorId(int id) {
        for (AuditoriaMantenimiento a : auditorias) {
            if (a.getIdAuditoria() == id) {
                return a;
            }
        }
        return null;
    }

    public boolean eliminarAuditoria(int id) {
        AuditoriaMantenimiento a = buscarPorId(id);
        if (a != null) {
            auditorias.remove(a);
            return true;
        }
        return false;
    }

    public List<AuditoriaMantenimiento> obtenerTodas() {
        return auditorias;
    }

    public boolean agregarHallazgo(int idAuditoria, String hallazgo) {
        AuditoriaMantenimiento a = buscarPorId(idAuditoria);
        if (a == null) return false;

        a.agregarHallazgo(hallazgo);
        return true;
    }
}

