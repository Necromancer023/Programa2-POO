package org.example;

import java.time.LocalDate;
import java.util.List;

public class OrdenPreventivaController {

    private OrdenPreventivaService ordenService;

    public OrdenPreventivaController() {
        this.ordenService = new OrdenPreventivaService();
    }

    // Crear desde UI (llamado por frame)
    public String crearOrdenPreventiva(int idOrden,
                                       LocalDate fecha,
                                       Equipo equipo,
                                       FasePreventiva fase,
                                       Tecnico tecnico) {

        if (idOrden <= 0) return "ID inválido.";
        if (fecha == null) return "Debe ingresar fecha válida.";
        if (equipo == null) return "Debe seleccionar un equipo.";
        if (fase == null) return "Debe seleccionar una fase.";

        boolean ok = ordenService.crearOrdenPreventiva(idOrden, fecha, equipo, fase, tecnico);

        return ok ? "Orden registrada con éxito."
                  : "No se pudo registrar (ID duplicado).";
    }

    // Crear desde autogeneración
    public String crearOrdenDesdeAutoGeneracion(int idOrden,
                                                LocalDate fecha,
                                                Equipo equipo,
                                                FasePreventiva fase,
                                                Tecnico tecnico) {

        boolean ok = ordenService.crearOrdenPreventiva(idOrden, fecha, equipo, fase, tecnico);

        return ok ? "Orden preventiva generada automáticamente."
                  : "La orden ya existe.";
    }

    // Iniciar orden
    public String iniciarOrden(int idOrden, LocalDate fecha) {

        boolean ok = ordenService.iniciarOrden(idOrden, fecha);

        return ok ? "Orden iniciada correctamente."
                  : "No se pudo iniciar la orden.";
    }

    // Completar orden
    public String completarOrden(int idOrden,
                                 LocalDate fecha,
                                 double tiempo,
                                 String diagFinal,
                                 Tecnico tecnico) {

        if (diagFinal == null || diagFinal.isBlank()) return "Debe ingresar diagnóstico final.";
        if (tiempo < 0) return "Tiempo inválido.";

        boolean ok = ordenService.completarOrden(idOrden, fecha, tiempo, diagFinal, tecnico);

        return ok ? "Orden completada correctamente."
                  : "No se pudo completar (¿Estado incorrecto?).";
    }

    // Cancelar orden
    public String cancelarOrden(int idOrden, String motivo) {

        if (motivo == null || motivo.isBlank()) return "Debe ingresar motivo.";

        boolean ok = ordenService.cancelarOrden(idOrden, motivo);

        return ok ? "Orden cancelada correctamente."
                  : "No se pudo cancelar.";
    }

    // Agregar material
    public String agregarMaterial(int idOrden, String material) {

        if (material == null || material.isBlank()) return "Debe ingresar material.";

        boolean ok = ordenService.agregarMaterial(idOrden, material);

        return ok ? "Material registrado."
                  : "No se pudo registrar material.";
    }

    public List<OrdenPreventiva> obtenerOrdenes() {
        return ordenService.obtenerOrdenes();
    }
}






