package co.unibague.agropecuario.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Clase utilitaria para formatear datos para presentación.
 * Implementa el principio de Single Responsibility.
 */
public class Formateador {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");
    private static final DecimalFormat ENTERO_FORMAT = new DecimalFormat("#,##0");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    /**
     * Formatea un número decimal con separadores de miles
     * @param valor valor a formatear
     * @return valor formateado
     */
    public static String formatearDecimal(double valor) {
        return DECIMAL_FORMAT.format(valor);
    }

    /**
     * Formatea un número entero con separadores de miles
     * @param valor valor a formatear
     * @return valor formateado
     */
    public static String formatearEntero(int valor) {
        return ENTERO_FORMAT.format(valor);
    }

    /**
     * Formatea una fecha en formato colombiano
     * @param fecha fecha a formatear
     * @return fecha formateada
     */
    public static String formatearFecha(LocalDate fecha) {
        if (fecha == null) return "N/A";
        return fecha.format(DATE_FORMAT);
    }

    /**
     * Formatea un valor como moneda colombiana
     * @param valor valor a formatear
     * @return valor formateado como moneda
     */
    public static String formatearMoneda(double valor) {
        return CURRENCY_FORMAT.format(valor);
    }

    /**
     * Formatea hectáreas con unidad
     * @param hectareas hectáreas a formatear
     * @return hectáreas formateadas
     */
    public static String formatearHectareas(double hectareas) {
        return formatearDecimal(hectareas) + " ha";
    }

    /**
     * Formatea peso con unidad
     * @param peso peso a formatear
     * @return peso formateado
     */
    public static String formatearPeso(double peso) {
        return formatearDecimal(peso) + " kg";
    }

    /**
     * Formatea porcentaje
     * @param porcentaje porcentaje a formatear
     * @return porcentaje formateado
     */
    public static String formatearPorcentaje(double porcentaje) {
        return formatearDecimal(porcentaje) + "%";
    }

    /**
     * Capitaliza la primera letra de cada palabra
     * @param texto texto a capitalizar
     * @return texto capitalizado
     */
    public static String capitalizarPalabras(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return texto;
        }

        StringBuilder resultado = new StringBuilder();
        String[] palabras = texto.toLowerCase().split("\\s+");

        for (int i = 0; i < palabras.length; i++) {
            if (i > 0) resultado.append(" ");
            if (!palabras[i].isEmpty()) {
                resultado.append(palabras[i].substring(0, 1).toUpperCase())
                        .append(palabras[i].substring(1));
            }
        }

        return resultado.toString();
    }

    /**
     * Formatea un ID para mostrar con guiones
     * @param id ID a formatear
     * @return ID formateado
     */
    public static String formatearId(String id) {
        if (id == null || id.length() != 6) return id;
        return id.substring(0, 3) + "-" + id.substring(3);
    }

    /**
     * Trunca texto largo para mostrar en tablas
     * @param texto texto a truncar
     * @param maxLength longitud máxima
     * @return texto truncado
     */
    public static String truncarTexto(String texto, int maxLength) {
        if (texto == null) return "";
        if (texto.length() <= maxLength) return texto;
        return texto.substring(0, maxLength - 3) + "...";
    }

    /**
     * Formatea información de contacto
     * @param telefono teléfono
     * @param email email
     * @return información de contacto formateada
     */
    public static String formatearContacto(String telefono, String email) {
        StringBuilder contacto = new StringBuilder();

        if (Validador.validarTelefono(telefono)) {
            contacto.append("Tel: ").append(telefono);
        }

        if (Validador.validarEmail(email)) {
            if (contacto.length() > 0) contacto.append(" | ");
            contacto.append("Email: ").append(email);
        }

        return contacto.toString();
    }
}