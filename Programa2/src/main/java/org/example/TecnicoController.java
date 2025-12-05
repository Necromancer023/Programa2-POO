package org.example;

import java.util.List;

public class TecnicoController {

    private TecnicoService tecnicoService;

    public TecnicoController() {
        this.tecnicoService = new TecnicoService();
    }

    // Crear técnico
    public String crearTecnico(int id, String nombre,
                               String especialidad, String telefono,
                               String email) {

        if (id <= 0) return "El ID debe ser mayor que cero.";
        if (nombre == null || nombre.isBlank()) return "El nombre no puede estar vacío.";
        if (especialidad == null || especialidad.isBlank()) return "Debe indicar una especialidad.";

        if (telefono == null) telefono = "";
        if (email == null) email = "";

        Tecnico nuevo = new Tecnico(id, nombre, especialidad, telefono, email);

        boolean agregado = tecnicoService.agregarTecnico(nuevo);

        return agregado ? "Técnico registrado correctamente."
                        : "Ya existe un técnico con ese ID.";
    }

    // Buscar por ID
    public Tecnico buscarTecnico(int id) {
        return tecnicoService.buscarTecnicoPorId(id);
    }

    // Activar/desactivar
    public String cambiarEstado(int id, boolean activo) {
        boolean ok = tecnicoService.cambiarEstado(id, activo);
        return ok ? "Estado actualizado." : "No se encontró el técnico.";
    }

    // Agregar certificación
    public String agregarCertificacion(int id, String cert) {
        if (cert == null || cert.isBlank()) return "Debe indicar una certificación.";

        boolean ok = tecnicoService.agregarCertificacion(id, cert);
        return ok ? "Certificación agregada." : "No se encontró el técnico.";
    }

    // Eliminar técnico
    public String eliminarTecnico(int id) {
        boolean ok = tecnicoService.eliminarTecnico(id);
        return ok ? "Técnico eliminado." : "No se encontró el técnico.";
    }

    // Listar
    public List<Tecnico> listarTecnicos() {
        return tecnicoService.obtenerTecnicos();
    }
}

