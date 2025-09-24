package co.unibague.agropecuario.model.interfaces;

/**
 * Interfaz que define comportamientos específicos para productos ganaderos
 * que pueden recibir aplicaciones como tratamientos, vacunas o certificaciones.
 * Implementa el principio de Segregación de Interfaces (ISP) de SOLID.
 */
public interface IAplicable {

    /**
     * Aplica un tratamiento, certificación o proceso específico al producto ganadero
     * @return true si la aplicación fue exitosa, false en caso contrario
     */
    boolean aplicar();

    /**
     * Obtiene el estado actual de las aplicaciones realizadas
     * @return String descriptivo del estado de aplicaciones
     */
    String obtenerEstadoAplicacion();

    /**
     * Verifica si el producto está listo para recibir nuevas aplicaciones
     * @return true si puede recibir aplicaciones, false en caso contrario
     */
    boolean puedeRecibirAplicacion();
}