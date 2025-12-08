package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una bitácora de mantenimiento asociada a un equipo.
 * 
 * Esta clase almacena los registros históricos de intervenciones,
 * actualizaciones o eventos relacionados con un equipo específico,
 * permitiendo consultar su evolución a lo largo del tiempo.
 */
public class BitacoraMantenimiento {

    /** Identificador único de la bitácora. */
    private int idBitacora;

    /** Equipo al que pertenece la bitácora. */
    private Equipo equipoAsociadoBitacora;

    /** Lista de registros históricos almacenados. */
    private List<EntradaBitacora> registros;

    /** Fecha de última actualización de la bitácora. */
    private LocalDate ultimaActualizacion;

    /**
     * Crea una bitácora para un equipo determinado.
     *
     * @param idBitacora identificador de la bitácora
     * @param equipoAsociadoBitacora equipo vinculado a los registros
     */
    public BitacoraMantenimiento(int idBitacora, Equipo equipoAsociadoBitacora) {
        this.idBitacora = idBitacora;
        this.equipoAsociadoBitacora = equipoAsociadoBitacora;
        this.registros = new ArrayList<>();
        this.ultimaActualizacion = LocalDate.now();
    }

    // ---- Getters ----

    /**
     * Obtiene el identificador de la bitácora.
     *
     * @return id de la bitácora
     */
    public int getIdBitacora() {
        return idBitacora;
    }

    /**
     * Retorna el equipo asociado a la bitácora.
     *
     * @return equipo vinculado
     */
    public Equipo getEquipoAsociadoBitacora() {
        return equipoAsociadoBitacora;
    }

    /**
     * Devuelve los registros almacenados en la bitácora.
     *
     * @return lista de entradas registradas
     */
    public List<EntradaBitacora> getRegistros() {
        return registros;
    }

    /**
     * Devuelve la fecha en la que la bitácora fue actualizada por última vez.
     *
     * @return fecha de modificación más reciente
     */
    public LocalDate getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    // ---- Métodos de operación ----

    /**
     * Agrega un nuevo registro de mantenimiento a la bitácora.
     * También actualiza la fecha de última modificación.
     *
     * @param nuevoRegistro entrada histórica que se almacenará
     */
    public void agregarRegistro(EntradaBitacora nuevoRegistro) {
        this.registros.add(nuevoRegistro);
        this.ultimaActualizacion = LocalDate.now();
    }

    /**
     * Imprime en consola el detalle de los registros asociados a esta bitácora.
     */
    public void imprimirBitacora() {
        System.out.println("Bitacora de Mantenimiento para el Equipo: " + equipoAsociadoBitacora.getDescripcion());
        for (EntradaBitacora registro : registros) {
            System.out.println(registro);
        }
    }

    /**
     * Representación textual del estado actual de la bitácora.
     *
     * @return descripción con identificador, equipo, cantidad de registros
     *         y última fecha de actualización
     */
    @Override
    public String toString() {
        return "BitacoraMantenimiento{" +
                "idBitacora=" + idBitacora +
                ", equipoAsociadoBitacora=" + equipoAsociadoBitacora.getDescripcion() +
                ", registros=" + registros.size() +
                ", ultimaActualizacion=" + ultimaActualizacion +
                '}';
    }
}

