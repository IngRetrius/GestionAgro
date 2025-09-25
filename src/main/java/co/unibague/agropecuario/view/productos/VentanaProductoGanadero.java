package co.unibague.agropecuario.view.productos;

import co.unibague.agropecuario.controller.ProductoGanaderoController;
import co.unibague.agropecuario.model.entities.ProductoGanadero;
import co.unibague.agropecuario.patterns.observer.Observer;
import co.unibague.agropecuario.utils.Constantes;
import co.unibague.agropecuario.utils.Formateador;
import co.unibague.agropecuario.utils.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Ventana para gestionar productos ganaderos.
 * Implementa el patrón Observer para actualización automática de la tabla.
 */
public class VentanaProductoGanadero extends JFrame implements Observer {

    private ProductoGanaderoController controller;

    // Componentes de la interfaz
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtNombre, txtCosto, txtCantidad, txtPrecio;
    private JTextField txtNumeroAnimales, txtPesoPromedio, txtProduccionDiaria;
    private JComboBox<String> cmbTipoGanado, cmbTipoAlimentacion;
    private JSpinner spnFecha;
    private JButton btnCrear, btnActualizar, btnEliminar, btnLimpiar, btnCalcular, btnAplicar;
    private JTextArea txtEstadoAplicacion;

    private ProductoGanadero productoSeleccionado;

    public VentanaProductoGanadero(ProductoGanaderoController controller) {
        this.controller = controller;

        // Registrarse como observador
        controller.addObserver(this);

        configurarVentana();
        crearComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Productos Ganaderos");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Panel principal dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(450);

        // Panel izquierdo - Formulario
        JPanel panelFormulario = crearPanelFormulario();
        splitPane.setLeftComponent(panelFormulario);

        // Panel derecho - Tabla
        JPanel panelTabla = crearPanelTabla();
        splitPane.setRightComponent(panelTabla);

        add(splitPane, BorderLayout.CENTER);

        // Panel inferior - Botones de acción
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Información del Producto Ganadero"));
        panel.setBackground(Constantes.COLOR_LIGHT);

        // Crear campos del formulario
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(Constantes.COLOR_LIGHT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // ID
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("ID:*"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panelCampos.add(txtId, gbc);

        // Nombre
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Nombre:*"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        panelCampos.add(txtNombre, gbc);

        // Tipo de ganado
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Tipo de Ganado:*"), gbc);
        gbc.gridx = 1;
        cmbTipoGanado = new JComboBox<>(Constantes.TIPOS_GANADO);
        panelCampos.add(cmbTipoGanado, gbc);

        // Fecha de producción
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Fecha Producción:*"), gbc);
        gbc.gridx = 1;
        spnFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy");
        spnFecha.setEditor(dateEditor);
        panelCampos.add(spnFecha, gbc);

        // Número de animales
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Número de Animales:*"), gbc);
        gbc.gridx = 1;
        txtNumeroAnimales = new JTextField(15);
        panelCampos.add(txtNumeroAnimales, gbc);

        // Peso promedio
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Peso Promedio (kg):*"), gbc);
        gbc.gridx = 1;
        txtPesoPromedio = new JTextField(15);
        panelCampos.add(txtPesoPromedio, gbc);

        // Producción diaria
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Producción Diaria:*"), gbc);
        gbc.gridx = 1;
        txtProduccionDiaria = new JTextField(15);
        panelCampos.add(txtProduccionDiaria, gbc);

        // Tipo de alimentación
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Tipo Alimentación:"), gbc);
        gbc.gridx = 1;
        cmbTipoAlimentacion = new JComboBox<>(Constantes.TIPOS_ALIMENTACION);
        panelCampos.add(cmbTipoAlimentacion, gbc);

        // Costo de producción
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Costo Producción:*"), gbc);
        gbc.gridx = 1;
        txtCosto = new JTextField(15);
        panelCampos.add(txtCosto, gbc);

        // Cantidad producida
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Cantidad Producida:*"), gbc);
        gbc.gridx = 1;
        txtCantidad = new JTextField(15);
        panelCampos.add(txtCantidad, gbc);

        // Precio de venta
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Precio Venta:*"), gbc);
        gbc.gridx = 1;
        txtPrecio = new JTextField(15);
        panelCampos.add(txtPrecio, gbc);

        panel.add(panelCampos);

        // Panel de estado de aplicaciones
        JPanel panelAplicaciones = new JPanel(new BorderLayout());
        panelAplicaciones.setBorder(BorderFactory.createTitledBorder("Estado de Aplicaciones (IAplicable)"));
        panelAplicaciones.setBackground(Constantes.COLOR_LIGHT);

        txtEstadoAplicacion = new JTextArea(4, 20);
        txtEstadoAplicacion.setEditable(false);
        txtEstadoAplicacion.setBackground(Color.white);
        txtEstadoAplicacion.setBorder(BorderFactory.createLoweredBevelBorder());

        JScrollPane scrollEstado = new JScrollPane(txtEstadoAplicacion);
        panelAplicaciones.add(scrollEstado, BorderLayout.CENTER);

        panel.add(Box.createVerticalStrut(10));
        panel.add(panelAplicaciones);

        // Nota sobre campos obligatorios
        JLabel lblNota = new JLabel("* Campos obligatorios");
        lblNota.setFont(Constantes.FONT_SMALL);
        lblNota.setForeground(Constantes.COLOR_DANGER);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblNota);

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Productos Ganaderos"));

        // Crear modelo de tabla
        String[] columnas = {"ID", "Nombre", "Tipo", "Animales", "Peso Total", "Rentabilidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarProducto();
            }
        });

