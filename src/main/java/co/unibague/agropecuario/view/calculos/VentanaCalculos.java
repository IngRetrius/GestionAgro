package co.unibague.agropecuario.view.calculos;

import co.unibague.agropecuario.controller.CalculadoraController;
import co.unibague.agropecuario.utils.Constantes;
import co.unibague.agropecuario.utils.Formateador;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Ventana para mostrar cálculos de rentabilidad y estadísticas.
 * Demuestra el uso de polimorfismo en los cálculos.
 */
public class VentanaCalculos extends JFrame {

    private CalculadoraController controller;

    // Componentes de la interfaz
    private JTextArea txtReporteRentabilidad;
    private JTextArea txtEstadisticasGenerales;
    private JTextArea txtResumenEjecutivo;
    private JLabel lblProductoMasRentable;
    private JButton btnActualizar, btnExportar, btnComparar;

    // Paneles para las gráficas visuales
    private JPanel panelGraficaRentabilidad;
    private JPanel panelGraficaEstadisticas;

    public VentanaCalculos(CalculadoraController controller) {
        this.controller = controller;

        configurarVentana();
        crearComponentes();
        actualizarDatos();
    }

    private void configurarVentana() {
        setTitle("Cálculos y Rentabilidad - Sistema Agropecuario");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Panel superior - Título y controles
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central - Pestañas con diferentes vistas
        JTabbedPane tabbedPane = crearTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        // Panel inferior - Botones de acción
        JPanel panelInferior = crearPanelInferior();
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Constantes.COLOR_PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Análisis de Rentabilidad y Estadísticas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        lblProductoMasRentable = new JLabel("", SwingConstants.CENTER);
        lblProductoMasRentable.setFont(Constantes.FONT_SUBTITLE);
        lblProductoMasRentable.setForeground(Constantes.COLOR_SECONDARY);

        panel.add(lblTitulo, BorderLayout.CENTER);
        panel.add(lblProductoMasRentable, BorderLayout.SOUTH);

        return panel;
    }

    private JTabbedPane crearTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(Constantes.FONT_NORMAL);

        // Pestaña 1: Reporte de Rentabilidad
        JPanel panelRentabilidad = crearPanelReporteRentabilidad();
        tabbedPane.addTab("Rentabilidad por Producto", panelRentabilidad);

        // Pestaña 2: Estadísticas Generales
        JPanel panelEstadisticas = crearPanelEstadisticasGenerales();
        tabbedPane.addTab("Estadísticas Generales", panelEstadisticas);

        // Pestaña 3: Resumen Ejecutivo
        JPanel panelResumen = crearPanelResumenEjecutivo();
        tabbedPane.addTab("Resumen Ejecutivo", panelResumen);

        // Pestaña 4: Gráficas
        JPanel panelGraficas = crearPanelGraficas();
        tabbedPane.addTab("Análisis Visual", panelGraficas);

