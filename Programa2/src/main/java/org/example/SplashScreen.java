package org.example;

import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JWindow {

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

    public void mostrar() {
        setVisible(true);
        try {
            Thread.sleep(2500); // duración
        } catch (Exception e) {}
        setVisible(false);
        dispose();
    }
}

