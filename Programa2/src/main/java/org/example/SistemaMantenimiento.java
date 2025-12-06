package org.example;

public class SistemaMantenimiento {

    private static final SistemaMantenimiento instancia = new SistemaMantenimiento();

    public static SistemaMantenimiento getInstance() {
        return instancia;
    }

    private Usuario usuarioActual;

    // Controllers accesibles por la interfaz
    private UsuarioController usuarioController = new UsuarioController();
    private TecnicoController tecnicoController = new TecnicoController();
    private EquipoController equipoController = new EquipoController();
    private OrdenPreventivaController ordenPreventivaController = new OrdenPreventivaController();
    private OrdenCorrectivaController ordenCorrectivaController = new OrdenCorrectivaController();
    private InventarioRepuestosController repuestoController = new InventarioRepuestosController();
    private AuditoriaMantenimientoController auditoriaController = new AuditoriaMantenimientoController();
    private ProgramaPreventivoController programaPreventivoController = new ProgramaPreventivoController();

    private SistemaMantenimiento() {}

    // LOGIN DEL SISTEMA
    public void login(Usuario usuario) {
        this.usuarioActual = usuario;

        auditoriaController.registrarEvento(
                usuario.getNombreCompleto(),
                "USUARIO",
                "LOGIN",
                "Inicio de sesión en el sistema"
        );
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
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
}


