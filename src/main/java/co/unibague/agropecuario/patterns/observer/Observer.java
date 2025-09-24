package co.unibague.agropecuario.patterns.observer;

/**
 * Interfaz Observer para el patrón Observer.
 * Define el contrato que deben cumplir los observadores.
 */
public interface Observer {

    /**
     * Método llamado cuando el objeto observado notifica cambios
     */
    void update();

    /**
     * Método llamado con información específica del cambio
     * @param data información sobre el cambio ocurrido
     */
    void update(Object data);
}