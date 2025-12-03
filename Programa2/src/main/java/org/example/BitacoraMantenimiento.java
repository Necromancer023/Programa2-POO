package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BitacoraMantenimiento {

    private int idBitacora;
    private Equipo equipoAsociadoBitacora;
    private List<EntradaBitacora> registros;
    private LocalDate ultimaActualizacion;

    // -- Constructor --
    public BitacoraMantenimiento(int idBitacora, Equipo equipoAsociadoBitacora) {
        this.idBitacora = idBitacora;
        this.equipoAsociadoBitacora = equipoAsociadoBitacora;
        this.registros = new ArrayList<>();
        this.ultimaActualizacion = LocalDate.now();
    }

    // -- Getters --

    public int getIdBitacora() {
        return idBitacora;
    }
    public Equipo getEquipoAsociadoBitacora() {
        return equipoAsociadoBitacora;
    }
    public List<EntradaBitacora> getRegistros() {
        return registros;
    }
    public LocalDate getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    // -- Métodos --

    public void agregarRegistro(EntradaBitacora nuevoRegistro) {
        this.registros.add(nuevoRegistro);
        this.ultimaActualizacion = LocalDate.now();
    }

    public void imprimirBitacora() {
        System.out.println("Bitacora de Mantenimiento para el Equipo: " + equipoAsociadoBitacora.getDescripcion());
        for (EntradaBitacora registro : registros) {
            System.out.println(registro);
        }
    }

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
