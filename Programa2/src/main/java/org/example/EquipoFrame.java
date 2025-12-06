package org.example;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

public class EquipoFrame extends JFrame {

    private JTextField txtId, txtDescripcion, txtTipo, txtUbicacion, txtFabricante,
            txtSerie, txtModelo, txtDimensiones, txtPeso, txtMesesVida, txtCosto;

    private JComboBox<Equipo.EstadoEquipo> cmbEstado;

    private EquipoController equipoController;

    private JTextArea txtLista;

    public EquipoFrame() {
        equipoController = new EquipoController();

        setTitle("Gestión de Equipos");
        setSize(650, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(12, 2, 5, 5));

        txtId = addField(form, "ID Equipo:");
        txtDescripcion = addField(form, "Descripción:");
        txtTipo = addField(form, "Tipo:");
        txtUbicacion = addField(form, "Ubicación:");
        txtFabricante = addField(form, "Fabricante:");
        txtSerie = addField(form, "Serie:");
        txtModelo = addField(form, "Modelo:");
        txtDimensiones = addField(form, "Dimensiones:");
        txtPeso = addField(form, "Peso (kg):");
        txtMesesVida = addField(form, "Vida útil (meses):");
        txtCosto = addField(form, "Costo inicial:");

        form.add(new JLabel("Estado:"));
        cmbEstado = new JComboBox<>(Equipo.EstadoEquipo.values());
        form.add(cmbEstado);

        JButton btnRegistrar = new JButton("Registrar Equipo");
        btnRegistrar.addActionListener(e -> registrar());

        JButton btnEliminar = new JButton("Eliminar Equipo");
        btnEliminar.addActionListener(e -> eliminar());

        JButton btnActualizarUbicacion = new JButton("Cambiar ubicación");
        btnActualizarUbicacion.addActionListener(e -> actualizarUbicacion());

        JButton btnActualizarFabricante = new JButton("Cambiar fabricante");
        btnActualizarFabricante.addActionListener(e -> actualizarFabricante());

        JButton btnActualizarEstado = new JButton("Cambiar estado");
        btnActualizarEstado.addActionListener(e -> actualizarEstado());

        JPanel acciones = new JPanel(new GridLayout(5, 1, 5, 5));
        acciones.add(btnRegistrar);
        acciones.add(btnEliminar);
        acciones.add(btnActualizarUbicacion);
        acciones.add(btnActualizarFabricante);
        acciones.add(btnActualizarEstado);

        txtLista = new JTextArea();
        txtLista.setEditable(false);
        txtLista.setFont(new Font("monospaced", Font.PLAIN, 12));

        add(new JScrollPane(txtLista), BorderLayout.EAST);
        add(form, BorderLayout.CENTER);
        add(acciones, BorderLayout.SOUTH);

        listar();
    }

    private JTextField addField(JPanel panel, String label) {
        panel.add(new JLabel(label));
        JTextField txt = new JTextField();
        panel.add(txt);
        return txt;
    }

    private void registrar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String descripcion = txtDescripcion.getText();
            String tipo = txtTipo.getText();
            String ubicacion = txtUbicacion.getText();
            String fabricante = txtFabricante.getText();
            String serie = txtSerie.getText();
            String modelo = txtModelo.getText();
            String dimensiones = txtDimensiones.getText();
            double peso = Double.parseDouble(txtPeso.getText());
            int vida = Integer.parseInt(txtMesesVida.getText());
            double costo = Double.parseDouble(txtCosto.getText());
            Equipo.EstadoEquipo estado = (Equipo.EstadoEquipo) cmbEstado.getSelectedItem();

            LocalDate hoy = LocalDate.now();

            String res = equipoController.crearEquipo(
                    id, descripcion, tipo, ubicacion, fabricante, serie,
                    hoy, hoy.plusDays(1), vida, costo, estado,
                    modelo, dimensiones, peso
            );

            JOptionPane.showMessageDialog(this, res);
            listar();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
        }
    }

    private void listar() {
        txtLista.setText("");

        for (Equipo e : equipoController.obtenerEquipos()) {
            txtLista.append(
                    "ID: " + e.getId() +
                    " | " + e.getDescripcion() +
                    " | " + e.getUbicacion() +
                    " | " + e.getEstado() + "\n"
            );
        }
    }

    private void eliminar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String res = equipoController.eliminarEquipo(id);
            JOptionPane.showMessageDialog(this, res);
            listar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void actualizarUbicacion() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String ubicacion = JOptionPane.showInputDialog("Nueva ubicación:");
            String res = equipoController.actualizarUbicacion(id, ubicacion);
            JOptionPane.showMessageDialog(this, res);
            listar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }

    private void actualizarFabricante() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String fabricante = JOptionPane.showInputDialog("Nuevo fabricante:");
            String res = equipoController.actualizarFabricante(id, fabricante);
            JOptionPane.showMessageDialog(this, res);
            listar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }

    private void actualizarEstado() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Equipo.EstadoEquipo estado = (Equipo.EstadoEquipo) JOptionPane.showInputDialog(
                    this,
                    "Seleccione estado",
                    "Estado",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    Equipo.EstadoEquipo.values(),
                    Equipo.EstadoEquipo.OPERATIVO
            );
            String res = equipoController.actualizarEstado(id, estado);
            JOptionPane.showMessageDialog(this, res);
            listar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error.");
        }
    }
}

