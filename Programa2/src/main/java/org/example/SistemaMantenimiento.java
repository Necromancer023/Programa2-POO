package org.example;

public class SistemaMantenimiento {

    private Usuario usuarioActual;

    // Controllers principales del sistema
    public EquipoController equipoController = new EquipoController();
    public OrdenPreventivaController ordenPreventivaController = new OrdenPreventivaController();
    public OrdenCorrectivaController ordenCorrectivaController = new OrdenCorrectivaController();
    public AuditoriaMantenimientoController auditoriaController = new AuditoriaMantenimientoController();
    public TecnicoController tecnicoController = new TecnicoController();
    public ProgramaPreventivoController programaPreventivoController = new ProgramaPreventivoController();

    // Constructor vacío por si la GUI lo usa
    public SistemaMantenimiento() {}

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
}

