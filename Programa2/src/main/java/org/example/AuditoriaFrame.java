package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class AuditoriaFrame extends JFrame {

    private SistemaMantenimiento sistema;
    private JTextArea salida;
    private JTextField txtFiltroUsuario;
    private JTextField txtFiltroEntidad;

    public AuditoriaFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Auditoría del Sistema");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior de filtros
        JPanel filtros = new JPanel(new GridLayout(2, 3, 5, 5));

        txtFiltroUsuario = new JTextField();
        txtFiltroEntidad = new JTextField();

        JButton btnFiltrarUsuario = new JButton("Filtrar por Usuario");
        btnFiltrarUsuario.addActionListener(e -> mostrarPorUsuario());

        JButton btnFiltrarEntidad = new JButton("Filtrar por Entidad");
        btnFiltrarEntidad.addActionListener(e -> mostrarPorEntidad());

        JButton btnVerTodo = new JButton("Ver Auditoría Completa");
        btnVerTodo.addActionListener(e -> mostrarTodo());

        filtros.add(new JLabel("Usuario:"));
        filtros.add(txtFiltroUsuario);
        filtros.add(btnFiltrarUsuario);

        filtros.add(new JLabel("Entidad:"));
        filtros.add(txtFiltroEntidad);
        filtros.add(btnFiltrarEntidad);

        add(filtros, BorderLayout.NORTH);
        add(btnVerTodo, BorderLayout.SOUTH);

        // Área de salida
        salida = new JTextArea();
        salida.setEditable(false);
        add(new JScrollPane(salida), BorderLayout.CENTER);
    }

    // ----------------------------------------------
    // Mostrar auditoría completa
    // ----------------------------------------------
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

    // ----------------------------------------------
    // Filtrar auditoría por usuario
    // ----------------------------------------------
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

    // ----------------------------------------------
    // Filtrar auditoría por entidad (EQUIPO, USUARIO, ORDEN, etc.)
    // ----------------------------------------------
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
}

