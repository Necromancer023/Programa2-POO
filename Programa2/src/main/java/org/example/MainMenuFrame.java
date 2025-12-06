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
    private JButton btnCerrarSesion;

    public MainMenuFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Sistema de Mantenimiento - Menú Principal");
        setSize(650, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // GridLayout 4x3 para incluir botón de cerrar sesión
        JPanel panel = new JPanel(new GridLayout(4, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Crear cada botón asignándole su acción
        btnUsuarios = btn("Usuarios", () -> new UsuarioFrame(sistema).setVisible(true));
        btnTecnicos = btn("Técnicos", () -> new TecnicoFrame(sistema).setVisible(true));
        btnEquipos = btn("Equipos", () -> new EquipoFrame(sistema).setVisible(true));
        btnPreventivas = btn("Órdenes Preventivas", () -> new OrdenPreventivaFrame(sistema).setVisible(true));
        btnCorrectivas = btn("Órdenes Correctivas", () -> new OrdenCorrectivaFrame(sistema).setVisible(true));
        btnInventario = btn("Inventario Repuestos", () -> new InventarioRepuestosFrame(sistema).setVisible(true));
        btnProgramas = btn("Programas Preventivos", () -> new ProgramaPreventivoFrame(sistema).setVisible(true));
        btnReportes = btn("Reportes", () -> new ReportesFrame(sistema).setVisible(true));
        btnAuditoria = btn("Auditoría", () -> new AuditoriaFrame(sistema).setVisible(true));
        
        btnCerrarSesion = btn("Cerrar Sesión", this::cerrarSesion);
        btnCerrarSesion.setBackground(new Color(220, 53, 69));
        btnCerrarSesion.setForeground(Color.WHITE);

        // Agregar botones al panel
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
        
        // Espacios vacíos
        panel.add(new JLabel());
        panel.add(new JLabel());
        
        // Botón cerrar sesión al final
        panel.add(btnCerrarSesion);

        add(panel);

        // Aplicar control de acceso DESPUÉS de crear todos los botones
        aplicarControlDeAcceso();
    }

    /**
     * Método para personalizar visibilidad del menú según Rol del usuario logueado
     */
    private void aplicarControlDeAcceso() {
        try {
            Usuario usuarioActual = sistema.getUsuarioActual();
            
            // Validar que el usuario exista
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
            
            // Mostrar información del usuario en el título
            setTitle("Sistema de Mantenimiento - " + usuarioActual.getNombreCompleto() + 
                    " (" + rol + ")");
            
            System.out.println("Aplicando control de acceso para: " + 
                             usuarioActual.getNombreCompleto() + " [" + rol + "]");

            switch (rol) {

                case TECNICO:
                    System.out.println("   → Perfil TÉCNICO: acceso operativo");
                    btnUsuarios.setEnabled(false);
                    btnAuditoria.setEnabled(false);
                    // Puede usar: Técnicos, Equipos, Órdenes, Inventario, Programas, Reportes
                    break;

                case AUDITOR:
                    System.out.println("   → Perfil AUDITOR: solo consulta y auditoría");
                    btnUsuarios.setEnabled(false);
                    btnTecnicos.setEnabled(false);
                    btnEquipos.setEnabled(false);
                    btnPreventivas.setEnabled(false);
                    btnCorrectivas.setEnabled(false);
                    btnInventario.setEnabled(false);
                    btnProgramas.setEnabled(false);
                    // Solo puede usar: Reportes y Auditoría
                    break;

                case SUPERVISOR:
                    System.out.println("   → Perfil SUPERVISOR: acceso de supervisión");
                    btnUsuarios.setEnabled(false);
                    // Puede usar todo excepto gestión de usuarios
                    break;

                case ADMINISTRADOR:
                    System.out.println("   → Perfil ADMINISTRADOR: acceso total");
                    // Todos los botones habilitados (no hace nada)
                    break;

                default:
                    System.out.println("   → Rol desconocido: acceso restringido");
                    // Bloquear todo excepto reportes
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
     * Cierra la sesión del usuario actual
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
            // Llamar al logout del sistema
            sistema.logout();
            
            dispose();  // Cierra el menú actual
            
            JOptionPane.showMessageDialog(null, 
                "Sesión cerrada correctamente",
                "Hasta pronto",
                JOptionPane.INFORMATION_MESSAGE);
            
            new LoginFrame(sistema).setVisible(true);  // Vuelve al login
        }
    }

    /**
     * Crea un botón con texto y acción
     */
    private JButton btn(String texto, Runnable action) {
        JButton b = new JButton(texto);
        b.addActionListener(e -> action.run());
        b.setFont(new Font("Arial", Font.PLAIN, 12));
        b.setFocusPainted(false);
        return b;
    }
}





