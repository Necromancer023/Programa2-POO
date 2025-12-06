package org.example;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class ConsultaMantenimientoFrame extends JFrame {

    private SistemaMantenimiento sistema;

    private JTree arbol;
    private JTextArea detalleArea;

    public ConsultaMantenimientoFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Consulta Integral de Mantenimiento");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        System.out.println(">>> [ConsultaMantenimiento] Inicializando UI...");

        // Crear estructura UI dividida
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                construirPanelArbol(),
                construirPanelDetalle()
        );

        split.setDividerLocation(300);
        add(split);

        actualizarArbol();
    }

    // Panel de árbol
    private JPanel construirPanelArbol() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Equipos");

        arbol = new JTree(root);
        arbol.setRootVisible(true);

        arbol.addTreeSelectionListener(e -> mostrarDetalleNodo());

        panel.add(new JScrollPane(arbol), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar Árbol");
        btnActualizar.addActionListener(e -> actualizarArbol());

        panel.add(btnActualizar, BorderLayout.SOUTH);

        return panel;
    }

    // Panel derecho con detalles
    private JPanel construirPanelDetalle() {
        JPanel panel = new JPanel(new BorderLayout());

        detalleArea = new JTextArea();
        detalleArea.setEditable(false);

        panel.add(new JScrollPane(detalleArea), BorderLayout.CENTER);

        return panel;
    }

    // Construye el árbol desde el sistema
    private void actualizarArbol() {
        System.out.println(">>> [ConsultaMantenimiento] Cargando árbol...");

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Equipos");

        for (Equipo eq : sistema.getEquipoController().obtenerEquipos()) {

            DefaultMutableTreeNode nodoEquipo =
                    new DefaultMutableTreeNode("EQUIPO: " + eq.getId() + " - " + eq.getDescripcion());

            // Componentes directos
            for (Equipo comp : eq.getComponentes()) {
                nodoEquipo.add(new DefaultMutableTreeNode(
                        "COMPONENTE: " + comp.getId() + " - " + comp.getDescripcion()
                ));
            }

            // Programa preventivo si existe
            if (eq.getProgramaPreventivo() != null) {

                ProgramaPreventivo prog = eq.getProgramaPreventivo();

                DefaultMutableTreeNode nodoProg =
                        new DefaultMutableTreeNode("PROGRAMA: " + prog.getNombrePrograma());

                for (FasePreventiva f : prog.getFases()) {
                    DefaultMutableTreeNode nodoFase =
                            new DefaultMutableTreeNode("FASE " + f.getNumeroFase() + ": " + f.getDescripcion());

                    nodoProg.add(nodoFase);
                }

                nodoEquipo.add(nodoProg);
            }

            // Órdenes correctivas asociadas
            DefaultMutableTreeNode nodoCorrectivas = new DefaultMutableTreeNode("Correctivas");

            for (OrdenCorrectiva oc : sistema.getOrdenCorrectivaController().obtenerOrdenes()) {
                if (oc.getEquipoAsociado().getId() == eq.getId()) {
                    nodoCorrectivas.add(new DefaultMutableTreeNode(
                            "OC-" + oc.getIdOrdenCorrectiva() + " " + oc.getEstado()
                    ));
                }
            }
            nodoEquipo.add(nodoCorrectivas);

            root.add(nodoEquipo);
        }

        arbol.setModel(new DefaultTreeModel(root));
    }

    // Mostrar detalles según nodo
    private void mostrarDetalleNodo() {
        TreePath path = arbol.getSelectionPath();
        if (path == null) return;

        String nodo = path.getLastPathComponent().toString();
        detalleArea.setText("");

        System.out.println(">>> [ConsultaMantenimiento] Nodo seleccionado: " + nodo);

        if (nodo.startsWith("EQUIPO:")) {
            mostrarDetalleEquipo(nodo);
        } else if (nodo.startsWith("OC-")) {
            mostrarDetalleCorrectiva(nodo);
        } else if (nodo.startsWith("FASE")) {
            mostrarDetalleFase(nodo);
        } else {
            detalleArea.setText("Nodo sin detalles específicos.");
        }
    }

    private void mostrarDetalleEquipo(String nodo) {
        try {
            String idStr = nodo.split(":")[1].split("-")[0].trim();
            int id = Integer.parseInt(idStr);

            Equipo eq = sistema.getEquipoController().buscarEquipo(id);

            if (eq != null) {
                detalleArea.append("DETALLES DEL EQUIPO\n");
                detalleArea.append("------------------------\n");
                detalleArea.append(eq.toString() + "\n");
                detalleArea.append("Componentes: " + eq.getComponentes().size() + "\n");
            }

        } catch (Exception e) {
            detalleArea.setText("Error al mostrar datos de equipo.");
        }
    }

    private void mostrarDetalleCorrectiva(String nodo) {
        try {
            String idStr = nodo.split("-")[1].split(" ")[0];
            int id = Integer.parseInt(idStr);

            for (OrdenCorrectiva oc : sistema.getOrdenCorrectivaController().obtenerOrdenes()) {
                if (oc.getIdOrdenCorrectiva() == id) {
                    detalleArea.append("ORDEN CORRECTIVA\n");
                    detalleArea.append("-------------------\n");
                    detalleArea.append(oc.toString());
                }
            }

        } catch (Exception e) {
            detalleArea.setText("Error al mostrar los detalles.");
        }
    }

    private void mostrarDetalleFase(String nodo) {
        try {
            String numStr = nodo.split(" ")[1].replace(":", "");
            int num = Integer.parseInt(numStr);

            detalleArea.append("FASE " + num + "\n----------------------\n");
            detalleArea.append("Para ver más detalles consulte el módulo Programa Preventivo.");

        } catch (Exception e) {
            detalleArea.setText("Error mostrando datos de fase.");
        }
    }
}

