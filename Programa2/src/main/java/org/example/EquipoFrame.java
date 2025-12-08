package org.example;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana gráfica para la gestión de equipos.
 * Permite registrar nuevos equipos, visualizar los existentes
 * y eliminar registros seleccionados desde una tabla.
 */
public class EquipoFrame extends JFrame {

    /** Controlador encargado de la lógica de negocio para la gestión de equipos. */
    private EquipoController equipoController;

    /** Campos de entrada para capturar atributos del equipo. */
    private JTextField txtId, txtDesc, txtTipo, txtUbicacion, txtFabricante, txtSerie,
            txtModelo, txtDimensiones, txtPeso, txtCosto, txtVida;

    /** Selector de estado del equipo mediante enumeración. */
    private JComboBox<Equipo.EstadoEquipo> comboEstado;

    /** Tabla de visualización de equipos registrados. */
    private JTable tabla;

    /** Modelo utilizado para alimentar la tabla con datos dinámicos. */
    private DefaultTableModel modeloTabla;

    /**
     * Constructor por defecto que instancia el frame usando la instancia
     * global del sistema de mantenimiento.
     */
    public EquipoFrame() {
        this(SistemaMantenimiento.getInstance());
    }

    /**
     * Crea la interfaz de administración de equipos vinculada a un gestor del sistema.
     *
     * @param sistema instancia principal que provee acceso a controladores
     */
    public EquipoFrame(SistemaMantenimiento sistema) {

        equipoController = sistema.getEquipoController();

        setTitle("Gestión de Equipos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---- Panel de entrada de datos ----
        JPanel panel = new JPanel(new GridLayout(12, 2, 5, 5));

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

        panel.add(new JLabel("Estado del equipo:"));
        comboEstado = new JComboBox<>(Equipo.EstadoEquipo.values());
        panel.add(comboEstado);

        add(panel, BorderLayout.NORTH);

        // ---- Botón para registrar equipo ----
        JButton btnRegistrar = new JButton("Registrar Equipo");
        btnRegistrar.addActionListener(e -> registrar());
        add(btnRegistrar, BorderLayout.CENTER);

        // ---- Modelo de tabla ----
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID","Descripción","Tipo","Ubicación","Fabricante","Estado"}, 0
        );

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.SOUTH);

        // ---- Panel inferior de acciones ----
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

    /**
     * Registra un nuevo equipo leyendo los datos del formulario,
     * ejecutando las validaciones desde el controlador y mostrando
     * el resultado en pantalla.
     */
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

            // Obtener estado seleccionado
            Equipo.EstadoEquipo estado = (Equipo.EstadoEquipo) comboEstado.getSelectedItem();

            String result = equipoController.crearEquipo(
                    id, desc, tipo, ubicacion, fabricante, serie,
                    hoy, hoy, vidaUtil, costo,
                    estado,
                    modelo, dim, peso
            );

            JOptionPane.showMessageDialog(this, result);

            limpiarCampos();
            cargarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Verifique que ID, peso, costo y vida útil sean números válidos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Refresca los datos mostrados en la tabla consultando nuevamente
     * la lista de equipos desde el controlador.
     */
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Equipo> lista = equipoController.obtenerEquipos();

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

    /**
     * Elimina el equipo seleccionado en la tabla mediante confirmación
     * del usuario, y luego actualiza la lista de visualización.
     */
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

    /**
     * Limpia el formulario regresando los campos a sus valores iniciales.
     */
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
        comboEstado.setSelectedIndex(0);
    }
}





