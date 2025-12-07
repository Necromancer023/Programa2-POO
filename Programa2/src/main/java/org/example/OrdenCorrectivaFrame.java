package org.example;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

public class OrdenCorrectivaFrame extends JFrame {

    private SistemaMantenimiento sistema;

    private JTextField txtId, txtEquipoId, txtFalla, txtCausa, txtDiagnostico;
    private JComboBox<OrdenCorrectiva.Prioridad> comboPrioridad;

    private JTextArea salida;

    public OrdenCorrectivaFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Gestión de Órdenes Correctivas");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        System.out.println(">>> [CorrectivaFrame] inicializando interfaz");

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Registrar Orden", panelRegistrar());
        tabs.add("Iniciar Atención", panelIniciar());
        tabs.add("Finalizar Orden", panelFinalizar());
        tabs.add("Cancelar Orden", panelCancelar());
        tabs.add("Listado", panelLista());

        add(tabs, BorderLayout.CENTER);
    }

    // ======================================================
    // PANEL CREACIÓN DE ORDEN RESPETANDO CONTROLLER
    // ======================================================
    private JPanel panelRegistrar() {
        JPanel p = new JPanel(new GridLayout(7, 2, 5, 5));

        txtId = new JTextField();
        txtEquipoId = new JTextField();
        txtFalla = new JTextField();
        txtCausa = new JTextField();
        txtDiagnostico = new JTextField();
        comboPrioridad = new JComboBox<>(OrdenCorrectiva.Prioridad.values());

        p.add(new JLabel("ID Orden:"));       p.add(txtId);
        p.add(new JLabel("ID Equipo:"));      p.add(txtEquipoId);
        p.add(new JLabel("Descripción falla:")); p.add(txtFalla);
        p.add(new JLabel("Causa falla:"));    p.add(txtCausa);
        p.add(new JLabel("Diagnóstico inicial:")); p.add(txtDiagnostico);
        p.add(new JLabel("Prioridad:"));      p.add(comboPrioridad);

        JButton btnGuardar = new JButton("Crear Orden Correctiva");
        btnGuardar.addActionListener(e -> registrarOrden());
        p.add(new JLabel());
        p.add(btnGuardar);

        return p;
    }

    private void registrarOrden() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int idEquipo = Integer.parseInt(txtEquipoId.getText());

            Equipo eq = sistema.getEquipoController().buscarEquipo(idEquipo);

            if (eq == null) {
                JOptionPane.showMessageDialog(this, "Equipo no encontrado.");
                return;
            }

            System.out.println(">>> [CorrectivaFrame] Creando orden " + id);

            String resultado = sistema.getOrdenCorrectivaController().crearOrdenCorrectiva(
                    id,
                    LocalDate.now(),
                    eq,
                    txtFalla.getText(),
                    txtCausa.getText(),
                    (OrdenCorrectiva.Prioridad) comboPrioridad.getSelectedItem(),
                    txtDiagnostico.getText()
            );

            JOptionPane.showMessageDialog(this, resultado);
            cargarOrdenes();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
        }
    }

    // ------------------------------------------------------------
    // PANEL: INICIAR ATENCIÓN
    // ------------------------------------------------------------
    private JPanel panelIniciar() {
        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField txtIdAtender = new JTextField();

        p.add(new JLabel("ID Orden a atender:"));
        p.add(txtIdAtender);

        JButton btnIniciar = new JButton("Iniciar Atención");
        btnIniciar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdAtender.getText());

                String r = sistema.getOrdenCorrectivaController().iniciarAtencion(
                        id,
                        java.time.LocalDate.now()
                );

                JOptionPane.showMessageDialog(this, r);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        p.add(new JLabel());
        p.add(btnIniciar);

        return p;
    }


    // ======================================================
    // PANEL FINALIZAR ORDEN
    // ======================================================
    private JTextField txtFinId, txtAcciones, txtObs, txtCosto, txtHoras;

    private JPanel panelFinalizar() {
        JPanel p = new JPanel(new GridLayout(6, 2, 5, 5));

        txtFinId = new JTextField();
        txtAcciones = new JTextField();
        txtObs = new JTextField();
        txtCosto = new JTextField();
        txtHoras = new JTextField();

        p.add(new JLabel("ID Orden:"));      p.add(txtFinId);
        p.add(new JLabel("Acciones realizadas:")); p.add(txtAcciones);
        p.add(new JLabel("Observaciones:"));       p.add(txtObs);
        p.add(new JLabel("Costo reparación:"));    p.add(txtCosto);
        p.add(new JLabel("Horas trabajadas:"));    p.add(txtHoras);

        JButton btnFin = new JButton("Finalizar");
        btnFin.addActionListener(e -> finalizarOrden());

        p.add(new JLabel());
        p.add(btnFin);

        return p;
    }

    private void finalizarOrden() {
        try {
            int id = Integer.parseInt(txtFinId.getText());
            double costo = Double.parseDouble(txtCosto.getText());
            double horas = Double.parseDouble(txtHoras.getText());

            String acciones = txtAcciones.getText();
            String obs = txtObs.getText();

            System.out.println(">>> [CorrectivaFrame] Intentando finalizar orden " + id);

            // ================= VALIDACIONES PREVIAS =================

            // 1) Validar orden existente
            OrdenCorrectiva orden = sistema.getOrdenCorrectivaController().buscarOrdenPorId(id);
            if (orden == null) {
                JOptionPane.showMessageDialog(this, "La orden no existe.");
                return;
            }

            // 2) Validar que ya haya sido iniciada
            if (orden.getFechaAtencion() == null) {
                JOptionPane.showMessageDialog(this, "Debe iniciar atención antes de finalizar la orden.");
                return;
            }

            // 3) Validar horas
            if (horas < 0) {
                JOptionPane.showMessageDialog(this, "Las horas trabajadas no pueden ser negativas.");
                return;
            }

            // 4) Validar costo
            if (costo < 0) {
                JOptionPane.showMessageDialog(this, "El costo no puede ser negativo.");
                return;
            }

            // 5) Validar acciones obligatorias
            if (acciones == null || acciones.isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el diagnóstico / acciones realizadas.");
                return;
            }

            // 6) Validar coherencia temporal
            LocalDate hoy = LocalDate.now();
            if (hoy.isBefore(orden.getFechaAtencion())) {
                JOptionPane.showMessageDialog(this, "La fecha de finalización no puede ser menor que la fecha de inicio.");
                return;
            }

            // ================= EJECUCIÓN REAL =================
            String r = sistema.getOrdenCorrectivaController().finalizarOrden(
                    id,
                    hoy,
                    acciones,
                    obs,
                    costo,
                    horas
            );

            System.out.println(">>> [CorrectivaFrame] Finalización resultó: " + r);

            JOptionPane.showMessageDialog(this, r);
            cargarOrdenes();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
        }
    }


    // ======================================================
    // PANEL LISTAR ÓRDENES
    // ======================================================
    private JPanel panelLista() {
        JPanel p = new JPanel(new BorderLayout());

        salida = new JTextArea();
        salida.setEditable(false);

        JButton btn = new JButton("Actualizar listado");
        btn.addActionListener(e -> cargarOrdenes());

        p.add(btn, BorderLayout.NORTH);
        p.add(new JScrollPane(salida), BorderLayout.CENTER);

        return p;
    }

    private void cargarOrdenes() {
        salida.setText("");

        System.out.println(">>> [CorrectivaFrame] Cargando órdenes...");

        List<OrdenCorrectiva> lista = sistema.getOrdenCorrectivaController().obtenerOrdenes();

        if (lista.isEmpty()) {
            salida.append("No hay órdenes registradas.");
            return;
        }

        for (OrdenCorrectiva oc : lista) {
            salida.append(oc.toString() + "\n");
        }
    }

    // ======================================================
    // PANEL CANCELAR ORDEN CORRECTIVA
    // ======================================================
    private JPanel panelCancelar() {
        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField txtIdCancelar = new JTextField();

        p.add(new JLabel("ID Orden a cancelar:"));
        p.add(txtIdCancelar);

        JButton btnCanc = new JButton("Cancelar Orden");
        btnCanc.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtIdCancelar.getText());
                String motivo = JOptionPane.showInputDialog("Ingrese motivo de cancelación:");

                if (motivo == null || motivo.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Debe indicar un motivo.");
                    return;
                }

                String r = sistema.getOrdenCorrectivaController()
                        .marcarNoReparada(id, motivo);

                JOptionPane.showMessageDialog(this, r);
                cargarOrdenes();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        p.add(new JLabel());
        p.add(btnCanc);

        return p;
    }

}




