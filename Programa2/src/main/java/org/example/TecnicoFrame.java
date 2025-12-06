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

    // ========== CONSTRUCTOR CORREGIDO ==========
    public TecnicoFrame() {
        this(SistemaMantenimiento.getInstance());
    }

    public TecnicoFrame(SistemaMantenimiento sistema) {
        // ✅ Usar el controlador del sistema compartido
        tecnicoController = sistema.getTecnicoController();

        setTitle("Gestión de Técnicos");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // PANEL FORMULARIO
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

        add(panelForm, BorderLayout.NORTH);

        // BOTÓN REGISTRAR
        JButton btnRegistrar = new JButton("Registrar Técnico");
        btnRegistrar.addActionListener(e -> registrarTecnico());
        add(btnRegistrar, BorderLayout.CENTER);

        // TABLA
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Especialidad", "Teléfono", "Email", "Activo"}, 0
        );

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.SOUTH);

        // PANEL BOTONES
        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar seleccionado");
        btnEliminar.addActionListener(e -> eliminarTecnico());

        JButton btnActualizar = new JButton("Actualizar lista");
        btnActualizar.addActionListener(e -> cargarTabla());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        add(panelBotones, BorderLayout.AFTER_LAST_LINE);

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
            
            // ✅ Limpiar campos después de registrar
            limpiarCampos();
            cargarTabla();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Tecnico> lista = tecnicoController.listarTecnicos();
        
        System.out.println("📋 Cargando " + lista.size() + " técnicos"); // Debug
        
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




