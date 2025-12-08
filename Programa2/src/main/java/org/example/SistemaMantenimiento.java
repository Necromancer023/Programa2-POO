package org.example;

/**
 * Clase principal del sistema de mantenimiento.
 * Implementa el patrón Singleton para garantizar una única instancia global,
 * la cual gestiona y provee acceso a todos los controladores del sistema.
 *
 * Además, administra la sesión del usuario autenticado y registra la auditoría
 * básica de ingreso y salida del sistema.
 */
public class SistemaMantenimiento {

    /**
     * Instancia única del sistema (patrón Singleton).
     */
    private static final SistemaMantenimiento instancia = new SistemaMantenimiento();

    /**
     * Obtiene la instancia global del sistema de mantenimiento.
     *
     * @return instancia única de SistemaMantenimiento
     */
    public static SistemaMantenimiento getInstance() {
        return instancia;
    }

    /**
     * Usuario autenticado actualmente en el sistema.
     */
    private Usuario usuarioActual;

    // Controllers
    private UsuarioController usuarioController = new UsuarioController();
    private TecnicoController tecnicoController = new TecnicoController();
    private EquipoController equipoController = new EquipoController();
    private OrdenPreventivaController ordenPreventivaController = new OrdenPreventivaController();
    private OrdenCorrectivaController ordenCorrectivaController = new OrdenCorrectivaController();
    private InventarioRepuestosController repuestoController = new InventarioRepuestosController();
    private AuditoriaMantenimientoController auditoriaController = new AuditoriaMantenimientoController();
    private ProgramaPreventivoController programaPreventivoController = new ProgramaPreventivoController();
    private FallaController fallaController = new FallaController();

    /**
     * Constructor privado para evitar instanciación directa.
     * Aplica el patrón Singleton.
     */
    private SistemaMantenimiento() {}

    // =================== Gestión de sesión ===================

    /**
     * Registra el inicio de sesión de un usuario,
     * almacena su referencia como usuario activo
     * y registra el evento en auditoría.
     *
     * @param usuario usuario autenticado
     */
    public void login(Usuario usuario) {
        this.usuarioActual = usuario;

        auditoriaController.registrarMovimiento(
                usuario.getNombreCompleto(),
                "USUARIO",
                "LOGIN",
                "Inicio de sesión en el sistema con rol: " + usuario.getRol()
        );

        System.out.println("Usuario logueado: " + usuario.getNombreCompleto() + " (" + usuario.getRol() + ")");
    }

    /**
     * Obtiene el usuario actualmente logueado.
     *
     * @return usuario activo o null si no hay sesión
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Finaliza la sesión del usuario activo,
     * registra la acción en auditoría y limpia el estado.
     */
    public void logout() {
        if (usuarioActual != null) {
            auditoriaController.registrarMovimiento(
                    usuarioActual.getNombreCompleto(),
                    "USUARIO",
                    "LOGOUT",
                    "Cierre de sesión"
            );
            System.out.println("Usuario cerró sesión: " + usuarioActual.getNombreCompleto());
        }
        this.usuarioActual = null;
    }

    // =================== Getters de Controllers ===================

    /**
     * @return controlador de gestión de usuarios
     */
    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    /**
     * @return controlador de gestión de técnicos
     */
    public TecnicoController getTecnicoController() {
        return tecnicoController;
    }

    /**
     * @return controlador de gestión de equipos
     */
    public EquipoController getEquipoController() {
        return equipoController;
    }

    /**
     * @return controlador de órdenes preventivas
     */
    public OrdenPreventivaController getOrdenPreventivaController() {
        return ordenPreventivaController;
    }

    /**
     * @return controlador de órdenes correctivas
     */
    public OrdenCorrectivaController getOrdenCorrectivaController() {
        return ordenCorrectivaController;
    }

    /**
     * @return controlador del inventario de repuestos
     */
    public InventarioRepuestosController getInventarioRepuestosController() {
        return repuestoController;
    }

    /**
     * @return controlador de auditoría del sistema
     */
    public AuditoriaMantenimientoController getAuditoriaController() {
        return auditoriaController;
    }

    /**
     * @return controlador de programas preventivos
     */
    public ProgramaPreventivoController getProgramaPreventivoController() {
        return programaPreventivoController;
    }

    /**
     * @return controlador de registro de fallas
     */
    public FallaController getFallaController() {
        return fallaController;
    }

    /**
     * Acceso alternativo a la instancia Singleton del sistema.
     *
     * @return instancia única de SistemaMantenimiento
     */
    public static SistemaMantenimiento getInstancia() {
        return instancia;
    }
}



