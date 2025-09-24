package co.unibague.agropecuario;

import co.unibague.agropecuario.view.principal.VentanaPrincipal;

import javax.swing.*;

/**
 * Clase principal de la aplicación Sistema de Gestión Agropecuaria.
 * Punto de entrada del sistema que implementa patrones SOLID, MVC, Observer y Singleton.
 *
 * @author Equipo de Desarrollo UNIBAGUE 2025
 * @version 1.0.0
 */
public class Main {

    /**
     * Método principal que inicia la aplicación
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Mostrar información de inicio en consola
        mostrarInformacionInicio();

        // Configurar propiedades del sistema para Swing
        configurarPropiedadesSistema();

        // Ejecutar la aplicación en el Event Dispatch Thread de Swing
        SwingUtilities.invokeLater(Main::iniciarAplicacion);
    }

    /**
     * Muestra información de inicio en la consola
     */
    private static void mostrarInformacionInicio() {
        System.out.println("=====================================");
        System.out.println("  Sistema de Gestión Agropecuaria   ");
        System.out.println("    Universidad de Ibagué - 2025    ");
        System.out.println("         Versión 1.0.0              ");
        System.out.println("=====================================");
        System.out.println();
        System.out.println("Iniciando aplicación...");
        System.out.println("Configurando componentes del sistema...");
    }

    /**
     * Configura las propiedades del sistema para optimizar Swing
     */
    private static void configurarPropiedadesSistema() {
        try {
            // Configurar propiedades de renderizado para mejor rendimiento
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            System.setProperty("sun.java2d.d3d", "false");

            // Configurar Look and Feel del sistema
            configurarLookAndFeel();

            System.out.println("Propiedades del sistema configuradas correctamente.");

        } catch (Exception e) {
            System.err.println("Advertencia: No se pudieron configurar todas las propiedades del sistema: "
                    + e.getMessage());
        }
    }

    /**
     * Configura el Look and Feel de la aplicación
     */
    private static void configurarLookAndFeel() {
        try {
            // Intentar configurar el Look and Feel del sistema operativo
            String sistemLAF = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("Look and Feel configurado: " + sistemLAF);

        } catch (Exception e) {
            try {
                // Si falla, intentar usar Nimbus (más moderno que Metal)
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        System.out.println("Look and Feel configurado: Nimbus");
                        return;
                    }
                }

                // Si no hay Nimbus disponible, usar el por defecto
                System.out.println("Usando Look and Feel por defecto: "
                        + UIManager.getLookAndFeel().getName());

            } catch (Exception ex) {
                System.err.println("No se pudo configurar el Look and Feel. Usando configuración por defecto.");
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }

    /**
     * Inicia la aplicación creando y mostrando la ventana principal
     */
    private static void iniciarAplicacion() {
        try {
            System.out.println("Creando ventana principal...");

            // Crear la ventana principal
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();

            // Configurar comportamiento al cerrar
            ventanaPrincipal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            ventanaPrincipal.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    confirmarSalida(ventanaPrincipal);
                }
            });

            // Mostrar la ventana
            ventanaPrincipal.mostrar();

            System.out.println("Aplicación iniciada exitosamente.");
            System.out.println("La ventana principal está visible.");
            System.out.println();

        } catch (Exception e) {
            System.err.println("ERROR: No se pudo iniciar la aplicación.");
            System.err.println("Detalles del error: " + e.getMessage());
            e.printStackTrace();

            // Mostrar mensaje de error al usuario
            mostrarErrorInicio(e);

            // Salir de la aplicación si hay un error crítico
            System.exit(1);
        }
    }

    /**
     * Confirma si el usuario realmente quiere salir de la aplicación
     * @param ventanaPadre ventana padre para el diálogo
     */
    private static void confirmarSalida(JFrame ventanaPadre) {
        int opcion = JOptionPane.showConfirmDialog(
                ventanaPadre,
                "¿Está seguro de que desea cerrar el Sistema de Gestión Agropecuaria?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            System.out.println("Cerrando aplicación...");
            System.out.println("¡Gracias por usar el Sistema de Gestión Agropecuaria!");
            System.exit(0);
        }
    }

    /**
     * Muestra un mensaje de error al usuario cuando la aplicación no puede iniciarse
     * @param error la excepción que causó el error
     */
    private static void mostrarErrorInicio(Exception error) {
        String mensaje = "No se pudo iniciar el Sistema de Gestión Agropecuaria.\n\n" +
                "Error: " + error.getMessage() + "\n\n" +
                "Por favor, verifique:\n" +
                "• Que Java esté correctamente instalado\n" +
                "• Que tenga permisos suficientes\n" +
                "• Que no haya otras instancias ejecutándose\n\n" +
                "Si el problema persiste, contacte al administrador del sistema.";

        try {
            JOptionPane.showMessageDialog(
                    null,
                    mensaje,
                    "Error de Inicio - Sistema de Gestión Agropecuaria",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            // Si incluso mostrar el diálogo falla, al menos imprimir en consola
            System.err.println("Error crítico: No se puede mostrar interfaz gráfica.");
            System.err.println(mensaje);
        }
    }
}