        // Configurar tabla
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(Constantes.COLOR_PRIMARY);
        tabla.getTableHeader().setForeground(Color.BLACK);
        tabla.getTableHeader().setFont(Constantes.FONT_SUBTITLE);

        JScrollPane scrollPane = new JScrollPane(tabla);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Constantes.COLOR_LIGHT);

        btnCrear = new JButton("Crear");
        btnCrear.setBackground(Constantes.COLOR_SUCCESS);
        btnCrear.setForeground(Color.BLACK);
        btnCrear.addActionListener(e -> crearProducto());

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(Constantes.COLOR_INFO);
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnActualizar.setEnabled(false);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(Constantes.COLOR_DANGER);
        btnEliminar.setForeground(Color.BLACK   );
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnEliminar.setEnabled(false);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(Constantes.COLOR_WARNING);
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        btnCalcular = new JButton("Calcular Rentabilidad");
        btnCalcular.setBackground(Constantes.COLOR_SECONDARY);
        btnCalcular.setForeground(Color.BLACK);
        btnCalcular.addActionListener(e -> calcularRentabilidad());
        btnCalcular.setEnabled(false);

        btnAplicar = new JButton("Aplicar Tratamiento");
        btnAplicar.setBackground(new Color(138, 43, 226)); // Purple
        btnAplicar.setForeground(Color.BLACK);
        btnAplicar.addActionListener(e -> aplicarTratamiento());
        btnAplicar.setEnabled(false);

        panel.add(btnCrear);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        panel.add(btnCalcular);
        panel.add(btnAplicar);

        return panel;
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<ProductoGanadero> productos = controller.listar();

        for (ProductoGanadero producto : productos) {
            Object[] fila = {
                    producto.getId(),
                    producto.getNombre(),
                    producto.getTipoGanado(),
                    String.valueOf(producto.getNumeroAnimales()),
                    Formateador.formatearPeso(producto.calcularPesoTotal()),
                    Formateador.formatearMoneda(producto.calcularRentabilidad())
            };
            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarProducto() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String id = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            productoSeleccionado = controller.buscar(id);

            if (productoSeleccionado != null) {
                cargarProductoEnFormulario(productoSeleccionado);
                actualizarEstadoAplicacion();
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnCalcular.setEnabled(true);
                btnAplicar.setEnabled(true);
                btnCrear.setEnabled(false);
            }
        }
    }

    private void cargarProductoEnFormulario(ProductoGanadero producto) {
        txtId.setText(producto.getId());
        txtNombre.setText(producto.getNombre());
        cmbTipoGanado.setSelectedItem(producto.getTipoGanado());
        spnFecha.setValue(java.sql.Date.valueOf(producto.getFechaProduccion()));
        txtNumeroAnimales.setText(String.valueOf(producto.getNumeroAnimales()));
        txtPesoPromedio.setText(String.valueOf(producto.getPesoPromedio()));
        txtProduccionDiaria.setText(String.valueOf(producto.getProduccionDiaria()));
        cmbTipoAlimentacion.setSelectedItem(producto.getTipoAlimentacion());
        txtCosto.setText(String.valueOf(producto.getCostoProduccion()));
        txtCantidad.setText(String.valueOf(producto.getCantidadProducida()));
        txtPrecio.setText(String.valueOf(producto.getPrecioVenta()));
    }

    private void actualizarEstadoAplicacion() {
        if (productoSeleccionado != null) {
            txtEstadoAplicacion.setText(productoSeleccionado.obtenerEstadoAplicacion());
        } else {
            txtEstadoAplicacion.setText("Seleccione un producto para ver el estado de aplicaciones");
        }
    }

    private void crearProducto() {
        if (validarFormulario()) {
            ProductoGanadero producto = crearProductoDesdeFormulario();
            producto.setId(controller.generarProximoId());

            if (controller.crear(producto)) {
                JOptionPane.showMessageDialog(this,
                        "Producto ganadero creado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al crear el producto ganadero",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarProducto() {
        if (productoSeleccionado != null && validarFormulario()) {
            ProductoGanadero productoActualizado = crearProductoDesdeFormulario();

            if (controller.actualizar(productoSeleccionado.getId(), productoActualizado)) {
                JOptionPane.showMessageDialog(this,
                        "Producto ganadero actualizado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al actualizar el producto ganadero",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarProducto() {
        if (productoSeleccionado != null) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar el producto '" + productoSeleccionado.getNombre() + "'?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (opcion == JOptionPane.YES_OPTION) {
                if (controller.eliminar(productoSeleccionado.getId())) {
                    JOptionPane.showMessageDialog(this,
                            "Producto ganadero eliminado exitosamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar el producto ganadero",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void calcularRentabilidad() {
        if (productoSeleccionado != null) {
            double rentabilidad = productoSeleccionado.calcularRentabilidad();
            double produccionMensual = productoSeleccionado.calcularProduccionMensual();

            String mensaje = String.format("Rentabilidad de '%s':\n\n" +
                            "Rentabilidad: %s por animal/mes\n" +
                            "Margen de ganancia: %s\n" +
                            "Producción mensual total: %s\n" +
                            "Peso total del ganado: %s\n" +
                            "Ingreso total: %s",
                    productoSeleccionado.getNombre(),
                    Formateador.formatearMoneda(rentabilidad),
                    Formateador.formatearPorcentaje(productoSeleccionado.calcularMargenGanancia()),
                    Formateador.formatearDecimal(produccionMensual),
                    Formateador.formatearPeso(productoSeleccionado.calcularPesoTotal()),
                    Formateador.formatearMoneda(productoSeleccionado.calcularIngresoTotal()));

            JOptionPane.showMessageDialog(this, mensaje, "Cálculo de Rentabilidad", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void aplicarTratamiento() {
        if (productoSeleccionado != null) {
            if (productoSeleccionado.puedeRecibirAplicacion()) {
                int opcion = JOptionPane.showConfirmDialog(this,
                        "¿Desea aplicar tratamiento a '" + productoSeleccionado.getNombre() + "'?\n" +
                                "Tipo de ganado: " + productoSeleccionado.getTipoGanado(),
                        "Confirmar Aplicación de Tratamiento",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (opcion == JOptionPane.YES_OPTION) {
                    if (controller.aplicarTratamiento(productoSeleccionado.getId())) {
                        JOptionPane.showMessageDialog(this,
                                "Tratamiento aplicado exitosamente",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);

                        // Recargar el producto seleccionado y actualizar estado
                        productoSeleccionado = controller.buscar(productoSeleccionado.getId());
                        actualizarEstadoAplicacion();
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Error al aplicar tratamiento",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Este producto no puede recibir aplicaciones en este momento",
                        "Aplicación no disponible", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private ProductoGanadero crearProductoDesdeFormulario() {
        ProductoGanadero producto = new ProductoGanadero();

        producto.setNombre(txtNombre.getText().trim());
        producto.setTipoGanado((String) cmbTipoGanado.getSelectedItem());

        java.util.Date fechaUtil = (java.util.Date) spnFecha.getValue();
        producto.setFechaProduccion(LocalDate.ofEpochDay(fechaUtil.getTime() / (24 * 60 * 60 * 1000)));

        producto.setNumeroAnimales(Integer.parseInt(txtNumeroAnimales.getText()));
        producto.setPesoPromedio(Double.parseDouble(txtPesoPromedio.getText()));
        producto.setProduccionDiaria(Double.parseDouble(txtProduccionDiaria.getText()));
        producto.setTipoAlimentacion((String) cmbTipoAlimentacion.getSelectedItem());
        producto.setCostoProduccion(Double.parseDouble(txtCosto.getText()));
        producto.setCantidadProducida(Double.parseDouble(txtCantidad.getText()));
        producto.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));

        return producto;
    }

    private boolean validarFormulario() {
        StringBuilder errores = new StringBuilder();

        if (!Validador.validarCadenaNoVacia(txtNombre.getText())) {
            errores.append("- El nombre es obligatorio\n");
        }

        try {
            int numeroAnimales = Integer.parseInt(txtNumeroAnimales.getText());
            if (!Validador.validarNumeroAnimales(numeroAnimales)) {
                errores.append("- El número de animales debe estar entre 1 y 50,000\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- El número de animales debe ser un número entero válido\n");
        }

        try {
            double peso = Double.parseDouble(txtPesoPromedio.getText());
            if (!Validador.validarNumeroPositivo(peso)) {
                errores.append("- El peso promedio debe ser positivo\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- El peso promedio debe ser un número válido\n");
        }

        try {
            double produccion = Double.parseDouble(txtProduccionDiaria.getText());
            if (!Validador.validarNumeroNoNegativo(produccion)) {
                errores.append("- La producción diaria no puede ser negativa\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- La producción diaria debe ser un número válido\n");
        }

        try {
            double costo = Double.parseDouble(txtCosto.getText());
            if (!Validador.validarNumeroPositivo(costo)) {
                errores.append("- El costo de producción debe ser positivo\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- El costo de producción debe ser un número válido\n");
        }

        try {
            double cantidad = Double.parseDouble(txtCantidad.getText());
            if (!Validador.validarNumeroPositivo(cantidad)) {
                errores.append("- La cantidad producida debe ser positiva\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- La cantidad producida debe ser un número válido\n");
        }

        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            if (!Validador.validarPrecioRazonable(precio)) {
                errores.append("- El precio debe estar entre $100 y $1,000,000\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- El precio de venta debe ser un número válido\n");
        }

        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Errores de validación:\n\n" + errores.toString(),
                    "Errores de Validación",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        cmbTipoGanado.setSelectedIndex(0);
        spnFecha.setValue(new java.util.Date());
        txtNumeroAnimales.setText("");
        txtPesoPromedio.setText("");
        txtProduccionDiaria.setText("");
        cmbTipoAlimentacion.setSelectedIndex(0);
        txtCosto.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        txtEstadoAplicacion.setText("");

        productoSeleccionado = null;
        tabla.clearSelection();

        btnCrear.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCalcular.setEnabled(false);
        btnAplicar.setEnabled(false);
    }

    // Implementación del patrón Observer
    @Override
    public void update() {
        SwingUtilities.invokeLater(this::cargarDatos);
    }

    @Override
    public void update(Object data) {
        SwingUtilities.invokeLater(this::cargarDatos);
    }

    @Override
    public void dispose() {
        // Desregistrarse como observador al cerrar la ventana
        controller.removeObserver(this);
        super.dispose();
    }
}