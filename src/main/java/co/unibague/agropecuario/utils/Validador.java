package co.unibague.agropecuario.utils;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Clase utilitaria para validaciones comunes en el sistema.
 * Implementa el principio de Single Responsibility.
 */
public class Validador {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    private static final Pattern TELEFONO_PATTERN =
            Pattern.compile("^[0-9]{3}-[0-9]{3}-[0-9]{4}$|^[0-9]{10}$");

    private static final Pattern ID_PATTERN =
            Pattern.compile("^[A-Z]{3}[0-9]{3}$");

    /**
     * Valida que una cadena no sea nula o vacía
     * @param valor cadena a validar
     * @return true si es válida
     */
    public static boolean validarCadenaNoVacia(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    /**
     * Valida que un número sea positivo
     * @param valor número a validar
     * @return true si es positivo
     */
    public static boolean validarNumeroPositivo(double valor) {
        return valor > 0;
    }

    /**
     * Valida que un número sea no negativo
     * @param valor número a validar
     * @return true si es no negativo
     */
    public static boolean validarNumeroNoNegativo(double valor) {
        return valor >= 0;
    }

    /**
     * Valida formato de email
     * @param email email a validar
     * @return true si el formato es válido
     */
    public static boolean validarEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Valida formato de teléfono colombiano
     * @param telefono teléfono a validar
     * @return true si el formato es válido
     */
    public static boolean validarTelefono(String telefono) {
        return telefono != null && TELEFONO_PATTERN.matcher(telefono).matches();
    }

    /**
     * Valida formato de ID del sistema (3 letras + 3 números)
     * @param id ID a validar
     * @return true si el formato es válido
     */
    public static boolean validarFormatoId(String id) {
        return id != null && ID_PATTERN.matcher(id).matches();
    }

    /**
     * Valida que una fecha no sea futura
     * @param fecha fecha a validar
     * @return true si es válida
     */
    public static boolean validarFechaNoFutura(LocalDate fecha) {
        return fecha != null && !fecha.isAfter(LocalDate.now());
    }

    /**
     * Valida que una fecha esté dentro de un rango razonable (últimos 10 años)
     * @param fecha fecha a validar
     * @return true si está en el rango
     */
    public static boolean validarFechaRangoRazonable(LocalDate fecha) {
        if (fecha == null) return false;
        LocalDate hace10Years = LocalDate.now().minusYears(10);
        return !fecha.isBefore(hace10Years) && !fecha.isAfter(LocalDate.now());
    }

    /**
     * Valida rango de hectáreas (entre 0.1 y 10000)
     * @param hectareas hectáreas a validar
     * @return true si está en rango válido
     */
    public static boolean validarRangoHectareas(double hectareas) {
        return hectareas >= 0.1 && hectareas <= 10000;
    }

    /**
     * Valida número de animales (entre 1 y 50000)
     * @param numeroAnimales número a validar
     * @return true si está en rango válido
     */
    public static boolean validarNumeroAnimales(int numeroAnimales) {
        return numeroAnimales >= 1 && numeroAnimales <= 50000;
    }

    /**
     * Limpia y formatea una cadena para almacenamiento
     * @param valor cadena a limpiar
     * @return cadena limpiada
     */
    public static String limpiarCadena(String valor) {
        if (valor == null) return "";
        return valor.trim().replaceAll("\\s+", " ");
    }

    /**
     * Valida que un precio sea razonable (entre $100 y $1,000,000)
     * @param precio precio a validar
     * @return true si está en rango razonable
     */
    public static boolean validarPrecioRazonable(double precio) {
        return precio >= 100 && precio <= 1000000;
    }
}