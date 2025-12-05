package org.example;

import java.time.LocalDate;
import java.util.List;

public class BitacoraMantenimientoController {

    private BitacoraMantenimientoService bitacoraService;

    // Constructor
    public BitacoraMantenimientoController() {
        this.bitacoraService = new BitacoraMantenimientoService();
    }

    // Crear una nueva bitácora
    public String crearBitacora(int idBitacora, Equipo equipoAsociado) {

        // Validaciones
        if (idBitacora <= 0) {
            return "El ID de la bitácora debe ser mayor que cero.";
        }
        if (equipoAsociado == null) {
            return "Debe seleccionar un equipo asociado.";
        }

        BitacoraMantenimiento nueva = new BitacoraMantenimiento(idBitacora, equipoAsociado);

        boolean creada = bitacoraService.agregarBitacoraMantenimiento(nueva);

        return creada ? "Bitácora creada exitosamente."
                      : "Ya existe una bitácora con ese ID.";
    }

    // Buscar bitácora por ID
    public BitacoraMantenimiento buscarBitacora(int idBitacora) {
        return bitacoraService.buscarBitacoraPorId(idBitacora);
    }

    // Eliminar una bitácora por ID
    public String eliminarBitacora(int idBitacora) {
        boolean eliminada = bitacoraService.eliminarBitacoraPorId(idBitacora);
        return eliminada ? "Bitácora eliminada correctamente."
                         : "No se encontró la bitácora.";
    }

    // Agregar un registro (entrada) a una bitácora
    public String agregarEntrada(int idBitacora, EntradaBitacora.TipoRegistro tipo,
                                 String descripcion) {

        if (descripcion == null || descripcion.isBlank()) {
            return "La descripción no puede estar vacía.";
        }

        if (tipo == null) {
            return "Debe seleccionar un tipo de registro.";
        }

        EntradaBitacora entrada = new EntradaBitacora(LocalDate.now(), tipo, descripcion);

        boolean agregada = bitacoraService.agregarRegistroABitacora(idBitacora, entrada);

        return agregada ? "Entrada agregada a la bitácora."
                        : "No se pudo agregar la entrada. Bitácora no encontrada.";
    }

    // Obtener todas las entradas de una bitácora
    public List<EntradaBitacora> obtenerEntradas(int idBitacora) {
        return bitacoraService.obtenerEntradasDeBitacora(idBitacora);
    }

    // Obtener todas las bitácoras
    public List<BitacoraMantenimiento> obtenerTodasLasBitacoras() {
        return bitacoraService.obtenerTodasLasBitacoras();
    }

}
