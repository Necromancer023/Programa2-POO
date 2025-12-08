package org.example;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

/**
 * Interfaz Swing para gestionar órdenes preventivas:
 * - Creación
 * - Inicio de ejecución
 * - Finalización
 * - Cancelación
 * - Registro de materiales
 * - Listado de órdenes registradas
 *
 * Se comunica con los controladores del sistema usando
 * validaciones mínimas antes de delegar acciones.
 */
public class OrdenPreventivaFrame extends JFrame {

    // ---- Componentes de entrada de datos ----
    private JTextField txtIdOrden, txtFechaProgramada, txtTiempoReal, txtDiagnosticoFinal;
    private JComboBox<Equipo> cmbEquipo;
       private JComboBox<FasePreventiva> cmbFase;
    private JComboBox<Tecnico> cmbTecnico;
    private JTextArea txtLista;

    // ---- Controladores utilizados ----
    private OrdenPreventivaController ordenController;
    private EquipoController equipoController;
    private TecnicoController tecnicoController;
    private ProgramaPreventivoController programaController;

    /**
     * Constructor que obtiene la instancia singleton del sistema.
     */
    public OrdenPreventivaFrame() { this(SistemaMantenimiento.getInstance()); }

    /**
     * Constructor principal: inicializa controladores y crea la interfaz.
     *
     * @param sistema instancia central del sistema de mantenimiento
     */
    public OrdenPreventivaFrame(SistemaMantenimiento sistema) {

        ordenController = sistema.getOrdenPreventivaController();
        equipoController = sistema.getEquipoController();
        tecnicoController = sistema.getTecnicoController();
        programaController = sistema.getProgramaPreventivoController();

        setTitle("Gestión de Órdenes Preventivas");
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ============================
        // PANEL SUPERIOR - FORMULARIO
        // ============================
        JPanel form = new JPanel(new GridLayout(4, 4, 5, 5));

        form.add(new JLabel("ID Orden:"));
        txtIdOrden = new JTextField();
        form.add(txtIdOrden);

        form.add(new JLabel("Fecha Programada (YYYY-MM-DD):"));
        txtFechaProgramada = new JTextField();
        form.add(txtFechaProgramada);

        form.add(new JLabel("Equipo:"));
        cmbEquipo = new JComboBox<>();
        form.add(cmbEquipo);

        form.add(new JLabel("Técnico Asignado:"));
        cmbTecnico = new JComboBox<>();
        form.add(cmbTecnico);

        form.add(new JLabel("Fase Preventiva:"));
        cmbFase = new JComboBox<>();
        form.add(cmbFase);

        JButton btnRecargar = new JButton("🔄 Recargar Datos");
        btnRecargar.addActionListener(e -> cargarDatos());
        form.add(btnRecargar);
        form.add(new JLabel());

        form.add(new JLabel("Diagnóstico Final:"));
        txtDiagnosticoFinal = new JTextField();
        form.add(txtDiagnosticoFinal);

        form.add(new JLabel("Tiempo real (hrs):"));
        txtTiempoReal = new JTextField();
        form.add(txtTiempoReal);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.add(form, BorderLayout.CENTER);
        panelSuperior.setPreferredSize(new Dimension(900, 160));

        add(panelSuperior, BorderLayout.PAGE_START);

        // ============================
        // PANEL DE ACCIONES
        // ============================
        JPanel acciones = new JPanel(new GridLayout(1, 5, 5, 5));

        JButton btnCrear = new JButton("Crear Orden");
        btnCrear.addActionListener(e -> crearOrden());
        acciones.add(btnCrear);

        JButton btnIniciar = new JButton("Iniciar Orden");
        btnIniciar.addActionListener(e -> iniciarOrden());
        acciones.add(btnIniciar);

        JButton btnCompletar = new JButton("Completar Orden");
        btnCompletar.addActionListener(e -> completarOrden());
        acciones.add(btnCompletar);

        JButton btnCancelar = new JButton("Cancelar Orden");
        btnCancelar.addActionListener(e -> cancelarOrden());
        acciones.add(btnCancelar);

        JButton btnMaterial = new JButton("Agregar Material");
        btnMaterial.addActionListener(e -> agregarMaterial());
        acciones.add(btnMaterial);

        add(acciones, BorderLayout.CENTER);

        // ============================
        // LISTADO INFERIOR
        // ============================
        txtLista = new JTextArea();
        txtLista.setEditable(false);
        txtLista.setFont(new Font("monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(txtLista);
        scroll.setPreferredSize(new Dimension(900, 300));
        add(scroll, BorderLayout.SOUTH);

        cargarDatos();
        listar();
    }

    /**
     * Recarga combos de equipos, técnicos y fases desde los controladores.
     */
    private void cargarDatos() {
        cmbEquipo.removeAllItems();
        for (Equipo e : equipoController.obtenerEquipos()) cmbEquipo.addItem(e);

        cmbTecnico.removeAllItems();
        List<Tecnico> tecnicos = tecnicoController.listarTecnicos();
        for (Tecnico t : tecnicos) cmbTecnico.addItem(t);

        cmbFase.removeAllItems();
        for (ProgramaPreventivo p : programaController.obtenerProgramas())
            for (FasePreventiva f : p.getFases()) cmbFase.addItem(f);
    }

    /**
     * Acción de UI para creación de orden preventiva.
     */
    private void crearOrden() {
        try {

            if (txtIdOrden.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un ID de orden.");
                return;
            }

            int id = Integer.parseInt(txtIdOrden.getText());
            LocalDate fecha = LocalDate.parse(txtFechaProgramada.getText());
            Equipo equipo = (Equipo) cmbEquipo.getSelectedItem();
            FasePreventiva fase = (FasePreventiva) cmbFase.getSelectedItem();
            Tecnico tecnico = (Tecnico) cmbTecnico.getSelectedItem();

            String res = ordenController.crearOrdenPreventiva(id, fecha, equipo, fase, tecnico);
            JOptionPane.showMessageDialog(this, res);

            limpiarFormulario();
            listar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    /** Inicia atención de la orden seleccionada. */
    private void iniciarOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            JOptionPane.showMessageDialog(this, ordenController.iniciarOrden(id, LocalDate.now()));
            listar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }

    /** Completa una orden preventiva con diagnóstico y tiempo real. */
    private void completarOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            double tiempo = Double.parseDouble(txtTiempoReal.getText());
            String diag = txtDiagnosticoFinal.getText();
            Tecnico tecnico = (Tecnico) cmbTecnico.getSelectedItem();

            JOptionPane.showMessageDialog(this,
                    ordenController.completarOrden(id, LocalDate.now(), tiempo, diag, tecnico));

            limpiarFormulario();
            listar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }

    /** Cancela una orden registrando motivo. */
    private void cancelarOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            String motivo = JOptionPane.showInputDialog("Indique motivo:");

            if (motivo == null || motivo.isBlank()) return;

            JOptionPane.showMessageDialog(this, ordenController.cancelarOrden(id, motivo));
            limpiarFormulario();
            listar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }

    /** Registra material consumido asociado a una orden preventiva. */
    private void agregarMaterial() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            String mat = JOptionPane.showInputDialog("Material usado:");
            if (mat == null || mat.isBlank()) return;
            JOptionPane.showMessageDialog(this, ordenController.agregarMaterial(id, mat));
            listar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }

    /** Lista todas las órdenes en la UI. */
    private void listar() {
        txtLista.setText("");
        for (OrdenPreventiva o : ordenController.obtenerOrdenes())
            txtLista.append(o.toString() + "\n");
    }

    /** Limpia campos del formulario superior. */
    private void limpiarFormulario() {
        txtIdOrden.setText("");
        txtFechaProgramada.setText("");
        txtDiagnosticoFinal.setText("");
        txtTiempoReal.setText("");
    }
}





