package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de la gestión de técnicos.
 * Almacena, consulta, actualiza y elimina registros relacionados con técnicos.
 */
public class TecnicoService {

    private List<Tecnico> tecnicos;

    /**
     * Constructor que inicializa la lista de técnicos en memoria.
     */
    public TecnicoService() {
        this.tecnicos = new ArrayList<>();
    }

    /**
     * Registra un técnico en la lista solo si su ID no se encuentra repetido.
     *
     * @param tecnico objeto Técnico a registrar
     * @return true si el registro fue exitoso, false si ya existe uno con el mismo ID
     */
    public boolean agregarTecnico(Tecnico tecnico) {

        for (Tecnico t : tecnicos) {
            if (t.getIdTecnico() == tecnico.getIdTecnico()) {
                return false; // ID repetido
            }
        }

        tecnicos.add(tecnico);
        return true;
    }

    /**
     * Busca un técnico existente basado en su identificador.
     *
     * @param idTecnico identificador numérico del técnico
     * @return el objeto Técnico encontrado o null si no existe
     */
    public Tecnico buscarTecnicoPorId(int idTecnico) {
        for (Tecnico t : tecnicos) {
            if (t.getIdTecnico() == idTecnico) {
                return t;
            }
        }
        return null;
    }

    /**
     * Elimina un técnico según su ID si existe en el sistema.
     *
     * @param idTecnico identificador del técnico
     * @return true si fue eliminado correctamente, false si no se encontró
     */
    public boolean eliminarTecnico(int idTecnico) {
        Tecnico t = buscarTecnicoPorId(idTecnico);
        if (t != null) {
            tecnicos.remove(t);
            return true;
        }
        return false;
    }

    /**
     * Cambia el estado activo/inactivo de un técnico si existe.
     *
     * @param idTecnico identificador del técnico
     * @param activo estado a asignar true o false
     * @return true si la actualización fue exitosa, false si no se encontró
     */
    public boolean cambiarEstado(int idTecnico, boolean activo) {
        Tecnico t = buscarTecnicoPorId(idTecnico);
        if (t != null) {
            t.setActivo(activo);
            return true;
        }
        return false;
    }

    /**
     * Agrega una certificación a un técnico ya registrado.
     *
     * @param idTecnico identificador del técnico
     * @param cert texto de la certificación
     * @return true si fue agregada exitosamente, false si el técnico no existe
     */
    public boolean agregarCertificacion(int idTecnico, String cert) {
        Tecnico t = buscarTecnicoPorId(idTecnico);
        if (t != null) {
            t.agregarCertificacion(cert);
            return true;
        }
        return false;
    }

    /**
     * Obtiene la lista completa de técnicos registrados.
     *
     * @return lista de objetos Técnico
     */
    public List<Tecnico> obtenerTecnicos() {
        return tecnicos;
    }
}

