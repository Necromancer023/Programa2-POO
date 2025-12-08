package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un equipo físico registrado dentro del sistema de mantenimiento.
 * Almacena su información técnica, ubicación, estado operativo, historial de órdenes
 * y una bitácora asociada que documenta sus eventos relevantes.
 */
public class Equipo {

    /** Identificador único del equipo. */
    private int idEquipo;

    /** Descripción breve o nombre identificador del equipo. */
    private String descripcion;

    /** Categoría o tipo del equipo (por ejemplo: industrial, biomédico, etc.). */
    private String tipo;

    /** Ubicación física donde se encuentra instalado el equipo. */
    private String ubicacion;

    /** Fabricante responsable del diseño y fabricación del equipo. */
    private String fabricante;

    /** Número de serie asignado por el fabricante. */
    private String serie;

    /** Fecha en que el equipo fue adquirido por la empresa. */
    private LocalDate fechaAdquisicion;

    /** Fecha en que el equipo se colocó en servicio operativo. */
    private LocalDate fechaPuestaEnServicio;

    /** Tiempo de vida útil estipulado en meses. */
    private int mesesVidaUtil;

    /** Nombre del modelo específico del equipo. */
    private String modelo;

    /** Dimensiones físicas reportadas del equipo. */
    private String dimensiones;

    /** Peso en kilogramos del dispositivo. */
    private double peso;

    /** Bitácora de mantenimiento asociada automáticamente al registrar el equipo. */
    private BitacoraMantenimiento bitacora;

    /**
     * Enumera los posibles estados operativos del equipo para seguimiento
     * de mantenimiento y disponibilidad.
     */
    public enum EstadoEquipo {
        EN_MANTENIMIENTO_PREVENTIVO,
        EN_MANTENIMIENTO_CORRECTIVO,
        FUERA_DE_SERVICIO,
        OPERATIVO,
        NO_OPERATIVO,
        DESECHADO
    }

    /** Estado vigente del equipo dentro del ciclo de mantenimiento. */
    private EstadoEquipo estado;

    /** Costo inicial del equipo calculado o registrado. */
    private double costoInicial;

    /** Texto libre con especificaciones técnicas detalladas. */
    private String especificacionesTecnicas;

    /** Información asociada a garantía o contratos de soporte. */
    private String informacionGarantia;

    /** Lista de equipos subordinados considerados como componentes instalados. */
    private List<Equipo> componentes;

    /** Programa preventivo asignado al equipo, si existe. */
    private ProgramaPreventivo programaPreventivo;

    /** Historial de órdenes preventivas ejecutadas sobre el equipo. */
    private List<OrdenPreventiva> ordenesPreventivas;

    /** Historial de órdenes correctivas aplicadas al equipo. */
    private List<OrdenCorrectiva> ordenesCorrectivas;

    /**
     * Construye un nuevo equipo con información básica y crea automáticamente
     * su bitácora de mantenimiento asociada.
     *
     * @param id identificador único del equipo
     * @param descripcion descripción general
     * @param tipo tipo de equipo
     * @param ubicacion ubicación física
     * @param fabricante nombre del fabricante
     * @param serie número de serie
     * @param fechaAdquisicion fecha de adquisición
     * @param fechaPuestaEnServicio fecha en que se puso en uso
     * @param mesesVidaUtil vida útil en meses
     * @param costoInicial inversión inicial del equipo
     * @param estado estado operativo inicial
     * @param modelo modelo específico
     * @param dimensiones dimensiones físicas
     * @param peso peso en kilogramos
     */
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

