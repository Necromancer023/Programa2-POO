package org.example;

/**
 * Enumeración que representa los diferentes roles permitidos
 * dentro del sistema de mantenimiento.
 *
 * Un rol define el perfil de acceso asignado a un usuario,
 * permitiendo controlar sus permisos y las acciones que puede realizar.
 */
public enum Rol {

    /**
     * Rol con permisos administrativos completos sobre el sistema.
     * Puede gestionar usuarios, equipos, programas, órdenes y reportes.
     */
    ADMINISTRADOR,

    /**
     * Rol orientado a labores operativas de mantenimiento.
     * Puede ejecutar órdenes preventivas y correctivas.
     */
    TECNICO,

    /**
     * Rol encargado de supervisar actividades, personal técnico
     * y avance de las operaciones de mantenimiento.
     */
    SUPERVISOR,

    /**
     * Rol responsable de monitorear actividades del sistema
     * con fines de auditoría, control y cumplimiento.
     */
    AUDITOR
}

