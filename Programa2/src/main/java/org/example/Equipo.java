package org.example;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Equipo {

    private int id;
    private String descripcion;
    private String tipo;
    private String ubicacion;
    private String fabricante;
    private String serie;
    private LocalDate fechaAdquisicion;
    private LocalDate fechaPuestaEnServicio;
    private int mesesVidaUtil;
    public enum EstadoEquipo {
        EN_MANTENIMIENTO_PREVENTIVO,
        EN_MANTENIMIENTO_CORRECTIVO,
        FUERA_DE_SERVICIO,
        OPERATIVO,
        NO_OPERATIVO,
        DESECHADO
    }
    private EstadoEquipo estado;
    private double costoInicial;

    private String especificacionesTecnicas; // opcional
    private String informacionGarantia; // opcional

    private List<Equipo> componentes;
    private ProgramaPreventivo programaPreventivo;
    private List<OrdenPreventiva> ordenesPreventivas;
    private List<OrdenCorrectiva> ordenesCorrectivas;

    // -- Constructor --

    public Equipo(int id, String descripcion, String tipo, String ubicacion, String fabricante, String serie,
                  LocalDate fechaAdquisicion, LocalDate fechaPuestaEnServicio, int mesesVidaUtil,
                  double costoInicial, EstadoEquipo estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.fabricante = fabricante;
        this.serie = serie;
        this.fechaAdquisicion = fechaAdquisicion;
        this.fechaPuestaEnServicio = fechaPuestaEnServicio;
        this.mesesVidaUtil = mesesVidaUtil;
        this.costoInicial = costoInicial;
        this.estado = estado;
        this.componentes = new ArrayList<>();
        this.ordenesPreventivas = new ArrayList<>();
        this.ordenesCorrectivas = new ArrayList<>();
        this.programaPreventivo = null; 
    }

    // -- Getters y Setters --

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFabricante() {
        return fabricante;
    }
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getSerie() {
        return serie;
    }
    public void setSerie(String serie) {
        this.serie = serie;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public LocalDate getFechaPuestaEnServicio() {
        return fechaPuestaEnServicio;
    }
    public void setFechaPuestaEnServicio(LocalDate fechaPuestaEnServicio) {
        this.fechaPuestaEnServicio = fechaPuestaEnServicio;
    }

    public int getMesesVidaUtil() {
        return mesesVidaUtil;
    }
    public void setMesesVidaUtil(int mesesVidaUtil) {
        this.mesesVidaUtil = mesesVidaUtil;
    }

    public double getCostoInicial() {
        return costoInicial;
    }
    public void setCostoInicial(double costoInicial) {
        this.costoInicial = costoInicial;
    }

    public EstadoEquipo getEstado() {
        return estado;
    }
    public void setEstado(EstadoEquipo estado) {
        this.estado = estado;
    }

    public String getEspecificacionesTecnicas() {
        return especificacionesTecnicas;
    }
    public void setEspecificacionesTecnicas(String especificacionesTecnicas) {
        this.especificacionesTecnicas = especificacionesTecnicas;
    }

    public String getInformacionGarantia() {
        return informacionGarantia;
    }
    public void setInformacionGarantia(String informacionGarantia) {
        this.informacionGarantia = informacionGarantia;
    }


    // -- Métodos adicionales --
    public void agregarComponente(Equipo componente) {
        if (componente != null) {
            this.componentes.add(componente);
        }
    }
    public void eliminarComponente(Equipo componente) {
        this.componentes.remove(componente);
    }
    public List<Equipo> getComponentes() {
        return componentes;
    }

    public void agregarOrdenPreventiva(OrdenPreventiva orden) {
        if (orden != null) {
            this.ordenesPreventivas.add(orden);
        }
    }

    public List<OrdenPreventiva> getOrdenesPreventivas() {
        return ordenesPreventivas;
    }
    
    public void agregarOrdenCorrectiva(OrdenCorrectiva orden) {
        if (orden != null) {
            this.ordenesCorrectivas.add(orden);
        }
    }

    public List<OrdenCorrectiva> getOrdenesCorrectivas() {
        return ordenesCorrectivas;
    }

    public ProgramaPreventivo getProgramaPreventivo() {
        return programaPreventivo;
    }
    public void setProgramaPreventivo(ProgramaPreventivo programaPreventivo) {
        this.programaPreventivo = programaPreventivo;
    }

    // -- Validaciones internas --

    public boolean validarFechas() {
        if (fechaPuestaEnServicio == null) {
            return true;
        }
        return !fechaPuestaEnServicio.isBefore(fechaAdquisicion);   
    }

    public boolean validarVidaUtil() {
        return mesesVidaUtil > 0;
    }
    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", serie='" + serie + '\'' +
                ", fechaAdquisicion=" + fechaAdquisicion +
                ", fechaPuestaEnServicio=" + fechaPuestaEnServicio +
                ", mesesVidaUtil=" + mesesVidaUtil +
                ", estado=" + estado +
                ", costoInicial=" + costoInicial +
                '}';
    }

}