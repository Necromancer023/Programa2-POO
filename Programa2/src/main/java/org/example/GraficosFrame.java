package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GraficosFrame extends JFrame {

    private final SistemaMantenimiento sistema;

    public GraficosFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        System.out.println(">>> [GraficosFrame] Inicializando interfaz de gráficos...");

        setTitle("Gráficos del Sistema");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Costos por equipo", panelCostosEquipos());
        tabs.add("Estado de órdenes", panelEstadoOrdenes());

        add(tabs, BorderLayout.CENTER);
    }

    // =============================================================
    //  GRÁFICO 1: BARRAS COSTOS POR EQUIPO
    // =============================================================
    private JPanel panelCostosEquipos() {

        JPanel panel = new JPanel(new BorderLayout());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Obtener datos reales del sistema
        System.out.println(">>> [GraficosFrame] Calculando costos por equipo...");

        // Simulación real: iterar equipos y sumar costos
        sistema.getEquipoController().obtenerEquipos().forEach(eq -> {
            double costo = sistema.getOrdenPreventivaController().costoTotalEquipo(eq.getIdEquipo())
                    + sistema.getOrdenCorrectivaController().costoTotalEquipo(eq.getIdEquipo());

            dataset.addValue(costo, "Costo Total", eq.getDescripcion());
            System.out.println(">>> Equipo " + eq.getDescripcion() + " costo: " + costo);
        });

        JFreeChart chart = ChartFactory.createBarChart(
                "Costos de Mantenimiento por Equipo",
                "Equipo",
                "Costo Total",
                dataset
        );

        panel.add(new ChartPanel(chart), BorderLayout.CENTER);
        return panel;
    }

    // =============================================================
    //  GRÁFICO 2: PASTEL ESTADO DE ÓRDENES
    // =============================================================
    private JPanel panelEstadoOrdenes() {

        JPanel panel = new JPanel(new BorderLayout());

        DefaultPieDataset dataset = new DefaultPieDataset();

        System.out.println(">>> [GraficosFrame] Calculando estados de órdenes...");

        Map<String, Integer> estados = sistema.getOrdenCorrectivaController().estadisticasEstado();

        estados.forEach((estado, cantidad) -> {
            dataset.setValue(estado, cantidad);
            System.out.println(">>> Estado " + estado + ": " + cantidad);
        });

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribución de Órdenes Correctivas",
                dataset,
                true,
                true,
                false
        );

        panel.add(new ChartPanel(chart), BorderLayout.CENTER);
        return panel;
    }
}

