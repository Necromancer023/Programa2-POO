package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TecnicoFrame extends JFrame {

    private TecnicoController tecnicoController;

    private JTextField txtId, txtNombre, txtEspecialidad, txtTelefono, txtEmail;
    private JTable tabla;
    private DefaultTableModel modelo;

    public TecnicoFrame() {
        this(SistemaMantenimiento.getInstance());
    }

    public TecnicoFrame(SistemaMantenimiento sistema) {

        tecnicoController = sistema.getTecnicoController();

        setTitle("Gestión de Técnicos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5, 2));

        form.add(new JLabel("ID:"));
        txtId = new JTextField();
        form.add(txtId);

        form.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        form.add(txtNombre);

        form.add(new JLabel("Especialidad:"));
        txtEspecialidad = new JTextField();
        form.add(txtEspecialidad);

        form.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        form.add(txtTelefono);

        form.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        form.add(txtEmail);

        add(form, BorderLayout.NORTH);

        JButton btn = new JButton("Registrar Técnico");
        btn.addActionListener(e -> registrar());
        add(btn, BorderLayout.CENTER);

        modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Especialidad", "Estado"}, 0);
        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.SOUTH);

        cargarTabla();
    }

    private void registrar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String esp = txtEspecialidad.getText();
            String tel = txtTelefono.getText();
            String email = txtEmail.getText();

            String msg = tecnicoController.crearTecnico(id, nombre, esp, tel, email);
            JOptionPane.showMessageDialog(this, msg);
            cargarTabla();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos");
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Tecnico> lista = tecnicoController.listarTecnicos();
        lista.forEach(t -> modelo.addRow(new Object[]{
                t.getIdTecnico(), t.getNombreCompleto(), t.getEspecialidad(), t.isActivo()
        }));
    }
}



