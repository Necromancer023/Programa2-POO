package org.example.gui;

import java.awt.*;
import javax.swing.*;
import org.example.Usuario;
import org.example.UsuarioController;

public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    private UsuarioController usuarioController;

    public LoginFrame() {
        usuarioController = new UsuarioController();

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
        String user = txtUsuario.getText();
        String pass = new String(txtPassword.getPassword());

        Usuario u = usuarioController.autenticar(user, pass);

        if (u != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + u.getNombreCompleto());
            new MainMenuFrame(u).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas");
        }
    }
}
