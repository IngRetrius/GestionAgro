package co.unibague.agropecuario.controller;

import co.unibague.agropecuario.model.entities.Finca;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar fincas.
 * Implementa el patrón MVC.
 */
public class FincaController {

    private List<Finca> fincas;

    /**
     * Constructor del controlador
     */
    public FincaController() {
        this.fincas = new ArrayList<>();
        inicializarDatosPrueba();
    }

    /**
     * Inicializa algunos datos de prueba
     */
    private void inicializarDatosPrueba() {
        Finca finca1 = new Finca();
        finca1.setCodigo("F001");
        finca1.setNombreFinca("Villa María");
        finca1.setPropietario("Carlos Rodríguez");
        finca1.setMunicipio("Ibagué");
        finca1.setVereda("La Gaviota");
        finca1.setAreaTotal(25.5);
        finca1.setTipoTerreno("Ondulado");
        finca1.setInfraestructura("Riego por aspersión, bodega, casa");
        finca1.setTelefono("310-555-0123");
        finca1.setEmail("carlos.rodriguez@email.com");

        Finca finca2 = new Finca();
        finca2.setCodigo("F002");
        finca2.setNombreFinca("El Horizonte");
        finca2.setPropietario("Ana Patricia López");
        finca2.setMunicipio("Espinal");
        finca2.setVereda("San Rafael");
        finca2.setAreaTotal(48.0);
        finca2.setTipoTerreno("Plano");
        finca2.setInfraestructura("Riego por goteo, silos, oficina");
        finca2.setTelefono("320-555-0456");
        finca2.setEmail("ana.lopez@email.com");

        Finca finca3 = new Finca();
        finca3.setCodigo("F003");
        finca3.setNombreFinca("La Esperanza");
        finca3.setPropietario("Miguel Ángel Torres");
        finca3.setMunicipio("Guamo");
        finca3.setVereda("El Carmen");
        finca3.setAreaTotal(15.8);
        finca3.setTipoTerreno("Montañoso");
        finca3.setInfraestructura("Básica, tanque de agua");
        finca3.setTelefono("315-555-0789");
        finca3.setEmail("miguel.torres@email.com");

        fincas.add(finca1);
        fincas.add(finca2);
        fincas.add(finca3);
    }

    /**
     * Crea una nueva finca
     * @param finca la finca a crear
     * @return true si se creó exitosamente
     */
    public boolean crear(Finca finca) {
        if (finca == null || buscar(finca.getCodigo()) != null) {
            return false;
        }
        return fincas.add(finca);
    }

    /**
     * Busca una finca por su código
     * @param codigo código de la finca
     * @return la finca encontrada o null
     */
    public Finca buscar(String codigo) {
        return fincas.stream()
                .filter(f -> f.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca fincas por propietario
     * @param propietario nombre del propietario
     * @return lista de fincas del propietario
     */
    public List<Finca> buscarPorPropietario(String propietario) {
        return fincas.stream()
                .filter(f -> f.getPropietario().toLowerCase().contains(propietario.toLowerCase()))
                .toList();
    }

    /**
     * Busca fincas por municipio
     * @param municipio nombre del municipio
     * @return lista de fincas del municipio
     */
    public List<Finca> buscarPorMunicipio(String municipio) {
        return fincas.stream()
                .filter(f -> f.getMunicipio().equalsIgnoreCase(municipio))
                .toList();
    }

    /**
     * Actualiza una finca existente
     * @param codigo código de la finca
     * @param fincaActualizada finca con datos actualizados
     * @return true si se actualizó exitosamente
     */
    public boolean actualizar(String codigo, Finca fincaActualizada) {
        for (int i = 0; i < fincas.size(); i++) {
            if (fincas.get(i).getCodigo().equals(codigo)) {
                fincaActualizada.setCodigo(codigo); // Mantener el código original
                fincas.set(i, fincaActualizada);
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina una finca por su código
     * @param codigo código de la finca
     * @return true si se eliminó exitosamente
     */
    public boolean eliminar(String codigo) {
        return fincas.removeIf(f -> f.getCodigo().equals(codigo));
    }

    /**
     * Lista todas las fincas
     * @return lista de fincas
     */
    public List<Finca> listar() {
        return new ArrayList<>(fincas);
    }

    /**
     * Obtiene fincas grandes (más de 50 hectáreas)
     * @return lista de fincas grandes
     */
    public List<Finca> obtenerFincasGrandes() {
        return fincas.stream()
                .filter(Finca::esFincaGrande)
                .toList();
    }

    /**
     * Obtiene fincas con riego
     * @return lista de fincas con sistema de riego
     */
    public List<Finca> obtenerFincasConRiego() {
        return fincas.stream()
                .filter(Finca::tieneRiego)
                .toList();
    }

    /**
     * Calcula el área total de todas las fincas
     * @return área total en hectáreas
     */
    public double calcularAreaTotal() {
        return fincas.stream()
                .mapToDouble(Finca::getAreaTotal)
                .sum();
    }

    /**
     * Calcula el área cultivable total
     * @return área cultivable total en hectáreas
     */
    public double calcularAreaCultivableTotal() {
        return fincas.stream()
                .mapToDouble(Finca::calcularAreaCultivable)
                .sum();
    }

    /**
     * Obtiene estadísticas por municipio
     * @return texto con estadísticas
     */
    public String obtenerEstadisticasPorMunicipio() {
        StringBuilder stats = new StringBuilder();

        List<String> municipios = fincas.stream()
                .map(Finca::getMunicipio)
                .distinct()
                .sorted()
                .toList();

        for (String municipio : municipios) {
            List<Finca> fincasMunicipio = buscarPorMunicipio(municipio);
            double areaTotal = fincasMunicipio.stream()
                    .mapToDouble(Finca::getAreaTotal)
                    .sum();

            stats.append(String.format("%s: %d fincas, %.2f hectáreas\n",
                    municipio, fincasMunicipio.size(), areaTotal));
        }

        return stats.toString();
    }

    /**
     * Genera el próximo código disponible
     * @return próximo código
     */
    public String generarProximoCodigo() {
        int maxNum = fincas.stream()
                .mapToInt(f -> {
                    try {
                        return Integer.parseInt(f.getCodigo().substring(1));
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);

        return String.format("F%03d", maxNum + 1);
    }

    /**
     * Valida que una finca tenga datos completos
     * @param finca finca a validar
     * @return mensaje de validación
     */
    public String validarFinca(Finca finca) {
        List<String> errores = new ArrayList<>();

        if (finca.getCodigo() == null || finca.getCodigo().trim().isEmpty()) {
            errores.add("El código es obligatorio");
        }

        if (finca.getNombreFinca() == null || finca.getNombreFinca().trim().isEmpty()) {
            errores.add("El nombre de la finca es obligatorio");
        }

        if (finca.getPropietario() == null || finca.getPropietario().trim().isEmpty()) {
            errores.add("El propietario es obligatorio");
        }

        if (finca.getAreaTotal() <= 0) {
            errores.add("El área debe ser mayor a cero");
        }

        if (finca.getMunicipio() == null || finca.getMunicipio().trim().isEmpty()) {
            errores.add("El municipio es obligatorio");
        }

        if (errores.isEmpty()) {
            return "Validación exitosa";
        } else {
            return "Errores encontrados: " + String.join(", ", errores);
        }
    }

    /**
     * Obtiene el número total de fincas
     * @return número total de fincas
     */
    public int obtenerTotal() {
        return fincas.size();
    }
}