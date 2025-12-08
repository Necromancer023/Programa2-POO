package org.example;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * Ventana gráfica para la consulta integral de mantenimiento.
 * Permite visualizar equipos, programas preventivos, fases
 * y órdenes correctivas mediante un árbol jerárquico con
 * un área de detalle contextual.
 */
public class ConsultaMantenimientoFrame extends JFrame {

    /** Referencia al sistema central de mantenimiento para consultas. */
    private SistemaMantenimiento sistema;

    /** Árbol que representa elementos estructurados: equipos, fases, correctivas. */
    private JTree arbol;

    /** Área de texto utilizada para mostrar información detallada sobre el nodo seleccionado. */
    private JTextArea detalleArea;

    /**
     * Construye la ventana de consulta de mantenimiento e inicializa sus componentes.
     * 
     * @param sistema instancia principal del sistema de mantenimiento para extraer datos
     */
    public ConsultaMantenimientoFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        setTitle("Consulta Integral de Mantenimiento");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        System.out.println(">>> [ConsultaMantenimiento] Inicializando UI...");

        // División de ventana en árbol y detalles
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                construirPanelArbol(),
                construirPanelDetalle()
        );

        split.setDividerLocation(300);
        add(split);

        actualizarArbol();
    }

    /**
     * Construye el panel que contiene el árbol de navegación.
     *
     * @return panel configurado con árbol y botón de actualización
     */
    private JPanel construirPanelArbol() {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Equipos");

        arbol = new JTree(root);
        arbol.setRootVisible(true);

        // Evento para mostrar detalles al seleccionar un nodo
        arbol.addTreeSelectionListener(e -> mostrarDetalleNodo());

        panel.add(new JScrollPane(arbol), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar Árbol");
        btnActualizar.addActionListener(e -> actualizarArbol());

        panel.add(btnActualizar, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Construye el panel que contiene el área de detalle informativo.
     *
     * @return panel con área de texto desplazable para información contextual
     */
    private JPanel construirPanelDetalle() {
        JPanel panel = new JPanel(new BorderLayout());

        detalleArea = new JTextArea();
        detalleArea.setEditable(false);

        panel.add(new JScrollPane(detalleArea), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Reconstruye el árbol de navegación utilizando datos del sistema,
     * agregando nodos de equipos, programas preventivos, fases
     * y órdenes correctivas asociadas.
     */
    private void actualizarArbol() {
        System.out.println(">>> [ConsultaMantenimiento] Cargando árbol...");

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Equipos");

        for (Equipo eq : sistema.getEquipoController().obtenerEquipos()) {

            DefaultMutableTreeNode nodoEquipo =
                    new DefaultMutableTreeNode("EQUIPO: " + eq.getId() + " - " + eq.getDescripcion());

            // Componentes asociados
            for (Equipo comp : eq.getComponentes()) {
                nodoEquipo.add(new DefaultMutableTreeNode(
                        "COMPONENTE: " + comp.getId() + " - " + comp.getDescripcion()
                ));
            }

            // Programa preventivo asociado (si existe)
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

            // Sección de órdenes correctivas por equipo
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

    /**
     * Determina qué nodo fue seleccionado en el árbol y redirige
     * la visualización de detalles según su categoría.
     */
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

    /**
     * Muestra información detallada de un equipo seleccionado
     * incluyendo programa preventivo y órdenes recientes.
     *
     * @param nodo cadena del nodo seleccionado con identificador de equipo
     */
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
                    detalleArea.append("\nPrograma Preventivo Asociado:\n");
                    detalleArea.append("Nombre: " + prog.getNombrePrograma() + "\n");
                    detalleArea.append("Responsable: " + prog.getResponsable() + "\n");
                    detalleArea.append("Fases: " + prog.getFases().size() + "\n");
                } else {
                    detalleArea.append("\nSin programa preventivo.\n");
                }

                detalleArea.append("\nUltimas ordenes correctivas:\n");

                int count = 0;
                for (OrdenCorrectiva oc : sistema.getOrdenCorrectivaController().obtenerOrdenes()) {
                    if (oc.getEquipoAsociado().getId() == id) {
                        if (count < 5) {
                            detalleArea.append("OC-" + oc.getIdOrdenCorrectiva()
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

    /**
     * Muestra información asociada a una orden correctiva seleccionada.
     *
     * @param nodo cadena con formato identificador de orden correctiva
     */
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

    /**
     * Muestra detalles completos de una fase programada,
     * incluyendo descripción, parámetros operativos, tareas
     * y recursos asignados.
     *
     * @param nodo texto del nodo seleccionado con número de fase
     */
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



