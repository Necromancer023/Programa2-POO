package org.example;

/**
 * Representa un repuesto o consumible del inventario de mantenimiento.
 * Lleva control de stock, ubicación en almacén y costo unitario.
 */
public class Repuesto {

    private int id;
    private String nombre;
    private String descripcion;
    private int stockActual;
    private int stockMinimo;
    private String ubicacionAlmacen; // Estantería, pasillo, etc.
    private double costoUnitario;

    // ---------------- CONSTRUCTOR ----------------

    /**
     * Crea un repuesto con sus datos principales.
     */
    public Repuesto(int id,
                    String nombre,
                    String descripcion,
                    int stockActual,
                    int stockMinimo,
                    String ubicacionAlmacen,
                    double costoUnitario) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stockActual = Math.max(stockActual, 0);
        this.stockMinimo = stockMinimo;
        this.ubicacionAlmacen = ubicacionAlmacen;
        this.costoUnitario = costoUnitario;
    }

    // ---------------- GETTERS / SETTERS ----------------

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

    // ---------------- MÉTODOS DE INVENTARIO ----------------

    /**
     * Ajusta el stock sumando la cantidad indicada.
     * Puede ser positiva (entrada) o negativa (consumo).
     * Si el resultado es menor a cero, el stock se corrige a cero.
     */
    public void ajustarStock(int delta) {
        this.stockActual += delta;
        if (this.stockActual < 0) {
            this.stockActual = 0;
        }
    }

    /**
     * Ajusta el stock con una asignación directa.
     * Se asegura que nunca quede negativo.
     */
    public void setStockActual(int stockActual) {
        this.stockActual = Math.max(stockActual, 0);
    }

    /**
     * Indica si el stock está por debajo del mínimo definido.
     */
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


