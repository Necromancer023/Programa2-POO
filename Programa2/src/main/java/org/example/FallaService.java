package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de administrar el catálogo de fallas del sistema.
 * Implementa operaciones básicas de almacenamiento en memoria incluyendo
 * registro, búsqueda, eliminación y consulta general de fallas.
 */
public class FallaService {

    /** Colección interna que almacena todas las fallas registradas. */
    private List<Falla> fallas;

    /**
     * Construye el servicio inicializando la lista de fallas.
     * La estructura se mantiene en memoria.
     */
    public FallaService() {
        this.fallas = new ArrayList<>();
    }

    /**
     * Registra una nueva falla si no existe otra con el mismo identificador.
     *
     * @param falla objeto que representa la falla a ser registrada
     * @return {@code true} si la operación fue exitosa, {@code false} si el ID ya existe
     */
    public boolean registrarFalla(Falla falla) {
        for (Falla f : fallas) {
            if (f.getIdFalla() == falla.getIdFalla()) {
                return false; // ya existe
            }
        }
        fallas.add(falla);
        return true;
    }

    /**
     * Busca una falla según su identificador.
     *
     * @param id identificador de la falla buscada
     * @return instancia encontrada o {@code null} si no existe coincidencia
     */
    public Falla buscarFalla(int id) {
        for (Falla f : fallas) {
            if (f.getIdFalla() == id) {
                return f;
            }
        }
        return null;
    }

    /**
     * Elimina una falla existente siempre que se encuentre registrada.
     *
     * @param id identificador de la falla a eliminar
     * @return {@code true} si fue eliminada, {@code false} si no se encontró la falla
     */
    public boolean eliminarFalla(int id) {
        Falla f = buscarFalla(id);
        if (f != null) {
            fallas.remove(f);
            return true;
        }
        return false;
    }

    /**
     * Obtiene todas las fallas almacenadas en el catálogo.
     *
     * @return lista completa de fallas registradas
     */
    public List<Falla> obtenerFallas() {
        return fallas;
    }
}


