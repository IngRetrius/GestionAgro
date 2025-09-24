package co.unibague.agropecuario.controller;

import co.unibague.agropecuario.model.entities.ProductoAgricola;
import co.unibague.agropecuario.model.entities.ProductoAgropecuario;
import co.unibague.agropecuario.model.entities.ProductoGanadero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para realizar cálculos de rentabilidad y estadísticas.
 * Demuestra el uso de polimorfismo en los cálculos.
 */
public class CalculadoraController {

    private ProductoAgricolaController agricolaController;
    private ProductoGanaderoController ganaderoController;

    /**
     * Constructor del controlador
     */
    public CalculadoraController(ProductoAgricolaController agricolaController,
                                 ProductoGanaderoController ganaderoController) {
        this.agricolaController = agricolaController;
        this.ganaderoController = ganaderoController;
    }

    /**
     * Calcula rentabilidades usando polimorfismo
     * @return mapa con rentabilidades por tipo
     */
    public Map<String, Double> calcularRentabilidadesPorTipo() {
        Map<String, Double> rentabilidades = new HashMap<>();

        // Usar polimorfismo - cada tipo calcula su rentabilidad específica
        List<ProductoAgropecuario> todosLosProductos = new ArrayList<>();
        todosLosProductos.addAll(agricolaController.listar());
        todosLosProductos.addAll(ganaderoController.listar());

        double rentabilidadAgricola = 0;
        double rentabilidadGanadera = 0;
        int countAgricola = 0;
        int countGanadero = 0;

        for (ProductoAgropecuario producto : todosLosProductos) {
            double rentabilidad = producto.calcularRentabilidad(); // Polimorfismo en acción

            if (producto instanceof ProductoAgricola) {
                rentabilidadAgricola += rentabilidad;
                countAgricola++;
            } else if (producto instanceof ProductoGanadero) {
                rentabilidadGanadera += rentabilidad;
                countGanadero++;
            }
        }

        rentabilidades.put("Productos Agrícolas", countAgricola > 0 ? rentabilidadAgricola / countAgricola : 0);
        rentabilidades.put("Productos Ganaderos", countGanadero > 0 ? rentabilidadGanadera / countGanadero : 0);

        return rentabilidades;
    }

    /**
     * Genera reporte completo de rentabilidades
     * @return texto con reporte detallado
     */
    public String generarReporteRentabilidad() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE RENTABILIDAD ===\n\n");

        // Productos Agrícolas
        reporte.append("PRODUCTOS AGRÍCOLAS:\n");
        List<ProductoAgricola> agricolas = agricolaController.listar();
        double totalRentabilidadAgricola = 0;

        for (ProductoAgricola producto : agricolas) {
            double rentabilidad = producto.calcularRentabilidad();
            totalRentabilidadAgricola += rentabilidad;
            reporte.append(String.format("- %s: $%.2f por hectárea\n",
                    producto.getNombre(), rentabilidad));
        }

        if (!agricolas.isEmpty()) {
            reporte.append(String.format("Promedio Agrícola: $%.2f por hectárea\n\n",
                    totalRentabilidadAgricola / agricolas.size()));
        }

        // Productos Ganaderos
        reporte.append("PRODUCTOS GANADEROS:\n");
        List<ProductoGanadero> ganaderos = ganaderoController.listar();
        double totalRentabilidadGanadera = 0;

        for (ProductoGanadero producto : ganaderos) {
            double rentabilidad = producto.calcularRentabilidad();
            totalRentabilidadGanadera += rentabilidad;
            reporte.append(String.format("- %s: $%.2f por animal/mes\n",
                    producto.getNombre(), rentabilidad));
        }

        if (!ganaderos.isEmpty()) {
            reporte.append(String.format("Promedio Ganadero: $%.2f por animal/mes\n\n",
                    totalRentabilidadGanadera / ganaderos.size()));
        }

        // Comparación
        if (!agricolas.isEmpty() && !ganaderos.isEmpty()) {
            reporte.append("COMPARACIÓN:\n");
            double promedioAgricola = totalRentabilidadAgricola / agricolas.size();
            double promedioGanadero = totalRentabilidadGanadera / ganaderos.size();

            if (promedioAgricola > promedioGanadero) {
                reporte.append("Los productos agrícolas muestran mayor rentabilidad promedio.\n");
            } else if (promedioGanadero > promedioAgricola) {
                reporte.append("Los productos ganaderos muestran mayor rentabilidad promedio.\n");
            } else {
                reporte.append("Ambos sectores muestran rentabilidad similar.\n");
            }
        }

