package org.example;

public class Main {
    public static void main(String[] args) {

        // Crear instancia del sistema
        SistemaMantenimiento sistema = new SistemaMantenimiento();

        // Registrar usuario por defecto para iniciar sesión
        UsuarioController usuarioController = sistema.getUsuarioController();
        usuarioController.crearUsuario(
                1,                      // ID
                "Administrador General", // nombre
                "admin",                // username
                "admin",                // password
                Rol.ADMINISTRADOR,              // rol
                "admin@sistema.com",    // email
                "0000-0000"             // telefono
        );

        // Abrir Login
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame(sistema);
            login.setVisible(true);
        });
    }
}



