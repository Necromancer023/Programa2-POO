package org.example;

import java.awt.*;
import java.time.LocalDate;
import javax.swing.*;

public class OrdenCorrectivaFrame extends JFrame {

    private JTextField txtId;
    private JTextArea txtAcciones;
    private JTextArea txtObservaciones;
    private JTextField txtCosto;
    private JTextField txtHoras;

    private OrdenCorrectivaController ordenController;

    public OrdenCorrectivaFrame() {
        ordenController = new OrdenCorrectivaController();

        setTitle("Finalizar Orden Correctiva");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel principal de formulario
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));

        form.add(new JLabel("ID Orden:"));
        txtId = new JTextField();
        form.add(txtId);

        form.add(new JLabel("Acciones realizadas:"));
        txtAcciones = new JTextArea();
        form.add(new JScrollPane(txtAcciones));

        form.add(new JLabel("Observaciones finales:"));
        txtObservaciones = new JTextArea();
        form.add(new JScrollPane(txtObservaciones));

        form.add(new JLabel("Costo total:"));
        txtCosto = new JTextField();
        form.add(txtCosto);

        form.add(new JLabel("Horas trabajadas:"));
        txtHoras = new JTextField();
        form.add(txtHoras);

        JButton btnFinalizar = new JButton("Finalizar Orden");
        btnFinalizar.addActionListener(e -> finalizarOrden());

        add(form, BorderLayout.CENTER);
        add(btnFinalizar, BorderLayout.SOUTH);
    }

    private void finalizarOrden() {

        try {
            int id = Integer.parseInt(txtId.getText());
            String acciones = txtAcciones.getText();
            String observ = txtObservaciones.getText();
            double costo = Double.parseDouble(txtCosto.getText());
            double horas = Double.parseDouble(txtHoras.getText());

            String resultado = ordenController.finalizarOrden(
                    id,
                    LocalDate.now(),
                    acciones,
                    observ,
                    costo,
                    horas
            );

            JOptionPane.showMessageDialog(this, resultado);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.");
        }
    }
}




