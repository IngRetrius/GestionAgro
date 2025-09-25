package co.unibague.agropecuario.view.productos;

import co.unibague.agropecuario.controller.FincaController;
import co.unibague.agropecuario.controller.ProductoAgricolaController;
import co.unibague.agropecuario.model.entities.Finca;
import co.unibague.agropecuario.model.entities.ProductoAgricola;
import co.unibague.agropecuario.patterns.observer.Observer;
import co.unibague.agropecuario.utils.Constantes;
import co.unibague.agropecuario.utils.Formateador;
import co.unibague.agropecuario.utils.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

/**
 * Ventana para gestionar productos agrícolas.
 * Implementa el patrón Observer para actualización automática de la tabla.
 */
public class VentanaProductoAgricola extends JFrame implements Observer {

    private ProductoAgricolaController controller;
    private FincaController fincaController;

    // Componentes de la interfaz
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtId, txtNombre, txtCosto, txtCantidad, txtPrecio;
    private JTextField txtHectareas, txtRendimiento;
    private JComboBox<String> cmbTipoCultivo, cmbTipoSuelo, cmbTemporada, cmbFinca;
    private JSpinner spnFecha;
    private JButton btnCrear, btnActualizar, btnEliminar, btnLimpiar, btnCalcular;

    private ProductoAgricola productoSeleccionado;

    public VentanaProductoAgricola(ProductoAgricolaController controller, FincaController fincaController) {
        this.controller = controller;
        this.fincaController = fincaController;

        // Registrarse como observador
        controller.addObserver(this);

        configurarVentana();
        crearComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Productos Agrícolas");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Panel principal dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);

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
        panel.setBorder(BorderFactory.createTitledBorder("Información del Producto Agrícola"));
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

        // Tipo de cultivo
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Tipo de Cultivo:*"), gbc);
        gbc.gridx = 1;
        cmbTipoCultivo = new JComboBox<>(Constantes.TIPOS_CULTIVO);
        panelCampos.add(cmbTipoCultivo, gbc);

        // Fecha de producción
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Fecha Producción:*"), gbc);
        gbc.gridx = 1;
        spnFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy");
        spnFecha.setEditor(dateEditor);
        panelCampos.add(spnFecha, gbc);

        // Hectáreas cultivadas
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Hectáreas:*"), gbc);
        gbc.gridx = 1;
        txtHectareas = new JTextField(15);
        panelCampos.add(txtHectareas, gbc);

        // Rendimiento por hectárea
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Rendimiento (ton/ha):*"), gbc);
        gbc.gridx = 1;
        txtRendimiento = new JTextField(15);
        panelCampos.add(txtRendimiento, gbc);

        // Tipo de suelo
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Tipo de Suelo:"), gbc);
        gbc.gridx = 1;
        cmbTipoSuelo = new JComboBox<>(Constantes.TIPOS_SUELO);
        panelCampos.add(cmbTipoSuelo, gbc);

        // Temporada
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Temporada:"), gbc);
        gbc.gridx = 1;
        cmbTemporada = new JComboBox<>(Constantes.TEMPORADAS);
        panelCampos.add(cmbTemporada, gbc);

        // Finca asociada
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Finca:"), gbc);
        gbc.gridx = 1;
        cmbFinca = new JComboBox<>();
        cargarFincas();
        panelCampos.add(cmbFinca, gbc);

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
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Productos Agrícolas"));

        // Crear modelo de tabla
        String[] columnas = {"ID", "Nombre", "Tipo", "Hectáreas", "Rendimiento", "Rentabilidad"};
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
        btnEliminar.setForeground(Color.BLACK);
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

        panel.add(btnCrear);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        panel.add(btnCalcular);

        return panel;
    }

    private void cargarFincas() {
        cmbFinca.removeAllItems();
        cmbFinca.addItem("-- Seleccionar Finca --");

        List<Finca> fincas = fincaController.listar();
        for (Finca finca : fincas) {
            cmbFinca.addItem(finca.getCodigo() + " - " + finca.getNombreFinca());
        }
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<ProductoAgricola> productos = controller.listar();

        for (ProductoAgricola producto : productos) {
            Object[] fila = {
                    producto.getId(),
                    producto.getNombre(),
                    producto.getTipoCultivo(),
                    Formateador.formatearHectareas(producto.getHectareasCultivadas()),
                    Formateador.formatearDecimal(producto.getRendimientoPorHa()) + " ton/ha",
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
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnCalcular.setEnabled(true);
                btnCrear.setEnabled(false);
            }
        }
    }

