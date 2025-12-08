package org.example;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.*;

/**
 * Ventana que permite consultar, filtrar y exportar registros
 * de auditoría del sistema.
 *
 * Permite visualizar eventos auditados, filtrarlos por usuario
 * o entidad, así como exportarlos a un documento PDF creado
 * manualmente sin uso de bibliotecas externas.
 */
public class AuditoriaFrame extends JFrame {

    /** Referencia al sistema de mantenimiento para acceder al controlador de auditoría. */
    private SistemaMantenimiento sistema;

    /** Área de texto donde se muestran los resultados de auditoría y filtros. */
    private JTextArea salida;

    /** Campo de entrada para filtrar registros por usuario. */
    private JTextField txtFiltroUsuario;

    /** Campo de entrada para filtrar registros por entidad del sistema. */
    private JTextField txtFiltroEntidad;

    /**
     * Construye la ventana de auditoría recibiendo el sistema principal.
     *
     * @param sistema referencia global al sistema
     */
    public AuditoriaFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Auditoría del Sistema");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel filtros = new JPanel(new GridLayout(2, 3, 5, 5));

        txtFiltroUsuario = new JTextField();
        txtFiltroEntidad = new JTextField();

        JButton btnFiltrarUsuario = new JButton("Filtrar por Usuario");
        btnFiltrarUsuario.addActionListener(e -> mostrarPorUsuario());

        JButton btnFiltrarEntidad = new JButton("Filtrar por Entidad");
        btnFiltrarEntidad.addActionListener(e -> mostrarPorEntidad());

        filtros.add(new JLabel("Usuario:"));
        filtros.add(txtFiltroUsuario);
        filtros.add(btnFiltrarUsuario);

        filtros.add(new JLabel("Entidad:"));
        filtros.add(txtFiltroEntidad);
        filtros.add(btnFiltrarEntidad);

        add(filtros, BorderLayout.NORTH);

        // Botones inferiores
        JButton btnVerTodo = new JButton("Ver Auditoría Completa");
        btnVerTodo.addActionListener(e -> mostrarTodo());

        JButton btnExportar = new JButton("Exportar Auditoría");
        btnExportar.addActionListener(e -> exportarAuditoria());

        JPanel bottom = new JPanel(new GridLayout(1, 2));
        bottom.add(btnVerTodo);
        bottom.add(btnExportar);

        add(bottom, BorderLayout.SOUTH);

        salida = new JTextArea();
        salida.setEditable(false);
        add(new JScrollPane(salida), BorderLayout.CENTER);
    }

    /** Muestra todos los registros auditados ya almacenados. */
    private void mostrarTodo() {
        salida.setText("");

        List<AuditoriaMantenimiento> lista =
                sistema.getAuditoriaController().obtenerAuditoria();

        if (lista.isEmpty()) {
            salida.append("No hay registros de auditoría.\n");
            return;
        }

        lista.forEach(a -> salida.append(a.toString() + "\n"));
    }

    /** Filtra la auditoría buscando entradas asociadas a un usuario específico. */
    private void mostrarPorUsuario() {
        salida.setText("");

        String usuario = txtFiltroUsuario.getText();

        if (usuario == null || usuario.isBlank()) {
            salida.append("Debe ingresar un nombre de usuario.\n");
            return;
        }

        List<AuditoriaMantenimiento> lista =
                sistema.getAuditoriaController().obtenerPorUsuario(usuario);

        if (lista.isEmpty()) {
            salida.append("Sin registros para el usuario: " + usuario + "\n");
            return;
        }

        lista.forEach(a -> salida.append(a.toString() + "\n"));
    }

    /** Filtra registros auditando una entidad del sistema (EQUIPO, ORDEN, USUARIO, etc.). */
    private void mostrarPorEntidad() {
        salida.setText("");

        String entidad = txtFiltroEntidad.getText();

        if (entidad == null || entidad.isBlank()) {
            salida.append("Debe ingresar una entidad (Ej: EQUIPO, ORDEN, USUARIO).\n");
            return;
        }

        List<AuditoriaMantenimiento> lista =
                sistema.getAuditoriaController().obtenerPorEntidad(entidad);

        if (lista.isEmpty()) {
            salida.append("Sin registros para entidad: " + entidad + "\n");
            return;
        }

        lista.forEach(a -> salida.append(a.toString() + "\n"));
    }

    /**
     * Exporta el contenido visible a un archivo PDF generado manualmente.
     * No requiere librerías externas.
     */
    private void exportarAuditoria() {
        try {
            if (salida.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "No hay contenido de auditoría para exportar.");
                return;
            }

            String ruta = System.getProperty("user.home") + "/Documents/auditoria.pdf";
            FileOutputStream fos = new FileOutputStream(ruta);

            fos.write("%PDF-1.4\n".getBytes());

            long pos1 = fos.getChannel().position();
            fos.write("1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n".getBytes());

            long pos2 = fos.getChannel().position();
            fos.write("2 0 obj << /Type /Pages /Kids [3 0 R] /Count 1 >> endobj\n".getBytes());

            long pos3 = fos.getChannel().position();
            fos.write(
                    ("3 0 obj << /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] " +
                            "/Contents 4 0 R /Resources << /Font << /F1 5 0 R >> >> >> endobj\n").getBytes()
            );

            StringBuilder contenido = new StringBuilder();
            contenido.append("BT\n/F1 10 Tf\n");

            int y = 800;

            for (String linea : salida.getText().split("\n")) {

                if (y < 50) {
                    y = 800;
                }

                contenido.append(String.format("1 0 0 1 40 %d Tm\n", y));
                contenido.append("(" +
                        linea.replace("(", "[").replace(")", "]") +
                        ") Tj\n");

                y -= 14;
            }

            contenido.append("ET\n");

            byte[] datos = contenido.toString().getBytes();

            long pos4 = fos.getChannel().position();
            fos.write(("4 0 obj << /Length " + datos.length + " >> stream\n").getBytes());
            fos.write(datos);
            fos.write("\nendstream\nendobj\n".getBytes());

            long pos5 = fos.getChannel().position();
            fos.write("5 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj\n".getBytes());

            long startxref = fos.getChannel().position();
            fos.write("xref\n".getBytes());
            fos.write("0 6\n".getBytes());
            fos.write("0000000000 65535 f \n".getBytes());
            fos.write(String.format("%010d 00000 n \n", pos1).getBytes());
            fos.write(String.format("%010d 00000 n \n", pos2).getBytes());
            fos.write(String.format("%010d 00000 n \n", pos3).getBytes());
            fos.write(String.format("%010d 00000 n \n", pos4).getBytes());
            fos.write(String.format("%010d 00000 n \n", pos5).getBytes());

            fos.write(("trailer << /Size 6 /Root 1 0 R >>\nstartxref\n" +
                    startxref + "\n%%EOF").getBytes());

            fos.close();

            JOptionPane.showMessageDialog(this, "Auditoría exportada correctamente en:\n" + ruta);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error exportando PDF:\n" + e.getMessage());
        }
    }
}



