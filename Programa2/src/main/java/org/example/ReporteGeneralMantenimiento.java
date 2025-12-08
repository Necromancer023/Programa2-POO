package org.example;

import java.util.List;

/**
 * Clase encargada de generar reportes relacionados con equipos,
 * órdenes preventivas, órdenes correctivas y programas preventivos.
 *
 * Su propósito es centralizar consultas de información para generar
 * reportes estructurados en texto.
 */
public class ReporteGeneralMantenimiento {

    private EquipoService equipoService;
    private OrdenPreventivaService ordenPreventivaService;
    private OrdenCorrectivaService ordenCorrectivaService;
    private ProgramaPreventivoService programaPreventivoService;

    /**
     * Constructor que recibe las dependencias necesarias para generar reportes.
     */
    public ReporteGeneralMantenimiento(EquipoService equipoService,
                                       OrdenPreventivaService ordenPreventivaService,
                                       OrdenCorrectivaService ordenCorrectivaService,
                                       ProgramaPreventivoService programaPreventivoService) {

        this.equipoService = equipoService;
        this.ordenPreventivaService = ordenPreventivaService;
        this.ordenCorrectivaService = ordenCorrectivaService;
        this.programaPreventivoService = programaPreventivoService;
    }

    /**
     * Genera un reporte con la información básica de los equipos registrados.
     *
     * @return reporte textual con datos de equipos.
     */
    public String generarReporteEquipos() {

        StringBuilder sb = new StringBuilder();
        sb.append("====== REPORTE DE EQUIPOS ======\n\n");

        List<Equipo> equipos = equipoService.obtenerEquipos();

        if (equipos.isEmpty()) {
            sb.append("No hay equipos registrados.\n");
            return sb.toString();
        }

        for (Equipo eq : equipos) {
            sb.append("ID: ").append(eq.getId()).append("\n");
            sb.append("Descripción: ").append(eq.getDescripcion()).append("\n");
            sb.append("Ubicación: ").append(eq.getUbicacion()).append("\n");
            sb.append("Estado: ").append(eq.getEstado()).append("\n");
            sb.append("Modelo: ").append(eq.getModelo()).append("\n");
            sb.append("----------\n");
        }

        return sb.toString();
    }

    /**
     * Genera un reporte con el listado de órdenes preventivas registradas.
     *
     * @return texto resumen de órdenes preventivas.
     */
    public String generarReporteOrdenesPreventivas() {

        StringBuilder sb = new StringBuilder();
        sb.append("====== REPORTE ÓRDENES PREVENTIVAS ======\n\n");

        List<OrdenPreventiva> lista = ordenPreventivaService.obtenerOrdenesPreventivas();

        if (lista.isEmpty()) {
            sb.append("No existen órdenes preventivas registradas.\n");
            return sb.toString();
        }

        for (OrdenPreventiva op : lista) {
            sb.append("ID Orden: ").append(op.getIdOrden()).append("\n");
            sb.append("Equipo: ").append(op.getEquipoAsociado().getDescripcion()).append("\n");
            sb.append("Fase: ").append(op.getFase().getDescripcion()).append("\n");
            sb.append("Estado: ").append(op.getEstado()).append("\n");
            sb.append("Fecha Programada: ").append(op.getFechaProgramada()).append("\n");

            if (op.getFechaEjecucion() != null) {
                sb.append("Fecha Ejecución: ").append(op.getFechaEjecucion()).append("\n");
                sb.append("Técnico: ").append(op.getTecnicoAsignado()).append("\n");
                sb.append("Diagnóstico Final: ").append(op.getDiagnosticoFinal()).append("\n");
            }

            sb.append("----------\n");
        }

        return sb.toString();
    }

    /**
     * Genera un reporte con la información sobre órdenes correctivas registradas.
     *
     * @return texto resumen de órdenes correctivas.
     */
    public String generarReporteOrdenesCorrectivas() {

        StringBuilder sb = new StringBuilder();
        sb.append("====== REPORTE ÓRDENES CORRECTIVAS ======\n\n");

        List<OrdenCorrectiva> lista = ordenCorrectivaService.obtenerOrdenesCorrectivas();

        if (lista.isEmpty()) {
            sb.append("No existen órdenes correctivas registradas.\n");
            return sb.toString();
        }

        for (OrdenCorrectiva oc : lista) {
            sb.append("ID Orden: ").append(oc.getIdOrdenCorrectiva()).append("\n");
            sb.append("Equipo: ").append(oc.getEquipoAsociado().getDescripcion()).append("\n");
            sb.append("Estado: ").append(oc.getEstado()).append("\n");
            sb.append("Fecha Reporte: ").append(oc.getFechaReporte()).append("\n");
            sb.append("Descripción Falla: ").append(oc.getDescripcionFalla()).append("\n");
            sb.append("Causa: ").append(oc.getCausaFalla()).append("\n");

            if (oc.getFechaFinalizacion() != null) {
                sb.append("Fecha Finalización: ").append(oc.getFechaFinalizacion()).append("\n");
                sb.append("Acciones: ").append(oc.getAccionesRealizadas()).append("\n");
                sb.append("Costo: ").append(oc.getCostoReparacion()).append("\n");
            }

            sb.append("----------\n");
        }

        return sb.toString();
    }

    /**
     * Genera un reporte con la información básica de los programas preventivos.
     *
     * @return texto resumen de programas preventivos.
     */
    public String generarReporteProgramasPreventivos() {

        StringBuilder sb = new StringBuilder();
        sb.append("====== REPORTE PROGRAMAS PREVENTIVOS ======\n\n");

        List<ProgramaPreventivo> programas = programaPreventivoService.obtenerProgramasPreventivos();

        if (programas.isEmpty()) {
            sb.append("No existen programas preventivos registrados.\n");
            return sb.toString();
        }

        for (ProgramaPreventivo pp : programas) {
            sb.append("ID Programa: ").append(pp.getIdPrograma()).append("\n");
            sb.append("Fecha Creación: ").append(pp.getFechaCreacion()).append("\n");
            sb.append("Responsable: ").append(pp.getResponsable()).append("\n");
            sb.append("Fases registradas: ").append(pp.getFases().size()).append("\n");
            sb.append("----------\n");
        }

        return sb.toString();
    }

    /**
     * Genera un reporte consolidado que integra los reportes
     * de equipos, órdenes preventivas, correctivas y programas preventivos.
     *
     * @return texto completo del reporte general.
     */
    public String generarReporteGeneral() {

        StringBuilder sb = new StringBuilder();

        sb.append("========= REPORTE GENERAL MANTENIMIENTO =========\n\n");

        sb.append(generarReporteEquipos()).append("\n");
        sb.append(generarReporteOrdenesPreventivas()).append("\n");
        sb.append(generarReporteOrdenesCorrectivas()).append("\n");
        sb.append(generarReporteProgramasPreventivos()).append("\n");

        sb.append("======== FIN DEL REPORTE ========");

        return sb.toString();
    }
}

