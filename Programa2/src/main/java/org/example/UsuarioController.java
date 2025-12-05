package org.example;

import java.util.List;

public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    // Crear usuario con validaciones
    public String crearUsuario(int idUsuario,
                               String nombreCompleto,
                               String username,
                               String password,
                               Rol rol,
                               String email,
                               String telefono) {

        if (idUsuario <= 0) return "El ID debe ser mayor que cero.";

        if (nombreCompleto == null || nombreCompleto.isBlank())
            return "El nombre completo no puede estar vacío.";

        if (username == null || username.isBlank())
            return "El nombre de usuario no puede estar vacío.";

        if (password == null || password.isBlank())
            return "La contraseña no puede estar vacía.";

        if (password.length() < 4)
            return "La contraseña debe tener al menos 4 caracteres.";

        if (rol == null)
            return "Debe seleccionar un rol.";

        if (email == null) email = "";
        if (telefono == null) telefono = "";

        Usuario nuevo = new Usuario(
                idUsuario,
                nombreCompleto,
                username,
                password,
                rol,
                email,
                telefono
        );

        boolean registrado = usuarioService.registrarUsuario(nuevo);

        return registrado ? "Usuario registrado correctamente."
                          : "No se pudo registrar. ID o username ya existen.";
    }

    // Login
    public String iniciarSesion(String username, String password) {
        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return "Debe ingresar usuario y contraseña.";
        }

        Usuario u = usuarioService.autenticar(username, password);

        return (u != null)
                ? "Inicio de sesión exitoso. Bienvenido " + u.getNombreCompleto() +
                  " (" + u.getRol() + ")."
                : "Usuario o contraseña incorrectos, o usuario inactivo.";
    }

    // Obtener objeto usuario tras login (para usar en interfaz)
    public Usuario obtenerUsuarioAutenticado(String username, String password) {
        return usuarioService.autenticar(username, password);
    }

    // Cambiar estado (activar/desactivar)
    public String cambiarEstadoUsuario(int idUsuario, boolean activo) {
        boolean ok = usuarioService.cambiarEstado(idUsuario, activo);
        return ok ? "Estado actualizado correctamente."
                  : "No se encontró el usuario.";
    }

    // Cambiar contraseña
    public String cambiarPassword(int idUsuario, String nuevaPassword) {
        if (nuevaPassword == null || nuevaPassword.isBlank()) {
            return "La contraseña no puede estar vacía.";
        }
        if (nuevaPassword.length() < 4) {
            return "La contraseña debe tener al menos 4 caracteres.";
        }

        boolean ok = usuarioService.actualizarPassword(idUsuario, nuevaPassword);
        return ok ? "Contraseña actualizada."
                  : "No se encontró el usuario.";
    }

    // Eliminar usuario
    public String eliminarUsuario(int idUsuario) {
        boolean eliminado = usuarioService.eliminarUsuario(idUsuario);
        return eliminado ? "Usuario eliminado correctamente."
                         : "No se encontró el usuario.";
    }

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    // Buscar por ID
    public Usuario buscarPorId(int idUsuario) {
        return usuarioService.buscarPorId(idUsuario);
    }

    // Buscar por username
    public Usuario buscarPorUsername(String username) {
        return usuarioService.buscarPorUsername(username);
    }

    // Método requerido por la interfaz GUI
    public Usuario autenticar(String username, String password) {
        return usuarioService.autenticar(username, password);
    }

}

