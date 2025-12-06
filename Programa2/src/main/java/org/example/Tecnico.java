package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tecnico {

    private int idTecnico;
    private String nombreCompleto;
    private String especialidad;
    private String telefono;
    private String email;

    private LocalDate fechaIngreso;
    private boolean activo;

    private List<String> certificaciones;

    // Constructor
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

    // Getters
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

    // Setters
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void agregarCertificacion(String certificacion) {
        this.certificaciones.add(certificacion);
    }
    
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

