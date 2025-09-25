package co.unibague.agropecuario.view.principal;

import co.unibague.agropecuario.controller.*;
import co.unibague.agropecuario.model.singleton.CooperativaInfo;
import co.unibague.agropecuario.utils.Constantes;
import co.unibague.agropecuario.view.calculos.VentanaCalculos;
import co.unibague.agropecuario.view.dialogs.AcercaDeDialog;
import co.unibague.agropecuario.view.fincas.VentanaFinca;
import co.unibague.agropecuario.view.productos.VentanaProductoAgricola;
import co.unibague.agropecuario.view.productos.VentanaProductoGanadero;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación.
 * Implementa el patrón MVC - Vista principal del sistema.
 */
public class VentanaPrincipal extends JFrame {

    // Controladores
    private ProductoAgricolaController agricolaController;
    private ProductoGanaderoController ganaderoController;
    private FincaController fincaController;
    private CalculadoraController calculadoraController;

    // Componentes de la interfaz
    private JPanel panelPrincipal;
    private JLabel lblBienvenida;
    private JLabel lblEstadisticas;

    /**
     * Constructor de la ventana principal
     */
    public VentanaPrincipal() {
        inicializarControladores();
        configurarVentana();
        crearMenuBar();
        crearPanelPrincipal();
        actualizarEstadisticas();
    }

    /**
     * Inicializa los controladores del sistema
     */
    private void inicializarControladores() {
        agricolaController = new ProductoAgricolaController();
        ganaderoController = new ProductoGanaderoController();
        fincaController = new FincaController();
        calculadoraController = new CalculadoraController(agricolaController, ganaderoController);
    }

    /**
     * Configura las propiedades básicas de la ventana
     */
    private void configurarVentana() {
        setTitle(Constantes.TITULO_APLICACION + " - v" + Constantes.VERSION);
        setSize(Constantes.VENTANA_ANCHO_DEFAULT, Constantes.VENTANA_ALTO_DEFAULT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Configurar look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            // Si no se puede cambiar el look and feel, continuar con el por defecto
            System.err.println("No se pudo establecer el Look and Feel del sistema: " + e.getMessage());
        }
    }

    /**
     * Crea el menú principal de la aplicación
     */
    private void crearMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Productos Agrícolas
        JMenu menuAgricolas = new JMenu("Productos Agrícolas");
        menuAgricolas.setFont(Constantes.FONT_NORMAL);

        JMenuItem itemGestionarAgricolas = new JMenuItem("Gestionar Productos Agrícolas");
        itemGestionarAgricolas.addActionListener(e -> abrirVentanaProductosAgricolas());
        menuAgricolas.add(itemGestionarAgricolas);

        JMenuItem itemListarAgricolas = new JMenuItem("Listar Productos Agrícolas");
        itemListarAgricolas.addActionListener(e -> {
            abrirVentanaProductosAgricolas();
            mostrarMensajeInformativo("Se ha abierto la ventana de gestión de productos agrícolas.\nPuede ver la lista en la tabla del lado derecho.");
        });
        menuAgricolas.add(itemListarAgricolas);

        // Menú Productos Ganaderos
        JMenu menuGanaderos = new JMenu("Productos Ganaderos");
        menuGanaderos.setFont(Constantes.FONT_NORMAL);

        JMenuItem itemGestionarGanaderos = new JMenuItem("Gestionar Productos Ganaderos");
        itemGestionarGanaderos.addActionListener(e -> abrirVentanaProductosGanaderos());
        menuGanaderos.add(itemGestionarGanaderos);

        JMenuItem itemListarGanaderos = new JMenuItem("Listar Productos Ganaderos");
        itemListarGanaderos.addActionListener(e -> {
            abrirVentanaProductosGanaderos();
            mostrarMensajeInformativo("Se ha abierto la ventana de gestión de productos ganaderos.\nPuede ver la lista en la tabla del lado derecho.");
        });
        menuGanaderos.add(itemListarGanaderos);

        JMenuItem itemAplicarTratamientos = new JMenuItem("Aplicar Tratamientos");
        itemAplicarTratamientos.addActionListener(e -> {
            abrirVentanaProductosGanaderos();
            mostrarMensajeInformativo("Se ha abierto la ventana de productos ganaderos.\nSeleccione un producto de la tabla y use el botón 'Aplicar Tratamiento' para aplicar tratamientos veterinarios.");
        });
        menuGanaderos.add(itemAplicarTratamientos);

