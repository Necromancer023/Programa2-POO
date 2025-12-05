package org.example;

import java.time.LocalDate;

public class ProgramadorPreventivoController {

    private ProgramadorPreventivoService programadorService;

    public ProgramadorPreventivoController(ProgramadorPreventivoService programadorService) {
        this.programadorService = programadorService;
    }

    public String programarFecha(LocalDate fecha) {
        if (fecha == null) {
            return "Debe ingresar una fecha válida.";
        }

        programadorService.programarFecha(fecha);
        return "Fecha programada en el calendario.";
    }

    public String generarOrdenes() {
        programadorService.generarOrdenesPendientes();
        return "Órdenes preventivas generadas según calendario.";
    }
}


