package org.example;

import java.util.List;

/**
 * Controlador encargado de gestionar operaciones relacionadas
 * con los usuarios del sistema, aplicando validaciones y delegando
 * la persistencia al servicio UsuarioService.
 */
public class UsuarioController {

    private UsuarioService usuarioService;

    /**
     * Constructor que inicializa el servicio asociado.
     */
    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    // Crear usuario con validaciones

    /**
     * Registra un nuevo usuario aplicando validaciones de datos.
     *
     * @param idUsuario identificador único del usuario
     * @param nombreCompleto nombre completo
     * @param username nombre de inicio de sesión
     * @param password contraseña de acceso
     * @param rol rol asignado dentro del sistema
     * @param email dirección de correo electrónico (opcional)
     * @param telefono número de teléfono (opcional)
     * @return mensaje indicando éxito o motivo de rechazo
     */
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

    /**
     * Verifica las credenciales de acceso y valida si el usuario es activo.
     *
     * @param username usuario ingresado
     * @param password contraseña ingresada
     * @return mensaje indicando resultado del intento de inicio de sesión
     */
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

    /**
     * Retorna el objeto usuario para uso posterior en la interfaz
     * si las credenciales son válidas.
     */
    public Usuario obtenerUsuarioAutenticado(String username, String password) {
        return usuarioService.autenticar(username, password);
    }

    // Cambiar estado

    /**
     * Activa o desactiva un usuario.
     *
     * @param idUsuario identificador del usuario
     * @param activo estado deseado
     * @return mensaje indicando resultado de la operación
     */
    public String cambiarEstadoUsuario(int idUsuario, boolean activo) {
        boolean ok = usuarioService.cambiarEstado(idUsuario,activo);
        return ok ? "Estado actualizado correctamente."
                  : "No se encontró el usuario.";
    }

    // Cambiar contraseña

    /**
     * Actualiza la contraseña del usuario.
     *
     * @param idUsuario identificador del usuario
     * @param nuevaPassword nueva clave válida
     * @return mensaje reflejando éxito o fallo
     */
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

    /**
     * Elimina un usuario del sistema.
     *
     * @param idUsuario identificador del usuario
     * @return mensaje indicando el resultado de la operación
     */
    public String eliminarUsuario(int idUsuario) {
        boolean eliminado = usuarioService.eliminarUsuario(idUsuario);
        return eliminado ? "Usuario eliminado correctamente."
                         : "No se encontró el usuario.";
    }

    // Listar usuarios

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     *
     * @return lista de objetos Usuario
     */
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    // Buscar por ID

    /**
     * Busca un usuario por su identificador.
     *
     * @param idUsuario valor del ID a buscar
     * @return objeto Usuario encontrado o null
     */
    public Usuario buscarPorId(int idUsuario) {
        return usuarioService.buscarPorId(idUsuario);
    }

    // Buscar por username

    /**
     * Busca un usuario por su nombre de acceso.
     *
     * @param username nombre de usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarPorUsername(String username) {
        return usuarioService.buscarPorUsername(username);
    }

    /**
     * Método requerido por la interfaz gráfica para autenticar y retornar
     * un usuario si las credenciales son correctas.
     *
     * @param username nombre de usuario
     * @param password contraseña asociada
     * @return Usuario autenticado o null
     */
    public Usuario autenticar(String username, String password) {
        return usuarioService.autenticar(username, password);
    }

}


