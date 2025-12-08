package org.example;

import java.awt.*;
import javax.swing.*;

/**
 * Ventana inicial del sistema.
 * Permite al usuario autenticarse ingresando sus credenciales.
 * Una vez validado, carga el menú principal.
 */
public class LoginFrame extends JFrame {

    /** Campo visual para ingresar el nombre de usuario. */
    private JTextField txtUsuario;

    /** Campo visual para ingresar la contraseña de forma segura. */
    private JPasswordField txtPassword;

    /** Referencia al sistema principal para acceder a controladores y contexto. */
    private SistemaMantenimiento sistema;

    /**
     * Constructor de la interfaz de Login.
     *
     * @param sistema instancia principal del sistema de mantenimiento
     */
    public LoginFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Sistema de Mantenimiento - Login");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel para los campos de formulario
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        // Botón de acción principal
        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.addActionListener(e -> login());

        panel.add(new JLabel()); // Espacio vacío para alineación estética
        panel.add(btnIngresar);

        add(panel);
    }

    /**
     * Método asociado al botón de ingresar.
     * Captura los datos, valida campos, autentica y carga menú principal.
     */
    private void login() {
        String user = txtUsuario.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();

        // Validación de entrada requerida antes de autenticar
        if (user.isBlank() || pass.isBlank()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar usuario y contraseña.",
                    "Faltan datos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Consulta al controlador de usuarios para validar credenciales
        Usuario u = sistema.getUsuarioController().obtenerUsuarioAutenticado(user, pass);

        if (u != null) {
            // Registrar sesión en el sistema
            sistema.login(u);

            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido " + u.getNombreCompleto() + "\nRol: " + u.getRol()
            );

            // Abrir interfaz principal y cerrar login
            new MainMenuFrame(sistema).setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Credenciales inválidas",
                    "Error de autenticación",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}




