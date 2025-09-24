package co.unibague.agropecuario.controller;

import co.unibague.agropecuario.model.entities.ProductoAgricola;
import co.unibague.agropecuario.patterns.observer.Observable;
import co.unibague.agropecuario.patterns.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para gestionar productos agrícolas.
 * Implementa el patrón MVC y Observable para notificar cambios.
 */
public class ProductoAgricolaController implements Observable {

    private List<ProductoAgricola> productosAgricolas;
    private List<Observer> observers;

    /**
     * Constructor del controlador
     */
    public ProductoAgricolaController() {
        this.productosAgricolas = new ArrayList<>();
        this.observers = new ArrayList<>();
        inicializarDatosPrueba();
    }

    /**
     * Inicializa algunos datos de prueba
     */
    private void inicializarDatosPrueba() {
        // Datos de ejemplo para testing
        ProductoAgricola cafe = new ProductoAgricola();
        cafe.setId("AGR001");
        cafe.setNombre("Café Arábica");
        cafe.setTipoCultivo("Café");
        cafe.setHectareasCultivadas(5.0);
        cafe.setRendimientoPorHa(1.8);
        cafe.setCostoProduccion(2500000);
        cafe.setPrecioVenta(8500);
        cafe.setCantidadProducida(9000);
        cafe.setTipoSuelo("Franco arcilloso");
        cafe.setTemporada("Cosecha principal");

        ProductoAgricola arroz = new ProductoAgricola();
        arroz.setId("AGR002");
        arroz.setNombre("Arroz Fedearroz 67");
        arroz.setTipoCultivo("Arroz");
        arroz.setHectareasCultivadas(12.0);
        arroz.setRendimientoPorHa(6.5);
        arroz.setCostoProduccion(4800000);
        arroz.setPrecioVenta(1850);
        arroz.setCantidadProducida(78000);
        arroz.setTipoSuelo("Franco");
        arroz.setTemporada("Temporada seca");

        productosAgricolas.add(cafe);
        productosAgricolas.add(arroz);
    }

    /**
     * Crea un nuevo producto agrícola
     * @param producto el producto a crear
     * @return true si se creó exitosamente
     */
    public boolean crear(ProductoAgricola producto) {
        if (producto == null || buscar(producto.getId()) != null) {
            return false;
        }

        boolean resultado = productosAgricolas.add(producto);
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
    public ProductoAgricola buscar(String id) {
        return productosAgricolas.stream()
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
    public boolean actualizar(String id, ProductoAgricola productoActualizado) {
        for (int i = 0; i < productosAgricolas.size(); i++) {
            if (productosAgricolas.get(i).getId().equals(id)) {
                productoActualizado.setId(id); // Mantener el ID original
                productosAgricolas.set(i, productoActualizado);
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
        boolean resultado = productosAgricolas.removeIf(p -> p.getId().equals(id));
        if (resultado) {
            notifyObservers("ELIMINAR");
        }
        return resultado;
    }

    /**
     * Lista todos los productos agrícolas
     * @return lista de productos
     */
    public List<ProductoAgricola> listar() {
        return new ArrayList<>(productosAgricolas);
    }

    /**
     * Calcula la rentabilidad total de todos los productos
     * @return rentabilidad total
     */
    public double calcularRentabilidadTotal() {
        return productosAgricolas.stream()
                .mapToDouble(ProductoAgricola::calcularRentabilidad)
                .sum();
    }

    /**
     * Obtiene productos por tipo de cultivo
     * @param tipoCultivo tipo de cultivo a filtrar
     * @return lista filtrada
     */
    public List<ProductoAgricola> obtenerPorTipoCultivo(String tipoCultivo) {
        return productosAgricolas.stream()
                .filter(p -> p.getTipoCultivo().equalsIgnoreCase(tipoCultivo))
                .toList();
    }

    /**
     * Genera el próximo ID disponible
     * @return próximo ID
     */
    public String generarProximoId() {
        int maxNum = productosAgricolas.stream()
                .mapToInt(p -> {
                    try {
                        return Integer.parseInt(p.getId().substring(3));
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);

        return String.format("AGR%03d", maxNum + 1);
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
        return productosAgricolas.size();
    }
}