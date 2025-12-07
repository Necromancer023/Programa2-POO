package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TecnicoFrame extends JFrame {

    private TecnicoController tecnicoController;

    private JTextField txtId, txtNombre, txtEspecialidad, txtTelefono, txtEmail;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public TecnicoFrame() {
        this(SistemaMantenimiento.getInstance());
    }

    public TecnicoFrame(SistemaMantenimiento sistema) {

        tecnicoController = sistema.getTecnicoController();

        setTitle("Gestión de Técnicos");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel principal
        JPanel root = new JPanel(new BorderLayout());
        add(root);

        // -------- FORMULARIO --------
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));

        panelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nombre:"));
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

        // contenedor superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelForm, BorderLayout.NORTH);

        JButton btnRegistrar = new JButton("Registrar Técnico");
        btnRegistrar.addActionListener(e -> registrarTecnico());
        panelSuperior.add(btnRegistrar, BorderLayout.CENTER);

        root.add(panelSuperior, BorderLayout.NORTH);

        // -------- TABLA --------
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Especialidad", "Teléfono", "Email", "Activo"}, 0
        );

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        root.add(scroll, BorderLayout.CENTER);

        // -------- PANEL BOTONES --------
        JPanel panelBotones = new JPanel();

        JButton btnEliminar = new JButton("Eliminar seleccionado");
        btnEliminar.addActionListener(e -> eliminarTecnico());

        JButton btnActualizar = new JButton("Actualizar lista");
        btnActualizar.addActionListener(e -> cargarTabla());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        root.add(panelBotones, BorderLayout.SOUTH);

        cargarTabla();
    }

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

            limpiarCampos();
            cargarTabla();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Tecnico> lista = tecnicoController.listarTecnicos();

        for (Tecnico t : lista) {
            modeloTabla.addRow(new Object[]{
                    t.getIdTecnico(),
                    t.getNombreCompleto(),
                    t.getEspecialidad(),
                    t.getTelefono(),
                    t.getEmail(),
                    t.isActivo() ? "Sí" : "No"
            });
        }
    }

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

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtEspecialidad.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }
}





