package org.example;

import java.time.LocalDate;
import java.util.List;

public class OrdenPreventivaController {

    private OrdenPreventivaService ordenService;

    public OrdenPreventivaController() {
        this.ordenService = new OrdenPreventivaService();
    }

    // ----------------------------------------------------------
    // CREAR ORDEN PREVENTIVA
    // ----------------------------------------------------------
    public String crearOrdenPreventiva(int idOrden, LocalDate fechaProgramada,
                                       Equipo equipoAsociado, FasePreventiva fase,
                                       Tecnico tecnicoAsignado) {

        if (idOrden <= 0) return "El ID debe ser mayor que cero.";
        if (fechaProgramada == null) return "Debe indicar una fecha programada.";
        if (equipoAsociado == null) return "Debe seleccionar un equipo asociado.";
        if (fase == null) return "Debe seleccionar una fase preventiva.";
        if (tecnicoAsignado == null) return "Debe seleccionar un técnico válido.";

        OrdenPreventiva nueva = new OrdenPreventiva(
                idOrden,
                fechaProgramada,
                equipoAsociado,
                fase,
                tecnicoAsignado
        );

        boolean creada = ordenService.agregarOrdenPreventiva(nueva);

        return creada ? "Orden preventiva creada exitosamente."
                      : "Ya existe una orden con ese ID.";
    }

    // ----------------------------------------------------------
    // BUSCAR ORDEN
    // ----------------------------------------------------------
    public OrdenPreventiva buscarOrden(int idOrden) {
        return ordenService.buscarOrdenPreventivaPorId(idOrden);
    }

    // ----------------------------------------------------------
    // ELIMINAR ORDEN
    // ----------------------------------------------------------
    public String eliminarOrden(int idOrden) {
        boolean eliminada = ordenService.eliminarOrdenPreventiva(idOrden);
        return eliminada ? "Orden eliminada correctamente."
                         : "No se encontró la orden.";
    }

    // ----------------------------------------------------------
    // INICIAR ORDEN
    // ----------------------------------------------------------
    public String iniciarOrden(int idOrden, LocalDate fechaInicio) {

        if (fechaInicio == null) {
            return "Debe ingresar una fecha de inicio válida.";
        }

        boolean iniciada = ordenService.iniciarOrdenPreventiva(idOrden, fechaInicio);

        return iniciada ? "La orden ha sido iniciada correctamente."
                        : "No se pudo iniciar la orden.";
    }

    // ----------------------------------------------------------
    // COMPLETAR ORDEN
    // ----------------------------------------------------------
    public String completarOrden(int idOrden,
                                 LocalDate fechaReal,
                                 double tiempoRealHoras,
                                 String diagnosticoFinal,
                                 Tecnico tecnico) {

        if (fechaReal == null) return "Debe ingresar una fecha válida.";
        if (tiempoRealHoras <= 0) return "El tiempo real debe ser mayor que cero.";
        if (diagnosticoFinal == null || diagnosticoFinal.isBlank())
            return "Debe ingresar el diagnóstico final.";
        if (tecnico == null) return "Debe seleccionar un técnico.";

        boolean completada = ordenService.completarOrdenPreventiva(
                idOrden, fechaReal, tiempoRealHoras, diagnosticoFinal, tecnico
        );

        return completada ? "Orden completada exitosamente."
                          : "No se pudo completar la orden.";
    }

    // ----------------------------------------------------------
    // CANCELAR ORDEN
    // ----------------------------------------------------------
    public String cancelarOrden(int idOrden, String motivo) {

        if (motivo == null || motivo.isBlank()) {
            return "Debe indicar el motivo de la cancelación.";
        }

        boolean cancelada = ordenService.cancelarOrdenPreventiva(idOrden, motivo);

        return cancelada ? "Orden cancelada correctamente."
                         : "No se pudo cancelar la orden.";
    }

    // ----------------------------------------------------------
    // AGREGAR MATERIAL A ORDEN
    // ----------------------------------------------------------
    public String agregarMaterial(int idOrden, String material) {

        if (material == null || material.isBlank()) {
            return "Debe indicar un material válido.";
        }

        boolean agregado = ordenService.agregarMaterialAOrden(idOrden, material);

        return agregado ? "Material agregado a la orden."
                        : "No se pudo agregar el material.";
    }

    // ----------------------------------------------------------
    // LISTAR ÓRDENES
    // ----------------------------------------------------------
    public List<OrdenPreventiva> obtenerOrdenes() {
        return ordenService.obtenerOrdenesPreventivas();
    }
}




