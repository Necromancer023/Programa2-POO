package org.example;

import java.time.LocalDate;

public class Usuario {

    private int idUsuario;
    private String nombreCompleto;
    private String username;
    private String password;
    private Rol rol;
    private boolean activo;
    private String email;
    private String telefono;
    private LocalDate fechaCreacion;

    // ----- Constructor -----

    public Usuario(int idUsuario,
                   String nombreCompleto,
                   String username,
                   String password,
                   Rol rol,
                   String email,
                   String telefono) {

        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.email = email;
        this.telefono = telefono;

        this.activo = true;
        this.fechaCreacion = LocalDate.now();
    }

    // ----- Getters y Setters -----

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    // ----- Métodos -----

    public void desactivar() {
        this.activo = false;
    }

    public void activar() {
        this.activo = true;
    }

    public void cambiarPassword(String nuevaPassword) {
        this.password = nuevaPassword;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", username='" + username + '\'' +
                ", rol=" + rol +
                ", activo=" + activo +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}

