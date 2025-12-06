package org.example;

import java.awt.*;
import javax.swing.*;

public class MainMenuFrame extends JFrame {

    private Usuario usuarioLogueado;

    public MainMenuFrame(Usuario usuario) {
        this.usuarioLogueado = usuario;

        setTitle("Sistema de Mantenimiento - Menú Principal");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10));

        panel.add(btn("Equipos"));
        panel.add(btn("Técnicos"));
        panel.add(btn("Órdenes preventivas"));
        panel.add(btn("Órdenes correctivas"));
        panel.add(btn("Inventario repuestos"));
        panel.add(btn("Programas preventivos"));
        panel.add(btn("Reportes"));
        panel.add(btn("Auditoría"));
        panel.add(btn("Cerrar sesión"));

        add(panel);
    }

    private JButton btn(String texto) {
        JButton b = new JButton(texto);
        b.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrir módulo: " + texto);
        });
        return b;
    }
}


