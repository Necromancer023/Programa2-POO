package org.example;

import java.time.LocalDate;

/**
 * Representa una entrada registrada dentro de una bitácora de mantenimiento.
 * Cada entrada documenta un evento ocurrido sobre un equipo en una fecha específica.
 */
public class EntradaBitacora {

    /**
     * Enumeración de los tipos de eventos que pueden registrarse en la bitácora.
     * Permite categorizar el evento según su naturaleza.
     */
    public enum TipoRegistro {
        /** Evento de mantenimiento preventivo ejecutado o programado. */
        MANTENIMIENTO_PREVENTIVO,
        /** Evento de mantenimiento correctivo realizado tras una falla. */
        MANTENIMIENTO_CORRECTIVO,
        /** Registro de cambio de estado del equipo. */
        CAMBIO_ESTADO,
        /** Observación o anotación general sobre el equipo. */
        OBSERVACION
    }

    /** Fecha en que se registró el evento de bitácora. */
    private LocalDate fecha;

    /** Tipo del evento de acuerdo con su categoría de mantenimiento. */
    private TipoRegistro tipo;

    /** Descripción detallada de lo ocurrido o registrado. */
    private String descripcion;

    /**
     * Construye una nueva entrada para la bitácora de mantenimiento.
     *
     * @param fecha fecha del registro del evento
     * @param tipo categoría del evento documentado
     * @param descripcion detalle narrativo del evento
     */
    public EntradaBitacora(LocalDate fecha, TipoRegistro tipo, String descripcion) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha del evento documentado.
     *
     * @return fecha del registro
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Devuelve el tipo de evento registrado en la bitácora.
     *
     * @return categoría del registro
     */
    public TipoRegistro getTipo() {
        return tipo;
    }

    /**
     * Recupera la descripción textual asociada al evento redactado.
     *
     * @return texto de descripción del evento
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Representación textual estandarizada de la entrada de bitácora,
     * útil para visualización y depuración.
     *
     * @return cadena que muestra fecha, tipo y descripción del evento
     */
    @Override
    public String toString() {
        return "[" + fecha + "][" + tipo + "] " + descripcion;
    }
}
