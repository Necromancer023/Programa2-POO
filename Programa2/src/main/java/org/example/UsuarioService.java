package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar el almacenamiento,
 * validación y operaciones relacionadas con los usuarios.
 * Actúa como capa intermedia entre el controlador y los datos.
 */
public class UsuarioService {

    private List<Usuario> usuarios;

    /**
     * Constructor. Inicializa la lista interna de usuarios.
     */
    public UsuarioService() {
        this.usuarios = new ArrayList<>();
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Valida que no existan usuarios con el mismo ID ni con el mismo nombre de usuario.
     *
     * @param usuario objeto Usuario a agregar
     * @return true si se registró correctamente, false si hubo duplicados
     */
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

    /**
     * Busca un usuario por su ID.
     *
     * @param idUsuario identificador del usuario
     * @return objeto Usuario si existe, o null si no se encuentra
     */
    public Usuario buscarPorId(int idUsuario) {
        for (Usuario u : usuarios) {
            if (u.getIdUsuario() == idUsuario) {
                return u;
            }
        }
        return null;
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username nombre de usuario
     * @return Usuario si existe coincidencia, o null en caso contrario
     */
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario de la lista según su ID.
     *
     * @param idUsuario identificador del usuario
     * @return true si fue eliminado, false si no se encontró
     */
    public boolean eliminarUsuario(int idUsuario) {
        Usuario u = buscarPorId(idUsuario);
        if (u != null) {
            usuarios.remove(u);
            return true;
        }
        return false;
    }

    /**
     * Cambia el estado de un usuario (activo o inactivo).
     *
     * @param idUsuario identificador del usuario
     * @param activo nuevo estado
     * @return true si se actualizó, false si no se encontró el usuario
     */
    public boolean cambiarEstado(int idUsuario, boolean activo) {
        Usuario u = buscarPorId(idUsuario);
        if (u != null) {
            u.setActivo(activo);
            return true;
        }
        return false;
    }

    /**
     * Actualiza la contraseña de un usuario existente.
     *
     * @param idUsuario identificador del usuario
     * @param nuevaPassword nueva contraseña
     * @return true si se realizó el cambio, false si no existe el usuario
     */
    public boolean actualizarPassword(int idUsuario, String nuevaPassword) {
        Usuario u = buscarPorId(idUsuario);
        if (u != null) {
            u.cambiarPassword(nuevaPassword);
            return true;
        }
        return false;
    }

    /**
     * Autenticación de usuario.
     * Verifica username, contraseña y estado activo.
     *
     * @param username nombre de usuario
     * @param password contraseña
     * @return Usuario si las credenciales son válidas y está activo, null si falla
     */
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

    /**
     * Retorna la lista completa de usuarios registrados.
     *
     * @return lista de objetos Usuario
     */
    public List<Usuario> obtenerUsuarios() {
        return usuarios;
    }
}


