package co.unibague.agropecuario.controller;

import co.unibague.agropecuario.model.entities.ProductoGanadero;
import co.unibague.agropecuario.patterns.observer.Observable;
import co.unibague.agropecuario.patterns.observer.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar productos ganaderos.
 * Implementa el patrón MVC y Observable para notificar cambios.
 */
public class ProductoGanaderoController implements Observable {

    private List<ProductoGanadero> productosGanaderos;
    private List<Observer> observers;

    /**
     * Constructor del controlador
     */
    public ProductoGanaderoController() {
        this.productosGanaderos = new ArrayList<>();
        this.observers = new ArrayList<>();
        inicializarDatosPrueba();
    }

    /**
     * Inicializa algunos datos de prueba
     */
    private void inicializarDatosPrueba() {
        // Ganado bovino
        ProductoGanadero bovino = new ProductoGanadero();
        bovino.setId("GAN001");
        bovino.setNombre("Ganado Holstein");
        bovino.setTipoGanado("Bovino lechero");
        bovino.setNumeroAnimales(45);
        bovino.setPesoPromedio(520.0);
        bovino.setProduccionDiaria(18.5);
        bovino.setCostoProduccion(3500000);
        bovino.setPrecioVenta(1200);
        bovino.setCantidadProducida(25000);
        bovino.setTipoAlimentacion("Pastoreo + suplemento");
        bovino.setFechaProduccion(LocalDate.now().minusDays(30));

        // Ganado porcino
        ProductoGanadero porcino = new ProductoGanadero();
        porcino.setId("GAN002");
        porcino.setNombre("Cerdos Pietrain");
        porcino.setTipoGanado("Porcino de engorde");
        porcino.setNumeroAnimales(120);
        porcino.setPesoPromedio(85.0);
        porcino.setProduccionDiaria(0.8);
        porcino.setCostoProduccion(2800000);
        porcino.setPrecioVenta(7500);
        porcino.setCantidadProducida(10200);
        porcino.setTipoAlimentacion("Concentrado balanceado");
        porcino.setFechaProduccion(LocalDate.now().minusDays(15));

        // Avícola
        ProductoGanadero avicola = new ProductoGanadero();
        avicola.setId("GAN003");
        avicola.setNombre("Gallinas Ponedoras");
        avicola.setTipoGanado("Avícola ponedora");
        avicola.setNumeroAnimales(500);
        avicola.setPesoPromedio(2.2);
        avicola.setProduccionDiaria(420.0);
        avicola.setCostoProduccion(1800000);
        avicola.setPrecioVenta(450);
        avicola.setCantidadProducida(12600);
        avicola.setTipoAlimentacion("Concentrado especializado");
        avicola.setFechaProduccion(LocalDate.now().minusDays(7));

        productosGanaderos.add(bovino);
        productosGanaderos.add(porcino);
        productosGanaderos.add(avicola);
    }

    /**
     * Crea un nuevo producto ganadero
     * @param producto el producto a crear
     * @return true si se creó exitosamente
     */
    public boolean crear(ProductoGanadero producto) {
        if (producto == null || buscar(producto.getId()) != null) {
            return false;
        }

        boolean resultado = productosGanaderos.add(producto);
        if (resultado) {
            notifyObservers("CREAR");
        }
        return resultado;
    }

    /**
     * Busca un producto por su ID
     * @param id identificador del producto
     * @return el producto encontrado o null
     */
    public ProductoGanadero buscar(String id) {
        return productosGanaderos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Actualiza un producto existente
     * @param id identificador del producto
     * @param productoActualizado producto con datos actualizados
     * @return true si se actualizó exitosamente
     */
    public boolean actualizar(String id, ProductoGanadero productoActualizado) {
        for (int i = 0; i < productosGanaderos.size(); i++) {
            if (productosGanaderos.get(i).getId().equals(id)) {
                productoActualizado.setId(id); // Mantener el ID original
                productosGanaderos.set(i, productoActualizado);
                notifyObservers("ACTUALIZAR");
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un producto por su ID
     * @param id identificador del producto
     * @return true si se eliminó exitosamente
     */
    public boolean eliminar(String id) {
        boolean resultado = productosGanaderos.removeIf(p -> p.getId().equals(id));
        if (resultado) {
            notifyObservers("ELIMINAR");
        }
        return resultado;
    }

    /**
     * Lista todos los productos ganaderos
     * @return lista de productos
     */
    public List<ProductoGanadero> listar() {
        return new ArrayList<>(productosGanaderos);
    }

    /**
     * Calcula la rentabilidad total de todos los productos
     * @return rentabilidad total
     */
    public double calcularRentabilidadTotal() {
        return productosGanaderos.stream()
                .mapToDouble(ProductoGanadero::calcularRentabilidad)
                .sum();
    }

    /**
     * Obtiene productos por tipo de ganado
     * @param tipoGanado tipo de ganado a filtrar
     * @return lista filtrada
     */
    public List<ProductoGanadero> obtenerPorTipoGanado(String tipoGanado) {
        return productosGanaderos.stream()
                .filter(p -> p.getTipoGanado().toLowerCase().contains(tipoGanado.toLowerCase()))
                .toList();
    }

    /**
     * Aplica tratamiento a un producto específico
     * @param id identificador del producto
     * @return true si se aplicó exitosamente
     */
    public boolean aplicarTratamiento(String id) {
        ProductoGanadero producto = buscar(id);
        if (producto != null && producto.puedeRecibirAplicacion()) {
            boolean resultado = producto.aplicar();
            if (resultado) {
                notifyObservers("APLICAR_TRATAMIENTO");
            }
            return resultado;
        }
        return false;
    }

    /**
     * Obtiene el estado de aplicación de un producto
     * @param id identificador del producto
     * @return estado de aplicación o mensaje de error
     */
    public String obtenerEstadoAplicacion(String id) {
        ProductoGanadero producto = buscar(id);
        if (producto != null) {
            return producto.obtenerEstadoAplicacion();
        }
        return "Producto no encontrado";
    }

    /**
     * Calcula la producción total diaria de todos los productos
     * @return producción total diaria
     */
    public double calcularProduccionTotalDiaria() {
        return productosGanaderos.stream()
                .mapToDouble(p -> p.getProduccionDiaria() * p.getNumeroAnimales())
                .sum();
    }

    /**
     * Calcula el peso total de todos los animales
     * @return peso total en kilogramos
     */
    public double calcularPesoTotal() {
        return productosGanaderos.stream()
                .mapToDouble(ProductoGanadero::calcularPesoTotal)
                .sum();
    }

    /**
     * Genera el próximo ID disponible
     * @return próximo ID
     */
    public String generarProximoId() {
        int maxNum = productosGanaderos.stream()
                .mapToInt(p -> {
                    try {
                        return Integer.parseInt(p.getId().substring(3));
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);

        return String.format("GAN%03d", maxNum + 1);
    }

    // Implementación del patrón Observer
    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void notifyObservers(Object data) {
        for (Observer observer : observers) {
            observer.update(data);
        }
    }

    /**
     * Obtiene el número total de productos
     * @return número total de productos
     */
    public int obtenerTotal() {
        return productosGanaderos.size();
    }

    /**
     * Obtiene el número total de animales
     * @return número total de animales
     */
    public int obtenerTotalAnimales() {
        return productosGanaderos.stream()
                .mapToInt(ProductoGanadero::getNumeroAnimales)
                .sum();
    }
}