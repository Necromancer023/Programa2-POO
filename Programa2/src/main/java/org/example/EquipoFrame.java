package org.example;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EquipoFrame extends JFrame {

    private EquipoController equipoController;
    private JTextField txtId, txtDescripcion, txtTipo, txtUbicacion, txtFabricante,
            txtSerie, txtModelo, txtDimensiones, txtPeso;

    private JTable tabla;
    private DefaultTableModel modelo;

    public EquipoFrame() {
        this(SistemaMantenimiento.getInstance());
    }

    public EquipoFrame(SistemaMantenimiento sistema) {

        equipoController = sistema.getEquipoController();

        setTitle("Gestión de Equipos");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(9,2));

        txtId = addField(form, "ID:");
        txtDescripcion = addField(form, "Descripción:");
        txtTipo = addField(form, "Tipo:");
        txtUbicacion = addField(form, "Ubicación:");
        txtFabricante = addField(form, "Fabricante:");
        txtSerie = addField(form, "Serie:");
        txtModelo = addField(form, "Modelo:");
        txtDimensiones = addField(form, "Dimensiones:");
        txtPeso = addField(form, "Peso:");

        add(form, BorderLayout.NORTH);

        JButton btn = new JButton("Registrar Equipo");
        btn.addActionListener(e -> registrar());
        add(btn, BorderLayout.CENTER);

        modelo = new DefaultTableModel(new Object[]{
                "ID","Descripción","Tipo","Ubicación","Estado"
        }, 0);

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.SOUTH);

        cargarTabla();
    }

    private JTextField addField(JPanel panel, String label){
        panel.add(new JLabel(label));
        JTextField field = new JTextField();
        panel.add(field);
        return field;
    }

    private void registrar() {
        try {
            int id = Integer.parseInt(txtId.getText());

            String msg = equipoController.crearEquipo(
                    id,
                    txtDescripcion.getText(),
                    txtTipo.getText(),
                    txtUbicacion.getText(),
                    txtFabricante.getText(),
                    txtSerie.getText(),
                    LocalDate.now(),
                    LocalDate.now(),
                    12,
                    1000,
                    Equipo.EstadoEquipo.OPERATIVO,
                    txtModelo.getText(),
                    txtDimensiones.getText(),
                    Double.parseDouble(txtPeso.getText())
            );

            JOptionPane.showMessageDialog(this, msg);
            cargarTabla();

        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error en datos");
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Equipo> lista = equipoController.obtenerEquipos();
        lista.forEach(eq -> modelo.addRow(new Object[]{
                eq.getId(), eq.getDescripcion(), eq.getTipo(),
                eq.getUbicacion(), eq.getEstado()
        }));
    }
}


