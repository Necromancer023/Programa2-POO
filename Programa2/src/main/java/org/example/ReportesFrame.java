package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ReportesFrame extends JFrame {

    private SistemaMantenimiento sistema;
    private JTextArea areaSalida;

    public ReportesFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Reportes del Sistema");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Selector de tipo de reporte
        String[] opciones = {
                "Usuarios",
                "Técnicos",
                "Equipos",
                "Órdenes Preventivas",
                "Órdenes Correctivas",
                "Inventario de Repuestos",
                "Auditoría del Sistema"
        };

        JComboBox<String> combo = new JComboBox<>(opciones);

        JButton btnGenerar = new JButton("Generar Reporte");
        btnGenerar.addActionListener(e -> generarReporte(combo.getSelectedItem().toString()));

        JPanel top = new JPanel();
        top.add(new JLabel("Seleccione reporte:"));
        top.add(combo);
        top.add(btnGenerar);

        add(top, BorderLayout.NORTH);

        // Área de salida
        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        add(new JScrollPane(areaSalida), BorderLayout.CENTER);
    }

    // ---------------- GENERAR REPORTES --------------------
    private void generarReporte(String tipo) {
        areaSalida.setText(""); // limpiar salida

        switch (tipo) {

            case "Usuarios":
                mostrarUsuarios();
                break;

            case "Técnicos":
                mostrarTecnicos();
                break;

            case "Equipos":
                mostrarEquipos();
                break;

            case "Órdenes Preventivas":
                mostrarOrdenesPreventivas();
                break;

            case "Órdenes Correctivas":
                mostrarOrdenesCorrectivas();
                break;

            case "Inventario de Repuestos":
                mostrarInventario();
                break;

            case "Auditoría del Sistema":
                mostrarAuditoria();
                break;
        }
    }

    // ---------------- Funciones Reportes --------------------

    private void mostrarUsuarios() {
        List<Usuario> lista = sistema.getUsuarioController().listarUsuarios();
        lista.forEach(u -> areaSalida.append(u + "\n"));
    }

    private void mostrarTecnicos() {
        List<Tecnico> lista = sistema.getTecnicoController().listarTecnicos();
        lista.forEach(t -> areaSalida.append(t + "\n"));
    }

    private void mostrarEquipos() {
        List<Equipo> lista = sistema.getEquipoController().obtenerEquipos();
        lista.forEach(eq -> areaSalida.append(eq + "\n"));
    }

    private void mostrarOrdenesPreventivas() {
        List<OrdenPreventiva> lista = sistema.getOrdenPreventivaController().obtenerOrdenes();
        lista.forEach(op -> areaSalida.append(op + "\n"));
    }

    private void mostrarOrdenesCorrectivas() {
        List<OrdenCorrectiva> lista = sistema.getOrdenCorrectivaController().obtenerOrdenes();
        lista.forEach(oc -> areaSalida.append(oc + "\n"));
    }

    private void mostrarInventario() {
        List<Repuesto> lista = sistema.getRepuestoController().obtenerRepuestos();
        lista.forEach(r -> areaSalida.append(r + "\n"));
    }

    private void mostrarAuditoria() {
        List<AuditoriaMantenimiento> lista = sistema.getAuditoriaController().obtenerAuditoria();
        lista.forEach(a -> areaSalida.append(a + "\n"));
    }
}