        return tabbedPane;
    }

    private JPanel crearPanelReporteRentabilidad() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título de la sección
        JLabel lblTitulo = new JLabel("Reporte Detallado de Rentabilidad por Tipo de Producto");
        lblTitulo.setFont(Constantes.FONT_TITLE);
        lblTitulo.setForeground(Constantes.COLOR_PRIMARY);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Área de texto para el reporte
        txtReporteRentabilidad = new JTextArea();
        txtReporteRentabilidad.setEditable(false);
        txtReporteRentabilidad.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtReporteRentabilidad.setBackground(Color.WHITE);
        txtReporteRentabilidad.setBorder(BorderFactory.createLoweredBevelBorder());
        txtReporteRentabilidad.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollReporte = new JScrollPane(txtReporteRentabilidad);
        scrollReporte.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollReporte, BorderLayout.CENTER);

        // Panel de información adicional
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información"));
        panelInfo.setPreferredSize(new Dimension(0, 60));

        JLabel lblInfo = new JLabel("<html><i>Este reporte muestra la rentabilidad calculada usando polimorfismo.<br>" +
                "Cada tipo de producto utiliza su propio algoritmo de cálculo.</i></html>");
        lblInfo.setFont(Constantes.FONT_SMALL);
        lblInfo.setForeground(Constantes.COLOR_DARK);
        panelInfo.add(lblInfo, BorderLayout.CENTER);

        panel.add(panelInfo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelEstadisticasGenerales() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título de la sección
        JLabel lblTitulo = new JLabel("Estadísticas Generales del Sistema");
        lblTitulo.setFont(Constantes.FONT_TITLE);
        lblTitulo.setForeground(Constantes.COLOR_PRIMARY);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Área de texto para las estadísticas
        txtEstadisticasGenerales = new JTextArea();
        txtEstadisticasGenerales.setEditable(false);
        txtEstadisticasGenerales.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtEstadisticasGenerales.setBackground(Color.WHITE);
        txtEstadisticasGenerales.setBorder(BorderFactory.createLoweredBevelBorder());
        txtEstadisticasGenerales.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollEstadisticas = new JScrollPane(txtEstadisticasGenerales);
        scrollEstadisticas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollEstadisticas, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelResumenEjecutivo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título de la sección
        JLabel lblTitulo = new JLabel("Resumen Ejecutivo");
        lblTitulo.setFont(Constantes.FONT_TITLE);
        lblTitulo.setForeground(Constantes.COLOR_PRIMARY);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Área de texto para el resumen ejecutivo
        txtResumenEjecutivo = new JTextArea();
        txtResumenEjecutivo.setEditable(false);
        txtResumenEjecutivo.setFont(new Font("Arial", Font.PLAIN, 13));
        txtResumenEjecutivo.setBackground(new Color(248, 249, 250));
        txtResumenEjecutivo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JScrollPane scrollResumen = new JScrollPane(txtResumenEjecutivo);
        scrollResumen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollResumen, BorderLayout.CENTER);

        // Panel de acciones rápidas
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAcciones.setBackground(Constantes.COLOR_LIGHT);

        JButton btnRecomendaciones = new JButton("Ver Recomendaciones");
        btnRecomendaciones.setBackground(Constantes.COLOR_INFO);
        btnRecomendaciones.setForeground(Color.WHITE);
        btnRecomendaciones.addActionListener(e -> mostrarRecomendaciones());

        JButton btnTendencias = new JButton("Análisis de Tendencias");
        btnTendencias.setBackground(Constantes.COLOR_WARNING);
        btnTendencias.setForeground(Color.BLACK);
        btnTendencias.addActionListener(e -> mostrarTendencias());

        panelAcciones.add(btnRecomendaciones);
        panelAcciones.add(btnTendencias);

        panel.add(panelAcciones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelGraficas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Título de la sección
        JLabel lblTitulo = new JLabel("Análisis Visual de Datos");
        lblTitulo.setFont(Constantes.FONT_TITLE);
        lblTitulo.setForeground(Constantes.COLOR_PRIMARY);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Panel central dividido para las gráficas
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(700);

        // Panel izquierdo - Gráfica de rentabilidad
        panelGraficaRentabilidad = crearPanelGraficaRentabilidad();
        splitPane.setLeftComponent(panelGraficaRentabilidad);

        // Panel derecho - Gráfica de estadísticas
        panelGraficaEstadisticas = crearPanelGraficaEstadisticas();
        splitPane.setRightComponent(panelGraficaEstadisticas);

        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelGraficaRentabilidad() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Comparación de Rentabilidad"));
        panel.setBackground(Color.WHITE);

        // Crear una representación visual simple de barras
        JPanel panelBarras = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarGraficaRentabilidad(g);
            }
        };
        panelBarras.setBackground(Color.WHITE);
        panelBarras.setPreferredSize(new Dimension(600, 300));

        panel.add(panelBarras, BorderLayout.CENTER);

        // Leyenda
        JLabel lblLeyenda = new JLabel("<html><b>Leyenda:</b> <span style='color: green;'>■</span> Productos Agrícolas " +
                "<span style='color: blue;'>■</span> Productos Ganaderos</html>");
        lblLeyenda.setFont(Constantes.FONT_SMALL);
        panel.add(lblLeyenda, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelGraficaEstadisticas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Distribución de Productos"));
        panel.setBackground(Color.WHITE);

        // Crear una representación visual simple de gráfica circular
        JPanel panelCircular = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarGraficaCircular(g);
            }
        };
        panelCircular.setBackground(Color.WHITE);
        panelCircular.setPreferredSize(new Dimension(500, 300));

        panel.add(panelCircular, BorderLayout.CENTER);

        return panel;
    }

    private void dibujarGraficaRentabilidad(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Map<String, Double> rentabilidades = controller.calcularRentabilidadesPorTipo();

        int x = 50;
        int baseY = 250;
        int anchoBarra = 100;
        int espaciado = 50;

        // Encontrar el valor máximo para escalar
        double maxValue = rentabilidades.values().stream().mapToDouble(Double::doubleValue).max().orElse(1.0);
        maxValue = Math.max(maxValue, 1000); // Mínimo para visualización

        int i = 0;
        for (Map.Entry<String, Double> entry : rentabilidades.entrySet()) {
            String tipo = entry.getKey();
            double valor = entry.getValue();

            // Calcular altura de la barra (máximo 200 píxeles)
            int alturaBarra = (int) ((valor / maxValue) * 200);

            // Color según el tipo
            if (tipo.contains("Agrícolas")) {
                g2d.setColor(Constantes.COLOR_SUCCESS);
            } else {
                g2d.setColor(Constantes.COLOR_INFO);
            }

            // Dibujar barra
            int posX = x + (i * (anchoBarra + espaciado));
            g2d.fillRect(posX, baseY - alturaBarra, anchoBarra, alturaBarra);

            // Dibujar borde
            g2d.setColor(Color.BLACK);
            g2d.drawRect(posX, baseY - alturaBarra, anchoBarra, alturaBarra);

            // Etiqueta del tipo
            g2d.setFont(Constantes.FONT_SMALL);
            String tipoCorto = tipo.contains("Agrícolas") ? "Agrícolas" : "Ganaderos";
            FontMetrics fm = g2d.getFontMetrics();
            int anchoTexto = fm.stringWidth(tipoCorto);
            g2d.drawString(tipoCorto, posX + (anchoBarra - anchoTexto) / 2, baseY + 15);

            // Valor de la rentabilidad
            String valorTexto = Formateador.formatearMoneda(valor);
            int anchoValor = fm.stringWidth(valorTexto);
            g2d.drawString(valorTexto, posX + (anchoBarra - anchoValor) / 2, baseY - alturaBarra - 5);

            i++;
        }

        // Título de la gráfica
        g2d.setFont(Constantes.FONT_SUBTITLE);
        g2d.setColor(Constantes.COLOR_DARK);
        g2d.drawString("Rentabilidad Promedio por Tipo", 50, 30);
    }

    private void dibujarGraficaCircular(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Map<String, Object> estadisticas = controller.calcularEstadisticasGenerales();

        int totalAgricolas = (Integer) estadisticas.get("Total Productos Agrícolas");
        int totalGanaderos = (Integer) estadisticas.get("Total Productos Ganaderos");
        int total = totalAgricolas + totalGanaderos;

        if (total == 0) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(Constantes.FONT_NORMAL);
            g2d.drawString("No hay datos para mostrar", 150, 150);
            return;
        }

        // Parámetros del círculo
        int centroX = 200;
        int centroY = 150;
        int radio = 80;

        // Calcular ángulos
        double anguloAgricolas = (double) totalAgricolas / total * 360;
        double anguloGanaderos = (double) totalGanaderos / total * 360;

        // Dibujar sector de productos agrícolas
        g2d.setColor(Constantes.COLOR_SUCCESS);
        g2d.fillArc(centroX - radio, centroY - radio, radio * 2, radio * 2, 0, (int) anguloAgricolas);

        // Dibujar sector de productos ganaderos
        g2d.setColor(Constantes.COLOR_INFO);
        g2d.fillArc(centroX - radio, centroY - radio, radio * 2, radio * 2, (int) anguloAgricolas, (int) anguloGanaderos);

        // Dibujar borde
        g2d.setColor(Color.BLACK);
        g2d.drawOval(centroX - radio, centroY - radio, radio * 2, radio * 2);

        // Etiquetas
        g2d.setFont(Constantes.FONT_SMALL);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Agrícolas: " + totalAgricolas + " (" + String.format("%.1f%%", (double) totalAgricolas / total * 100) + ")",
                centroX + radio + 20, centroY - 20);
        g2d.drawString("Ganaderos: " + totalGanaderos + " (" + String.format("%.1f%%", (double) totalGanaderos / total * 100) + ")",
                centroX + radio + 20, centroY + 10);

        // Título
        g2d.setFont(Constantes.FONT_SUBTITLE);
        g2d.setColor(Constantes.COLOR_DARK);
        g2d.drawString("Distribución de Productos", 120, 30);
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Constantes.COLOR_LIGHT);

        btnActualizar = new JButton("Actualizar Datos");
        btnActualizar.setBackground(Constantes.COLOR_SUCCESS);
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFont(Constantes.FONT_NORMAL);
        btnActualizar.addActionListener(e -> actualizarDatos());

        btnExportar = new JButton("Exportar Reporte");
        btnExportar.setBackground(Constantes.COLOR_WARNING);
        btnExportar.setForeground(Color.BLACK);
        btnExportar.setFont(Constantes.FONT_NORMAL);
        btnExportar.addActionListener(e -> exportarReporte());

        btnComparar = new JButton("Análisis Comparativo");
        btnComparar.setBackground(Constantes.COLOR_INFO);
        btnComparar.setForeground(Color.BLACK);
        btnComparar.setFont(Constantes.FONT_NORMAL);
        btnComparar.addActionListener(e -> mostrarAnalisisComparativo());

        panel.add(btnActualizar);
        panel.add(btnExportar);
        panel.add(btnComparar);

        return panel;
    }

    private void actualizarDatos() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Actualizar reporte de rentabilidad
                String reporteRentabilidad = controller.generarReporteRentabilidad();
                txtReporteRentabilidad.setText(reporteRentabilidad);
                txtReporteRentabilidad.setCaretPosition(0);

                // Actualizar estadísticas generales
                Map<String, Object> estadisticas = controller.calcularEstadisticasGenerales();
                StringBuilder statsText = new StringBuilder();
                statsText.append("=== ESTADÍSTICAS GENERALES DEL SISTEMA ===\n\n");

                for (Map.Entry<String, Object> entry : estadisticas.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    if (value instanceof Double) {
                        statsText.append(String.format("%-30s: %s\n", key, Formateador.formatearDecimal((Double) value)));
                    } else {
                        statsText.append(String.format("%-30s: %s\n", key, value.toString()));
                    }
                }

                txtEstadisticasGenerales.setText(statsText.toString());
                txtEstadisticasGenerales.setCaretPosition(0);

                // Actualizar resumen ejecutivo
                String resumenEjecutivo = controller.generarResumenEjecutivo();
                txtResumenEjecutivo.setText(resumenEjecutivo);
                txtResumenEjecutivo.setCaretPosition(0);

                // Actualizar producto más rentable
                String productoMasRentable = controller.encontrarProductoMasRentable();
                lblProductoMasRentable.setText(productoMasRentable);

                // Repintar las gráficas
                panelGraficaRentabilidad.repaint();
                panelGraficaEstadisticas.repaint();

                JOptionPane.showMessageDialog(this,
                        "Datos actualizados correctamente",
                        "Actualización Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error al actualizar los datos: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void exportarReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE COMPLETO DEL SISTEMA AGROPECUARIO ===\n");
        reporte.append("Fecha de generación: ").append(java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n\n");

        reporte.append(txtResumenEjecutivo.getText()).append("\n\n");
        reporte.append("=== DETALLES DE RENTABILIDAD ===\n");
        reporte.append(txtReporteRentabilidad.getText()).append("\n\n");
        reporte.append("=== ESTADÍSTICAS COMPLETAS ===\n");
        reporte.append(txtEstadisticasGenerales.getText());

        JTextArea areaReporte = new JTextArea(reporte.toString());
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font("Monospaced", Font.PLAIN, 10));

        JScrollPane scrollPane = new JScrollPane(areaReporte);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(this, scrollPane, "Reporte Completo para Exportar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarRecomendaciones() {
        Map<String, Double> rentabilidades = controller.calcularRentabilidadesPorTipo();

        StringBuilder recomendaciones = new StringBuilder();
        recomendaciones.append("=== RECOMENDACIONES ESTRATÉGICAS ===\n\n");

        double rentabilidadAgricola = rentabilidades.get("Productos Agrícolas");
        double rentabilidadGanadera = rentabilidades.get("Productos Ganaderos");

        if (rentabilidadAgricola > rentabilidadGanadera * 1.2) {
            recomendaciones.append("✓ RECOMENDACIÓN: Considere expandir la producción agrícola\n");
            recomendaciones.append("  Razón: La rentabilidad agrícola supera significativamente a la ganadera\n\n");
        } else if (rentabilidadGanadera > rentabilidadAgricola * 1.2) {
            recomendaciones.append("✓ RECOMENDACIÓN: Considere expandir la producción ganadera\n");
            recomendaciones.append("  Razón: La rentabilidad ganadera supera significativamente a la agrícola\n\n");
        } else {
            recomendaciones.append("✓ RECOMENDACIÓN: Mantenga un portafolio diversificado\n");
            recomendaciones.append("  Razón: Ambos sectores muestran rentabilidades similares\n\n");
        }

        recomendaciones.append("ACCIONES SUGERIDAS:\n");
        recomendaciones.append("• Optimizar costos de producción en productos menos rentables\n");
        recomendaciones.append("• Investigar nuevas variedades o razas más productivas\n");
        recomendaciones.append("• Evaluar la implementación de tecnologías de precisión\n");
        recomendaciones.append("• Considerar la certificación orgánica para productos premium\n");
        recomendaciones.append("• Analizar oportunidades de integración vertical\n");

        JTextArea areaRecomendaciones = new JTextArea(recomendaciones.toString());
        areaRecomendaciones.setEditable(false);
        areaRecomendaciones.setFont(Constantes.FONT_NORMAL);
        areaRecomendaciones.setBackground(new Color(240, 255, 240));

        JScrollPane scrollPane = new JScrollPane(areaRecomendaciones);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Recomendaciones Estratégicas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarTendencias() {
        String analisisTendencias = """
            === ANÁLISIS DE TENDENCIAS DEL SECTOR AGROPECUARIO ===
            
            TENDENCIAS GLOBALES:
            • Aumento de la demanda por productos orgánicos (+15% anual)
            • Implementación de agricultura de precisión
            • Énfasis en sostenibilidad y reducción de huella de carbono
            • Integración de IoT y sensores en el campo
            
            OPORTUNIDADES EN EL TOLIMA:
            • Café especial con certificaciones internacionales
            • Diversificación hacia frutas exóticas
            • Ganadería sostenible y sistemas silvopastoriles
            • Agroturismo como fuente de ingresos adicional
            
            RIESGOS A CONSIDERAR:
            • Cambio climático y variabilidad en precipitaciones
            • Fluctuaciones en precios internacionales
            • Competencia de productos importados
            • Necesidad de capacitación tecnológica
            
            RECOMENDACIONES DE INVERSIÓN:
            • Sistemas de riego tecnificado
            • Mejoramiento genético
            • Procesamiento y valor agregado
            • Certificaciones de calidad
            """;

        JTextArea areaTendencias = new JTextArea(analisisTendencias);
        areaTendencias.setEditable(false);
        areaTendencias.setFont(Constantes.FONT_NORMAL);
        areaTendencias.setBackground(new Color(240, 248, 255));

        JScrollPane scrollPane = new JScrollPane(areaTendencias);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Análisis de Tendencias", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarAnalisisComparativo() {
        Map<String, Object> stats = controller.calcularEstadisticasGenerales();
        Map<String, Double> rentabilidades = controller.calcularRentabilidadesPorTipo();

        StringBuilder analisis = new StringBuilder();
        analisis.append("=== ANÁLISIS COMPARATIVO DETALLADO ===\n\n");

        // Comparación cuantitativa
        int totalAgricolas = (Integer) stats.get("Total Productos Agrícolas");
        int totalGanaderos = (Integer) stats.get("Total Productos Ganaderos");

        analisis.append("CANTIDAD DE PRODUCTOS:\n");
        analisis.append(String.format("Productos Agrícolas: %d (%.1f%%)\n",
                totalAgricolas, (double) totalAgricolas / (totalAgricolas + totalGanaderos) * 100));
        analisis.append(String.format("Productos Ganaderos: %d (%.1f%%)\n\n",
                totalGanaderos, (double) totalGanaderos / (totalAgricolas + totalGanaderos) * 100));

        // Comparación de rentabilidad
        double rentAgricola = rentabilidades.get("Productos Agrícolas");
        double rentGanadera = rentabilidades.get("Productos Ganaderos");

        analisis.append("RENTABILIDAD PROMEDIO:\n");
        analisis.append(String.format("Sector Agrícola: %s\n", Formateador.formatearMoneda(rentAgricola)));
        analisis.append(String.format("Sector Ganadero: %s\n\n", Formateador.formatearMoneda(rentGanadera)));

        // Análisis de eficiencia
        double hectareas = (Double) stats.get("Hectáreas Cultivadas Total");
        int animales = (Integer) stats.get("Total Animales");

        analisis.append("EFICIENCIA:\n");
        if (totalAgricolas > 0 && hectareas > 0) {
            analisis.append(String.format("Rentabilidad por hectárea: %s\n",
                    Formateador.formatearMoneda(rentAgricola * totalAgricolas / hectareas)));
        }
        if (totalGanaderos > 0 && animales > 0) {
            analisis.append(String.format("Rentabilidad por animal: %s\n\n",
                    Formateador.formatearMoneda(rentGanadera * totalGanaderos / animales)));
        }

        // Recomendación final
        analisis.append("CONCLUSIÓN:\n");
        if (Math.abs(rentAgricola - rentGanadera) / Math.max(rentAgricola, rentGanadera) < 0.1) {
            analisis.append("Ambos sectores muestran rentabilidades equilibradas. Se recomienda mantener la diversificación actual.");
        } else if (rentAgricola > rentGanadera) {
            analisis.append("El sector agrícola muestra mayor rentabilidad. Considere optimizar las operaciones ganaderas.");
        } else {
            analisis.append("El sector ganadero muestra mayor rentabilidad. Evalúe oportunidades de mejora en agricultura.");
        }

        JTextArea areaAnalisis = new JTextArea(analisis.toString());
        areaAnalisis.setEditable(false);
        areaAnalisis.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaAnalisis.setBackground(new Color(255, 248, 240));

        JScrollPane scrollPane = new JScrollPane(areaAnalisis);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Análisis Comparativo", JOptionPane.INFORMATION_MESSAGE);
    }
}