package org.example;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

public class OrdenPreventivaFrame extends JFrame {

    private JTextField txtIdOrden, txtFechaProgramada, txtTiempoReal, txtDiagnosticoFinal;
    private JComboBox<Equipo> cmbEquipo;
    private JComboBox<FasePreventiva> cmbFase;
    private JComboBox<Tecnico> cmbTecnico;
    private JTextArea txtLista;

    private OrdenPreventivaController ordenController;
    private EquipoController equipoController;
    private TecnicoController tecnicoController;
    private ProgramaPreventivoController programaController;

    public OrdenPreventivaFrame() { this(SistemaMantenimiento.getInstance()); }

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

        // ================================================
        // PANEL SUPERIOR  —  ahora apilado verticalmente
        // ================================================
        JPanel form = new JPanel(new GridLayout(4, 4, 5, 5)); // ← 4 filas en lugar de 2

        // === FILA 1 ===
        form.add(new JLabel("ID Orden:"));
        txtIdOrden = new JTextField();
        form.add(txtIdOrden);

        form.add(new JLabel("Fecha Programada (YYYY-MM-DD):"));
        txtFechaProgramada = new JTextField();
        form.add(txtFechaProgramada);

        // === FILA 2 ===
        form.add(new JLabel("Equipo:"));
        cmbEquipo = new JComboBox<>();
        form.add(cmbEquipo);

        form.add(new JLabel("Técnico Asignado:"));
        cmbTecnico = new JComboBox<>();
        form.add(cmbTecnico);

        // === FILA 3 ===
        form.add(new JLabel("Fase Preventiva:"));
        cmbFase = new JComboBox<>();
        form.add(cmbFase);

        JButton btnRecargar = new JButton("🔄 Recargar Datos");
        btnRecargar.addActionListener(e -> cargarDatos());
        form.add(btnRecargar);

        // campo vacío para mantener simetría
        form.add(new JLabel());

        // === FILA 4 ===
        form.add(new JLabel("Diagnóstico Final:"));
        txtDiagnosticoFinal = new JTextField();
        form.add(txtDiagnosticoFinal);

        form.add(new JLabel("Tiempo real (hrs):"));
        txtTiempoReal = new JTextField();
        form.add(txtTiempoReal);

        // === Panel contenedor ===
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.add(form, BorderLayout.CENTER);
        panelSuperior.setPreferredSize(new Dimension(900, 150));

        add(panelSuperior, BorderLayout.PAGE_START);

        // =======================================================
        // PANEL BOTONES ACCIÓN — sin cambios
        // =======================================================
        JButton btnCrear = new JButton("Crear Orden");
        btnCrear.addActionListener(e -> crearOrden());

        JButton btnIniciar = new JButton("Iniciar Orden");
        btnIniciar.addActionListener(e -> iniciarOrden());

        JButton btnCompletar = new JButton("Completar Orden");
        btnCompletar.addActionListener(e -> completarOrden());

        JButton btnCancelar = new JButton("Cancelar Orden");
        btnCancelar.addActionListener(e -> cancelarOrden());

        JButton btnMaterial = new JButton("Agregar Material");
        btnMaterial.addActionListener(e -> agregarMaterial());

        JPanel acciones = new JPanel(new GridLayout(6, 1, 5, 5));
        acciones.add(btnCrear);
        acciones.add(btnIniciar);
        acciones.add(btnCompletar);
        acciones.add(btnCancelar);
        acciones.add(btnMaterial);

        // =======================================================
        // PANEL LISTADO — sin cambios
        // =======================================================
        txtLista = new JTextArea();
        txtLista.setEditable(false);
        txtLista.setFont(new Font("monospaced", Font.PLAIN, 12));
        add(new JScrollPane(txtLista), BorderLayout.EAST);
        add(acciones, BorderLayout.SOUTH);

        cargarDatos();
        listar();
    }

    private void cargarDatos() {
        cmbEquipo.removeAllItems();
        for (Equipo e : equipoController.obtenerEquipos()) cmbEquipo.addItem(e);

        cmbTecnico.removeAllItems();
        List<Tecnico> tecnicos = tecnicoController.listarTecnicos();
        if (tecnicos.isEmpty())
            JOptionPane.showMessageDialog(this,"No hay técnicos registrados.");
        for (Tecnico t : tecnicos) cmbTecnico.addItem(t);

        cmbFase.removeAllItems();
        for (ProgramaPreventivo p : programaController.obtenerProgramas())
            for (FasePreventiva f : p.getFases()) cmbFase.addItem(f);
    }

    private void crearOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            LocalDate fecha = LocalDate.parse(txtFechaProgramada.getText());
            Equipo equipo = (Equipo) cmbEquipo.getSelectedItem();
            FasePreventiva fase = (FasePreventiva) cmbFase.getSelectedItem();
            Tecnico tecnico = (Tecnico) cmbTecnico.getSelectedItem();

            if (equipo == null) { JOptionPane.showMessageDialog(this, "Seleccione un equipo."); return; }
            if (fase == null) { JOptionPane.showMessageDialog(this, "Seleccione una fase."); return; }
            if (tecnico == null) { JOptionPane.showMessageDialog(this, "Seleccione un técnico."); return; }

            String res = ordenController.crearOrdenPreventiva(id, fecha, equipo, fase, tecnico);
            JOptionPane.showMessageDialog(this, res);
            listar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de datos: " + ex.getMessage());
        }
    }

    private void iniciarOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            JOptionPane.showMessageDialog(this, ordenController.iniciarOrden(id, LocalDate.now()));
            listar();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error."); }
    }

    private void completarOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            double tiempo = Double.parseDouble(txtTiempoReal.getText());
            String diag = txtDiagnosticoFinal.getText();
            Tecnico tecnico = (Tecnico) cmbTecnico.getSelectedItem();

            if (tecnico == null) { JOptionPane.showMessageDialog(this, "Seleccione técnico"); return; }

            JOptionPane.showMessageDialog(this,
                    ordenController.completarOrden(id, LocalDate.now(), tiempo, diag, tecnico));
            listar();

        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error."); }
    }

    private void cancelarOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            String motivo = JOptionPane.showInputDialog("Indique motivo:");
            if (motivo == null || motivo.isBlank()) return;
            JOptionPane.showMessageDialog(this, ordenController.cancelarOrden(id, motivo));
            listar();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error."); }
    }

    private void agregarMaterial() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            String mat = JOptionPane.showInputDialog("Material usado:");
            if (mat == null || mat.isBlank()) return;
            JOptionPane.showMessageDialog(this, ordenController.agregarMaterial(id, mat));
            listar();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error."); }
    }

    private void listar() {
        txtLista.setText("");
        for (OrdenPreventiva o : ordenController.obtenerOrdenes())
            txtLista.append(o.toString() + "\n");
    }
}