    private void cargarProductoEnFormulario(ProductoAgricola producto) {
        txtId.setText(producto.getId());
        txtNombre.setText(producto.getNombre());
        cmbTipoCultivo.setSelectedItem(producto.getTipoCultivo());
        spnFecha.setValue(java.sql.Date.valueOf(producto.getFechaProduccion()));
        txtHectareas.setText(String.valueOf(producto.getHectareasCultivadas()));
        txtRendimiento.setText(String.valueOf(producto.getRendimientoPorHa()));
        cmbTipoSuelo.setSelectedItem(producto.getTipoSuelo());
        cmbTemporada.setSelectedItem(producto.getTemporada());
        txtCosto.setText(String.valueOf(producto.getCostoProduccion()));
        txtCantidad.setText(String.valueOf(producto.getCantidadProducida()));
        txtPrecio.setText(String.valueOf(producto.getPrecioVenta()));

        // Seleccionar finca si está asociada
        if (producto.getCodigoFinca() != null && !producto.getCodigoFinca().isEmpty()) {
            for (int i = 0; i < cmbFinca.getItemCount(); i++) {
                String item = cmbFinca.getItemAt(i);
                if (item.startsWith(producto.getCodigoFinca())) {
                    cmbFinca.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void crearProducto() {
        if (validarFormulario()) {
            ProductoAgricola producto = crearProductoDesdeFormulario();
            producto.setId(controller.generarProximoId());

            if (controller.crear(producto)) {
                JOptionPane.showMessageDialog(this,
                        "Producto agrícola creado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al crear el producto agrícola",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarProducto() {
        if (productoSeleccionado != null && validarFormulario()) {
            ProductoAgricola productoActualizado = crearProductoDesdeFormulario();

            if (controller.actualizar(productoSeleccionado.getId(), productoActualizado)) {
                JOptionPane.showMessageDialog(this,
                        "Producto agrícola actualizado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al actualizar el producto agrícola",
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
                            "Producto agrícola eliminado exitosamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar el producto agrícola",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void calcularRentabilidad() {
        if (productoSeleccionado != null) {
            double rentabilidad = productoSeleccionado.calcularRentabilidad();
            String mensaje = String.format("Rentabilidad de '%s':\n\n" +
                            "Rentabilidad: %s por hectárea\n" +
                            "Margen de ganancia: %s\n" +
                            "Ingreso total: %s",
                    productoSeleccionado.getNombre(),
                    Formateador.formatearMoneda(rentabilidad),
                    Formateador.formatearPorcentaje(productoSeleccionado.calcularMargenGanancia()),
                    Formateador.formatearMoneda(productoSeleccionado.calcularIngresoTotal()));

            JOptionPane.showMessageDialog(this, mensaje, "Cálculo de Rentabilidad", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private ProductoAgricola crearProductoDesdeFormulario() {
        ProductoAgricola producto = new ProductoAgricola();

        producto.setNombre(txtNombre.getText().trim());
        producto.setTipoCultivo((String) cmbTipoCultivo.getSelectedItem());

        java.util.Date fechaUtil = (java.util.Date) spnFecha.getValue();
        producto.setFechaProduccion(LocalDate.ofEpochDay(fechaUtil.getTime() / (24 * 60 * 60 * 1000)));

        producto.setHectareasCultivadas(Double.parseDouble(txtHectareas.getText()));
        producto.setRendimientoPorHa(Double.parseDouble(txtRendimiento.getText()));
        producto.setTipoSuelo((String) cmbTipoSuelo.getSelectedItem());
        producto.setTemporada((String) cmbTemporada.getSelectedItem());
        producto.setCostoProduccion(Double.parseDouble(txtCosto.getText()));
        producto.setCantidadProducida(Double.parseDouble(txtCantidad.getText()));
        producto.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));

        // Asociar finca si está seleccionada
        if (cmbFinca.getSelectedIndex() > 0) {
            String fincaSeleccionada = (String) cmbFinca.getSelectedItem();
            String codigoFinca = fincaSeleccionada.split(" - ")[0];
            producto.setCodigoFinca(codigoFinca);
        }

        return producto;
    }

    private boolean validarFormulario() {
        StringBuilder errores = new StringBuilder();

        if (!Validador.validarCadenaNoVacia(txtNombre.getText())) {
            errores.append("- El nombre es obligatorio\n");
        }

        try {
            double hectareas = Double.parseDouble(txtHectareas.getText());
            if (!Validador.validarRangoHectareas(hectareas)) {
                errores.append("- Las hectáreas deben estar entre 0.1 y 10,000\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- Las hectáreas deben ser un número válido\n");
        }

        try {
            double rendimiento = Double.parseDouble(txtRendimiento.getText());
            if (!Validador.validarNumeroPositivo(rendimiento)) {
                errores.append("- El rendimiento debe ser positivo\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- El rendimiento debe ser un número válido\n");
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
        cmbTipoCultivo.setSelectedIndex(0);
        spnFecha.setValue(new java.util.Date());
        txtHectareas.setText("");
        txtRendimiento.setText("");
        cmbTipoSuelo.setSelectedIndex(0);
        cmbTemporada.setSelectedIndex(0);
        cmbFinca.setSelectedIndex(0);
        txtCosto.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");

        productoSeleccionado = null;
        tabla.clearSelection();

        btnCrear.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCalcular.setEnabled(false);
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