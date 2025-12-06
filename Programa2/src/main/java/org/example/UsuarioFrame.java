package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UsuarioFrame extends JFrame {

    private UsuarioController usuarioController;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JPasswordField txtPassword;
    private JComboBox<Rol> comboRol;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public UsuarioFrame() {

        usuarioController = new UsuarioController();

        setTitle("Gestión de Usuarios");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));

        panelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Usuario:"));
        txtUsername = new JTextField();
        panelForm.add(txtUsername);

        panelForm.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelForm.add(txtPassword);

        panelForm.add(new JLabel("Rol:"));
        comboRol = new JComboBox<>(Rol.values());
        panelForm.add(comboRol);

        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);

        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);

        add(panelForm, BorderLayout.NORTH);

        JButton btnRegistrar = new JButton("Registrar Usuario");
        btnRegistrar.addActionListener(e -> registrarUsuario());
        add(btnRegistrar, BorderLayout.CENTER);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{
                "ID", "Nombre", "Usuario", "Rol", "Activo", "Email", "Teléfono"
        }, 0);

        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.SOUTH);

        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar seleccionado");
        btnEliminar.addActionListener(e -> eliminarUsuario());

        JButton btnRefrescar = new JButton("Actualizar lista");
        btnRefrescar.addActionListener(e -> cargarTabla());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);
        add(panelBotones, BorderLayout.AFTER_LAST_LINE);

        cargarTabla();
    }

    private void registrarUsuario() {

        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String username = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());
            Rol rol = (Rol) comboRol.getSelectedItem();
            String email = txtEmail.getText();
            String tel = txtTelefono.getText();

            String resultado = usuarioController.crearUsuario(
                    id, nombre, username, pass, rol, email, tel
            );

            JOptionPane.showMessageDialog(this, resultado);
            cargarTabla();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Usuario> lista = usuarioController.listarUsuarios();

        for (Usuario u : lista) {
            modeloTabla.addRow(new Object[]{
                u.getIdUsuario(),
                u.getNombreCompleto(),
                u.getUsername(),
                u.getRol(),
                u.isActivo(),
                u.getEmail(),
                u.getTelefono()
            });
        }
    }

    private void eliminarUsuario() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);

        String msg = usuarioController.eliminarUsuario(id);
        JOptionPane.showMessageDialog(this, msg);

        cargarTabla();
    }
}

