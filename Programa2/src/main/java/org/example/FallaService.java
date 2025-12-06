package org.example;

import java.util.ArrayList;
import java.util.List;

public class FallaService {

    private List<Falla> fallas;

    public FallaService() {
        this.fallas = new ArrayList<>();
    }

    public boolean registrarFalla(Falla falla) {
        for (Falla f : fallas) {
            if (f.getIdFalla() == falla.getIdFalla()) {
                return false; // ya existe
            }
        }
        fallas.add(falla);
        return true;
    }

    public Falla buscarFalla(int id) {
        for (Falla f : fallas) {
            if (f.getIdFalla() == id) {
                return f;
            }
        }
        return null;
    }

    public boolean eliminarFalla(int id) {
        Falla f = buscarFalla(id);
        if (f != null) {
            fallas.remove(f);
            return true;
        }
        return false;
    }

    public List<Falla> obtenerFallas() {
        return fallas;
    }
}

