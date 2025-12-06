package org.example;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OrdenCorrectivaFrame extends JFrame {

    private OrdenCorrectivaController ordenController;
    private EquipoController equipoController;

    private JTextField txtId, txtEquipoId, txtDescripcion, txtCausa, txtDiagnostico, txtCosto, txtHoras;
    private JComboBox<OrdenCorrectiva.Prioridad> comboPrioridad;

    private JTable tabla;
    private DefaultTableModel modelo;

    public OrdenCorrectivaFrame() {
        this(SistemaMantenimiento.getInstance());
    }

    public OrdenCorrectivaFrame(SistemaMantenimiento sistema){

        ordenController = sistema.getOrdenCorrectivaController();
        equipoController = sistema.getEquipoController();

        setTitle("Órdenes Correctivas");
        setSize(700, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(8,2));

        txtId = addField(form,"ID Orden:");
        txtEquipoId = addField(form,"ID Equipo:");
        txtDescripcion = addField(form,"Descripción falla:");
        txtCausa = addField(form,"Causa:");
        txtDiagnostico = addField(form,"Diagnóstico inicial:");

        form.add(new JLabel("Prioridad:"));
        comboPrioridad = new JComboBox<>(OrdenCorrectiva.Prioridad.values());
        form.add(comboPrioridad);

        txtCosto = addField(form,"Costo final:");
        txtHoras = addField(form,"Horas trabajo:");

        add(form, BorderLayout.NORTH);

        JButton btn = new JButton("Registrar Orden");
        btn.addActionListener(e -> registrar());
        add(btn, BorderLayout.CENTER);

        modelo = new DefaultTableModel(new Object[]{
                "ID","Equipo","Estado","Prioridad","Costo"
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
            int idOrden = Integer.parseInt(txtId.getText());
            int idEq = Integer.parseInt(txtEquipoId.getText());

            Equipo eq = equipoController.buscarEquipo(idEq);
            if (eq == null) {
                JOptionPane.showMessageDialog(this, "Equipo no existe");
                return;
            }

            String mensaje = ordenController.crearOrdenCorrectiva(
                    idOrden,
                    LocalDate.now(), // ← Aquí va la fecha
                    eq,
                    txtDescripcion.getText(),
                    txtCausa.getText(),
                    (OrdenCorrectiva.Prioridad) comboPrioridad.getSelectedItem(),
                    txtDiagnostico.getText()
            );

            JOptionPane.showMessageDialog(this, mensaje);
            cargarTabla();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en datos");
        }
    }



    private void cargarTabla(){
        modelo.setRowCount(0);
        List<OrdenCorrectiva> lista = ordenController.obtenerOrdenes();

        lista.forEach(o -> modelo.addRow(new Object[]{
                o.getIdOrdenCorrectiva(),
                o.getEquipoAsociado().getDescripcion(),
                o.getEstado(),
                o.getPrioridad(),
                o.getCostoReparacion()
        }));
    }
}





