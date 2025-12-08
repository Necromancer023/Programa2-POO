package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar el almacenamiento y la manipulación
 * de bitácoras de mantenimiento dentro del sistema.
 *
 * Provee operaciones para registrar bitácoras, buscar por identificador,
 * agregar entradas asociadas y eliminar registros.
 */
public class BitacoraMantenimientoService {

    /** Lista interna que almacena todas las bitácoras registradas. */
    private List<BitacoraMantenimiento> bitacoras;

    /**
     * Construye un servicio de bitácoras inicializando la colección interna.
     */
    public BitacoraMantenimientoService() {
        this.bitacoras = new ArrayList<>();
    }

    /**
     * Agrega una nueva bitácora siempre que su identificador no exista previamente.
     *
     * @param bitacora instancia de bitácora a registrar
     * @return true si fue agregada correctamente, false si ya existía otra con el mismo ID
     */
    public boolean agregarBitacoraMantenimiento(BitacoraMantenimiento bitacora) {
        for (BitacoraMantenimiento b : bitacoras) {
            if (b.getIdBitacora() == bitacora.getIdBitacora()) {
                return false; // Ya existe una bitácora con el mismo ID
            }
        }
        bitacoras.add(bitacora);
        return true;
    }

    /**
     * Busca una bitácora según su identificador.
     *
     * @param idBitacora identificador de la bitácora
     * @return bitácora asociada o null si no existe
     */
    public BitacoraMantenimiento buscarBitacoraPorId(int idBitacora) {
        for (BitacoraMantenimiento b : bitacoras) {
            if (b.getIdBitacora() == idBitacora) {
                return b;
            }
        }
        return null; // No se encontró la bitácora
    }

    /**
     * Agrega un registro de mantenimiento a una bitácora existente.
     *
     * @param idBitacora identificador de la bitácora destinataria
     * @param entrada registro a insertar
     * @return true si la operación fue exitosa, false si la bitácora no existe
     */
    public boolean agregarRegistroABitacora(int idBitacora, EntradaBitacora entrada) {
        BitacoraMantenimiento bitacora = buscarBitacoraPorId(idBitacora);
        if (bitacora != null) {
            bitacora.agregarRegistro(entrada);
            return true;
        }
        return false; // No se encontró la bitácora
    }

    /**
     * Recupera todos los registros asociados a una bitácora.
     *
     * @param idBitacora identificador de búsqueda
     * @return lista de entradas o null si la bitácora no existe
     */
    public List<EntradaBitacora> obtenerEntradasDeBitacora(int idBitacora) {
        BitacoraMantenimiento bitacora = buscarBitacoraPorId(idBitacora);
        if (bitacora != null) {
            return bitacora.getRegistros();
        }
        return null; // No se encontró la bitácora
    }

    /**
     * Elimina una bitácora según su identificador.
     *
     * @param idBitacora identificador de la bitácora
     * @return true si se eliminó, false si no existe
     */
    public boolean eliminarBitacoraPorId(int idBitacora) {
        BitacoraMantenimiento bitacora = buscarBitacoraPorId(idBitacora);
        if (bitacora != null) {
            bitacoras.remove(bitacora);
            return true;
        }
        return false; // No se encontró la bitácora
    }

    /**
     * Obtiene todas las bitácoras registradas en el sistema.
     *
     * @return colección completa de bitácoras
     */
    public List<BitacoraMantenimiento> obtenerTodasLasBitacoras() {
        return bitacoras;
    }
}

