package co.unibague.agropecuario.patterns.observer;

/**
 * Interfaz Observable para el patrón Observer.
 * Define el contrato que deben cumplir los objetos observables.
 */
public interface Observable {

    /**
     * Agrega un observador a la lista de observadores
     * @param observer el observador a agregar
     */
    void addObserver(Observer observer);

    /**
     * Remueve un observador de la lista
     * @param observer el observador a remover
     */
    void removeObserver(Observer observer);

    /**
     * Notifica a todos los observadores sobre un cambio
     */
    void notifyObservers();

    /**
     * Notifica a todos los observadores con datos específicos
     * @param data información sobre el cambio
     */
    void notifyObservers(Object data);
}