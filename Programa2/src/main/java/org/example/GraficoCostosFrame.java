package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GraficoCostosFrame extends JFrame {

    private SistemaMantenimiento sistema;
    private GraficoPanel panelGrafico;

    public GraficoCostosFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Gráfico — Costo de Mantenimiento por Equipo");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        System.out.println(">>> [GraficoCostosFrame] inicializando ventana");

        panelGrafico = new GraficoPanel();

        JButton btnActualizar = new JButton("Actualizar gráfico");
        btnActualizar.addActionListener(e -> actualizarGrafico());

        add(btnActualizar, BorderLayout.NORTH);
        add(panelGrafico, BorderLayout.CENTER);

        actualizarGrafico();
    }

    private void actualizarGrafico() {
        System.out.println(">>> [GraficoCostosFrame] generando datos para gráfico...");

        List<Equipo> equipos = sistema.getEquipoController().obtenerEquipos();
        List<OrdenCorrectiva> órdenes = sistema.getOrdenCorrectivaController().obtenerOrdenes();

        double[] costos = new double[equipos.size()];
        String[] etiquetas = new String[equipos.size()];

        // Calcular costo total por equipo
        for (int i = 0; i < equipos.size(); i++) {
            Equipo eq = equipos.get(i);
            etiquetas[i] = String.valueOf(eq.getId());

            double costoTotal = 0;

            for (OrdenCorrectiva oc : órdenes) {
                if (oc.getEquipoAsociado().getId() == eq.getId()) {
                    costoTotal += oc.getCostoReparacion();
                }
            }
            costos[i] = costoTotal;
        }

        panelGrafico.setDatos(costos, etiquetas);
        panelGrafico.repaint();

        System.out.println(">>> [GraficoCostosFrame] gráfico actualizado");
    }

    // Panel que dibuja barras manualmente
    private static class GraficoPanel extends JPanel {

        private double[] valores = new double[0];
        private String[] etiquetas = new String[0];

        public void setDatos(double[] valores, String[] etiquetas) {
            this.valores = valores;
            this.etiquetas = etiquetas;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (valores.length == 0) {
                g.drawString("No hay datos para mostrar", 20, 20);
                return;
            }

            int anchoBarra = 40;
            int separacion = 30;
            int x = 50;
            int baseY = getHeight() - 60;

            // Buscar valor máximo para escalar barras
            double max = 1;
            for (double v : valores) if (v > max) max = v;

            for (int i = 0; i < valores.length; i++) {
                int altura = (int) ((valores[i] / max) * 250);

                g.setColor(Color.BLUE);
                g.fillRect(x, baseY - altura, anchoBarra, altura);

                g.setColor(Color.BLACK);
                g.drawRect(x, baseY - altura, anchoBarra, altura);

                g.drawString(etiquetas[i], x, baseY + 15);

                x += anchoBarra + separacion;
            }
        }
    }
}


