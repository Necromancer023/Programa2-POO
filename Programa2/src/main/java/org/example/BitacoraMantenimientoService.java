package org.example;

import java.util.ArrayList;
import java.util.List;

public class BitacoraMantenimientoService {

    private List<BitacoraMantenimiento> bitacoras;

    // Constructor
    public BitacoraMantenimientoService() {
        this.bitacoras = new ArrayList<>();
    }

    // Método para agregar una bitácora de mantenimiento
    public boolean agregarBitacoraMantenimiento(BitacoraMantenimiento bitacora) {
        for (BitacoraMantenimiento b : bitacoras) {
            if (b.getIdBitacora() == bitacora.getIdBitacora()) {
                return false; // Ya existe una bitácora con el mismo ID
            }
        }
        bitacoras.add(bitacora);
        return true;
    }

    // Método para buscar una bitácora por ID
    public BitacoraMantenimiento buscarBitacoraPorId(int idBitacora) {
        for (BitacoraMantenimiento b : bitacoras) {
            if (b.getIdBitacora() == idBitacora) {
                return b;
            }
        }
        return null; // No se encontró la bitácora
    }

    // Método para agregar un registro a una bitácora existente
    public boolean agregarRegistroABitacora(int idBitacora, EntradaBitacora entrada) {
        BitacoraMantenimiento bitacora = buscarBitacoraPorId(idBitacora);
        if (bitacora != null) {
            bitacora.agregarRegistro(entrada);
            return true;
        }
        return false; // No se encontró la bitácora
    }

    // Método para obtener todas las entradas de una bitácora por su ID
    public List<EntradaBitacora> obtenerEntradasDeBitacora(int idBitacora) {
        BitacoraMantenimiento bitacora = buscarBitacoraPorId(idBitacora);
        if (bitacora != null) {
            return bitacora.getRegistros();
        }
        return null; // No se encontró la bitácora
    }

    // Método para eliminar una bitácora por su ID
    public boolean eliminarBitacoraPorId(int idBitacora) {
        BitacoraMantenimiento bitacora = buscarBitacoraPorId(idBitacora);
        if (bitacora != null) {
            bitacoras.remove(bitacora);
            return true;
        }
        return false; // No se encontró la bitácora
    }

    // Método para obtener todas las bitacoras
    public List<BitacoraMantenimiento> obtenerTodasLasBitacoras() {
        return bitacoras;
    }
}
