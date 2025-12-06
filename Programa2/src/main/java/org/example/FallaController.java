package org.example;

import java.util.List;

public class FallaController {

    private FallaService fallaService;

    public FallaController() {
        this.fallaService = new FallaService();
    }

    public String crearFalla(int id, String descripcion) {

        if (id <= 0) return "El ID debe ser mayor a cero.";
        if (descripcion == null || descripcion.isBlank()) return "Debe indicar una descripción.";

        Falla nueva = new Falla(id, descripcion);

        boolean ok = fallaService.registrarFalla(nueva);

        return ok ? "Falla registrada." : "Ya existe una falla con ese ID.";
    }

    public Falla buscarFalla(int id) {
        return fallaService.buscarFalla(id);
    }

    public String eliminarFalla(int id) {
        return fallaService.eliminarFalla(id) ?
                "Falla eliminada." :
                "No se encontró la falla.";
    }

    public List<Falla> obtenerFallas() {
        return fallaService.obtenerFallas();
    }
}

