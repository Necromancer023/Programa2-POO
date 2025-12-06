package org.example;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

public class OrdenPreventivaFrame extends JFrame {

    private JTextField txtIdOrden, txtFechaProgramada, txtTiempoReal, txtMaterial, txtDiagnosticoFinal;
    private JComboBox<Equipo> cmbEquipo;
    private JComboBox<FasePreventiva> cmbFase;
    private JComboBox<Tecnico> cmbTecnico;
    private JTextArea txtLista;

    private OrdenPreventivaController ordenController;
    private EquipoController equipoController;
    private TecnicoController tecnicoController;
    private ProgramaPreventivoController programaController;

    public OrdenPreventivaFrame() {

        ordenController = new OrdenPreventivaController();
        equipoController = new EquipoController();
        tecnicoController = new TecnicoController();
        programaController = new ProgramaPreventivoController();

        setTitle("Gestión de Órdenes Preventivas");
        setSize(800, 550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(7, 2, 5, 5));

        txtIdOrden = addField(form, "ID Orden:");

        txtFechaProgramada = addField(form, "Fecha Programada (YYYY-MM-DD):");

        form.add(new JLabel("Equipo:"));
        cmbEquipo = new JComboBox<>();
        form.add(cmbEquipo);

        form.add(new JLabel("Fase Preventiva:"));
        cmbFase = new JComboBox<>();
        form.add(cmbFase);

        form.add(new JLabel("Técnico Asignado:"));
        cmbTecnico = new JComboBox<>();
        form.add(cmbTecnico);

        txtDiagnosticoFinal = addField(form, "Diagnóstico Final:");

        txtTiempoReal = addField(form, "Tiempo real (hrs):");

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

        JPanel acciones = new JPanel(new GridLayout(5, 1, 5, 5));
        acciones.add(btnCrear);
        acciones.add(btnIniciar);
        acciones.add(btnCompletar);
        acciones.add(btnCancelar);
        acciones.add(btnMaterial);

        txtLista = new JTextArea();
        txtLista.setEditable(false);
        txtLista.setFont(new Font("monospaced", Font.PLAIN, 12));

        add(new JScrollPane(txtLista), BorderLayout.EAST);
        add(form, BorderLayout.CENTER);
        add(acciones, BorderLayout.SOUTH);

        cargarDatos();
        listar();
    }

    private JTextField addField(JPanel panel, String label) {
        panel.add(new JLabel(label));
        JTextField txt = new JTextField();
        panel.add(txt);
        return txt;
    }

    private void cargarDatos() {
        cmbEquipo.removeAllItems();
        for (Equipo e : equipoController.obtenerEquipos()) {
            cmbEquipo.addItem(e);
        }

        cmbTecnico.removeAllItems();
        for (Tecnico t : tecnicoController.listarTecnicos()) {
            cmbTecnico.addItem(t);
        }

        cmbFase.removeAllItems();
        for (ProgramaPreventivo p : programaController.obtenerProgramas()) {
            for (FasePreventiva f : p.getFases()) {
                cmbFase.addItem(f);
            }
        }
    }

    private void crearOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            LocalDate fecha = LocalDate.parse(txtFechaProgramada.getText());
            Equipo equipo = (Equipo) cmbEquipo.getSelectedItem();
            FasePreventiva fase = (FasePreventiva) cmbFase.getSelectedItem();
            Tecnico tecnico = (Tecnico) cmbTecnico.getSelectedItem();

            String res = ordenController.crearOrdenPreventiva(
                    id, fecha, equipo, fase, tecnico
            );

            JOptionPane.showMessageDialog(this, res);
            listar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de datos.");
        }
    }

    private void iniciarOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            String res = ordenController.iniciarOrden(id, LocalDate.now());
            JOptionPane.showMessageDialog(this, res);
            listar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en operación.");
        }
    }

    private void completarOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            LocalDate fecha = LocalDate.now();
            double tiempo = Double.parseDouble(txtTiempoReal.getText());
            String diag = txtDiagnosticoFinal.getText();
            Tecnico tecnico = (Tecnico) cmbTecnico.getSelectedItem();

            String res = ordenController.completarOrden(
                    id, fecha, tiempo, diag, tecnico
            );

            JOptionPane.showMessageDialog(this, res);
            listar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al completar.");
        }
    }

    private void cancelarOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            String motivo = JOptionPane.showInputDialog("Indique motivo cancelación");
            String res = ordenController.cancelarOrden(id, motivo);
            JOptionPane.showMessageDialog(this, res);
            listar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en cancelación.");
        }
    }

    private void agregarMaterial() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            String mat = JOptionPane.showInputDialog("Material utilizado:");
            String res = ordenController.agregarMaterial(id, mat);
            JOptionPane.showMessageDialog(this, res);
            listar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }

    private void listar() {
        txtLista.setText("");
        for (OrdenPreventiva o : ordenController.obtenerOrdenes()) {
            txtLista.append(o.toString() + "\n");
        }
    }
}