        // Menú Fincas
        JMenu menuFincas = new JMenu("Fincas");
        menuFincas.setFont(Constantes.FONT_NORMAL);

        JMenuItem itemGestionarFincas = new JMenuItem("Gestionar Fincas");
        itemGestionarFincas.addActionListener(e -> abrirVentanaFincas());
        menuFincas.add(itemGestionarFincas);

        JMenuItem itemListarFincas = new JMenuItem("Listar Fincas");
        itemListarFincas.addActionListener(e -> {
            abrirVentanaFincas();
            mostrarMensajeInformativo("Se ha abierto la ventana de gestión de fincas.\nPuede ver la lista de fincas registradas en la tabla.");
        });
        menuFincas.add(itemListarFincas);

        JMenuItem itemEstadisticasFincas = new JMenuItem("Estadísticas de Fincas");
        itemEstadisticasFincas.addActionListener(e -> {
            VentanaFinca ventanaFincas = new VentanaFinca(fincaController);
            ventanaFincas.setVisible(true);
            // Simular clic en botón estadísticas después de un momento
            SwingUtilities.invokeLater(() -> {
                mostrarMensajeInformativo("Se ha abierto la ventana de fincas.\nUse el botón 'Estadísticas' para ver información detallada por municipios.");
            });
        });
        menuFincas.add(itemEstadisticasFincas);

        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        menuReportes.setFont(Constantes.FONT_NORMAL);

        JMenuItem itemCalculos = new JMenuItem("Cálculos y Rentabilidad");
        itemCalculos.addActionListener(e -> abrirVentanaCalculos());
        menuReportes.add(itemCalculos);

        JMenuItem itemEstadisticas = new JMenuItem("Estadísticas Generales");
        itemEstadisticas.addActionListener(e -> mostrarEstadisticas());
        menuReportes.add(itemEstadisticas);

        JMenuItem itemReporteCompleto = new JMenuItem("Reporte Ejecutivo");
        itemReporteCompleto.addActionListener(e -> {
            abrirVentanaCalculos();
            mostrarMensajeInformativo("Se ha abierto la ventana de cálculos.\nDiríjase a la pestaña 'Resumen Ejecutivo' para ver el reporte completo.");
        });
        menuReportes.add(itemReporteCompleto);

        JMenuItem itemAnalisisComparativo = new JMenuItem("Análisis Comparativo");
        itemAnalisisComparativo.addActionListener(e -> {
            VentanaCalculos ventanaCalculos = new VentanaCalculos(calculadoraController);
            ventanaCalculos.setVisible(true);
            SwingUtilities.invokeLater(() -> {
                mostrarMensajeInformativo("Se ha abierto la ventana de cálculos.\nUse el botón 'Análisis Comparativo' para ver comparaciones detalladas entre sectores.");
            });
        });
        menuReportes.add(itemAnalisisComparativo);

        // Menú Herramientas
        JMenu menuHerramientas = new JMenu("Herramientas");
        menuHerramientas.setFont(Constantes.FONT_NORMAL);

        JMenuItem itemActualizar = new JMenuItem("Actualizar Dashboard");
        itemActualizar.addActionListener(e -> {
            actualizarEstadisticas();
            repaint();
            mostrarMensajeInformativo("Dashboard actualizado correctamente.");
        });
        menuHerramientas.add(itemActualizar);

        JMenuItem itemConfiguracion = new JMenuItem("Configuración");
        itemConfiguracion.addActionListener(e -> {
            mostrarMensajeInformativo("Configuración del sistema:\n\n" +
                    "• Versión: " + Constantes.VERSION + "\n" +
                    "• Java: " + System.getProperty("java.version") + "\n" +
                    "• OS: " + System.getProperty("os.name") + "\n" +
                    "• Memoria disponible: " + (Runtime.getRuntime().freeMemory() / 1024 / 1024) + " MB");
        });
        menuHerramientas.add(itemConfiguracion);

        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        menuAyuda.setFont(Constantes.FONT_NORMAL);

        JMenuItem itemAcercaDe = new JMenuItem("Acerca de...");
        itemAcercaDe.addActionListener(e -> mostrarAcercaDe());
        menuAyuda.add(itemAcercaDe);

        JMenuItem itemCooperativa = new JMenuItem("Información de la Cooperativa");
        itemCooperativa.addActionListener(e -> mostrarInfoCooperativa());
        menuAyuda.add(itemCooperativa);

        JMenuItem itemManualUsuario = new JMenuItem("Manual de Usuario");
        itemManualUsuario.addActionListener(e -> mostrarManualUsuario());
        menuAyuda.add(itemManualUsuario);

