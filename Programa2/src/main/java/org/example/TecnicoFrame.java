package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TecnicoFrame extends JFrame {

    private TecnicoController tecnicoController;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtEspecialidad;
    private JTextField txtTelefono;
    private JTextField txtEmail;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public TecnicoFrame() {

        tecnicoController = new TecnicoController();

        setTitle("Gestión de Técnicos");
        setSize(600, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // PANEL FORMULARIO
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));

        panelForm.add(new JLabel("ID Técnico:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Especialidad:"));
        txtEspecialidad = new JTextField();
        panelForm.add(txtEspecialidad);

        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);

        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);

        add(panelForm, BorderLayout.NORTH);

        // BOTÓN DE REGISTRO
        JButton btnRegistrar = new JButton("Registrar Técnico");
        btnRegistrar.addActionListener(e -> registrarTecnico());
        add(btnRegistrar, BorderLayout.CENTER);

        // TABLA
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Especialidad", "Teléfono", "Email"}, 0);

        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.SOUTH);

        // BOTONES ACCIÓN
        JPanel panelBotones = new JPanel();

        JButton btnEliminar = new JButton("Eliminar seleccionado");
        btnEliminar.addActionListener(e -> eliminarTecnico());

        JButton btnRefrescar = new JButton("Actualizar lista");
        btnRefrescar.addActionListener(e -> cargarTabla());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        add(panelBotones, BorderLayout.AFTER_LAST_LINE);

        cargarTabla();
    }

    // ===================================================
    // MÉTODO PARA REGISTRAR
    // ===================================================
    private void registrarTecnico() {

        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String especialidad = txtEspecialidad.getText();
            String telefono = txtTelefono.getText();
            String email = txtEmail.getText();

            String resultado = tecnicoController.crearTecnico(
                    id, nombre, especialidad, telefono, email
            );

            JOptionPane.showMessageDialog(this, resultado);

            cargarTabla();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    // ===================================================
    // CARGAR TABLA
    // ===================================================
    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Tecnico> lista = tecnicoController.listarTecnicos();

        for (Tecnico t : lista) {
            modeloTabla.addRow(new Object[]{
                    t.getIdTecnico(),             // ajusta si tu getter es distinto
                    t.getNombreCompleto(),
                    t.getEspecialidad(),
                    t.getTelefono(),
                    t.getEmail()
            });
        }
    }

    // ===================================================
    // ELIMINAR
    // ===================================================
    private void eliminarTecnico() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un técnico.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        String msg = tecnicoController.eliminarTecnico(id);

        JOptionPane.showMessageDialog(this, msg);

        cargarTabla();
    }
}


