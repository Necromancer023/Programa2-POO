package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a un técnico de mantenimiento dentro del sistema,
 * incluyendo su identificación, especialidad, información de contacto
 * y certificaciones asociadas.
 */
public class Tecnico {

    private int idTecnico;
    private String nombreCompleto;
    private String especialidad;
    private String telefono;
    private String email;

    private LocalDate fechaIngreso;
    private boolean activo;

    private List<String> certificaciones;

    /**
     * Constructor principal.
     *
     * Inicializa el técnico con datos personales básicos,
     * asigna la fecha de ingreso como la fecha actual
     * y marca al técnico como activo.
     *
     * @param idTecnico identificador único del técnico
     * @param nombreCompleto nombre completo del técnico
     * @param especialidad área de especialización
     * @param telefono número de contacto
     * @param email correo electrónico asociado
     */
    public Tecnico(int idTecnico, String nombreCompleto, String especialidad,
                   String telefono, String email) {

        this.idTecnico = idTecnico;
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;

        this.fechaIngreso = LocalDate.now();
        this.activo = true;

        this.certificaciones = new ArrayList<>();
    }

    // ===== Getters =====

    public int getIdTecnico() {
        return idTecnico;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean isActivo() {
        return activo;
    }

    public List<String> getCertificaciones() {
        return certificaciones;
    }

    // ===== Setters y métodos funcionales =====

    /**
     * Cambia el estado laboral del técnico.
     *
     * @param activo indica si el técnico está activo en la organización
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Agrega una certificación al perfil del técnico.
     *
     * @param certificacion nombre o referencia de certificación obtenida
     */
    public void agregarCertificacion(String certificacion) {
        this.certificaciones.add(certificacion);
    }

    /**
     * Representación en texto de los datos del técnico.
     *
     * @return cadena con información relevante del técnico
     */
    @Override
    public String toString() {
        return "Tecnico{" +
                "idTecnico=" + idTecnico +
                ", nombre='" + nombreCompleto + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", activo=" + activo +
                ", certificaciones=" + certificaciones +
                '}';
    }
}


