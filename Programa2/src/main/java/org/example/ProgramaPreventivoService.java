package org.example;

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

}
