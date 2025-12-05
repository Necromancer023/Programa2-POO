package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaMantenimiento {

    public enum NivelCumplimiento {
        ALTO,
        MEDIO,
        BAJO
    }

    private int idAuditoria;
    private LocalDate fechaAuditoria;
    private String responsable; // nombre del auditor
    private ProgramaPreventivo programaAuditado;

    private List<String> hallazgos;
    private String conclusiones;
    private String recomendaciones;

    private NivelCumplimiento cumplimientoGeneral;

    // Constructor
    public AuditoriaMantenimiento(int idAuditoria, LocalDate fechaAuditoria,
                                  String responsable, ProgramaPreventivo programaAuditado) {

        this.idAuditoria = idAuditoria;
        this.fechaAuditoria = fechaAuditoria;
        this.responsable = responsable;
        this.programaAuditado = programaAuditado;

        this.hallazgos = new ArrayList<>();
        this.conclusiones = "";
        this.recomendaciones = "";
        this.cumplimientoGeneral = NivelCumplimiento.MEDIO;
    }

    // Getters y setters
    public int getIdAuditoria() { return idAuditoria; }

    public LocalDate getFechaAuditoria() { return fechaAuditoria; }

    public String getResponsable() { return responsable; }

    public ProgramaPreventivo getProgramaAuditado() { return programaAuditado; }

    public List<String> getHallazgos() { return hallazgos; }
    public void agregarHallazgo(String h) { this.hallazgos.add(h); }

    public String getConclusiones() { return conclusiones; }
    public void setConclusiones(String conclusiones) { this.conclusiones = conclusiones; }

    public String getRecomendaciones() { return recomendaciones; }
    public void setRecomendaciones(String recomendaciones) { this.recomendaciones = recomendaciones; }

    public NivelCumplimiento getCumplimientoGeneral() { return cumplimientoGeneral; }
    public void setCumplimientoGeneral(NivelCumplimiento c) { this.cumplimientoGeneral = c; }

    @Override
    public String toString() {
        return "AuditoriaMantenimiento {" +
                "ID=" + idAuditoria +
                ", Fecha=" + fechaAuditoria +
                ", Responsable='" + responsable + '\'' +
                ", Programa=" + programaAuditado.getIdPrograma() +
                ", Cumplimiento=" + cumplimientoGeneral +
                ", Hallazgos=" + hallazgos +
                '}';
    }
}

