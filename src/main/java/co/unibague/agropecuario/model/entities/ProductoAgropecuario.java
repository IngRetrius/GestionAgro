package co.unibague.agropecuario.model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase abstracta que representa la base de todos los productos agropecuarios.
 * Implementa los principios SOLID: Single Responsibility y Open/Closed.
 */
public abstract class ProductoAgropecuario {

    protected String id;
    protected String nombre;
    protected LocalDate fechaProduccion;
    protected double costoProduccion;
    protected double cantidadProducida;
    protected double precioVenta;

    /**
     * Constructor base para productos agropecuarios
     */
    public ProductoAgropecuario(String id, String nombre, LocalDate fechaProduccion,
                                double costoProduccion, double cantidadProducida,
                                double precioVenta) {
        this.id = id;
        this.nombre = nombre;
        this.fechaProduccion = fechaProduccion;
        this.costoProduccion = costoProduccion;
        this.cantidadProducida = cantidadProducida;
        this.precioVenta = precioVenta;
    }

    /**
     * Constructor por defecto
     */
    public ProductoAgropecuario() {
        this.fechaProduccion = LocalDate.now();
    }

    /**
     * Método abstracto para calcular la rentabilidad específica de cada tipo de producto.
     * Implementa polimorfismo permitiendo que cada subclase defina su propia lógica.
     * @return valor de rentabilidad calculado según el tipo de producto
     */
    public abstract double calcularRentabilidad();

    /**
     * Calcula el margen de ganancia básico
     * @return margen de ganancia en porcentaje
     */
    public double calcularMargenGanancia() {
        if (costoProduccion == 0) return 0;
        return ((precioVenta - costoProduccion) / costoProduccion) * 100;
    }

    /**
     * Calcula el ingreso total basado en cantidad producida y precio
     * @return ingreso total
     */
    public double calcularIngresoTotal() {
        return cantidadProducida * precioVenta;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaProduccion() { return fechaProduccion; }
    public void setFechaProduccion(LocalDate fechaProduccion) { this.fechaProduccion = fechaProduccion; }

    public double getCostoProduccion() { return costoProduccion; }
    public void setCostoProduccion(double costoProduccion) { this.costoProduccion = costoProduccion; }

    public double getCantidadProducida() { return cantidadProducida; }
    public void setCantidadProducida(double cantidadProducida) { this.cantidadProducida = cantidadProducida; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("ID: %s | %s | Fecha: %s | Cantidad: %.2f | Costo: $%.2f | Precio: $%.2f",
                id, nombre, fechaProduccion.format(formatter), cantidadProducida,
                costoProduccion, precioVenta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductoAgropecuario that = (ProductoAgropecuario) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}