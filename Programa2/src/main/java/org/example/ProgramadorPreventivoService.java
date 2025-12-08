package org.example;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio responsable de automatizar la generación de órdenes preventivas
 * basadas en fechas registradas en un calendario de mantenimiento.
 *
 * Este componente actúa como un programador automático que:
 *  - Registra fechas para mantenimiento preventivo
 *  - Evalúa si dichas fechas han llegado o pasado
 *  - Genera órdenes preventivas por cada fase de cada programa preventivo
 *    configurado en los equipos del sistema
 */
public class ProgramadorPreventivoService {

    /** Calendario donde se almacenan fechas programadas de mantenimiento */
    private CalendarioMantenimiento calendario;

    /** Servicio para obtener y gestionar equipos registrados */
    private EquipoService equipoService;

    /** Servicio que administra programas preventivos asignados a equipos */
    private ProgramaPreventivoService programaService;

    /** Servicio encargado de registrar y almacenar órdenes preventivas */
    private OrdenPreventivaService ordenService;

    /**
     * Constructor del programador automático.
     *
     * @param equipoService servicio usado para listar equipos
     * @param programaService servicio que administra programas y fases preventivas
     * @param ordenService servicio encargado de crear órdenes preventivas
     */
    public ProgramadorPreventivoService(EquipoService equipoService,
                                        ProgramaPreventivoService programaService,
                                        OrdenPreventivaService ordenService) {

        this.equipoService = equipoService;
        this.programaService = programaService;
        this.ordenService = ordenService;
        this.calendario = new CalendarioMantenimiento();
    }

    /**
     * Registra una fecha para futura generación automática de órdenes preventivas.
     *
     * @param fecha fecha programada
     */
    public void programarFecha(LocalDate fecha) {
        calendario.agregarFecha(fecha);
    }

    /**
     * Recorre las fechas pendientes del calendario y genera automáticamente
     * órdenes preventivas para todos los equipos que tengan un programa preventivo.
     *
     * Cada fase del programa del equipo se traduce en una orden preventiva independiente.
     * No asigna técnico en esta versión del sistema — queda SIN ASIGNAR.
     */
    public void generarOrdenesPendientes() {

        // Obtener todas las fechas vencidas o igual a hoy
        List<LocalDate> pendientes = calendario.obtenerFechasPendientes();

        for (LocalDate fecha : pendientes) {

            // Obtener todos los equipos en el sistema
            List<Equipo> equipos = equipoService.obtenerEquipos();

            for (Equipo equipo : equipos) {

                // Recuperar el programa preventivo del equipo
                ProgramaPreventivo programa = equipo.getProgramaPreventivo();
                if (programa == null) continue; // equipos sin programa no generan órdenes

                // Por cada fase del programa se genera una orden individual
                for (FasePreventiva fase : programa.getFases()) {

                    OrdenPreventiva nueva = new OrdenPreventiva(
                            generarIdAuto(),  // ID generado temporalmente
                            fecha,
                            equipo,
                            fase,
                            null   // técnico aún no asignado
                    );

                    // Registrar orden en el sistema
                    ordenService.agregarOrdenPreventiva(nueva);
                }
            }
        }
    }

    /**
     * Generador simple de identificadores aleatorios para órdenes preventivas.
     * Se usa en lugar de un secuencial real para evitar colisiones mínimas.
     *
     * @return identificador entero pseudo-único
     */
    private int generarIdAuto() {
        return (int) (Math.random() * 100000);
    }
}



