package org.example;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

/**
 * Ventana Swing encargada de gestionar el ciclo completo de las
 * órdenes correctivas: registro, inicio de atención, finalización,
 * cancelación y consulta de registros.
 *
 * Interactúa con {@link SistemaMantenimiento} y los controladores:
 * EquipoController y OrdenCorrectivaController.
 */
public class OrdenCorrectivaFrame extends JFrame {

    private SistemaMantenimiento sistema;

    // Campos de entrada de datos para creación de orden
    private JTextField txtId, txtEquipoId, txtFalla, txtCausa, txtDiagnostico;
    private JComboBox<OrdenCorrectiva.Prioridad> comboPrioridad;

    private JTextArea salida;

    /**
     * Constructor principal que construye la interfaz
     * y configura pestañas de operación.
     */
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
    // PANEL DE REGISTRO DE ORDENES
    // ======================================================

    /**
     * Construye panel para la creación inicial de orden correctiva.
     */
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

    /**
     * Ejecuta la creación de una orden correctiva
     * validando existencia de equipo y datos numéricos.
     */
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

    // ======================================================
    // PANEL INICIAR ATENCION
    // ======================================================

    /**
     * Construye panel que permite cambiar estado de la orden a "EN_PROCESO".
     */
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

    /**
     * Panel para finalizar las órdenes ingresando diagnósticos y costos.
     */
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

    /**
     * Aplica la finalización real de la orden con validaciones
     * preventivas antes de delegar al controlador.
     */
    private void finalizarOrden() {
        try {
            int id = Integer.parseInt(txtFinId.getText());
            double costo = Double.parseDouble(txtCosto.getText());
            double horas = Double.parseDouble(txtHoras.getText());

            String acciones = txtAcciones.getText();
            String obs = txtObs.getText();

            System.out.println(">>> [CorrectivaFrame] Intentando finalizar orden " + id);

            // Validaciones internas previas
            OrdenCorrectiva orden = sistema.getOrdenCorrectivaController().buscarOrdenPorId(id);
            if (orden == null) {
                JOptionPane.showMessageDialog(this, "La orden no existe.");
                return;
            }

            if (orden.getFechaAtencion() == null) {
                JOptionPane.showMessageDialog(this, "Debe iniciar atención antes de finalizar la orden.");
                return;
            }

            if (horas < 0) {
                JOptionPane.showMessageDialog(this, "Las horas trabajadas no pueden ser negativas.");
                return;
            }

            if (costo < 0) {
                JOptionPane.showMessageDialog(this, "El costo no puede ser negativo.");
                return;
            }

            if (acciones == null || acciones.isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el diagnóstico / acciones realizadas.");
                return;
            }

            LocalDate hoy = LocalDate.now();
            if (hoy.isBefore(orden.getFechaAtencion())) {
                JOptionPane.showMessageDialog(this, "La fecha de finalización no puede ser menor que la fecha de inicio.");
                return;
            }

            // Delegación al controlador
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
    // PANEL LISTADO
    // ======================================================

    /**
     * Construye panel que muestra todas las órdenes registradas.
     */
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

    /**
     * Carga órdenes existentes y las muestra en consola y panel.
     */
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

    /**
     * Panel que permite marcar una orden como no reparada.
     */
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





