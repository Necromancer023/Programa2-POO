package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * Ventana gráfica dedicada a la visualización del estado de las órdenes correctivas
 * almacenadas en el sistema. Presenta un gráfico de barras que resume la cantidad
 * de órdenes en cada categoría de estado.
 */
public class GraficoEstadosFrame extends JFrame {

    /** Instancia principal del sistema utilizada para consulta de datos. */
    private SistemaMantenimiento sistema;

    /** Panel encargado de dibujar las barras del gráfico. */
    private GraficoPanel panelGrafico;

    /**
     * Constructor de la ventana gráfica que recibe una referencia al sistema.
     *
     * @param sistema instancia del sistema principal desde donde se extraen los datos
     */
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

    /**
     * Recupera las órdenes correctivas del sistema, analiza sus estados y
     * alimenta el gráfico para reflejar el número de órdenes en cada categoría.
     */
    private void actualizar() {
        System.out.println(">>> [GraficoEstadosFrame] recargando datos...");

        List<OrdenCorrectiva> lista = sistema.getOrdenCorrectivaController().obtenerOrdenes();

        int reportadas = 0, proceso = 0, completadas = 0, noReparadas = 0;

        // Conteo por estado
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

    /**
     * Panel interno encargado del renderizado del gráfico de barras.
     * Dibuja visualmente la cantidad de órdenes según su estado.
     */
    private static class GraficoPanel extends JPanel {

        /** Valores numéricos correspondientes a cada categoría de estado. */
        private double[] valores = new double[0];

        /** Nombres o etiquetas asociadas a cada barra. */
        private String[] etiquetas = new String[0];

        /**
         * Establece los datos que serán utilizados para graficar.
         *
         * @param valores arreglo con cantidades por estado
         * @param etiquetas nombres de cada barra en el gráfico
         */
        public void setDatos(double[] valores, String[] etiquetas) {
            this.valores = valores;
            this.etiquetas = etiquetas;
        }

        /**
         * Método de renderizado que dibuja las barras del gráfico
         * proporcionalmente al valor máximo detectado.
         *
         * @param g objeto gráfico proporcionado por Swing
         */
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

                // Dibujar barra
                g.setColor(Color.ORANGE);
                g.fillRect(x, baseY - altura, anchoBarra, altura);

                g.setColor(Color.BLACK);
                g.drawRect(x, baseY - altura, anchoBarra, altura);

                // Etiqueta inferior
                g.drawString(etiquetas[i], x, baseY + 20);

                // Valor superior
                g.drawString(String.valueOf((int) valores[i]),
                             x + anchoBarra / 2 - 5,
                             baseY - altura - 10);

                x += anchoBarra + separacion;
            }
        }
    }
}

