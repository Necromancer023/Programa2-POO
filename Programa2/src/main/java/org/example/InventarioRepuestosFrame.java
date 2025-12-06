package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class InventarioRepuestosFrame extends JFrame {

    private SistemaMantenimiento sistema;
    private JTextArea salida;

    // Campos para agregar repuesto
    private JTextField txtId, txtNombre, txtDesc, txtStockIni, txtStockMin, txtUbicacion, txtCosto;

    // Campos para movimientos
    private JTextField txtMovId, txtCantidad, txtMotivo, txtReferencia, txtNuevoStock;

    public InventarioRepuestosFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Inventario de Repuestos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        System.out.println(">>> [InventarioFrame] inicializando interfaz");

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Registrar repuesto", panelAgregar());
        tabs.add("Movimientos", panelMovimientos());
        tabs.add("Inventario", panelLista());

        add(tabs, BorderLayout.CENTER);
    }

    // ------------------------------------------------------------
    // PANEL: AGREGAR REPUESTO
    // ------------------------------------------------------------
    private JPanel panelAgregar() {
        JPanel p = new JPanel(new GridLayout(8, 2, 5, 5));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtDesc = new JTextField();
        txtStockIni = new JTextField();
        txtStockMin = new JTextField();
        txtUbicacion = new JTextField();
        txtCosto = new JTextField();

        p.add(new JLabel("ID:"));          p.add(txtId);
        p.add(new JLabel("Nombre:"));      p.add(txtNombre);
        p.add(new JLabel("Descripción:")); p.add(txtDesc);
        p.add(new JLabel("Stock inicial:")); p.add(txtStockIni);
        p.add(new JLabel("Stock mínimo:")); p.add(txtStockMin);
        p.add(new JLabel("Ubicación:"));   p.add(txtUbicacion);
        p.add(new JLabel("Costo unitario:")); p.add(txtCosto);

        JButton btnAgregar = new JButton("Registrar repuesto");
        btnAgregar.addActionListener(e -> agregarRepuesto());
        p.add(new JLabel());
        p.add(btnAgregar);

        return p;
    }

    private void agregarRepuesto() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int stockIni = Integer.parseInt(txtStockIni.getText());
            int stockMin = Integer.parseInt(txtStockMin.getText());
            double costo = Double.parseDouble(txtCosto.getText());

            System.out.println(">>> [InventarioFrame] Registrando repuesto ID " + id);

            String resultado = sistema.getInventarioRepuestosController().agregarRepuesto(
                    id,
                    txtNombre.getText(),
                    txtDesc.getText(),
                    stockIni,
                    stockMin,
                    txtUbicacion.getText(),
                    costo
            );

            JOptionPane.showMessageDialog(this, resultado);
            cargarInventario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
        }
    }

    // ------------------------------------------------------------
    // PANEL: MOVIMIENTOS
    // ------------------------------------------------------------
    private JPanel panelMovimientos() {
        JPanel p = new JPanel(new GridLayout(7, 2, 5, 5));

        txtMovId = new JTextField();
        txtCantidad = new JTextField();
        txtMotivo = new JTextField();
        txtReferencia = new JTextField();
        txtNuevoStock = new JTextField();

        JButton btnEntrada = new JButton("Registrar Entrada");
        btnEntrada.addActionListener(e -> registrarEntrada());

        JButton btnSalida = new JButton("Registrar Salida");
        btnSalida.addActionListener(e -> registrarSalida());

        JButton btnAjuste = new JButton("Registrar Ajuste");
        btnAjuste.addActionListener(e -> registrarAjuste());

        p.add(new JLabel("ID Repuesto:"));   p.add(txtMovId);
        p.add(new JLabel("Cantidad:"));      p.add(txtCantidad);
        p.add(new JLabel("Motivo:"));        p.add(txtMotivo);
        p.add(new JLabel("Referencia:"));    p.add(txtReferencia);

        p.add(btnEntrada); p.add(btnSalida);

        p.add(new JLabel("Nuevo stock (para ajuste):"));
        p.add(txtNuevoStock);

        p.add(new JLabel());
        p.add(btnAjuste);

        return p;
    }

    private void registrarEntrada() {
        try {
            int id = Integer.parseInt(txtMovId.getText());
            int cant = Integer.parseInt(txtCantidad.getText());

            System.out.println(">>> [InventarioFrame] Entrada ID " + id);

            String r = sistema.getInventarioRepuestosController().registrarEntrada(
                    id, cant, txtMotivo.getText(), txtReferencia.getText()
            );

            JOptionPane.showMessageDialog(this, r);
            cargarInventario();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
        }
    }

    private void registrarSalida() {
        try {
            int id = Integer.parseInt(txtMovId.getText());
            int cant = Integer.parseInt(txtCantidad.getText());

            System.out.println(">>> [InventarioFrame] Salida ID " + id);

            String r = sistema.getInventarioRepuestosController().registrarSalida(
                    id, cant, txtMotivo.getText(), txtReferencia.getText()
            );

            JOptionPane.showMessageDialog(this, r);
            cargarInventario();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
        }
    }

    private void registrarAjuste() {
        try {
            int id = Integer.parseInt(txtMovId.getText());
            int nuevoStock = Integer.parseInt(txtNuevoStock.getText());

            System.out.println(">>> [InventarioFrame] Ajuste ID " + id);

            String r = sistema.getInventarioRepuestosController().registrarAjuste(
                    id, nuevoStock, txtMotivo.getText()
            );

            JOptionPane.showMessageDialog(this, r);
            cargarInventario();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos.");
        }
    }

    // ------------------------------------------------------------
    // PANEL: LISTA INVENTARIO
    // ------------------------------------------------------------
    private JPanel panelLista() {
        JPanel p = new JPanel(new BorderLayout());

        salida = new JTextArea();
        salida.setEditable(false);

        JButton btnCargar = new JButton("Actualizar Inventario");
        btnCargar.addActionListener(e -> cargarInventario());

        p.add(btnCargar, BorderLayout.NORTH);
        p.add(new JScrollPane(salida), BorderLayout.CENTER);

        return p;
    }

    private void cargarInventario() {
        salida.setText("");

        System.out.println(">>> [InventarioFrame] Cargando inventario...");

        List<Repuesto> lista = sistema.getInventarioRepuestosController().obtenerRepuestos();

        if (lista.isEmpty()) {
            salida.append("No hay repuestos registrados.");
            return;
        }

        for (Repuesto r : lista) {
            salida.append(r.toString() + "\n");
        }
    }
}


