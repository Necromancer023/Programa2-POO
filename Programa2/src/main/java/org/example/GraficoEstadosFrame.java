package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GraficoEstadosFrame extends JFrame {

    private SistemaMantenimiento sistema;
    private GraficoPanel panelGrafico;

    public GraficoEstadosFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Gráfico — Estado de Órdenes Correctivas");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        System.out.println(">>> [GraficoEstadosFrame] inicializando ventana");

        panelGrafico = new GraficoPanel();

        JButton btnActualizar = new JButton("Actualizar gráfico");
        btnActualizar.addActionListener(e -> actualizar());

        add(btnActualizar, BorderLayout.NORTH);
        add(panelGrafico, BorderLayout.CENTER);

        actualizar();
    }

    private void actualizar() {
        System.out.println(">>> [GraficoEstadosFrame] recargando datos...");

        List<OrdenCorrectiva> lista = sistema.getOrdenCorrectivaController().obtenerOrdenes();

        int reportadas = 0, proceso = 0, completadas = 0, noReparadas = 0;

        for (OrdenCorrectiva oc : lista) {
            switch (oc.getEstado()) {
                case REPORTADA -> reportadas++;
                case EN_PROCESO -> proceso++;
                case COMPLETADA -> completadas++;
                case NO_REPARADA -> noReparadas++;
            }
        }

        double[] datos = {reportadas, proceso, completadas, noReparadas};
        String[] etiquetas = {"Reportadas", "En Proceso", "Completadas", "No Reparadas"};

        panelGrafico.setDatos(datos, etiquetas);
        panelGrafico.repaint();

        System.out.println(">>> [GraficoEstadosFrame] gráfico actualizado");
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

            int anchoBarra = 80;
            int separacion = 40;
            int x = 50;
            int baseY = getHeight() - 80;

            double max = 1;
            for (double v : valores) if (v > max) max = v;

            for (int i = 0; i < valores.length; i++) {
                int altura = (int) ((valores[i] / max) * 250);

                // Bordes y color
                g.setColor(Color.ORANGE);
                g.fillRect(x, baseY - altura, anchoBarra, altura);

                g.setColor(Color.BLACK);
                g.drawRect(x, baseY - altura, anchoBarra, altura);

                // Etiquetas debajo
                g.drawString(etiquetas[i], x, baseY + 20);

                // Número encima de barra
                g.drawString(String.valueOf((int) valores[i]), x + anchoBarra / 2 - 5, baseY - altura - 10);

                x += anchoBarra + separacion;
            }
        }
    }
}

