package org.example;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class FallaFrame extends JFrame {

    private SistemaMantenimiento sistema;
    private JTextField txtId;
    private JTextField txtDesc;
    private JTextArea salida;

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

    // Panel registrar
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

    private void registrarFalla() {
        try {
            int id = Integer.parseInt(txtId.getText());

            System.out.println(">>> [FallaFrame] Registrando falla ID = " + id);

            String r = sistema.getFallaController().crearFalla(id, txtDesc.getText());

            // === Registro Auditoría ===
            sistema.getAuditoriaController().registrarEvento(
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

    // Panel eliminar
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

                // === Registro Auditoría ===
                sistema.getAuditoriaController().registrarEvento(
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

    // Panel listado
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



