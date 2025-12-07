package org.example;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

public class ProgramaPreventivoFrame extends JFrame {

    private final SistemaMantenimiento sistema;

    // Campos para crear programa
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtResponsable;
    private JTextArea txtObjetivo;

    // Campos para ver detalles / eliminar
    private JTextField txtIdDetalle;
    private JTextField txtIdEliminar;
    private JTextArea areaDetalle;

    // Listado general
    private JTextArea areaListado;

    public ProgramaPreventivoFrame(SistemaMantenimiento sistema) {
        this.sistema = sistema;

        System.out.println(">>> [ProgPrevFrame] inicializando interfaz");

        setTitle("Gestión de Programas Preventivos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Registrar programa", panelRegistrar());
        tabs.add("Registrar fase", panelRegistrarFase());
        tabs.add("Editar Fase", panelEditarFase());
        tabs.add("Detalle de programa", panelDetalle());
        tabs.add("Listado de programas", panelListado());

        add(tabs, BorderLayout.CENTER);
    }

    // =========================================================
    // TAB 1: REGISTRAR PROGRAMA
    // =========================================================
    private JPanel panelRegistrar() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtResponsable = new JTextField();
        txtObjetivo = new JTextArea(3, 20);
        txtObjetivo.setLineWrap(true);
        txtObjetivo.setWrapStyleWord(true);

        form.add(new JLabel("ID Programa:"));
        form.add(txtId);

        form.add(new JLabel("Nombre del programa:"));
        form.add(txtNombre);

        form.add(new JLabel("Responsable:"));
        form.add(txtResponsable);

        form.add(new JLabel("Objetivo:"));

        JScrollPane scrollObj = new JScrollPane(txtObjetivo);
        form.add(scrollObj);

        JButton btnCrear = new JButton("Crear programa");
        btnCrear.addActionListener(e -> registrarPrograma());

        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        abajo.add(btnCrear);

        p.add(form, BorderLayout.CENTER);
        p.add(abajo, BorderLayout.SOUTH);

