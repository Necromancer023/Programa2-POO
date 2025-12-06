package org.example;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

public class ProgramaPreventivoFrame extends JFrame {

    private ProgramaPreventivoController programaController;

    private JTextField txtId, txtNombre, txtObjetivo, txtResponsable;
    private JTextArea areaLista;

    public ProgramaPreventivoFrame() {

        programaController = new ProgramaPreventivoController();

        setTitle("Gestión de Programas Preventivos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ======= Panel de entrada =======
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));

        panel.add(new JLabel("ID Programa:"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Nombre del Programa:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Objetivo:"));
        txtObjetivo = new JTextField();
        panel.add(txtObjetivo);

        panel.add(new JLabel("Responsable:"));
        txtResponsable = new JTextField();
        panel.add(txtResponsable);

        JButton btnCrear = new JButton("Crear Programa");
        btnCrear.addActionListener(e -> crearPrograma());
        panel.add(btnCrear);

        add(panel, BorderLayout.NORTH);

        // ===== Lista salida ======
        areaLista = new JTextArea();
        areaLista.setEditable(false);
        add(new JScrollPane(areaLista), BorderLayout.CENTER);

        // ===== Botón actualizar ======
        JButton btnListar = new JButton("Actualizar Lista");
        btnListar.addActionListener(e -> listarProgramas());
        add(btnListar, BorderLayout.SOUTH);
    }

    private void crearPrograma() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String objetivo = txtObjetivo.getText();
            String responsable = txtResponsable.getText();

            // Fecha creación automática
            String res = programaController.crearProgramaPreventivo(
                    id,
                    nombre,
                    objetivo,
                    LocalDate.now(),
                    responsable
            );

            JOptionPane.showMessageDialog(this, res);
            listarProgramas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: ID inválido");
        }
    }

    private void listarProgramas() {
        areaLista.setText("");
        for (ProgramaPreventivo p : programaController.obtenerProgramas()) {
            areaLista.append(p.toString() + "\n");
        }
    }
}

