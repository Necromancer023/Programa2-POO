package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UsuarioFrame extends JFrame {

    private UsuarioController usuarioController;

    private JTextField txtId, txtNombre, txtUsername, txtEmail, txtTelefono;
    private JPasswordField txtPassword;
    private JComboBox<Rol> comboRol;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public UsuarioFrame() {
        this(SistemaMantenimiento.getInstance());
    }

    public UsuarioFrame(SistemaMantenimiento sistema) {

        usuarioController = sistema.getUsuarioController();

        setTitle("Gestión de Usuarios");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // PANEL PRINCIPAL VERTICAL
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        add(root);

        // ===== FORMULARIO SUPERIOR =====
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

        // Panel contenedor para formulario y botón registrar
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(panelForm, BorderLayout.NORTH);

        JButton btnRegistrar = new JButton("Registrar Usuario");
        btnRegistrar.addActionListener(e -> registrarUsuario());
        topPanel.add(btnRegistrar, BorderLayout.CENTER);

        root.add(topPanel, BorderLayout.NORTH);

        // ===== TABLA =====
        modeloTabla = new DefaultTableModel(new Object[]{
                "ID", "Nombre", "Usuario", "Rol", "Activo", "Email", "Teléfono"
        }, 0);

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        root.add(scroll, BorderLayout.CENTER);

        // ===== PANEL BOTONES INFERIORES =====
        JPanel panelBotones = new JPanel();

        JButton btnEliminar = new JButton("Eliminar seleccionado");
        btnEliminar.addActionListener(e -> eliminarUsuario());

        JButton btnRefrescar = new JButton("Actualizar lista");
        btnRefrescar.addActionListener(e -> cargarTabla());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        root.add(panelBotones, BorderLayout.SOUTH);

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







