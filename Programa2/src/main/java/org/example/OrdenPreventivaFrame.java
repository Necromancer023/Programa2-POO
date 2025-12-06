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

    // ========== CONSTRUCTOR CORREGIDO ==========
    public OrdenPreventivaFrame() {
        this(SistemaMantenimiento.getInstance());
    }

    public OrdenPreventivaFrame(SistemaMantenimiento sistema) {

        // ✅ Usar controladores del sistema compartido
        ordenController = sistema.getOrdenPreventivaController();
        equipoController = sistema.getEquipoController();
        tecnicoController = sistema.getTecnicoController();
        programaController = sistema.getProgramaPreventivoController();

        setTitle("Gestión de Órdenes Preventivas");
        setSize(900, 600);
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

        // ✅ Botón para recargar datos
        JButton btnRecargar = new JButton("🔄 Recargar Datos");
        btnRecargar.addActionListener(e -> cargarDatos());
        form.add(btnRecargar);

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
        
        // ===== CARGAR EQUIPOS =====
        cmbEquipo.removeAllItems();
        for (Equipo e : equipoController.obtenerEquipos()) {
            cmbEquipo.addItem(e);
        }
        System.out.println("✅ Equipos cargados: " + cmbEquipo.getItemCount());

        // ===== CARGAR TÉCNICOS =====
        cmbTecnico.removeAllItems();
        
        List<Tecnico> tecnicos = tecnicoController.listarTecnicos();
        System.out.println("🔍 Técnicos disponibles: " + tecnicos.size());
        
        if (tecnicos.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ No hay técnicos registrados. Por favor registre técnicos primero.");
        }
        
        for (Tecnico t : tecnicos) {
            System.out.println("  → Agregando: " + t.getNombreCompleto());
            cmbTecnico.addItem(t);
        }
        
        System.out.println("✅ Técnicos en combo: " + cmbTecnico.getItemCount());

        // ===== CARGAR FASES =====
        cmbFase.removeAllItems();
        for (ProgramaPreventivo p : programaController.obtenerProgramas()) {
            for (FasePreventiva f : p.getFases()) {
                cmbFase.addItem(f);
            }
        }
        System.out.println("✅ Fases cargadas: " + cmbFase.getItemCount());
    }

    private void crearOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            LocalDate fecha = LocalDate.parse(txtFechaProgramada.getText());
            
            Equipo equipo = (Equipo) cmbEquipo.getSelectedItem();
            FasePreventiva fase = (FasePreventiva) cmbFase.getSelectedItem();
            Tecnico tecnico = (Tecnico) cmbTecnico.getSelectedItem();

            // ✅ Validar selecciones
            if (equipo == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un equipo.");
                return;
            }
            if (fase == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una fase.");
                return;
            }
            if (tecnico == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un técnico.");
                return;
            }

            String res = ordenController.crearOrdenPreventiva(
                    id, fecha, equipo, fase, tecnico
            );

            JOptionPane.showMessageDialog(this, res);
            listar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de datos: " + ex.getMessage());
            ex.printStackTrace();
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

            if (tecnico == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un técnico.");
                return;
            }

            String res = ordenController.completarOrden(
                    id, fecha, tiempo, diag, tecnico
            );

            JOptionPane.showMessageDialog(this, res);
            listar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al completar: " + ex.getMessage());
        }
    }

    private void cancelarOrden() {
        try {
            int id = Integer.parseInt(txtIdOrden.getText());
            String motivo = JOptionPane.showInputDialog("Indique motivo cancelación");
            
            if (motivo == null || motivo.isBlank()) {
                return;
            }
            
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
            
            if (mat == null || mat.isBlank()) {
                return;
            }
            
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

