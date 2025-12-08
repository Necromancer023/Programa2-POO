package org.example;

import java.time.LocalDate;

/**
 * Clase principal del sistema de mantenimiento.
 * Inicializa datos base, muestra el splash y abre la ventana de login.
 */
public class Main {

    /**
     * Punto de entrada del programa.
     *
     * @param args argumentos de línea de comando (no utilizados)
     */
    public static void main(String[] args) {

        // Crear u obtener la instancia general del sistema
        SistemaMantenimiento sistema = SistemaMantenimiento.getInstance();

        // --------------------------------------------------------------
        // CARGA DE DATOS DE PRUEBA
        // --------------------------------------------------------------

        // Registrar un usuario con rol administrador
        sistema.getUsuarioController().crearUsuario(
                1,
                "Administrador del Sistema",
                "admin",
                "1234",
                Rol.ADMINISTRADOR,
                "admin@mail.com",
                "8888-0000"
        );

        // Registrar un técnico en el sistema
        sistema.getTecnicoController().crearTecnico(
                1,
                "Carlos Gómez",
                "Mecánico",
                "5555-1234",
                "carlos@mail.com"
        );

        // Crear un equipo inicial de prueba dentro del inventario
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

        // --------------------------------------------------------------
        // INTERFAZ INICIAL
        // --------------------------------------------------------------

        // Crear y mostrar pantalla inicial (splash)
        SplashScreen splash = new SplashScreen();
        splash.mostrar();

        // Lanzar ventana de login en el hilo gráfico de Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame(sistema);
            login.setVisible(true);
        });
    }
}




