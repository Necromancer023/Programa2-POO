package org.example;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private List<Usuario> usuarios;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
    }

    // Registrar usuario nuevo
    public boolean registrarUsuario(Usuario usuario) {

        // Validar que no exista ID o username repetido
        for (Usuario u : usuarios) {
            if (u.getIdUsuario() == usuario.getIdUsuario()) {
                return false; // ID repetido
            }
            if (u.getUsername().equalsIgnoreCase(usuario.getUsername())) {
                return false; // username repetido
            }
        }

        usuarios.add(usuario);
        return true;
    }

    // Buscar por ID
    public Usuario buscarPorId(int idUsuario) {
        for (Usuario u : usuarios) {
            if (u.getIdUsuario() == idUsuario) {
                return u;
            }
        }
        return null;
    }

    // Buscar por username
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    // Eliminar usuario (lógico o físico, aquí físico)
    public boolean eliminarUsuario(int idUsuario) {
        Usuario u = buscarPorId(idUsuario);
        if (u != null) {
            usuarios.remove(u);
            return true;
        }
        return false;
    }

    // Cambiar estado (activo / inactivo)
    public boolean cambiarEstado(int idUsuario, boolean activo) {
        Usuario u = buscarPorId(idUsuario);
        if (u != null) {
            u.setActivo(activo);
            return true;
        }
        return false;
    }

    // Cambiar contraseña
    public boolean actualizarPassword(int idUsuario, String nuevaPassword) {
        Usuario u = buscarPorId(idUsuario);
        if (u != null) {
            u.cambiarPassword(nuevaPassword);
            return true;
        }
        return false;
    }

    // Autenticar (login)
    public Usuario autenticar(String username, String password) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)
                    && u.getPassword().equals(password)
                    && u.isActivo()) {
                return u;
            }
        }
        return null;
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        return usuarios;
    }
}

