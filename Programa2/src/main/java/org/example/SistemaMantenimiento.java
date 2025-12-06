package org.example;

public class SistemaMantenimiento {

    private static final SistemaMantenimiento instancia = new SistemaMantenimiento();

    public static SistemaMantenimiento getInstance() {
        return instancia;
    }

    private Usuario usuarioActual;  // ← Esto debe existir

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
    
    private SistemaMantenimiento() {}

    // ===== Gestión de sesión =====
    // Iniciar sesión
    public void login(Usuario usuario) {
        this.usuarioActual = usuario;

        auditoriaController.registrarEvento(
                usuario.getNombreCompleto(),
                "USUARIO",
                "LOGIN",
                "Inicio de sesión en el sistema con rol: " + usuario.getRol()
        );
        
        System.out.println("Usuario logueado: " + usuario.getNombreCompleto() + " (" + usuario.getRol() + ")");
    }

    // Obtener usuario actual
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    // Cerrar sesión
    public void logout() {
        if (usuarioActual != null) {
            auditoriaController.registrarEvento(
                    usuarioActual.getNombreCompleto(),
                    "USUARIO",
                    "LOGOUT",
                    "Cierre de sesión"
            );
            System.out.println("Usuario cerró sesión: " + usuarioActual.getNombreCompleto());
        }
        this.usuarioActual = null;
    }

    // ===== Getters de controllers =====
    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public TecnicoController getTecnicoController() {
        return tecnicoController;
    }

    public EquipoController getEquipoController() {
        return equipoController;
    }

    public OrdenPreventivaController getOrdenPreventivaController() {
        return ordenPreventivaController;
    }

    public OrdenCorrectivaController getOrdenCorrectivaController() {
        return ordenCorrectivaController;
    }

    public InventarioRepuestosController getInventarioRepuestosController() {
        return repuestoController;
    }

    public AuditoriaMantenimientoController getAuditoriaController() {
        return auditoriaController;
    }

    public ProgramaPreventivoController getProgramaPreventivoController() {
        return programaPreventivoController;
    }
    public FallaController getFallaController() {
        return fallaController;
    }

    public static SistemaMantenimiento getInstancia() {
        return instancia;
    }
}


