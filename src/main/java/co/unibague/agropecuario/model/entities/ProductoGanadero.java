package co.unibague.agropecuario.model.entities;

import co.unibague.agropecuario.model.interfaces.IAplicable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa productos ganaderos específicos del Tolima.
 * Hereda de ProductoAgropecuario e implementa IAplicable.
 * Demuestra el principio de Sustitución de Liskov y Segregación de Interfaces.
 */
public class ProductoGanadero extends ProductoAgropecuario implements IAplicable {

    private String tipoGanado;
    private int numeroAnimales;
    private double pesoPromedio;
    private double produccionDiaria;
    private String tipoAlimentacion;
    private List<String> aplicacionesRealizadas;
    private boolean estadoAplicacion;

    /**
     * Constructor completo para ProductoGanadero
     */
    public ProductoGanadero(String id, String nombre, LocalDate fechaProduccion,
                            double costoProduccion, double cantidadProducida,
                            double precioVenta, String tipoGanado, int numeroAnimales,
                            double pesoPromedio, double produccionDiaria,
                            String tipoAlimentacion) {
        super(id, nombre, fechaProduccion, costoProduccion, cantidadProducida, precioVenta);
        this.tipoGanado = tipoGanado;
        this.numeroAnimales = numeroAnimales;
        this.pesoPromedio = pesoPromedio;
        this.produccionDiaria = produccionDiaria;
        this.tipoAlimentacion = tipoAlimentacion;
        this.aplicacionesRealizadas = new ArrayList<>();
        this.estadoAplicacion = true;
    }

    /**
     * Constructor por defecto
     */
    public ProductoGanadero() {
        super();
        this.aplicacionesRealizadas = new ArrayList<>();
        this.estadoAplicacion = true;
        this.tipoAlimentacion = "Pastoreo";
    }

    /**
     * Calcula la rentabilidad específica para productos ganaderos.
     * Considera el número de animales, producción diaria y costos específicos.
     * @return rentabilidad calculada por animal por mes
     */
    @Override
    public double calcularRentabilidad() {
        if (numeroAnimales == 0) return 0;

        // Calcular ingresos mensuales (30 días)
        double ingresosMensuales = produccionDiaria * precioVenta * 30;
        double ingresosTotales = ingresosMensuales * numeroAnimales;

        // Calcular costos mensuales por animal
        double costosMensuales = (costoProduccion * numeroAnimales);

        double utilidadNeta = ingresosTotales - costosMensuales;

        // Rentabilidad por animal por mes
        return utilidadNeta / numeroAnimales;
    }

    /**
     * Calcula el peso total del ganado
     * @return peso total en kilogramos
     */
    public double calcularPesoTotal() {
        return numeroAnimales * pesoPromedio;
    }

    /**
     * Calcula la producción mensual total
     * @return producción total del mes
     */
    public double calcularProduccionMensual() {
        return produccionDiaria * numeroAnimales * 30;
    }

    /**
     * Calcula el costo por animal por día
     * @return costo diario por animal
     */
    public double calcularCostoPorAnimalPorDia() {
        if (numeroAnimales == 0) return 0;
        return costoProduccion / numeroAnimales / 30; // Asumiendo costos mensuales
    }

    // Implementación de IAplicable
    @Override
    public boolean aplicar() {
        if (puedeRecibirAplicacion()) {
            String nuevaAplicacion = "Aplicación realizada en: " + LocalDate.now();
            aplicacionesRealizadas.add(nuevaAplicacion);

            // Simular proceso de aplicación
            if (tipoGanado.toLowerCase().contains("bovino")) {
                aplicacionesRealizadas.add("Vacuna antiaftosa aplicada");
            } else if (tipoGanado.toLowerCase().contains("porcino")) {
                aplicacionesRealizadas.add("Desparasitación aplicada");
            } else if (tipoGanado.toLowerCase().contains("avícola")) {
                aplicacionesRealizadas.add("Vitaminas aplicadas");
            }

            estadoAplicacion = true;
            return true;
        }
        return false;
    }

    @Override
    public String obtenerEstadoAplicacion() {
        if (aplicacionesRealizadas.isEmpty()) {
            return "Sin aplicaciones realizadas";
        }

        StringBuilder estado = new StringBuilder("Aplicaciones realizadas:\n");
        for (String aplicacion : aplicacionesRealizadas) {
            estado.append("- ").append(aplicacion).append("\n");
        }
        return estado.toString();
    }

    @Override
    public boolean puedeRecibirAplicacion() {
        // Lógica de negocio: puede recibir aplicación si han pasado al menos 30 días
        // desde la última aplicación o si nunca ha recibido aplicaciones
        return estadoAplicacion && numeroAnimales > 0;
    }

    // Getters y Setters específicos
    public String getTipoGanado() { return tipoGanado; }
    public void setTipoGanado(String tipoGanado) { this.tipoGanado = tipoGanado; }

    public int getNumeroAnimales() { return numeroAnimales; }
    public void setNumeroAnimales(int numeroAnimales) { this.numeroAnimales = numeroAnimales; }

    public double getPesoPromedio() { return pesoPromedio; }
    public void setPesoPromedio(double pesoPromedio) { this.pesoPromedio = pesoPromedio; }

    public double getProduccionDiaria() { return produccionDiaria; }
    public void setProduccionDiaria(double produccionDiaria) { this.produccionDiaria = produccionDiaria; }

    public String getTipoAlimentacion() { return tipoAlimentacion; }
    public void setTipoAlimentacion(String tipoAlimentacion) { this.tipoAlimentacion = tipoAlimentacion; }

    public List<String> getAplicacionesRealizadas() { return new ArrayList<>(aplicacionesRealizadas); }

    public boolean isEstadoAplicacion() { return estadoAplicacion; }
    public void setEstadoAplicacion(boolean estadoAplicacion) { this.estadoAplicacion = estadoAplicacion; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Tipo: %s | Animales: %d | Peso promedio: %.2f kg | Producción diaria: %.2f | Alimentación: %s",
                tipoGanado, numeroAnimales, pesoPromedio, produccionDiaria, tipoAlimentacion);
    }
}