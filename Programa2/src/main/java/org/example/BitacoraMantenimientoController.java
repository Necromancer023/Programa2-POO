package org.example;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador responsable de gestionar las operaciones sobre bitácoras de mantenimiento.
 * 
 * Encapsula la lógica de creación, consulta, actualización de registros y eliminación,
 * delegando el almacenamiento y manejo interno al servicio correspondiente.
 */
public class BitacoraMantenimientoController {

    /** Servicio encargado del almacenamiento y administración de bitácoras. */
    private BitacoraMantenimientoService bitacoraService;

    /**
     * Construye un controlador inicializando el servicio asociado.
     */
    public BitacoraMantenimientoController() {
        this.bitacoraService = new BitacoraMantenimientoService();
    }

    /**
     * Crea una nueva bitácora para un equipo específico.
     *
     * @param idBitacora identificador único de la bitácora
     * @param equipoAsociado equipo al cual se vinculará la bitácora
     * @return mensaje informando éxito o causa de error
     */
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

    /**
     * Busca una bitácora existente por su ID.
     *
     * @param idBitacora identificador de búsqueda
     * @return bitácora encontrada o null si no existe
     */
    public BitacoraMantenimiento buscarBitacora(int idBitacora) {
        return bitacoraService.buscarBitacoraPorId(idBitacora);
    }

    /**
     * Elimina una bitácora según su identificador.
     *
     * @param idBitacora ID de la bitácora a eliminar
     * @return mensaje indicativo del resultado de la operación
     */
    public String eliminarBitacora(int idBitacora) {
        boolean eliminada = bitacoraService.eliminarBitacoraPorId(idBitacora);
        return eliminada ? "Bitácora eliminada correctamente."
                         : "No se encontró la bitácora.";
    }

    /**
     * Agrega una entrada de mantenimiento a una bitácora.
     *
     * @param idBitacora ID de la bitácora destinataria
     * @param tipo tipo de registro (preventivo, correctivo, inspección, etc.)
     * @param descripcion detalle textual de la acción registrada
     * @return mensaje confirmando éxito o indicando error
     */
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

    /**
     * Obtiene todas las entradas asociadas a una bitácora.
     *
     * @param idBitacora identificador de la bitácora
     * @return lista de registros almacenados
     */
    public List<EntradaBitacora> obtenerEntradas(int idBitacora) {
        return bitacoraService.obtenerEntradasDeBitacora(idBitacora);
    }

    /**
     * Recupera todas las bitácoras registradas en el sistema.
     *
     * @return colección de bitácoras existentes
     */
    public List<BitacoraMantenimiento> obtenerTodasLasBitacoras() {
        return bitacoraService.obtenerTodasLasBitacoras();
    }

}

