package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de administrar la lógica de negocio relacionada con los
 * Programas Preventivos.
 *
 * Se encarga de:
 *  Registrar programas
 *  Buscar programas
 *  Modificar fases
 *  Validar dependencias antes de eliminar
 *  Generar órdenes preventivas automáticamente con base en fases
 *
 * Esta clase opera como capa intermedia entre los controladores y la entidad ProgramaPreventivo.
 */
public class ProgramaPreventivoService {

    /** Lista que almacena todos los programas preventivos registrados */
    private List<ProgramaPreventivo> programasPreventivos;

    /** Constructor: inicializa lista interna */
    public ProgramaPreventivoService() {
        this.programasPreventivos = new ArrayList<>();
    }

    // ======================================================
    // CRUD PROGRAMAS PREVENTIVOS
    // ======================================================

    /**
     * Agrega un programa preventivo a la colección.
     * Antes valida que no exista otro con el mismo ID.
     *
     * @return true si fue agregado, false si ya existía un programa con ese ID
     */
    public boolean agregarProgramaPreventivo(ProgramaPreventivo programa) {
        for (ProgramaPreventivo p : programasPreventivos) {
            if (p.getIdPrograma() == programa.getIdPrograma()) {
                return false; // duplicado
            }
        }

        programasPreventivos.add(programa);
        return true;
    }

    /**
     * Obtiene la lista completa de programas preventivos registrados.
     */
    public List<ProgramaPreventivo> obtenerProgramasPreventivos() {
        return programasPreventivos;
    }

    /**
     * Busca un programa preventivo por su identificador.
     *
     * @return el programa encontrado o null si no existe
     */
    public ProgramaPreventivo buscarProgramaPreventivo(int idPrograma) {
        for (ProgramaPreventivo p : programasPreventivos) {
            if (p.getIdPrograma() == idPrograma) {
                return p;
            }
        }
        return null;
    }

    /**
     * Elimina un programa de la colección si existe.
     *
     * @return true si fue eliminado, false en caso contrario
     */
    public boolean eliminarProgramaPreventivo(int idPrograma) {
        ProgramaPreventivo programaAEliminar = buscarProgramaPreventivo(idPrograma);
        if (programaAEliminar != null) {
            programasPreventivos.remove(programaAEliminar);
            return true;
        }
        return false;
    }

    // ======================================================
    // GESTIÓN DE FASES EN PROGRAMAS
    // ======================================================

    /**
     * Agrega una fase preventiva vinculada a un programa existente.
     *
     * @return true si se pudo agregar, false si no existe el programa
     */
    public boolean agregarFaseAPrograma(int idPrograma, FasePreventiva fase) {
        ProgramaPreventivo programa = buscarProgramaPreventivo(idPrograma);
        if (programa != null) {
            programa.agregarFase(fase);
            return true;
        }
        return false;
    }

    /**
     * Elimina una fase específica dentro de un programa preventivo.
     *
     * @return true si la fase existía y se eliminó, false si no se encontró
     */
    public boolean eliminarFaseDePrograma(int idPrograma, int numeroFase) {
        ProgramaPreventivo programa = buscarProgramaPreventivo(idPrograma);
        if (programa != null) {
            FasePreventiva fase = programa.obtenerFase(numeroFase);
            if (fase != null) {
                programa.eliminarFase(fase);
                return true;
            }
        }
        return false;
    }

    // ======================================================
    // GENERACIÓN DE ID AUTOMÁTICO
    // ======================================================

    /** Contador interno utilizado para emitir IDs incrementalmente */
    private int contadorOrdenes = 1;

    /**
     * Genera un ID incremental simple para órdenes preventivas.
     * Puede reemplazarse por lógica persistente o de base de datos.
     */
    public int generarIdOrden() {
        return contadorOrdenes++;
    }

    // ======================================================
    // VALIDACIONES ANTES DE ELIMINACIONES
    // ======================================================

    /**
     * Verifica si un programa preventivo tiene órdenes preventivas ya generadas.
     * Si es así, el sistema no permitirá eliminar el programa.
     */
    public boolean tieneOrdenesGeneradas(int idPrograma) {
        return SistemaMantenimiento.getInstancia()
                .getOrdenPreventivaController()
                .obtenerOrdenes()
                .stream()
                .anyMatch(op ->
                        op.getFase() != null
                        && op.getFase().getPrograma() != null
                        && op.getFase().getPrograma().getIdPrograma() == idPrograma);
    }

    // ======================================================
    // GENERACIÓN AUTOMÁTICA DE ÓRDENES SEGÚN CICLOS Y FASES
    // ======================================================

    /**
     * Genera órdenes preventivas automáticamente para un equipo según las fases
     * y frecuencia indicada en su Programa Preventivo.
     *
     * Se omite si el equipo está desechado
     * Si una orden ya existe para una fase/fecha, no se duplica
     */
    public void generarOrdenesParaEquipo(Equipo equipo, OrdenPreventivaService ordenService) {

        // Evitar generar para equipos dados de baja
        if (equipo.getEstado() == Equipo.EstadoEquipo.DESECHADO)
            return;

        ProgramaPreventivo programa = equipo.getProgramaPreventivo();
        if (programa == null) return;

        LocalDate base = equipo.getFechaPuestaEnServicio();

        for (FasePreventiva fase : programa.getFases()) {

            // Si ciclos = 0 → se considera infinito pero se acota a un máximo de 6
            int ciclos = fase.getCantidadCiclos() == 0 ? 6 : fase.getCantidadCiclos();

            for (int i = 1; i <= ciclos; i++) {

                LocalDate fecha = base.plusDays(fase.getIntervaloDias() * i);

                // Validar que no exista otra orden igual
                boolean existe = ordenService.obtenerOrdenesPreventivas()
                        .stream()
                        .anyMatch(op ->
                                op.getEquipoAsociado().getId() == equipo.getId()
                                && op.getFase().getNumeroFase() == fase.getNumeroFase()
                                && op.getFechaProgramada().equals(fecha)
                        );

                // Evitar insertos duplicados
                if (!existe) {
                    ordenService.crearOrdenPreventiva(
                            generarIdOrden(),
                            fecha,
                            equipo,
                            fase,
                            null // técnico se asigna luego
                    );
                }
            }
        }
    }

}

