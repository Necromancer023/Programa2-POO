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
        List<OrdenCorrectiva> ordenes = sistema.getOrdenCorrectivaController().obtenerOrdenes();

        double[] costos = new double[equipos.size()];
        String[] etiquetas = new String[equipos.size()];

        // Calcular costo total por equipo
        for (int i = 0; i < equipos.size(); i++) {
            Equipo eq = equipos.get(i);
            etiquetas[i] = String.valueOf(eq.getId());

            double costoTotal = 0;

            for (OrdenCorrectiva oc : ordenes) {
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

    // Panel mejorado para renderizar barras
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

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            int baseY = height - 80;
            int margenIzq = 60;

            // Dibujar ejes
            g2.setColor(Color.BLACK);
            g2.drawLine(margenIzq, baseY, width - 20, baseY);   // eje X
            g2.drawLine(margenIzq, baseY, margenIzq, 40);       // eje Y

            // Obtener máximo
            double max = 1;
            for (double v : valores) if (v > max) max = v;

            // Cálculo de barras
            int disponibles = width - margenIzq - 40;
            int anchoBarra = Math.max(40, disponibles / (valores.length * 2));
            int separacion = anchoBarra;

            int x = margenIzq + 10;

            for (int i = 0; i < valores.length; i++) {
                double valor = valores[i];

                int altura = (int) ((valor / max) * (height - 140));

                // Dibujar barra
                g2.setColor(new Color(50, 90, 230));
                g2.fillRect(x, baseY - altura, anchoBarra, altura);

                g2.setColor(Color.BLACK);
                g2.drawRect(x, baseY - altura, anchoBarra, altura);

                // Valor encima de la barra
                g2.drawString(String.format("%.1f", valor), x + 5, baseY - altura - 5);

                // Etiqueta bajo la barra
                String etiqueta = etiquetas[i];
                int txtWidth = g2.getFontMetrics().stringWidth(etiqueta);
                g2.drawString(etiqueta, x + (anchoBarra - txtWidth) / 2, baseY + 15);

                x += anchoBarra + separacion;
            }

            // Título
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString("Costo de Mantenimiento por Equipo", margenIzq, 30);
        }
    }
}




