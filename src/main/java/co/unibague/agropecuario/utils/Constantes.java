package co.unibague.agropecuario.utils;

import java.awt.Color;
import java.awt.Font;

/**
 * Clase que contiene constantes del sistema.
 * Implementa el principio DRY (Don't Repeat Yourself).
 */
public final class Constantes {

    // Prevenir instanciación
    private Constantes() {}

    // === CONSTANTES DE APLICACIÓN ===
    public static final String TITULO_APLICACION = "Sistema de Gestión Agropecuaria";
    public static final String VERSION = "1.0.0";
    public static final String DESARROLLADORES = "Equipo de Desarrollo UNIBAGUE 2025";

    // === TIPOS DE CULTIVO ===
    public static final String[] TIPOS_CULTIVO = {
            "Café", "Arroz", "Cacao", "Mango", "Aguacate",
            "Cítricos", "Maracuyá", "Sorgo", "Algodón", "Plátano"
    };

    // === TIPOS DE GANADO ===
    public static final String[] TIPOS_GANADO = {
            "Bovino lechero", "Bovino de carne", "Bovino doble propósito",
            "Porcino de engorde", "Porcino de cría",
            "Avícola ponedora", "Avícola de engorde",
            "Piscicultura - Tilapia", "Piscicultura - Cachama"
    };

    // === TIPOS DE SUELO ===
    public static final String[] TIPOS_SUELO = {
            "Franco", "Franco arcilloso", "Franco arenoso",
            "Arcilloso", "Arenoso", "Limoso"
    };

    // === TIPOS DE TERRENO ===
    public static final String[] TIPOS_TERRENO = {
            "Plano", "Ondulado", "Montañoso", "Quebrado"
    };

    // === MUNICIPIOS DEL TOLIMA ===
    public static final String[] MUNICIPIOS_TOLIMA = {
            "Ibagué", "Espinal", "Guamo", "Flandes", "Girardot",
            "Honda", "Líbano", "Mariquita", "Melgar", "Purificación",
            "Armero", "Casabianca", "Chaparral", "Coello", "Cunday",
            "Dolores", "Falan", "Fresno", "Herveo", "Icononzo",
            "Lérida", "Ortega", "Palocabildo", "Piedras", "Planadas",
            "Prado", "Roncesvalles", "Rovira", "Saldaña", "San Antonio",
            "San Luis", "Santa Isabel", "Suárez", "Valle de San Juan",
            "Venadillo", "Villahermosa", "Villarrica"
    };

    // === TEMPORADAS ===
    public static final String[] TEMPORADAS = {
            "Todo el año", "Temporada seca", "Temporada lluviosa",
            "Cosecha principal", "Cosecha mitaca", "Temporada alta", "Temporada baja"
    };

    // === TIPOS DE ALIMENTACIÓN ===
    public static final String[] TIPOS_ALIMENTACION = {
            "Pastoreo", "Pastoreo + suplemento", "Concentrado balanceado",
            "Concentrado especializado", "Forraje verde", "Mixta"
    };

    // === COLORES DE LA INTERFAZ ===
    public static final Color COLOR_PRIMARY = new Color(34, 139, 34);      // Verde forestal
    public static final Color COLOR_SECONDARY = new Color(255, 215, 0);    // Dorado
    public static final Color COLOR_SUCCESS = new Color(40, 167, 69);      // Verde éxito
    public static final Color COLOR_WARNING = new Color(255, 193, 7);      // Amarillo advertencia
    public static final Color COLOR_DANGER = new Color(220, 53, 69);       // Rojo peligro
    public static final Color COLOR_INFO = new Color(23, 162, 184);        // Azul información
    public static final Color COLOR_LIGHT = new Color(248, 249, 250);      // Gris claro
    public static final Color COLOR_DARK = new Color(52, 58, 64);          // Gris oscuro

    // === FUENTES ===
    public static final Font FONT_TITLE = new Font("Arial", Font.BOLD, 16);
    public static final Font FONT_SUBTITLE = new Font("Arial", Font.BOLD, 14);
    public static final Font FONT_NORMAL = new Font("Arial", Font.PLAIN, 12);
    public static final Font FONT_SMALL = new Font("Arial", Font.PLAIN, 10);

    // === DIMENSIONES ===
    public static final int VENTANA_ANCHO_DEFAULT = 1000;
    public static final int VENTANA_ALTO_DEFAULT = 700;
    public static final int VENTANA_ANCHO_DIALOG = 500;
    public static final int VENTANA_ALTO_DIALOG = 400;

    // === MENSAJES ===
    public static final String MSG_OPERACION_EXITOSA = "Operación realizada con éxito";
    public static final String MSG_ERROR_VALIDACION = "Error en la validación de datos";
    public static final String MSG_CONFIRMAR_ELIMINAR = "¿Está seguro de que desea eliminar este elemento?";
    public static final String MSG_NO_SELECCIONADO = "Debe seleccionar un elemento de la lista";
    public static final String MSG_CAMPOS_OBLIGATORIOS = "Todos los campos marcados con * son obligatorios";

    // === VALIDACIONES ===
    public static final int MIN_LENGTH_NOMBRE = 2;
    public static final int MAX_LENGTH_NOMBRE = 100;
    public static final int MAX_LENGTH_DESCRIPCION = 500;
    public static final double MIN_HECTAREAS = 0.1;
    public static final double MAX_HECTAREAS = 10000.0;
    public static final int MIN_ANIMALES = 1;
    public static final int MAX_ANIMALES = 50000;
    public static final double MIN_PRECIO = 100.0;
    public static final double MAX_PRECIO = 1000000.0;

    // === FORMATOS DE ARCHIVO ===
    public static final String[] EXTENSIONES_IMAGEN = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
    public static final String[] EXTENSIONES_DOCUMENTO = {".pdf", ".doc", ".docx", ".txt"};

    // === CONFIGURACIÓN DE TABLAS ===
    public static final int FILAS_POR_PAGINA = 20;
    public static final int ALTO_FILA_TABLA = 25;

    // === PATHS ===
    public static final String PATH_IMAGENES = "/imagenes/";
    public static final String PATH_ICONOS = "/iconos/";
    public static final String PATH_REPORTES = "/reportes/";

    // === ICONOS (si se usan) ===
    public static final String ICONO_AGRICULTOR = "agricultor.png";
    public static final String ICONO_GANADO = "ganado.png";
    public static final String ICONO_FINCA = "finca.png";
    public static final String ICONO_CALCULADORA = "calculadora.png";
}