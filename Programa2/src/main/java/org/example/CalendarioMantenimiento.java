package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarioMantenimiento {

    private List<LocalDate> fechasProgramadas;

    public CalendarioMantenimiento() {
        this.fechasProgramadas = new ArrayList<>();
    }

    /** Registrar una nueva fecha de programación preventiva */
    public void agregarFecha(LocalDate fecha) {
        if (fecha != null && !fechasProgramadas.contains(fecha)) {
            fechasProgramadas.add(fecha);
        }
    }

    /** Obtener fechas próximas o iguales a hoy */
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

    public List<LocalDate> getFechasProgramadas() {
        return fechasProgramadas;
    }
}

