package org.example;

public class SistemaMantenimiento {

    private Usuario usuarioActual;

    private UsuarioController usuarioController = new UsuarioController();
    private EquipoController equipoController = new EquipoController();
    private OrdenPreventivaController ordenPreventivaController = new OrdenPreventivaController();
    private OrdenCorrectivaController ordenCorrectivaController = new OrdenCorrectivaController();
    private AuditoriaMantenimientoController auditoriaController = new AuditoriaMantenimientoController();
    private TecnicoController tecnicoController = new TecnicoController();
    private ProgramaPreventivoController programaPreventivoController = new ProgramaPreventivoController();
    private InventarioRepuestosController repuestoController = new InventarioRepuestosController();

    public SistemaMantenimiento() {}

    // ---------- LOGIN ----------
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

    // ---------- GETTERS PARA CONTROLADORES ----------
    public UsuarioController getUsuarioController() { return usuarioController; }
    public EquipoController getEquipoController() { return equipoController; }
    public OrdenPreventivaController getOrdenPreventivaController() { return ordenPreventivaController; }
    public OrdenCorrectivaController getOrdenCorrectivaController() { return ordenCorrectivaController; }
    public AuditoriaMantenimientoController getAuditoriaController() { return auditoriaController; }
    public TecnicoController getTecnicoController() { return tecnicoController; }
    public ProgramaPreventivoController getProgramaPreventivoController() { return programaPreventivoController; }
    public InventarioRepuestosController getRepuestoController() { return repuestoController; }
}


