package org.example;

public class Repuesto {

    private int id;
    private String nombre;
    private String descripcion;
    private int stockActual;
    private int stockMinimo;
    private String ubicacionAlmacen; // Estantería, pasillo, etc.
    private double costoUnitario;

    // --- Constructor ---

    public Repuesto(int id, String nombre, String descripcion,
                    int stockActual, int stockMinimo,
                    String ubicacionAlmacen, double costoUnitario) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.ubicacionAlmacen = ubicacionAlmacen;
        this.costoUnitario = costoUnitario;
    }

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStockActual() {
        return stockActual;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getUbicacionAlmacen() {
        return ubicacionAlmacen;
    }
    public void setUbicacionAlmacen(String ubicacionAlmacen) {
        this.ubicacionAlmacen = ubicacionAlmacen;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }
    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    // --- Métodos auxiliares ---

    /** Ajusta el stock sumando la cantidad (puede ser negativa). */
    public void ajustarStock(int delta) {
        this.stockActual += delta;
        if (this.stockActual < 0) {
            this.stockActual = 0; // por seguridad
        }
    }

    /** Asigna el stock a un valor exacto (para ajustes físicos). */
    public void setStockActual(int stockActual) {
        this.stockActual = Math.max(stockActual, 0);
    }

    /** ¿Está por debajo del mínimo recomendado? */
    public boolean estaBajoMinimo() {
        return stockActual <= stockMinimo;
    }

    @Override
    public String toString() {
        return "Repuesto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", stockActual=" + stockActual +
                ", stockMinimo=" + stockMinimo +
                ", ubicacionAlmacen='" + ubicacionAlmacen + '\'' +
                ", costoUnitario=" + costoUnitario +
                '}';
    }
}

