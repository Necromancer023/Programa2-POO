package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un calendario de mantenimiento preventivo.
 * Permite almacenar fechas programadas y consultar aquellas
 * que están próximas o vencidas respecto a la fecha actual.
 */
public class CalendarioMantenimiento {

    /** Lista de fechas de mantenimiento programadas. */
    private List<LocalDate> fechasProgramadas;

    /**
     * Crea un calendario vacío para gestionar programación de mantenimientos.
     * Inicializa la lista interna de fechas registradas.
     */
    public CalendarioMantenimiento() {
        this.fechasProgramadas = new ArrayList<>();
    }

    /**
     * Registra una nueva fecha de mantenimiento preventivo,
     * siempre que no sea nula ni exista previamente.
     *
     * @param fecha fecha a agregar al calendario
     */
    public void agregarFecha(LocalDate fecha) {
        if (fecha != null && !fechasProgramadas.contains(fecha)) {
            fechasProgramadas.add(fecha);
        }
    }

    /**
     * Obtiene las fechas que son anteriores o iguales a la fecha actual.
     * Estas representan mantenimientos que están pendientes o vencidos.
     *
     * @return lista de fechas pendientes de ejecución
     */
    public List<LocalDate> obtenerFechasPendientes() {
        List<LocalDate> pendientes = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        for (LocalDate fecha : fechasProgramadas) {
            if (!fecha.isAfter(hoy)) {
                pendientes.add(fecha);
            }
        }
        return pendientes;
    }

    /**
     * Obtiene todas las fechas registradas en el calendario.
     *
     * @return colección de fechas programadas
     */
    public List<LocalDate> getFechasProgramadas() {
        return fechasProgramadas;
    }
}