        // Crear bitácora automática para trazabilidad.
        this.bitacora = new BitacoraMantenimiento(id, this);
    }

    // ==================================================================
    // GETTERS Y SETTERS DOCUMENTADOS
    // ==================================================================

    /** @return identificador único del equipo. */
    public int getId() {
        return idEquipo;
    }

    /** @return descripción general del equipo. */
    public String getDescripcion() {
        return descripcion;
    }

    /** Establece una descripción para el equipo. */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** @return tipo de equipo registrado. */
    public String getTipo() {
        return tipo;
    }

    /** @return ubicación actual del equipo. */
    public String getUbicacion() {
        return ubicacion;
    }

    /** Modifica la ubicación física reportada del equipo. */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /** @return nombre del fabricante. */
    public String getFabricante() {
        return fabricante;
    }

    /** Define o actualiza el fabricante del equipo. */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /** @return número de serie del equipo. */
    public String getSerie() {
        return serie;
    }

    /** Establece o actualiza el número de serie. */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /** @return fecha de adquisición del equipo. */
    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    /** @return fecha de puesta en operación. */
    public LocalDate getFechaPuestaEnServicio() {
        return fechaPuestaEnServicio;
    }

    /** Modifica la fecha de puesta en servicio. */
    public void setFechaPuestaEnServicio(LocalDate fechaPuestaEnServicio) {
        this.fechaPuestaEnServicio = fechaPuestaEnServicio;
    }

    /** @return vida útil en meses. */
    public int getMesesVidaUtil() {
        return mesesVidaUtil;
    }

    /** Modifica la vida útil estimada. */
    public void setMesesVidaUtil(int mesesVidaUtil) {
        this.mesesVidaUtil = mesesVidaUtil;
    }

    /** @return costo inicial registrado del equipo. */
    public double getCostoInicial() {
        return costoInicial;
    }

    /** Actualiza el costo inicial calculado o documentado. */
    public void setCostoInicial(double costoInicial) {
        this.costoInicial = costoInicial;
    }

    /** @return estado operativo actual del equipo. */
    public EstadoEquipo getEstado() {
        return estado;
    }

    /**
     * Cambia el estado operativo y registra responsable y motivo para futura auditoría.
     *
     * @param nuevoEstado nuevo estado operativo
     * @param responsable responsable de la modificación
     * @param motivo justificación del cambio
     */
    public void setEstado(EstadoEquipo nuevoEstado, String responsable, String motivo) {
        this.estado = nuevoEstado;
        // Aquí luego se agregará registro a historial.
    }

    /** Setter simple del estado, usado por servicios existentes. */
    public void setEstado(EstadoEquipo nuevoEstado) {
        this.estado = nuevoEstado;
    }

    /** @return texto con especificaciones técnicas. */
    public String getEspecificacionesTecnicas() {
        return especificacionesTecnicas;
    }

    /** Establece especificaciones detalladas. */
    public void setEspecificacionesTecnicas(String especificacionesTecnicas) {
        this.especificacionesTecnicas = especificacionesTecnicas;
    }

    /** @return detalle de garantía del equipo. */
    public String getInformacionGarantia() {
        return informacionGarantia;
    }

    /** Establece o actualiza información de garantía. */
    public void setInformacionGarantia(String informacionGarantia) {
        this.informacionGarantia = informacionGarantia;
    }

    /** @return modelo físico del equipo. */
    public String getModelo() {
        return modelo;
    }

    /** Actualiza el modelo del equipo. */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /** @return dimensiones físicas. */
    public String getDimensiones() {
        return dimensiones;
    }

    /** Establece dimensiones físicas del equipo. */
    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    /** @return peso del equipo en kilogramos. */
    public double getPeso() {
        return peso;
    }

    /** Asigna un peso en kilogramos. */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /** @return bitácora asociada que registra eventos del equipo. */
    public BitacoraMantenimiento getBitacora() {
        return bitacora;
    }

    /** Permite reemplazar o actualizar la bitácora asociada. */
    public void setBitacora(BitacoraMantenimiento bitacora) {
        this.bitacora = bitacora;
    }

    // ===============================================================
    // MÉTODOS DE LISTA (Componentes y órdenes)
    // ===============================================================

    /**
     * Añade un nuevo componente subordinado al equipo.
     *
     * @param componente equipo considerado parte estructural del principal
     */
    public void agregarComponente(Equipo componente) {
        if (componente != null) {
            this.componentes.add(componente);
        }
    }

    /** Elimina un componente registrado. */
    public void eliminarComponente(Equipo componente) {
        this.componentes.remove(componente);
    }

    /** @return lista de componentes instalados. */
    public List<Equipo> getComponentes() {
        return componentes;
    }

    /** Registra una orden preventiva asociada. */
    public void agregarOrdenPreventiva(OrdenPreventiva orden) {
        if (orden != null) {
            this.ordenesPreventivas.add(orden);
        }
    }

    /** @return lista de órdenes preventivas realizadas. */
    public List<OrdenPreventiva> getOrdenesPreventivas() {
        return ordenesPreventivas;
    }

    /** Registra una orden correctiva aplicada. */
    public void agregarOrdenCorrectiva(OrdenCorrectiva orden) {
        if (orden != null) {
            this.ordenesCorrectivas.add(orden);
        }
    }

    /** @return lista de órdenes correctivas aplicadas. */
    public List<OrdenCorrectiva> getOrdenesCorrectivas() {
        return ordenesCorrectivas;
    }

    /** @return programa preventivo asignado o null si no existe. */
    public ProgramaPreventivo getProgramaPreventivo() {
        return programaPreventivo;
    }

    /** Asigna o actualiza el programa preventivo del equipo. */
    public void setProgramaPreventivo(ProgramaPreventivo programaPreventivo) {
        this.programaPreventivo = programaPreventivo;
    }

    // ===============================================================
    // VALIDACIONES
    // ===============================================================

    /**
     * Verifica coherencia entre fecha de compra y puesta en servicio.
     *
     * @return true si las fechas son válidas o no existen inconsistencias
     */
    public boolean validarFechas() {
        if (fechaPuestaEnServicio == null) return true;
        return !fechaPuestaEnServicio.isBefore(fechaAdquisicion);
    }

    /**
     * Valida que la vida útil declarada sea positiva.
     *
     * @return true si la vida útil es mayor que cero
     */
    public boolean validarVidaUtil() {
        return mesesVidaUtil > 0;
    }

    /**
     * Representación estandarizada del equipo usada para reportes
     * y depuración visual.
     *
     * @return cadena con datos principales del equipo
     */
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

