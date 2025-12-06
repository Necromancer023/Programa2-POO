package org.example;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenuFrame extends JFrame {
    private SistemaMantenimiento sistema;

    public MainMenuFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;
        setTitle("Sistema de Mantenimiento - Menú Principal");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10));

        panel.add(btn("Usuarios", () -> new UsuarioFrame().setVisible(true)));
        panel.add(btn("Técnicos", () -> new TecnicoFrame().setVisible(true)));
        panel.add(btn("Equipos", () -> new EquipoFrame().setVisible(true)));
        panel.add(btn("Órdenes Preventivas", () -> new OrdenPreventivaFrame().setVisible(true)));
        panel.add(btn("Órdenes Correctivas", () -> new OrdenCorrectivaFrame().setVisible(true)));
        panel.add(btn("Inventario Repuestos", () -> new InventarioRepuestosFrame(sistema).setVisible(true)));
        panel.add(btn("Programas Preventivos", () -> new ProgramaPreventivoFrame().setVisible(true)));
        panel.add(btn("Reportes", () -> new ReportesFrame(sistema).setVisible(true)));
        panel.add(btn("Auditoría", () -> new AuditoriaFrame(sistema).setVisible(true)));

        add(panel);
    }

    private JButton btn(String texto, Runnable action) {
        JButton b = new JButton(texto);
        b.addActionListener(e -> action.run());
        return b;
    }
}




