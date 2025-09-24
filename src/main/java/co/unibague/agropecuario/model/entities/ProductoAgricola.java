package co.unibague.agropecuario.model.entities;

import java.time.LocalDate;

/**
 * Clase que representa productos agrícolas específicos del Tolima.
 * Hereda de ProductoAgropecuario y sobrescribe el método de cálculo de rentabilidad.
 * Implementa el principio de Sustitución de Liskov (LSP).
 */
public class ProductoAgricola extends ProductoAgropecuario {

    private String tipoSuelo;
    private String temporada;
    private double hectareasCultivadas;
    private double rendimientoPorHa;
    private String tipoCultivo;
    private String codigoFinca;

    /**
     * Constructor completo para ProductoAgricola
     */
    public ProductoAgricola(String id, String nombre, LocalDate fechaProduccion,
                            double costoProduccion, double cantidadProducida,
                            double precioVenta, String tipoSuelo, String temporada,
                            double hectareasCultivadas, double rendimientoPorHa,
                            String tipoCultivo) {
        super(id, nombre, fechaProduccion, costoProduccion, cantidadProducida, precioVenta);
        this.tipoSuelo = tipoSuelo;
        this.temporada = temporada;
        this.hectareasCultivadas = hectareasCultivadas;
        this.rendimientoPorHa = rendimientoPorHa;
        this.tipoCultivo = tipoCultivo;
    }

    /**
     * Constructor por defecto
     */
    public ProductoAgricola() {
        super();
        this.temporada = "Todo el año";
        this.tipoSuelo = "Franco";
    }

    /**
     * Calcula la rentabilidad específica para productos agrícolas.
     * Considera factores como hectáreas cultivadas, rendimiento y costos específicos.
     * @return rentabilidad calculada en pesos por hectárea
     */
    @Override
    public double calcularRentabilidad() {
        if (hectareasCultivadas == 0) return 0;

        double ingresoTotal = calcularIngresoTotal();
        double costoTotal = costoProduccion * hectareasCultivadas;
        double utilidadNeta = ingresoTotal - costoTotal;

        // Rentabilidad por hectárea
        return utilidadNeta / hectareasCultivadas;
    }

    /**
     * Calcula la producción total basada en hectáreas y rendimiento
     * @return producción total esperada
     */
    public double calcularProduccionTotal() {
        return hectareasCultivadas * rendimientoPorHa;
    }

    /**
     * Calcula el costo por kilogramo producido
     * @return costo unitario por kg
     */
    public double calcularCostoPorKg() {
        if (cantidadProducida == 0) return 0;
        return costoProduccion / cantidadProducida;
    }

    /**
     * Determina si es temporada alta para el cultivo
     * @return true si está en temporada alta
     */
    public boolean esTemporadaAlta() {
        return temporada.toLowerCase().contains("alta") ||
                temporada.toLowerCase().contains("seca");
    }

    // Getters y Setters específicos
    public String getTipoSuelo() { return tipoSuelo; }
    public void setTipoSuelo(String tipoSuelo) { this.tipoSuelo = tipoSuelo; }

    public String getTemporada() { return temporada; }
    public void setTemporada(String temporada) { this.temporada = temporada; }

    public double getHectareasCultivadas() { return hectareasCultivadas; }
    public void setHectareasCultivadas(double hectareasCultivadas) { this.hectareasCultivadas = hectareasCultivadas; }

    public double getRendimientoPorHa() { return rendimientoPorHa; }
    public void setRendimientoPorHa(double rendimientoPorHa) { this.rendimientoPorHa = rendimientoPorHa; }

    public String getTipoCultivo() { return tipoCultivo; }
    public void setTipoCultivo(String tipoCultivo) { this.tipoCultivo = tipoCultivo; }

    public String getCodigoFinca() { return codigoFinca; }
    public void setCodigoFinca(String codigoFinca) { this.codigoFinca = codigoFinca; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Tipo: %s | Hectáreas: %.2f | Rendimiento: %.2f ton/ha | Suelo: %s",
                tipoCultivo, hectareasCultivadas, rendimientoPorHa, tipoSuelo);
    }
}