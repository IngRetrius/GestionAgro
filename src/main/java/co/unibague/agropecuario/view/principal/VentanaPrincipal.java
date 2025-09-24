package co.unibague.agropecuario.view.principal;

import co.unibague.agropecuario.controller.*;
import co.unibague.agropecuario.model.singleton.CooperativaInfo;
import co.unibague.agropecuario.utils.Constantes;

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
        itemGestionarAgricolas.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo", "Información", JOptionPane.INFORMATION_MESSAGE);
        });
        menuAgricolas.add(itemGestionarAgricolas);

        // Menú Productos Ganaderos
        JMenu menuGanaderos = new JMenu("Productos Ganaderos");
        menuGanaderos.setFont(Constantes.FONT_NORMAL);

        JMenuItem itemGestionarGanaderos = new JMenuItem("Gestionar Productos Ganaderos");
        itemGestionarGanaderos.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo", "Información", JOptionPane.INFORMATION_MESSAGE);
        });
        menuGanaderos.add(itemGestionarGanaderos);

        // Menú Fincas
        JMenu menuFincas = new JMenu("Fincas");
        menuFincas.setFont(Constantes.FONT_NORMAL);

        JMenuItem itemGestionarFincas = new JMenuItem("Gestionar Fincas");
        itemGestionarFincas.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo", "Información", JOptionPane.INFORMATION_MESSAGE);
        });
        menuFincas.add(itemGestionarFincas);

        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        menuReportes.setFont(Constantes.FONT_NORMAL);

        JMenuItem itemCalculos = new JMenuItem("Cálculos y Rentabilidad");
        itemCalculos.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo", "Información", JOptionPane.INFORMATION_MESSAGE);
        });
        menuReportes.add(itemCalculos);

        JMenuItem itemEstadisticas = new JMenuItem("Estadísticas Generales");
        itemEstadisticas.addActionListener(e -> mostrarEstadisticas());
        menuReportes.add(itemEstadisticas);

        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        menuAyuda.setFont(Constantes.FONT_NORMAL);

        JMenuItem itemAcercaDe = new JMenuItem("Acerca de...");
        itemAcercaDe.addActionListener(e -> mostrarAcercaDe());
        menuAyuda.add(itemAcercaDe);

        JMenuItem itemCooperativa = new JMenuItem("Información de la Cooperativa");
        itemCooperativa.addActionListener(e -> mostrarInfoCooperativa());
        menuAyuda.add(itemCooperativa);

        // Agregar menús a la barra
        menuBar.add(menuAgricolas);
        menuBar.add(menuGanaderos);
        menuBar.add(menuFincas);
        menuBar.add(menuReportes);
        menuBar.add(menuAyuda);

        setJMenuBar(menuBar);
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
                Constantes.COLOR_SUCCESS), gbc);

        gbc.gridx = 1;
        panelCentral.add(crearTarjetaResumen("Productos Ganaderos",
                String.valueOf(ganaderoController.obtenerTotal()),
                Constantes.COLOR_INFO), gbc);

        gbc.gridx = 2;
        panelCentral.add(crearTarjetaResumen("Fincas Registradas",
                String.valueOf(fincaController.obtenerTotal()),
                Constantes.COLOR_WARNING), gbc);

        // Panel inferior con información adicional
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(Constantes.COLOR_LIGHT);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblEstadisticas = new JLabel("<html><center>Utilice el menú superior para navegar entre las diferentes funcionalidades del sistema.<br/>Para comenzar, seleccione una opción del menú.</center></html>", SwingConstants.CENTER);
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
    private JPanel crearTarjetaResumen(String titulo, String valor, Color color) {
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

        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);

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

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Información de la Cooperativa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarAcercaDe() {
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

    /**
     * Muestra la ventana principal
     */
    public void mostrar() {
        setVisible(true);
    }
}