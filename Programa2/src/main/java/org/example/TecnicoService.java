package org.example;

import java.util.ArrayList;
import java.util.List;

public class TecnicoService {

    private List<Tecnico> tecnicos;

    public TecnicoService() {
        this.tecnicos = new ArrayList<>();
    }

    // Registrar técnico
    public boolean agregarTecnico(Tecnico tecnico) {

        for (Tecnico t : tecnicos) {
            if (t.getIdTecnico() == tecnico.getIdTecnico()) {
                return false; // ID repetido
            }
        }

        tecnicos.add(tecnico);
        return true;
    }

    // Buscar por ID
    public Tecnico buscarTecnicoPorId(int idTecnico) {
        for (Tecnico t : tecnicos) {
            if (t.getIdTecnico() == idTecnico) {
                return t;
            }
        }
        return null;
    }

    // Eliminar tecnico
    public boolean eliminarTecnico(int idTecnico) {
        Tecnico t = buscarTecnicoPorId(idTecnico);
        if (t != null) {
            tecnicos.remove(t);
            return true;
        }
        return false;
    }

    // Activar o desactivar estado
    public boolean cambiarEstado(int idTecnico, boolean activo) {
        Tecnico t = buscarTecnicoPorId(idTecnico);
        if (t != null) {
            t.setActivo(activo);
            return true;
        }
        return false;
    }

    // Agregar certificación
    public boolean agregarCertificacion(int idTecnico, String cert) {
        Tecnico t = buscarTecnicoPorId(idTecnico);
        if (t != null) {
            t.agregarCertificacion(cert);
            return true;
        }
        return false;
    }

    // Obtener todos
    public List<Tecnico> obtenerTecnicos() {
        return tecnicos;
    }
}

