package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private int idEquipo;
    private String descripcion;
    private String tipo;
    private String ubicacion;
    private String fabricante;
    private String serie;
    private LocalDate fechaAdquisicion;
    private LocalDate fechaPuestaEnServicio;
    private int mesesVidaUtil;

    private String modelo;
    private String dimensiones;
    private double peso;

    private BitacoraMantenimiento bitacora;

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

    private String especificacionesTecnicas;
    private String informacionGarantia;

    private List<Equipo> componentes;
    private ProgramaPreventivo programaPreventivo;
    private List<OrdenPreventiva> ordenesPreventivas;
    private List<OrdenCorrectiva> ordenesCorrectivas;

    // ----------------------------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------------------------
    public Equipo(int id, String descripcion, String tipo, String ubicacion, String fabricante, String serie,
                  LocalDate fechaAdquisicion, LocalDate fechaPuestaEnServicio, int mesesVidaUtil,
                  double costoInicial, EstadoEquipo estado,
                  String modelo, String dimensiones, double peso) {

        this.idEquipo = id;
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

        this.modelo = modelo;
        this.dimensiones = dimensiones;
        this.peso = peso;

        this.componentes = new ArrayList<>();
        this.ordenesPreventivas = new ArrayList<>();
        this.ordenesCorrectivas = new ArrayList<>();
        this.programaPreventivo = null;

        this.especificacionesTecnicas = "";
        this.informacionGarantia = "";

        // Crear bitácora automática
        this.bitacora = new BitacoraMantenimiento(id, this);
    }

    // ----------------------------------------------------------
    // GETTERS Y SETTERS
    // ----------------------------------------------------------

    public int getId() {
        return idEquipo;
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

    // Setter extendido (cuando implementemos historial)
    public void setEstado(EstadoEquipo nuevoEstado, String responsable, String motivo) {
        this.estado = nuevoEstado;
        // Aquí luego se agregará registro a historial
    }

    // Setter básico para compatibilidad (controladores y servicios que ya existen)
    public void setEstado(EstadoEquipo nuevoEstado) {
        this.estado = nuevoEstado;
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

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDimensiones() {
        return dimensiones;
    }
    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }

    public BitacoraMantenimiento getBitacora() {
        return bitacora;
    }
    public void setBitacora(BitacoraMantenimiento bitacora) {
        this.bitacora = bitacora;
    }

    // ----------------------------------------------------------
    // MÉTODOS DE LISTAS (componentes, órdenes, etc.)
    // ----------------------------------------------------------

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

    // ----------------------------------------------------------
    // VALIDACIONES
    // ----------------------------------------------------------

    public boolean validarFechas() {
        if (fechaPuestaEnServicio == null) return true;
        return !fechaPuestaEnServicio.isBefore(fechaAdquisicion);
    }

    public boolean validarVidaUtil() {
        return mesesVidaUtil > 0;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "idEquipo=" + idEquipo +
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
