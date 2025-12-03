package org.example;

import java.time.LocalDate;
import java.util.List;

public class ReporteGeneralMantenimiento {

    private Equipo equipo;
    private int totalOrdenesPreventivas;
    private int totalOrdenesCorrectivas;
    private double costoTotalCorrectivas;
    private LocalDate fechaGeneracion;
    private String generadoPor; // Nombre del usuario que genera el reporte

    // -- Constructor --
    public ReporteGeneralMantenimiento(Equipo equipo, String generadoPor) {
        this.equipo = equipo;
        this.generadoPor = generadoPor;
        this.fechaGeneracion = LocalDate.now();
        calcularTotales();
    }

    // -- Métodos Internos --

    private void calcularTotales() {
        List<OrdenPreventiva> preventivas = equipo.getOrdenesPreventivas();
        List<OrdenCorrectiva> correctivas = equipo.getOrdenesCorrectivas();

        this.totalOrdenesPreventivas = preventivas.size();
        this.totalOrdenesCorrectivas = correctivas.size();

        double totalCostos = 0;
        for (OrdenCorrectiva orden : correctivas) {
            totalCostos += orden.getCostoReparacion();
        }
        this.costoTotalCorrectivas = totalCostos;
    }

    // -- Getters --

    public Equipo getEquipo() {
        return equipo;
    }
    public int getTotalOrdenesPreventivas() {
        return totalOrdenesPreventivas;
    }
    public int getTotalOrdenesCorrectivas() {
        return totalOrdenesCorrectivas;
    }
    public double getCostoTotalCorrectivas() {
        return costoTotalCorrectivas;
    }
    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }
    public String getGeneradoPor() {
        return generadoPor;
    }

    // -- Metodo principal del reporte --

    public String generarReporte() {
        return "REPORTE GENERAL DE MANTENIMIENTO\n" +
                "---------------------------------\n" +
                "Equipo: " + equipo.getDescripcion() + "\n" +
                "Tipo: " + equipo.getTipo() + "\n" +
                "Estado actual: " + equipo.getEstado() + "\n" +
                "Fecha generación: " + fechaGeneracion + "\n" +
                "Generado por: " + generadoPor + "\n\n" +
                "TOTAL ÓRDENES PREVENTIVAS: " + totalOrdenesPreventivas + "\n" +
                "TOTAL ÓRDENES CORRECTIVAS: " + totalOrdenesCorrectivas + "\n" +
                "COSTO TOTAL CORRECTIVOS: $" + costoTotalCorrectivas + "\n";
    }

    @Override
    public String toString() {
        return generarReporte();
    }
}