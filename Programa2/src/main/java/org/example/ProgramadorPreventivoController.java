package org.example;

import java.time.LocalDate;

/**
 * Controlador encargado de gestionar las operaciones del programador preventivo.
 *
 * Este controlador delega en ProgramadorPreventivoService la lógica de:
 *  - Registrar fechas de ejecución programada de mantenimientos preventivos.
 *  - Generar automáticamente órdenes preventivas en función del calendario registrado.
 */
public class ProgramadorPreventivoController {

    /** Servicio que contiene la lógica de programación y generación automatizada */
    private ProgramadorPreventivoService programadorService;

    /**
     * Constructor que inicializa el controlador con su servicio asociado.
     *
     * @param programadorService instancia del servicio encargado de programación
     */
    public ProgramadorPreventivoController(ProgramadorPreventivoService programadorService) {
        this.programadorService = programadorService;
    }

    /**
     * Registra una fecha para programación automática de mantenimiento preventivo.
     *
     * @param fecha fecha programada
     * @return mensaje de confirmación o error si la fecha es inválida
     */
    public String programarFecha(LocalDate fecha) {
        if (fecha == null) {
            return "Debe ingresar una fecha válida.";
        }

        programadorService.programarFecha(fecha);
        return "Fecha programada en el calendario.";
    }

    /**
     * Genera órdenes preventivas pendientes verificando fechas programadas.
     *
     * @return mensaje de confirmación tras ejecutar generación automática
     */
    public String generarOrdenes() {
        programadorService.generarOrdenesPendientes();
        return "Órdenes preventivas generadas según calendario.";
    }
}



