package org.example;

import java.time.LocalDate;
import java.util.List;

public class ProgramadorPreventivoService {

    private CalendarioMantenimiento calendario;
    private EquipoService equipoService;
    private ProgramaPreventivoService programaService;
    private OrdenPreventivaService ordenService;

    public ProgramadorPreventivoService(EquipoService equipoService,
                                        ProgramaPreventivoService programaService,
                                        OrdenPreventivaService ordenService) {

        this.equipoService = equipoService;
        this.programaService = programaService;
        this.ordenService = ordenService;
        this.calendario = new CalendarioMantenimiento();
    }

    /** Registrar una fecha programada en el calendario */
    public void programarFecha(LocalDate fecha) {
        calendario.agregarFecha(fecha);
    }

    /** Generar órdenes preventivas para las fechas pendientes */
    public void generarOrdenesPendientes() {

        List<LocalDate> pendientes = calendario.obtenerFechasPendientes();

        for (LocalDate fecha : pendientes) {

            List<Equipo> equipos = equipoService.obtenerEquipos();

            for (Equipo equipo : equipos) {

                ProgramaPreventivo programa = equipo.getProgramaPreventivo();
                if (programa == null) continue;

                for (FasePreventiva fase : programa.getFases()) {

                    // Por ahora no asignamos técnico (queda SIN ASIGNAR)
                    OrdenPreventiva nueva = new OrdenPreventiva(
                            generarIdAuto(),
                            fecha,
                            equipo,
                            fase,
                            null   // técnico aún no asignado
                    );

                    ordenService.agregarOrdenPreventiva(nueva);
                }
            }
        }
    }

    private int generarIdAuto() {
        return (int) (Math.random() * 100000);
    }
}


