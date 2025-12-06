package org.example;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EquipoFrame extends JFrame {

    private EquipoController equipoController;

    private JTextField txtId, txtDesc, txtTipo, txtUbicacion, txtFabricante, txtSerie,
            txtModelo, txtDimensiones, txtPeso, txtCosto, txtVida;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // ========== CONSTRUCTOR CORREGIDO ==========
    public EquipoFrame() {
        this(SistemaMantenimiento.getInstance());
    }

    public EquipoFrame(SistemaMantenimiento sistema) {

        // ✅ Usar el controlador del sistema compartido
        equipoController = sistema.getEquipoController();

        setTitle("Gestión de Equipos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(11, 2, 5, 5));

        panel.add(new JLabel("ID:"));
        txtId = new JTextField(); panel.add(txtId);

        panel.add(new JLabel("Descripción:"));
        txtDesc = new JTextField(); panel.add(txtDesc);

        panel.add(new JLabel("Tipo:"));
        txtTipo = new JTextField(); panel.add(txtTipo);

        panel.add(new JLabel("Ubicación:"));
        txtUbicacion = new JTextField(); panel.add(txtUbicacion);

        panel.add(new JLabel("Fabricante:"));
        txtFabricante = new JTextField(); panel.add(txtFabricante);

        panel.add(new JLabel("Serie:"));
        txtSerie = new JTextField(); panel.add(txtSerie);

        panel.add(new JLabel("Modelo:"));
        txtModelo = new JTextField(); panel.add(txtModelo);

        panel.add(new JLabel("Dimensiones:"));
        txtDimensiones = new JTextField(); panel.add(txtDimensiones);

        panel.add(new JLabel("Peso (kg):"));
        txtPeso = new JTextField(); panel.add(txtPeso);

        panel.add(new JLabel("Costo inicial:"));
        txtCosto = new JTextField(); panel.add(txtCosto);

        panel.add(new JLabel("Vida útil (meses):"));
        txtVida = new JTextField("12"); panel.add(txtVida);

        add(panel, BorderLayout.NORTH);

        JButton btnRegistrar = new JButton("Registrar Equipo");
        btnRegistrar.addActionListener(e -> registrar());
        add(btnRegistrar, BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel(
                new Object[]{"ID","Descripción","Tipo","Ubicación","Fabricante","Estado"}, 0
        );

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.SOUTH);

        JPanel botPanel = new JPanel();
        JButton btnEliminar = new JButton("Eliminar seleccionado");
        btnEliminar.addActionListener(e -> eliminar());

        JButton btnUpdate = new JButton("Actualizar lista");
        btnUpdate.addActionListener(e -> cargarTabla());

        botPanel.add(btnEliminar);
        botPanel.add(btnUpdate);

        add(botPanel, BorderLayout.AFTER_LAST_LINE);

        cargarTabla();
    }

    private void registrar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String desc = txtDesc.getText();
            String tipo = txtTipo.getText();
            String ubicacion = txtUbicacion.getText();
            String fabricante = txtFabricante.getText();
            String serie = txtSerie.getText();
            String modelo = txtModelo.getText();
            String dim = txtDimensiones.getText();
            double peso = Double.parseDouble(txtPeso.getText());
            double costo = Double.parseDouble(txtCosto.getText());
            int vidaUtil = Integer.parseInt(txtVida.getText());

            LocalDate hoy = LocalDate.now();

            String result = equipoController.crearEquipo(
                    id, desc, tipo, ubicacion, fabricante, serie,
                    hoy, hoy, vidaUtil, costo,
                    Equipo.EstadoEquipo.OPERATIVO,
                    modelo, dim, peso
            );

            JOptionPane.showMessageDialog(this, result);
            
            // ✅ Limpiar campos después de registrar
            limpiarCampos();
            cargarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Verifique que ID, peso, costo y vida útil sean números válidos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Equipo> lista = equipoController.obtenerEquipos();
        
        System.out.println("📋 Cargando " + lista.size() + " equipos"); // Debug
        
        for (Equipo e : lista) {
            modeloTabla.addRow(new Object[]{
                    e.getId(), 
                    e.getDescripcion(), 
                    e.getTipo(), 
                    e.getUbicacion(), 
                    e.getFabricante(),
                    e.getEstado()
            });
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un equipo.");
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar el equipo con ID " + id + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String resultado = equipoController.eliminarEquipo(id);
            JOptionPane.showMessageDialog(this, resultado);
            cargarTabla();
        }
    }
    
    private void limpiarCampos() {
        txtId.setText("");
        txtDesc.setText("");
        txtTipo.setText("");
        txtUbicacion.setText("");
        txtFabricante.setText("");
        txtSerie.setText("");
        txtModelo.setText("");
        txtDimensiones.setText("");
        txtPeso.setText("");
        txtCosto.setText("");
        txtVida.setText("12");
    }
}



