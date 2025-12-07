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

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                construirPanelArbol(),
                construirPanelDetalle()
        );

        split.setDividerLocation(300);
        add(split);

        actualizarArbol();
    }

    // Panel del árbol
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

    // Panel derecho de detalles
    private JPanel construirPanelDetalle() {
        JPanel panel = new JPanel(new BorderLayout());

        detalleArea = new JTextArea();
        detalleArea.setEditable(false);

        panel.add(new JScrollPane(detalleArea), BorderLayout.CENTER);

        return panel;
    }

    // Construcción del árbol
    private void actualizarArbol() {
        System.out.println(">>> [ConsultaMantenimiento] Cargando árbol...");

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Equipos");

        for (Equipo eq : sistema.getEquipoController().obtenerEquipos()) {

            DefaultMutableTreeNode nodoEquipo =
                    new DefaultMutableTreeNode("EQUIPO: " + eq.getId() + " - " + eq.getDescripcion());

            for (Equipo comp : eq.getComponentes()) {
                nodoEquipo.add(new DefaultMutableTreeNode(
                        "COMPONENTE: " + comp.getId() + " - " + comp.getDescripcion()
                ));
            }

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

    // Mostrar información según el nodo seleccionado
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

    // --- DETALLE EQUIPO ---
    private void mostrarDetalleEquipo(String nodo) {
        try {
            String idStr = nodo.split(":")[1].split("-")[0].trim();
            int id = Integer.parseInt(idStr);

            Equipo eq = sistema.getEquipoController().buscarEquipo(id);

            detalleArea.setText("");

            if (eq != null) {
                detalleArea.append("DETALLES DEL EQUIPO\n");
                detalleArea.append("------------------------\n");
                detalleArea.append(eq.toString() + "\n");
                detalleArea.append("Componentes: " + eq.getComponentes().size() + "\n");

                if (eq.getProgramaPreventivo() != null) {
                    ProgramaPreventivo prog = eq.getProgramaPreventivo();
                    detalleArea.append("\n✔ Programa Preventivo Asociado:\n");
                    detalleArea.append("Nombre: " + prog.getNombrePrograma() + "\n");
                    detalleArea.append("Responsable: " + prog.getResponsable() + "\n");
                    detalleArea.append("Fases: " + prog.getFases().size() + "\n");
                } else {
                    detalleArea.append("\n⚠ Sin programa preventivo.\n");
                }

                detalleArea.append("\nÚltimas órdenes correctivas:\n");

                int count = 0;
                for (OrdenCorrectiva oc : sistema.getOrdenCorrectivaController().obtenerOrdenes()) {
                    if (oc.getEquipoAsociado().getId() == id) {
                        if (count < 5) {
                            detalleArea.append("• OC-" + oc.getIdOrdenCorrectiva()
                                    + " [" + oc.getEstado() + "]\n");
                            count++;
                        }
                    }
                }

                if (count == 0) detalleArea.append("Sin correctivas registradas.");
            }

        } catch (Exception e) {
            detalleArea.setText("Error al mostrar datos de equipo.");
        }
    }

    // --- DETALLE CORRECTIVA ---
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

    // --- DETALLE FASE CON TAREAS, RECURSOS Y MÉTRICAS ---
    private void mostrarDetalleFase(String nodo) {
        try {
            String numStr = nodo.split(" ")[1].replace(":", "");
            int num = Integer.parseInt(numStr);

            detalleArea.setText("");  
            detalleArea.append("FASE " + num + "\n");
            detalleArea.append("----------------------\n");

            TreePath path = arbol.getSelectionPath();
            if (path == null) return;

            String equipoNodo = path.getPathComponent(1).toString();
            String idEquipoStr = equipoNodo.split(":")[1].split("-")[0].trim();
            int idEquipo = Integer.parseInt(idEquipoStr);

            Equipo eq = sistema.getEquipoController().buscarEquipo(idEquipo);

            if (eq == null || eq.getProgramaPreventivo() == null) {
                detalleArea.append(" No hay detalles del programa preventivo.");
                return;
            }

            ProgramaPreventivo prog = eq.getProgramaPreventivo();

            FasePreventiva fase = prog.obtenerFase(num);

            if (fase == null) {
                detalleArea.append("No se encontró la fase.");
                return;
            }

            detalleArea.append("Descripción: " + fase.getDescripcion() + "\n");
            detalleArea.append("Frecuencia: " + fase.getFrecuencia() + "\n");
            detalleArea.append("Tiempo estimado (h): " + fase.getTiempoEstimadoHoras() + "\n");
            detalleArea.append("Observaciones: " + fase.getObservaciones() + "\n");
            detalleArea.append("Ciclos: " + fase.getCantidadCiclos() + "\n");

            detalleArea.append("\n--- TAREAS ---\n");
            if (fase.getTareas().isEmpty()) {
                detalleArea.append("No hay tareas registradas.\n");
            } else {
                for (String t : fase.getTareas()) detalleArea.append("• " + t + "\n");
            }

            detalleArea.append("\n--- RECURSOS ---\n");
            if (fase.getRecursosNecesarios().isEmpty()) {
                detalleArea.append("No hay recursos asignados.\n");
            } else {
                for (String r : fase.getRecursosNecesarios()) detalleArea.append("• " + r + "\n");
            }

        } catch (Exception e) {
            detalleArea.setText("Error mostrando datos de fase.");
        }
    }
}


