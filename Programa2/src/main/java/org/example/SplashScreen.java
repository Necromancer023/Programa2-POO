package org.example;

import java.awt.*;
import javax.swing.*;

/**
 * Ventana de presentación inicial del sistema (splash screen).
 * Se muestra de forma temporal al iniciar la aplicación mientras
 * se realizan cargas internas o inicialización de módulos.
 *
 * Extiende JWindow para ser una pantalla simple sin bordes ni controles.
 */
public class SplashScreen extends JWindow {

    /**
     * Constructor que configura la apariencia del splash,
     * incluyendo colores, texto y tamaño de la ventana.
     */
    public SplashScreen() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(45, 85, 150));
        panel.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("SISTEMA DE MANTENIMIENTO", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel cargando = new JLabel("Inicializando módulos...", SwingConstants.CENTER);
        cargando.setForeground(Color.WHITE);
        cargando.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(titulo, BorderLayout.CENTER);
        panel.add(cargando, BorderLayout.SOUTH);

        add(panel);
        setSize(350, 150);
        setLocationRelativeTo(null);
    }

    /**
     * Muestra el splash en pantalla durante un breve intervalo
     * de tiempo y luego lo cierra automáticamente.
     *
     * Se usa en la fase de arranque antes de mostrar la ventana principal.
     */
    public void mostrar() {
        setVisible(true);
        try {
            Thread.sleep(2500); // duración
        } catch (Exception e) {}
        setVisible(false);
        dispose();
    }
}


