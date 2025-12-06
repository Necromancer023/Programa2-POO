package org.example;

import java.time.LocalDate;
import java.util.List;

public class OrdenPreventivaController {

    private OrdenPreventivaService ordenService;

    public OrdenPreventivaController() {
        this.ordenService = new OrdenPreventivaService();
    }

    public String crearOrdenPreventiva(int idOrden,
                                       LocalDate fechaPlanificada,
                                       ProgramaPreventivo programa,
                                       Equipo equipo) {

        if (idOrden <= 0) return "El ID debe ser mayor que cero.";
        if (fechaPlanificada == null) return "Debe ingresar una fecha válida.";
        if (programa == null) return "Debe seleccionar programa.";
        if (equipo == null) return "Debe seleccionar equipo.";

        // El programa debe tener al menos 1 fase para generar la orden
        FasePreventiva fase = programa.obtenerFase(1);
        if (fase == null) return "El programa preventivo no tiene fases registradas.";

        // Se asigna sin técnico inicialmente (puedes agregar asignación después)
        OrdenPreventiva nuevo = new OrdenPreventiva(
            idOrden,
            fechaPlanificada,
            equipo,
            fase,
            null  // Técnico no asignado aún
        );

        boolean ok = ordenService.agregarOrdenPreventiva(nuevo);

        return ok ? "Orden preventiva registrada."
                  : "Ya existe una orden con ese ID.";
    }


    public String finalizarOrden(int idOrden,
                                 LocalDate fechaRealizacion,
                                 String resultado) {

        if (fechaRealizacion == null) return "Fecha inválida.";
        if (resultado == null || resultado.isBlank()) return "Debe indicar resultado.";

        if (!ordenService.puedeFinalizar(idOrden)) {
            return "No se puede finalizar — La orden aún no está en ejecución o ya está cerrada.";
        }

        boolean ok = ordenService.finalizarOrdenPreventiva(idOrden, fechaRealizacion, resultado);

        return ok ? "Orden finalizada."
                  : "No se pudo finalizar la orden.";
    }

    public List<OrdenPreventiva> obtenerOrdenes() {
        return ordenService.obtenerOrdenesPreventivas();
    }
}





