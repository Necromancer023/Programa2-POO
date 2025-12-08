package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * Ventana gráfica para la gestión del catálogo de fallas dentro del sistema.
 * Permite registrar nuevas fallas, eliminarlas y visualizar el listado existente.
 * Además, registra movimientos en la auditoría del sistema cuando se realizan acciones.
 */
public class FallaFrame extends JFrame {

    /** Referencia al sistema principal para acceso a controladores. */
    private SistemaMantenimiento sistema;

    /** Campo de texto para capturar el ID de la falla. */
    private JTextField txtId;

    /** Campo de texto para ingresar la descripción de la falla. */
    private JTextField txtDesc;

    /** Área de texto utilizada para mostrar resultados del listado de fallas. */
    private JTextArea salida;

    /**
     * Construye la ventana con acceso a los servicios del sistema.
     *
     * @param sistema instancia central del sistema de mantenimiento
     */
    public FallaFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Catálogo de Fallas");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        System.out.println(">>> [FallaFrame] UI Inicializado");

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Registrar Falla", panelRegistrar());
        tabs.add("Eliminar Falla", panelEliminar());
        tabs.add("Ver Lista", panelLista());

        add(tabs, BorderLayout.CENTER);
    }

    /**
     * Construye el panel de registro de fallas.
     *
     * @return panel de captura para alta de fallas
     */
    private JPanel panelRegistrar() {
        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));

        txtId = new JTextField();
        txtDesc = new JTextField();

        p.add(new JLabel("ID Falla:"));
        p.add(txtId);

        p.add(new JLabel("Descripción:"));
        p.add(txtDesc);

        JButton btn = new JButton("Registrar");
        btn.addActionListener(e -> registrarFalla());

        p.add(new JLabel());
        p.add(btn);

        return p;
    }

    /**
     * Acción para registrar una nueva falla.
     * Obtiene los datos ingresados, los valida, delega al controlador y registra auditoría.
     */
    private void registrarFalla() {
        try {
            int id = Integer.parseInt(txtId.getText());

            System.out.println(">>> [FallaFrame] Registrando falla ID = " + id);

            String r = sistema.getFallaController().crearFalla(id, txtDesc.getText());

            // Registro en auditoría del sistema
            sistema.getAuditoriaController().registrarMovimiento(
                    sistema.getUsuarioActual().getNombreCompleto(),
                    "FALLA",
                    "ALTA",
                    "Se registró la falla ID=" + id
            );

            JOptionPane.showMessageDialog(this, r);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos");
        }
    }

    /**
     * Construye el panel para eliminar fallas del sistema.
     *
     * @return panel con campo de ID y botón de eliminación
     */
    private JPanel panelEliminar() {
        JPanel p = new JPanel(new GridLayout(2, 2, 5, 5));

        JTextField txtDel = new JTextField();

        p.add(new JLabel("ID a eliminar:"));
        p.add(txtDel);

        JButton btn = new JButton("Eliminar");
        btn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtDel.getText());

                System.out.println(">>> [FallaFrame] Eliminando falla " + id);

                String r = sistema.getFallaController().eliminarFalla(id);

                // Registro en auditoría del sistema
                sistema.getAuditoriaController().registrarMovimiento(
                        sistema.getUsuarioActual().getNombreCompleto(),
                        "FALLA",
                        "BAJA",
                        "Se eliminó la falla ID=" + id
                );

                JOptionPane.showMessageDialog(this, r);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido");
            }
        });

        p.add(new JLabel());
        p.add(btn);

        return p;
    }

    /**
     * Construye el panel que muestra el listado total de fallas.
     *
     * @return panel con botón para actualizar y área de salida
     */
    private JPanel panelLista() {
        JPanel p = new JPanel(new BorderLayout());

        salida = new JTextArea();
        salida.setEditable(false);

        JButton btn = new JButton("Cargar listado");
        btn.addActionListener(e -> cargarFallas());

        p.add(btn, BorderLayout.NORTH);
        p.add(new JScrollPane(salida), BorderLayout.CENTER);

        return p;
    }

    /**
     * Carga la lista completa de fallas registradas y la muestra en el área de salida.
     */
    private void cargarFallas() {
        salida.setText("");
        System.out.println(">>> [FallaFrame] Listando fallas...");

        List<Falla> lista = sistema.getFallaController().obtenerFallas();

        if (lista.isEmpty()) {
            salida.append("No hay fallas registradas.");
            return;
        }

        for (Falla f : lista) {
            salida.append(f.toString() + "\n");
        }
    }
}




