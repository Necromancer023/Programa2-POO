package org.example;

import java.awt.*;
import javax.swing.*;

public class MainMenuFrame extends JFrame {

    private SistemaMantenimiento sistema;

    // Botones del menú (necesarios para habilitar/deshabilitar según rol)
    private JButton btnUsuarios;
    private JButton btnTecnicos;
    private JButton btnEquipos;
    private JButton btnPreventivas;
    private JButton btnCorrectivas;
    private JButton btnInventario;
    private JButton btnProgramas;
    private JButton btnReportes;
    private JButton btnAuditoria;

    public MainMenuFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Sistema de Mantenimiento - Menú Principal");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10));

        // Crear cada botón asignándole su acción
        btnUsuarios = btn("Usuarios", () -> new UsuarioFrame().setVisible(true));
        btnTecnicos = btn("Técnicos", () -> new TecnicoFrame().setVisible(true));
        btnEquipos = btn("Equipos", () -> new EquipoFrame().setVisible(true));
        btnPreventivas = btn("Órdenes Preventivas", () -> new OrdenPreventivaFrame().setVisible(true));
        btnCorrectivas = btn("Órdenes Correctivas", () -> new OrdenCorrectivaFrame().setVisible(true));
        btnInventario = btn("Inventario Repuestos", () -> new InventarioRepuestosFrame(sistema).setVisible(true));
        btnProgramas = btn("Programas Preventivos", () -> new ProgramaPreventivoFrame().setVisible(true));
        btnReportes = btn("Reportes", () -> new ReportesFrame(sistema).setVisible(true));
        btnAuditoria = btn("Auditoría", () -> new AuditoriaFrame(sistema).setVisible(true));

        // Agregar al panel
        panel.add(btnUsuarios);
        panel.add(btnTecnicos);
        panel.add(btnEquipos);
        panel.add(btnPreventivas);
        panel.add(btnCorrectivas);
        panel.add(btnInventario);
        panel.add(btnProgramas);
        panel.add(btnReportes);
        panel.add(btnAuditoria);

        add(panel);

        aplicarControldeAcceso();
    }

    /**
     * Método para personalizar visibilidad del menú según Rol del usuario logueado
     */
    private void aplicarControldeAcceso() {

        try {
            Rol rol = sistema.getUsuarioActual().getRol();

            switch (rol) {

                case TECNICO:
                    btnUsuarios.setEnabled(false);
                    btnAuditoria.setEnabled(false);
                    break;

                case AUDITOR:
                    btnUsuarios.setEnabled(false);
                    btnTecnicos.setEnabled(false);
                    btnEquipos.setEnabled(false);
                    btnPreventivas.setEnabled(false);
                    btnCorrectivas.setEnabled(false);
                    btnInventario.setEnabled(false);
                    btnProgramas.setEnabled(false);
                    break;

                case ADMINISTRADOR:
                default:
                    // Acceso total
                    break;
            }

        } catch (Exception e) {
            System.out.println("⚠ Error aplicando control de acceso: " + e.getMessage());
        }
    }

    private JButton btn(String texto, Runnable action) {
        JButton b = new JButton(texto);
        b.addActionListener(e -> action.run());
        return b;
    }
}





