package org.example;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.*;

/**
 * Ventana para visualización y exportación de reportes del sistema
 * relacionados con usuarios, técnicos, equipos, órdenes preventivas,
 * correctivas, inventario de repuestos y auditoría.
 */
public class ReportesFrame extends JFrame {

    private SistemaMantenimiento sistema;
    private JTextArea areaSalida;

    /**
     * Constructor que recibe el sistema de mantenimiento como dependencia.
     */
    public ReportesFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Reportes del Sistema");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Selector del tipo de reporte a generar
        String[] opciones = {
                "Usuarios",
                "Técnicos",
                "Equipos",
                "Órdenes Preventivas",
                "Órdenes Correctivas",
                "Inventario de Repuestos",
                "Auditoría del Sistema"
        };

        JComboBox<String> combo = new JComboBox<>(opciones);

        JButton btnGenerar = new JButton("Generar Reporte");
        btnGenerar.addActionListener(e -> generarReporte(combo.getSelectedItem().toString()));

        JButton btnExportar = new JButton("Exportar Reporte");
        btnExportar.addActionListener(e -> exportarReporte());

        JPanel top = new JPanel();
        top.add(new JLabel("Seleccione reporte:"));
        top.add(combo);
        top.add(btnGenerar);
        top.add(btnExportar);

        add(top, BorderLayout.NORTH);

        // Área donde se muestra el reporte generado
        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        add(new JScrollPane(areaSalida), BorderLayout.CENTER);
    }

    /**
     * Genera y muestra el reporte seleccionado.
     */
    private void generarReporte(String tipo) {
        areaSalida.setText(""); // limpiar contenido previo

        switch (tipo) {
            case "Usuarios":
                mostrarUsuarios();
                break;

            case "Técnicos":
                mostrarTecnicos();
                break;

            case "Equipos":
                mostrarEquipos();
                break;

            case "Órdenes Preventivas":
                mostrarOrdenesPreventivas();
                break;

            case "Órdenes Correctivas":
                mostrarOrdenesCorrectivas();
                break;

            case "Inventario de Repuestos":
                mostrarInventario();
                break;

            case "Auditoría del Sistema":
                mostrarAuditoria();
                break;
        }
    }

    // ==================== Funciones para mostrar reportes ==================== //

    private void mostrarUsuarios() {
        List<Usuario> lista = sistema.getUsuarioController().listarUsuarios();
        lista.forEach(u -> areaSalida.append(u + "\n"));
    }

    private void mostrarTecnicos() {
        List<Tecnico> lista = sistema.getTecnicoController().listarTecnicos();
        lista.forEach(t -> areaSalida.append(t + "\n"));
    }

    private void mostrarEquipos() {
        List<Equipo> lista = sistema.getEquipoController().obtenerEquipos();
        lista.forEach(eq -> areaSalida.append(eq + "\n"));
    }

    private void mostrarOrdenesPreventivas() {
        List<OrdenPreventiva> lista = sistema.getOrdenPreventivaController().obtenerOrdenes();
        lista.forEach(op -> areaSalida.append(op + "\n"));
    }

    private void mostrarOrdenesCorrectivas() {
        List<OrdenCorrectiva> lista = sistema.getOrdenCorrectivaController().obtenerOrdenes();
        lista.forEach(oc -> areaSalida.append(oc + "\n"));
    }

    private void mostrarInventario() {
        List<Repuesto> lista = sistema.getInventarioRepuestosController().obtenerRepuestos();
        lista.forEach(r -> areaSalida.append(r + "\n"));
    }

    private void mostrarAuditoria() {
        List<AuditoriaMantenimiento> lista = sistema.getAuditoriaController().obtenerAuditoria();
        lista.forEach(a -> areaSalida.append(a + "\n"));
    }

    // ==================== Exportar reporte generado a PDF simple ==================== //

    /**
     * Exporta el contenido del área de texto a un archivo PDF simple generado manualmente.
     */
    private void exportarReporte() {

        if (areaSalida.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Nada para exportar");
            return;
        }

        try {
            String ruta = System.getProperty("user.home") + "/Documents/reporte.pdf";
            FileOutputStream fos = new FileOutputStream(ruta);

            // Cabecera básica PDF
            fos.write("%PDF-1.4\n".getBytes());

            // OBJETO 1: Catálogo
            long pos1 = fos.getChannel().position();
            fos.write("1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n".getBytes());

            // OBJETO 2: Conjunto de páginas
            long pos2 = fos.getChannel().position();
            fos.write("2 0 obj << /Type /Pages /Kids [3 0 R] /Count 1 >> endobj\n".getBytes());

            // OBJETO 3: Página
            long pos3 = fos.getChannel().position();
            fos.write(
                ("3 0 obj << /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] " +
                "/Contents 4 0 R /Resources << /Font << /F1 5 0 R >> >> >> endobj\n").getBytes()
            );

            // Construcción del contenido de texto PDF
            StringBuilder contenido = new StringBuilder();
            contenido.append("BT\n");
            contenido.append("/F1 12 Tf\n");

            int y = 800;

            for (String linea : areaSalida.getText().split("\n")) {

                if (y < 50) {
                    y = 800;
                }

                contenido.append(String.format("1 0 0 1 40 %d Tm\n", y));
                contenido.append("("
                        + linea.replace("(", "[").replace(")", "]")
                        + ") Tj\n");

                y -= 16;
            }

            contenido.append("ET\n");

            byte[] datos = contenido.toString().getBytes();

            // OBJETO 4: Stream de contenido
            long pos4 = fos.getChannel().position();
            fos.write(("4 0 obj << /Length " + datos.length + " >> stream\n").getBytes());
            fos.write(datos);
            fos.write("\nendstream\nendobj\n".getBytes());

            // OBJETO 5: Fuente
            long pos5 = fos.getChannel().position();
            fos.write("5 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj\n".getBytes());

            // Construcción del índice xref
            long startxref = fos.getChannel().position();
            fos.write("xref\n".getBytes());
            fos.write("0 6\n".getBytes());
            fos.write("0000000000 65535 f \n".getBytes());
            fos.write(String.format("%010d 00000 n \n", pos1).getBytes());
            fos.write(String.format("%010d 00000 n \n", pos2).getBytes());
            fos.write(String.format("%010d 00000 n \n", pos3).getBytes());
            fos.write(String.format("%010d 00000 n \n", pos4).getBytes());
            fos.write(String.format("%010d 00000 n \n", pos5).getBytes());

            // Trailer final
            fos.write(("trailer << /Size 6 /Root 1 0 R >>\nstartxref\n" +
                    startxref + "\n%%EOF").getBytes());

            fos.close();

            JOptionPane.showMessageDialog(this, "PDF exportado en:\n" + ruta);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al exportar PDF: " + e.getMessage());
        }
    }

}