        // Agregar menús a la barra
        menuBar.add(menuAgricolas);
        menuBar.add(menuGanaderos);
        menuBar.add(menuFincas);
        menuBar.add(menuReportes);
        menuBar.add(menuHerramientas);
        menuBar.add(menuAyuda);

        setJMenuBar(menuBar);
    }

    /**
     * Abre la ventana de gestión de productos agrícolas
     */
    private void abrirVentanaProductosAgricolas() {
        try {
            VentanaProductoAgricola ventana = new VentanaProductoAgricola(agricolaController, fincaController);
            ventana.setVisible(true);
        } catch (Exception e) {
            mostrarError("Error al abrir la ventana de productos agrícolas: " + e.getMessage());
        }
    }

    /**
     * Abre la ventana de gestión de productos ganaderos
     */
    private void abrirVentanaProductosGanaderos() {
        try {
            VentanaProductoGanadero ventana = new VentanaProductoGanadero(ganaderoController);
            ventana.setVisible(true);
        } catch (Exception e) {
            mostrarError("Error al abrir la ventana de productos ganaderos: " + e.getMessage());
        }
    }

    /**
     * Abre la ventana de gestión de fincas
     */
    private void abrirVentanaFincas() {
        try {
            VentanaFinca ventana = new VentanaFinca(fincaController);
            ventana.setVisible(true);
        } catch (Exception e) {
            mostrarError("Error al abrir la ventana de fincas: " + e.getMessage());
        }
    }

    /**
     * Abre la ventana de cálculos y rentabilidad
     */
    private void abrirVentanaCalculos() {
        try {
            VentanaCalculos ventana = new VentanaCalculos(calculadoraController);
            ventana.setVisible(true);
        } catch (Exception e) {
            mostrarError("Error al abrir la ventana de cálculos: " + e.getMessage());
        }
    }

    /**
     * Crea el panel principal con información de bienvenida
     */
    private void crearPanelPrincipal() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Constantes.COLOR_LIGHT);

        // Panel superior con bienvenida
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Constantes.COLOR_PRIMARY);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblBienvenida = new JLabel(Constantes.TITULO_APLICACION, SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 28));
        lblBienvenida.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel(CooperativaInfo.getInstance().getInformacionResumida(), SwingConstants.CENTER);
        lblSubtitulo.setFont(Constantes.FONT_SUBTITLE);
        lblSubtitulo.setForeground(Color.WHITE);

        panelSuperior.add(lblBienvenida, BorderLayout.CENTER);
        panelSuperior.add(lblSubtitulo, BorderLayout.SOUTH);

        // Panel central con estadísticas
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(Constantes.COLOR_LIGHT);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Tarjetas de resumen
        gbc.gridx = 0; gbc.gridy = 0;
        panelCentral.add(crearTarjetaResumen("Productos Agrícolas",
                String.valueOf(agricolaController.obtenerTotal()),
                Constantes.COLOR_SUCCESS, "Gestionar productos del campo"), gbc);

        gbc.gridx = 1;
        panelCentral.add(crearTarjetaResumen("Productos Ganaderos",
                String.valueOf(ganaderoController.obtenerTotal()),
                Constantes.COLOR_INFO, "Gestionar ganado y tratamientos"), gbc);

        gbc.gridx = 2;
        panelCentral.add(crearTarjetaResumen("Fincas Registradas",
                String.valueOf(fincaController.obtenerTotal()),
                Constantes.COLOR_WARNING, "Administrar propiedades"), gbc);

        // Panel inferior con información adicional
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Constantes.COLOR_LIGHT);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblEstadisticas = new JLabel("<html><center>" +
                "<b>¡Bienvenido al Sistema de Gestión Agropecuaria!</b><br><br>" +
                "Utilice el menú superior para navegar entre las diferentes funcionalidades:<br>" +
                "• <b>Productos Agrícolas:</b> Gestione cultivos, hectáreas y cosechas<br>" +
                "• <b>Productos Ganaderos:</b> Administre ganado y aplique tratamientos<br>" +
                "• <b>Fincas:</b> Registre y administre propiedades rurales<br>" +
                "• <b>Reportes:</b> Analice rentabilidad y genere estadísticas<br><br>" +
                "<i>Todas las funcionalidades están completamente operativas.</i>" +
                "</center></html>", SwingConstants.CENTER);
        lblEstadisticas.setFont(Constantes.FONT_NORMAL);
        lblEstadisticas.setForeground(Constantes.COLOR_DARK);

        panelInferior.add(lblEstadisticas, BorderLayout.CENTER);

        // Ensamblar panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    /**
     * Crea una tarjeta de resumen para el dashboard
     */
    private JPanel crearTarjetaResumen(String titulo, String valor, Color color, String descripcion) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(200, 120));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(Constantes.FONT_SUBTITLE);
        lblTitulo.setForeground(color);

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 36));
        lblValor.setForeground(Constantes.COLOR_DARK);

        JLabel lblDescripcion = new JLabel("<html><center><small>" + descripcion + "</small></center></html>", SwingConstants.CENTER);
        lblDescripcion.setFont(Constantes.FONT_SMALL);
        lblDescripcion.setForeground(Color.GRAY);

        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);
        tarjeta.add(lblDescripcion, BorderLayout.SOUTH);

        // Agregar efecto de hover
        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                tarjeta.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                tarjeta.setBackground(Color.WHITE);
            }
        });

        return tarjeta;
    }

    /**
     * Actualiza las estadísticas mostradas en el panel principal
     */
    private void actualizarEstadisticas() {
        SwingUtilities.invokeLater(this::repaint);
    }

    // === MÉTODOS DE DIÁLOGOS ===

    private void mostrarEstadisticas() {
        String estadisticas = calculadoraController.generarResumenEjecutivo();
        JTextArea textArea = new JTextArea(estadisticas);
        textArea.setEditable(false);
        textArea.setFont(Constantes.FONT_NORMAL);
        textArea.setBackground(Constantes.COLOR_LIGHT);
        textArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Estadísticas Generales", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarInfoCooperativa() {
        String info = CooperativaInfo.getInstance().getInformacionCompleta();
        JTextArea textArea = new JTextArea(info);
        textArea.setEditable(false);
        textArea.setFont(Constantes.FONT_NORMAL);
        textArea.setBackground(Constantes.COLOR_LIGHT);
        textArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Información de la Cooperativa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarAcercaDe() {
        try {
            AcercaDeDialog dialog = new AcercaDeDialog(this);
            dialog.setVisible(true);
        } catch (Exception e) {
            // Si hay error con el diálogo personalizado, mostrar uno simple
            String mensaje = "=== " + Constantes.TITULO_APLICACION + " ===\n\n" +
                    "Versión: " + Constantes.VERSION + "\n" +
                    "Desarrollado por: " + Constantes.DESARROLLADORES + "\n\n" +
                    "Este sistema permite gestionar información agropecuaria\n" +
                    "de productos agrícolas, ganaderos y fincas del Tolima.\n\n" +
                    "Implementa los principios SOLID y patrones de diseño\n" +
                    "MVC, Observer y Singleton.\n\n" +
                    "Universidad de Ibagué - 2025";

            JOptionPane.showMessageDialog(this, mensaje, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarManualUsuario() {
        String manual = """
                === MANUAL DE USUARIO ===
                
                PRODUCTOS AGRÍCOLAS:
                • Crear, editar y eliminar productos del campo
                • Asociar productos con fincas específicas
                • Calcular rentabilidad por hectárea
                • Gestionar tipos de cultivo del Tolima
                
                PRODUCTOS GANADEROS:
                • Administrar diferentes tipos de ganado
                • Aplicar tratamientos veterinarios
                • Calcular rentabilidad por animal
                • Controlar alimentación y producción
                
                FINCAS:
                • Registrar propiedades rurales
                • Gestionar información de propietarios
                • Calcular áreas cultivables
                • Ver estadísticas por municipio
                
                REPORTES Y ANÁLISIS:
                • Generar reportes de rentabilidad
                • Comparar sectores agrícola vs ganadero
                • Visualizar gráficas de análisis
                • Exportar información para estudios
                
                NAVEGACIÓN:
                • Use el menú superior para acceder a funciones
                • Las tablas se actualizan automáticamente
                • Los formularios incluyen validaciones
                • Los cálculos usan polimorfismo por tipo
                """;

        JTextArea textArea = new JTextArea(manual);
        textArea.setEditable(false);
        textArea.setFont(Constantes.FONT_NORMAL);
        textArea.setBackground(Constantes.COLOR_LIGHT);
        textArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 500));

        JOptionPane.showMessageDialog(this, scrollPane, "Manual de Usuario", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarMensajeInformativo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra la ventana principal
     */
    public void mostrar() {
        setVisible(true);
    }
}