        return reporte.toString();
    }

    /**
     * Calcula estadísticas generales del sistema
     * @return mapa con estadísticas
     */
    public Map<String, Object> calcularEstadisticasGenerales() {
        Map<String, Object> estadisticas = new HashMap<>();

        List<ProductoAgricola> agricolas = agricolaController.listar();
        List<ProductoGanadero> ganaderos = ganaderoController.listar();

        // Estadísticas básicas
        estadisticas.put("Total Productos Agrícolas", agricolas.size());
        estadisticas.put("Total Productos Ganaderos", ganaderos.size());
        estadisticas.put("Total General", agricolas.size() + ganaderos.size());

        // Hectáreas totales
        double hectareasTotal = agricolas.stream()
                .mapToDouble(ProductoAgricola::getHectareasCultivadas)
                .sum();
        estadisticas.put("Hectáreas Cultivadas Total", hectareasTotal);

        // Animales totales
        int animalesTotal = ganaderos.stream()
                .mapToInt(ProductoGanadero::getNumeroAnimales)
                .sum();
        estadisticas.put("Total Animales", animalesTotal);

        // Producción diaria total ganadera
        double produccionDiariaTotal = ganaderoController.calcularProduccionTotalDiaria();
        estadisticas.put("Producción Diaria Total", produccionDiariaTotal);

        // Peso total ganado
        double pesoTotal = ganaderoController.calcularPesoTotal();
        estadisticas.put("Peso Total Ganado (kg)", pesoTotal);

        return estadisticas;
    }

    /**
     * Encuentra el producto más rentable (usando polimorfismo)
     * @return información del producto más rentable
     */
    public String encontrarProductoMasRentable() {
        List<ProductoAgropecuario> todosLosProductos = new ArrayList<>();
        todosLosProductos.addAll(agricolaController.listar());
        todosLosProductos.addAll(ganaderoController.listar());

        if (todosLosProductos.isEmpty()) {
            return "No hay productos registrados";
        }

        ProductoAgropecuario masRentable = null;
        double maxRentabilidad = Double.NEGATIVE_INFINITY;

        for (ProductoAgropecuario producto : todosLosProductos) {
            double rentabilidad = producto.calcularRentabilidad(); // Polimorfismo
            if (rentabilidad > maxRentabilidad) {
                maxRentabilidad = rentabilidad;
                masRentable = producto;
            }
        }

        String tipo = masRentable instanceof ProductoAgricola ? "Agrícola" : "Ganadero";
        String unidad = masRentable instanceof ProductoAgricola ? "por hectárea" : "por animal/mes";

        return String.format("Producto más rentable: %s (%s) - $%.2f %s",
                masRentable.getNombre(), tipo, maxRentabilidad, unidad);
    }

    /**
     * Genera resumen ejecutivo
     * @return resumen para la administración
     */
    public String generarResumenEjecutivo() {
        StringBuilder resumen = new StringBuilder();

        Map<String, Object> stats = calcularEstadisticasGenerales();
        Map<String, Double> rentabilidades = calcularRentabilidadesPorTipo();

        resumen.append("=== RESUMEN EJECUTIVO ===\n\n");
        resumen.append("INVENTARIO:\n");
        resumen.append(String.format("- Productos Registrados: %d\n", (Integer)stats.get("Total General")));
        resumen.append(String.format("- Hectáreas bajo cultivo: %.2f ha\n", (Double)stats.get("Hectáreas Cultivadas Total")));
        resumen.append(String.format("- Total de animales: %d cabezas\n", (Integer)stats.get("Total Animales")));

        resumen.append("\nRENTABILIDAD PROMEDIO:\n");
        resumen.append(String.format("- Sector Agrícola: $%.2f por hectárea\n", rentabilidades.get("Productos Agrícolas")));
        resumen.append(String.format("- Sector Ganadero: $%.2f por animal/mes\n", rentabilidades.get("Productos Ganaderos")));

        resumen.append("\n").append(encontrarProductoMasRentable());

        return resumen.toString();
    }
}