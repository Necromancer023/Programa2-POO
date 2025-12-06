package org.example;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // Crear instancia del sistema
        SistemaMantenimiento sistema = new SistemaMantenimiento();

        // Usuario admin
        sistema.getUsuarioController().crearUsuario(
            1, "Administrador del Sistema", "admin", "1234",
            Rol.ADMINISTRADOR, "admin@mail.com", "8888-0000"
        );

        // Técnico
        sistema.getTecnicoController().crearTecnico(
            1, "Carlos Gómez", "Mecánico", "5555-1234", "carlos@mail.com"
        );

        // Equipo de prueba
        sistema.getEquipoController().crearEquipo(
        1,
        "Torno CNC",
        "Industrial",
        "Taller 1",
        "Haas Automation",
        "SN-001",
        LocalDate.now().minusYears(2),
        LocalDate.now().minusYears(1),
        80,
        15000.0,
        Equipo.EstadoEquipo.OPERATIVO,
        "VF-2SS",
        "2m x 1m x 1m",
        1200.0
        );


        // Mostrar pantalla splash
        SplashScreen splash = new SplashScreen();
        splash.mostrar();
        
        // Abrir Login
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame(sistema);
            login.setVisible(true);
        });
    }
}



