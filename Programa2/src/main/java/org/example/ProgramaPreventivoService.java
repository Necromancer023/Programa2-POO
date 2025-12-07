package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgramaPreventivoService {

    private List<ProgramaPreventivo> programasPreventivos;

    // Constructor
    public ProgramaPreventivoService() {
        this.programasPreventivos = new ArrayList<>();
    }

    // Método para agregar un programa preventivo
    public boolean agregarProgramaPreventivo(ProgramaPreventivo programa) {
        //Validar que no exista un programa con el mismo ID
        for (ProgramaPreventivo p : programasPreventivos) {
            if (p.getIdPrograma() == programa.getIdPrograma()) {
                return false; // No se agrega el programa porque el ID ya existe
                
            }    
        }

        programasPreventivos.add(programa);
        return true;
    }

    // Método para obtener la lista de programas preventivos
    public List<ProgramaPreventivo> obtenerProgramasPreventivos() {
        return programasPreventivos;
    }

    // Método para buscar un programa preventivo por ID
    public ProgramaPreventivo buscarProgramaPreventivo(int idPrograma) {
        for (ProgramaPreventivo p: programasPreventivos) {
            if (p.getIdPrograma() == idPrograma) {
                return p;
            }
        }
        return null; // No se encontró el programa preventivo
    }

    //Método para eliminar un programa preventivo por ID
    public boolean eliminarProgramaPreventivo(int idPrograma) {
        ProgramaPreventivo programaAEliminar = buscarProgramaPreventivo(idPrograma);
        if (programaAEliminar != null) {
            programasPreventivos.remove(programaAEliminar);
            return true; // Programa preventivo eliminado exitosamente
        }
        return false; // No se encontró el programa preventivo para eliminar
    }

    // Método para agregar fase a un programa preventivo
    public boolean agregarFaseAPrograma(int idPrograma, FasePreventiva fase) {
        ProgramaPreventivo programa = buscarProgramaPreventivo(idPrograma);
        if (programa != null) {
            programa.agregarFase(fase);
            return true; // Fase agregada exitosamente
        }
        return false; // No se encontró el programa preventivo
    }

    // Método para eliminar fase de un programa preventivo
    public boolean eliminarFaseDePrograma(int idPrograma, int numeroFase) {
        ProgramaPreventivo programa = buscarProgramaPreventivo(idPrograma);
        if (programa != null) {
            FasePreventiva fase = programa.obtenerFase(numeroFase);
            if (fase != null){
                programa.eliminarFase(fase);
                return true; // Retorna true si se eliminó, false si no se encontró la fase
            }
        }
        return false; // No se encontró el programa preventivo
    }

    // =============================================
    // GENERAR ID AUTOMÁTICO PARA ÓRDENES PREVENTIVAS
    // =============================================
    private int contadorOrdenes = 1;

    /**
    * Genera un ID incremental para órdenes preventivas.
    * Puede reemplazarse con lógica más compleja si el PDF pide.
    */
    public int generarIdOrden() {
        return contadorOrdenes++;
}


    // ============================================
    // Validación: no eliminar si tiene órdenes generadas
    // ============================================
    public boolean tieneOrdenesGeneradas(int idPrograma) {
        return SistemaMantenimiento.getInstancia()
                .getOrdenPreventivaController()
                .obtenerOrdenes()
                .stream()
                .anyMatch(op -> op.getFase() != null
                    && op.getFase().getPrograma() != null
                    && op.getFase().getPrograma().getIdPrograma() == idPrograma);
    }

    public void generarOrdenesParaEquipo(Equipo equipo, OrdenPreventivaService ordenService) {

        if (equipo.getEstado() == Equipo.EstadoEquipo.DESECHADO)
            return;

        ProgramaPreventivo programa = equipo.getProgramaPreventivo();
        if (programa == null) return;

        LocalDate base = equipo.getFechaPuestaEnServicio();

        for (FasePreventiva fase : programa.getFases()) {

            int ciclos = fase.getCantidadCiclos() == 0 ? 6 : fase.getCantidadCiclos();

            for (int i = 1; i <= ciclos; i++) {

                LocalDate fecha = base.plusDays(fase.getIntervaloDias() * i);

                // Validar si ya existe orden para esa fecha y fase
                boolean existe = ordenService.obtenerOrdenesPreventivas()
                        .stream()
                        .anyMatch(op ->
                            op.getEquipoAsociado().getId() == equipo.getId()
                                        && op.getFase().getNumeroFase() == fase.getNumeroFase()
                                        && op.getFechaProgramada().equals(fecha)
                        );

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