        return p;
    }

    private void registrarPrograma() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String responsable = txtResponsable.getText().trim();
            String objetivo = txtObjetivo.getText().trim();

            System.out.println(">>> [ProgPrevFrame] Creando programa ID=" + id);

            String resultado = sistema.getProgramaPreventivoController().crearProgramaPreventivo(
                    id,
                    nombre,
                    objetivo,
                    LocalDate.now(),  // fechaCreacion = hoy
                    responsable
            );

            JOptionPane.showMessageDialog(this, resultado);
            System.out.println(">>> [ProgPrevFrame] Resultado crear: " + resultado);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
            System.out.println(">>> [ProgPrevFrame] Error al parsear ID: " + ex.getMessage());
        }
    }

    // =========================================================
    // TAB 2: DETALLE DE PROGRAMA
    // =========================================================
    private JPanel panelDetalle() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtIdDetalle = new JTextField(10);
        JButton btnBuscar = new JButton("Ver detalle");
        btnBuscar.addActionListener(e -> cargarDetallePrograma());

        txtIdEliminar = new JTextField(10);
        JButton btnEliminar = new JButton("Eliminar programa");
        btnEliminar.addActionListener(e -> eliminarPrograma());

        arriba.add(new JLabel("ID Programa:"));
        arriba.add(txtIdDetalle);
        arriba.add(btnBuscar);

        arriba.add(new JLabel("   ID para eliminar:"));
        arriba.add(txtIdEliminar);
        arriba.add(btnEliminar);

        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);

        p.add(arriba, BorderLayout.NORTH);
        p.add(new JScrollPane(areaDetalle), BorderLayout.CENTER);

        return p;
    }

    private void cargarDetallePrograma() {
        areaDetalle.setText("");

        try {
            int id = Integer.parseInt(txtIdDetalle.getText().trim());
            System.out.println(">>> [ProgPrevFrame] Buscando detalle de programa ID=" + id);

            ProgramaPreventivo prog = sistema.getProgramaPreventivoController().buscarPrograma(id);

            if (prog == null) {
                areaDetalle.setText("No se encontró el programa con ID " + id);
                System.out.println(">>> [ProgPrevFrame] Programa no encontrado");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(prog.getIdPrograma()).append("\n");
            sb.append("Nombre: ").append(prog.getNombrePrograma()).append("\n");
            sb.append("Objetivo: ").append(prog.getObjetivo()).append("\n");
            sb.append("Fecha creación: ").append(prog.getFechaCreacion()).append("\n");
            sb.append("Responsable: ").append(prog.getResponsable()).append("\n\n");

            sb.append("Fases registradas: ").append(prog.getFases().size()).append("\n");
            sb.append("-------------------------------------------------\n");

            // Mostramos fases si existen
            for (FasePreventiva f : prog.getFases()) {
                sb.append("Fase #").append(f.getNumeroFase()).append("\n");
                sb.append("  Descripción: ").append(f.getDescripcion()).append("\n");
                sb.append("  Intervalo días: ").append(f.getIntervaloDias()).append("\n");
                sb.append("  Cantidad ciclos: ").append(f.getCantidadCiclos()).append("\n");

                if (f.getTareas() != null && !f.getTareas().isEmpty()) {
                    sb.append("  ▫ Tareas:\n");
                    for (String t : f.getTareas()) {
                        sb.append("     - ").append(t).append("\n");
                    }
                }

                if (f.getRecursosNecesarios() != null && !f.getRecursosNecesarios().isEmpty()) {
                    sb.append("  ▫ Recursos:\n");
                    for (String r : f.getRecursosNecesarios()) {
                        sb.append("     - ").append(r).append("\n");
                    }
                }

                sb.append("-------------------------------------------------\n");
            }

            areaDetalle.setText(sb.toString());
            System.out.println(">>> [ProgPrevFrame] Detalle cargado correctamente");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
            System.out.println(">>> [ProgPrevFrame] Error al parsear ID detalle: " + ex.getMessage());
        }
    }

    private void eliminarPrograma() {
        try {
            int id = Integer.parseInt(txtIdEliminar.getText().trim());
            System.out.println(">>> [ProgPrevFrame] Eliminando programa ID=" + id);

            String msg = sistema.getProgramaPreventivoController().eliminarPrograma(id);

            JOptionPane.showMessageDialog(this, msg);
            System.out.println(">>> [ProgPrevFrame] Resultado eliminar: " + msg);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido para eliminar.");
            System.out.println(">>> [ProgPrevFrame] Error al parsear ID eliminar: " + ex.getMessage());
        }
    }

    // =========================================================
    // TAB 3: LISTADO GENERAL
    // =========================================================
    private JPanel panelListado() {
        JPanel p = new JPanel(new BorderLayout());

        areaListado = new JTextArea();
        areaListado.setEditable(false);

        JButton btnActualizar = new JButton("Actualizar listado");
        btnActualizar.addActionListener(e -> cargarProgramas());

        p.add(btnActualizar, BorderLayout.NORTH);
        p.add(new JScrollPane(areaListado), BorderLayout.CENTER);

        return p;
    }

    private void cargarProgramas() {
        areaListado.setText("");
        System.out.println(">>> [ProgPrevFrame] Cargando listado de programas...");

        List<ProgramaPreventivo> lista = sistema.getProgramaPreventivoController().obtenerProgramas();

        if (lista == null || lista.isEmpty()) {
            areaListado.setText("No hay programas registrados.");
            System.out.println(">>> [ProgPrevFrame] No hay programas en la lista");
            return;
        }

        for (ProgramaPreventivo p : lista) {
            areaListado.append(p.toString() + "\n");
        }

        System.out.println(">>> [ProgPrevFrame] Listado cargado. Total programas: " + lista.size());
    }

    // ------------------------------------------------------------
    // PANEL: REGISTRAR FASE PREVENTIVA
    // ------------------------------------------------------------
    private JPanel panelRegistrarFase() {
        System.out.println(">>> [ProgramaFrame] Creando panel Registrar Fase");

        JPanel p = new JPanel(new GridLayout(6, 2, 5, 5));

        JTextField txtProgId = new JTextField();
        JTextField txtNumFase = new JTextField();
        JTextField txtDesc = new JTextField();
        JTextField txtDias = new JTextField();
        JTextField txtCiclos = new JTextField();

        p.add(new JLabel("ID Programa:"));
        p.add(txtProgId);

        p.add(new JLabel("Número de fase:"));
        p.add(txtNumFase);

        p.add(new JLabel("Descripción fase:"));
        p.add(txtDesc);

        p.add(new JLabel("Intervalo (días):"));
        p.add(txtDias);

        p.add(new JLabel("Cantidad ciclos (0 = infinito):"));
        p.add(txtCiclos);

        JButton btnAgregar = new JButton("Registrar fase");
        btnAgregar.addActionListener(e -> {
            try {
                int idProg = Integer.parseInt(txtProgId.getText());
                int numFase = Integer.parseInt(txtNumFase.getText());
                int dias = Integer.parseInt(txtDias.getText());
                int ciclos = Integer.parseInt(txtCiclos.getText());

                if (dias <= 0) {
                    JOptionPane.showMessageDialog(this, "El intervalo debe ser mayor a cero.");
                    return;
                }

                System.out.println(">>> [ProgramaFrame] Registrando fase para programa " + idProg);

                // crear fase
                FasePreventiva fase = new FasePreventiva(
                        numFase,
                        txtDesc.getText(),
                        dias,
                        ciclos
                );

                // enviar al controller
                String r = sistema.getProgramaPreventivoController()
                        .agregarFaseAPrograma(idProg, fase);

                JOptionPane.showMessageDialog(this, r);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        });

        p.add(new JLabel());
        p.add(btnAgregar);

        return p;
    }

    // ======================================================
    // PANEL EDITAR FASE PREVENTIVA
    // ======================================================
    private JPanel panelEditarFase() {

        System.out.println(">>> [ProgramaPreventivoFrame] Inicializando panel Edición de Fase");

        JPanel p = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(9, 2, 5, 5));

        JTextField txtProgId = new JTextField();
        JTextField txtNumFase = new JTextField();
        JComboBox<FasePreventiva.Frecuencia> comboFrecuencia =
                new JComboBox<>(FasePreventiva.Frecuencia.values());

        JTextField txtTiempoHoras = new JTextField();
        JTextField txtObs = new JTextField();

        JTextField txtNuevaTarea = new JTextField();
        JTextField txtNuevoRecurso = new JTextField();

        JButton btnAgregarTarea = new JButton("Agregar Tarea");
        JButton btnAgregarRecurso = new JButton("Agregar Recurso");
        JButton btnGuardarCambios = new JButton("Guardar cambios");

        form.add(new JLabel("ID Programa:"));
        form.add(txtProgId);
        form.add(new JLabel("Número de fase:"));
        form.add(txtNumFase);
        form.add(new JLabel("Frecuencia:"));
        form.add(comboFrecuencia);
        form.add(new JLabel("Tiempo estimado (horas):"));
        form.add(txtTiempoHoras);
        form.add(new JLabel("Observaciones:"));
        form.add(txtObs);
        form.add(new JLabel("Nueva tarea:"));
        form.add(txtNuevaTarea);
        form.add(new JLabel("Nuevo recurso:"));
        form.add(txtNuevoRecurso);
        form.add(btnAgregarTarea);
        form.add(btnAgregarRecurso);
        form.add(new JLabel(""));
        form.add(btnGuardarCambios);

        p.add(form, BorderLayout.NORTH);

        JTextArea salida = new JTextArea();
        salida.setEditable(false);
        p.add(new JScrollPane(salida), BorderLayout.CENTER);

        // ---------- BOTONES LÓGICA ----------

        btnAgregarTarea.addActionListener(e -> {
            try {
                int pId = Integer.parseInt(txtProgId.getText());
                int num = Integer.parseInt(txtNumFase.getText());

                ProgramaPreventivo programa = sistema.getProgramaPreventivoController().buscarPrograma(pId);

                if (programa == null) {
                    JOptionPane.showMessageDialog(this, "Programa no encontrado.");
                    return;
                }

                FasePreventiva fase = programa.obtenerFase(num);
                if (fase == null) {
                    JOptionPane.showMessageDialog(this, "Fase no encontrada.");
                    return;
                }

                fase.agregarTarea(txtNuevaTarea.getText());
                salida.append("➕ Tarea agregada a fase " + num + "\n");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        });

        btnAgregarRecurso.addActionListener(e -> {
            try {
                int pId = Integer.parseInt(txtProgId.getText());
                int num = Integer.parseInt(txtNumFase.getText());

                ProgramaPreventivo programa = sistema.getProgramaPreventivoController().buscarPrograma(pId);

                if (programa == null) {
                    JOptionPane.showMessageDialog(this, "Programa no encontrado.");
                    return;
                }

                FasePreventiva fase = programa.obtenerFase(num);
                if (fase == null) {
                    JOptionPane.showMessageDialog(this, "Fase no encontrada.");
                    return;
                }

                fase.agregarRecurso(txtNuevoRecurso.getText());
                salida.append("🔧 Recurso agregado a fase " + num + "\n");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        });

        btnGuardarCambios.addActionListener(e -> {
            try {
                int pId = Integer.parseInt(txtProgId.getText());
                int num = Integer.parseInt(txtNumFase.getText());

                ProgramaPreventivo programa = sistema.getProgramaPreventivoController().buscarPrograma(pId);

                if (programa == null) {
                    JOptionPane.showMessageDialog(this, "Programa no encontrado.");
                    return;
                }

                FasePreventiva fase = programa.obtenerFase(num);
                if (fase == null) {
                    JOptionPane.showMessageDialog(this, "Fase no encontrada.");
                    return;
                }

                // Aplicar cambios
                fase.setFrecuencia((FasePreventiva.Frecuencia) comboFrecuencia.getSelectedItem());
                fase.setTiempoEstimadoHoras(Double.parseDouble(txtTiempoHoras.getText()));
                fase.setObservaciones(txtObs.getText());

                salida.append("✔ Cambios guardados en fase " + num + "\n");

                System.out.println(">>> [ProgramaPreventivoFrame] Fase " + num + " actualizada");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        });

        return p;
    }

}



