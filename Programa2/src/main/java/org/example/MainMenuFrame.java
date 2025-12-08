package org.example;

import java.awt.*;
import javax.swing.*;

/**
 * Ventana principal del sistema.
 * Presenta un menú con accesos a los distintos módulos del sistema
 * y controla la visibilidad según el rol del usuario autenticado.
 */
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
    private JButton btnCerrarSesion;

    /**
     * Constructor que recibe el sistema central para interactuar con sus controladores.
     *
     * @param sistema instancia única del sistema de mantenimiento
     */
    public MainMenuFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Sistema de Mantenimiento - Menú Principal");
        setSize(650, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con distribución en cuadrícula
        JPanel panel = new JPanel(new GridLayout(5, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Creación y asociación de cada botón con su acción correspondiente
        btnUsuarios = btn("Usuarios", () -> new UsuarioFrame(sistema).setVisible(true));
        btnTecnicos = btn("Técnicos", () -> new TecnicoFrame(sistema).setVisible(true));
        btnEquipos = btn("Equipos", () -> new EquipoFrame(sistema).setVisible(true));
        btnPreventivas = btn("Órdenes Preventivas", () -> new OrdenPreventivaFrame(sistema).setVisible(true));
        btnCorrectivas = btn("Órdenes Correctivas", () -> new OrdenCorrectivaFrame(sistema).setVisible(true));
        btnInventario = btn("Inventario Repuestos", () -> new InventarioRepuestosFrame(sistema).setVisible(true));
        btnProgramas = btn("Programas Preventivos", () -> new ProgramaPreventivoFrame(sistema).setVisible(true));
        btnReportes = btn("Reportes", () -> new ReportesFrame(sistema).setVisible(true));
        btnAuditoria = btn("Auditoría", () -> new AuditoriaFrame(sistema).setVisible(true));

        // Botón especial de cierre de sesión
        btnCerrarSesion = btn("Cerrar Sesión", this::cerrarSesion);
        btnCerrarSesion.setBackground(new Color(220, 53, 69));
        btnCerrarSesion.setForeground(Color.WHITE);

        // Incorporación de los botones al panel en orden visual
        panel.add(btnUsuarios);
        panel.add(btnTecnicos);
        panel.add(btnEquipos);
        panel.add(btnPreventivas);
        panel.add(btnCorrectivas);
        panel.add(btnInventario);
        panel.add(btnProgramas);
        panel.add(btnReportes);
        panel.add(btnAuditoria);
        panel.add(btn("Fallas", () -> new FallaFrame(sistema).setVisible(true)));
        panel.add(btn("Consulta Mantenimiento", () -> new ConsultaMantenimientoFrame(sistema).setVisible(true)));
        panel.add(btn("Gráfico Costos", () -> new GraficoCostosFrame(sistema).setVisible(true)));
        panel.add(btn("Gráfico Estados", () -> new GraficoEstadosFrame(sistema).setVisible(true)));

        // Espacios de relleno para diseño
        panel.add(new JLabel());
        panel.add(new JLabel());

        // Botón de cierre de sesión al final
        panel.add(btnCerrarSesion);

        add(panel);

        // Aplicación de restricciones por rol del usuario después de crear los botones
        aplicarControlDeAcceso();
    }

    /**
     * Ajusta la visibilidad y habilitación de opciones según el rol
     * del usuario que ingresó al sistema.
     */
    private void aplicarControlDeAcceso() {
        try {
            Usuario usuarioActual = sistema.getUsuarioActual();

            // Validación inicial de sesión
            if (usuarioActual == null) {
                System.err.println("ERROR: No hay usuario logueado en el sistema");
                JOptionPane.showMessageDialog(this,
                        "Error: No se detectó un usuario logueado.\nSerá redirigido al login.",
                        "Error de sesión",
                        JOptionPane.ERROR_MESSAGE);

                dispose();
                new LoginFrame(sistema).setVisible(true);
                return;
            }

            Rol rol = usuarioActual.getRol();

            // Mostrar nombre y rol en título
            setTitle("Sistema de Mantenimiento - " + usuarioActual.getNombreCompleto() +
                    " (" + rol + ")");

            System.out.println("Aplicando control de acceso para: " +
                    usuarioActual.getNombreCompleto() + " [" + rol + "]");

            // Configuración específica según el tipo de rol
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

                case SUPERVISOR:
                    btnUsuarios.setEnabled(false);
                    break;

                case ADMINISTRADOR:
                    // Todos habilitados, no requiere cambios
                    break;

                default:
                    btnUsuarios.setEnabled(false);
                    btnTecnicos.setEnabled(false);
                    btnEquipos.setEnabled(false);
                    btnPreventivas.setEnabled(false);
                    btnCorrectivas.setEnabled(false);
                    btnInventario.setEnabled(false);
                    btnProgramas.setEnabled(false);
                    btnAuditoria.setEnabled(false);
                    break;
            }

        } catch (NullPointerException e) {
            System.err.println("Error: Usuario actual es null");
            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Error crítico: No se pudo determinar el usuario actual.\n" +
                            "La sesión será reiniciada.",
                    "Error de Sistema",
                    JOptionPane.ERROR_MESSAGE);

            dispose();
            new LoginFrame(sistema).setVisible(true);

        } catch (Exception e) {
            System.err.println("Error aplicando control de acceso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Cierra sesión del usuario y vuelve a la pantalla de login.
     */
    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea cerrar sesión?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            sistema.logout();
            dispose();

            JOptionPane.showMessageDialog(null,
                    "Sesión cerrada correctamente",
                    "Hasta pronto",
                    JOptionPane.INFORMATION_MESSAGE);

            new LoginFrame(sistema).setVisible(true);
        }
    }

    /**
     * Método de utilidad para crear botones uniformes con su acción.
     *
     * @param texto  etiqueta del botón
     * @param action operación ejecutada al presionar el botón
     * @return botón configurado
     */
    private JButton btn(String texto, Runnable action) {
        JButton b = new JButton(texto);
        b.addActionListener(e -> action.run());
        b.setFont(new Font("Arial", Font.PLAIN, 12));
        b.setFocusPainted(false);
        return b;
    }
}






