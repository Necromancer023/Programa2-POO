package org.example;

import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private SistemaMantenimiento sistema;

    public LoginFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Sistema de Mantenimiento - Login");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.addActionListener(e -> login());

        panel.add(new JLabel());
        panel.add(btnIngresar);

        add(panel);
    }

    private void login() {
        String user = txtUsuario.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();

        // 🔹 Validación obligatoria para presentación
        if (user.isBlank() || pass.isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar usuario y contraseña.",
                    "Faltan datos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario u = sistema.getUsuarioController().obtenerUsuarioAutenticado(user, pass);

        if (u != null) {
            // Guardar el usuario en el sistema
            sistema.login(u);

            JOptionPane.showMessageDialog(this,
                    "Bienvenido " + u.getNombreCompleto() + "\nRol: " + u.getRol());

            // Abrir menú principal
            new MainMenuFrame(sistema).setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(this,
                    "Credenciales inválidas",
                    "Error de autenticación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}



