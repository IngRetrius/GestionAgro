package co.unibague.agropecuario.view.fincas;

import co.unibague.agropecuario.controller.FincaController;
import co.unibague.agropecuario.model.entities.Finca;
import co.unibague.agropecuario.utils.Constantes;
import co.unibague.agropecuario.utils.Formateador;
import co.unibague.agropecuario.utils.Validador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para gestionar fincas.
 * Implementa el patrón MVC para la gestión de fincas.
 */
public class VentanaFinca extends JFrame {

    private FincaController controller;

    // Componentes de la interfaz
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtCodigo, txtNombreFinca, txtPropietario, txtVereda;
    private JTextField txtAreaTotal, txtTelefono, txtEmail, txtInfraestructura;
    private JComboBox<String> cmbMunicipio, cmbTipoTerreno;
    private JButton btnCrear, btnActualizar, btnEliminar, btnLimpiar, btnEstadisticas;

    private Finca fincaSeleccionada;

    public VentanaFinca(FincaController controller) {
        this.controller = controller;

        configurarVentana();
        crearComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Gestión de Fincas");
        setSize(1200, 600);
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
        panel.setBorder(BorderFactory.createTitledBorder("Información de la Finca"));
        panel.setBackground(Constantes.COLOR_LIGHT);

        // Crear campos del formulario
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(Constantes.COLOR_LIGHT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Código
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Código:*"), gbc);
        gbc.gridx = 1;
        txtCodigo = new JTextField(15);
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(Color.LIGHT_GRAY);
        panelCampos.add(txtCodigo, gbc);

        // Nombre de la finca
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Nombre Finca:*"), gbc);
        gbc.gridx = 1;
        txtNombreFinca = new JTextField(15);
        panelCampos.add(txtNombreFinca, gbc);

        // Propietario
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Propietario:*"), gbc);
        gbc.gridx = 1;
        txtPropietario = new JTextField(15);
        panelCampos.add(txtPropietario, gbc);

        // Municipio
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Municipio:*"), gbc);
        gbc.gridx = 1;
        cmbMunicipio = new JComboBox<>(Constantes.MUNICIPIOS_TOLIMA);
        panelCampos.add(cmbMunicipio, gbc);

        // Vereda
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Vereda:"), gbc);
        gbc.gridx = 1;
        txtVereda = new JTextField(15);
        panelCampos.add(txtVereda, gbc);

        // Área total
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Área Total (ha):*"), gbc);
        gbc.gridx = 1;
        txtAreaTotal = new JTextField(15);
        panelCampos.add(txtAreaTotal, gbc);

        // Tipo de terreno
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Tipo de Terreno:"), gbc);
        gbc.gridx = 1;
        cmbTipoTerreno = new JComboBox<>(Constantes.TIPOS_TERRENO);
        panelCampos.add(cmbTipoTerreno, gbc);

        // Infraestructura
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Infraestructura:"), gbc);
        gbc.gridx = 1;
        txtInfraestructura = new JTextField(15);
        panelCampos.add(txtInfraestructura, gbc);

        // Teléfono
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        panelCampos.add(txtTelefono, gbc);

        // Email
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelCampos.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(15);
        panelCampos.add(txtEmail, gbc);

        panel.add(panelCampos);

        // Panel de información adicional
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información Calculada"));
        panelInfo.setBackground(Constantes.COLOR_LIGHT);

        JLabel lblInfo = new JLabel("<html>Área cultivable: <b>--</b><br>Código ubicación: <b>--</b><br>Tipo: <b>--</b></html>");
        lblInfo.setFont(Constantes.FONT_SMALL);
        panelInfo.add(lblInfo, BorderLayout.CENTER);

        panel.add(Box.createVerticalStrut(10));
        panel.add(panelInfo);

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
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Fincas"));

        // Crear modelo de tabla
        String[] columnas = {"Código", "Nombre", "Propietario", "Municipio", "Área (ha)", "Tipo"};
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
                seleccionarFinca();
            }
        });

        // Configurar tabla
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(Constantes.COLOR_PRIMARY);
        tabla.getTableHeader().setForeground(Color.WHITE);
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
        btnCrear.setForeground(Color.WHITE);
        btnCrear.addActionListener(e -> crearFinca());

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(Constantes.COLOR_INFO);
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.addActionListener(e -> actualizarFinca());
        btnActualizar.setEnabled(false);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(Constantes.COLOR_DANGER);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarFinca());
        btnEliminar.setEnabled(false);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(Constantes.COLOR_WARNING);
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        btnEstadisticas = new JButton("Estadísticas");
        btnEstadisticas.setBackground(Constantes.COLOR_SECONDARY);
        btnEstadisticas.setForeground(Color.BLACK);
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());

        panel.add(btnCrear);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        panel.add(btnEstadisticas);

        return panel;
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Finca> fincas = controller.listar();

        for (Finca finca : fincas) {
            Object[] fila = {
                    finca.getCodigo(),
                    finca.getNombreFinca(),
                    finca.getPropietario(),
                    finca.getMunicipio(),
                    Formateador.formatearDecimal(finca.getAreaTotal()),
                    finca.getTipoTerreno()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void seleccionarFinca() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String codigo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            fincaSeleccionada = controller.buscar(codigo);

            if (fincaSeleccionada != null) {
                cargarFincaEnFormulario(fincaSeleccionada);
                btnActualizar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnCrear.setEnabled(false);
            }
        }
    }

    private void cargarFincaEnFormulario(Finca finca) {
        txtCodigo.setText(finca.getCodigo());
        txtNombreFinca.setText(finca.getNombreFinca());
        txtPropietario.setText(finca.getPropietario());
        cmbMunicipio.setSelectedItem(finca.getMunicipio());
        txtVereda.setText(finca.getVereda() != null ? finca.getVereda() : "");
        txtAreaTotal.setText(String.valueOf(finca.getAreaTotal()));
        cmbTipoTerreno.setSelectedItem(finca.getTipoTerreno());
        txtInfraestructura.setText(finca.getInfraestructura() != null ? finca.getInfraestructura() : "");
        txtTelefono.setText(finca.getTelefono() != null ? finca.getTelefono() : "");
        txtEmail.setText(finca.getEmail() != null ? finca.getEmail() : "");
    }

    private void crearFinca() {
        if (validarFormulario()) {
            Finca finca = crearFincaDesdeFormulario();
            finca.setCodigo(controller.generarProximoCodigo());

            if (controller.crear(finca)) {
                JOptionPane.showMessageDialog(this,
                        "Finca creada exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al crear la finca. Verifique que el código no exista.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarFinca() {
        if (fincaSeleccionada != null && validarFormulario()) {
            Finca fincaActualizada = crearFincaDesdeFormulario();

            if (controller.actualizar(fincaSeleccionada.getCodigo(), fincaActualizada)) {
                JOptionPane.showMessageDialog(this,
                        "Finca actualizada exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al actualizar la finca",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarFinca() {
        if (fincaSeleccionada != null) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar la finca '" + fincaSeleccionada.getNombreFinca() + "'?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (opcion == JOptionPane.YES_OPTION) {
                if (controller.eliminar(fincaSeleccionada.getCodigo())) {
                    JOptionPane.showMessageDialog(this,
                            "Finca eliminada exitosamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error al eliminar la finca",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void mostrarEstadisticas() {
        String estadisticas = controller.obtenerEstadisticasPorMunicipio();
        double areaTotal = controller.calcularAreaTotal();
        double areaCultivable = controller.calcularAreaCultivableTotal();
        int totalFincas = controller.obtenerTotal();

        StringBuilder mensaje = new StringBuilder();
        mensaje.append("=== ESTADÍSTICAS DE FINCAS ===\n\n");
        mensaje.append("Total de fincas registradas: ").append(totalFincas).append("\n");
        mensaje.append("Área total: ").append(Formateador.formatearHectareas(areaTotal)).append("\n");
        mensaje.append("Área cultivable: ").append(Formateador.formatearHectareas(areaCultivable)).append("\n\n");
        mensaje.append("DISTRIBUCIÓN POR MUNICIPIO:\n");
        mensaje.append(estadisticas);

        JTextArea textArea = new JTextArea(mensaje.toString());
        textArea.setEditable(false);
        textArea.setFont(Constantes.FONT_NORMAL);
        textArea.setBackground(Constantes.COLOR_LIGHT);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Estadísticas de Fincas", JOptionPane.INFORMATION_MESSAGE);
    }

    private Finca crearFincaDesdeFormulario() {
        Finca finca = new Finca();

        finca.setNombreFinca(txtNombreFinca.getText().trim());
        finca.setPropietario(txtPropietario.getText().trim());
        finca.setMunicipio((String) cmbMunicipio.getSelectedItem());
        finca.setVereda(txtVereda.getText().trim());
        finca.setAreaTotal(Double.parseDouble(txtAreaTotal.getText()));
        finca.setTipoTerreno((String) cmbTipoTerreno.getSelectedItem());
        finca.setInfraestructura(txtInfraestructura.getText().trim());
        finca.setTelefono(txtTelefono.getText().trim());
        finca.setEmail(txtEmail.getText().trim());

        return finca;
    }

    private boolean validarFormulario() {
        StringBuilder errores = new StringBuilder();

        if (!Validador.validarCadenaNoVacia(txtNombreFinca.getText())) {
            errores.append("- El nombre de la finca es obligatorio\n");
        }

        if (!Validador.validarCadenaNoVacia(txtPropietario.getText())) {
            errores.append("- El propietario es obligatorio\n");
        }

        try {
            double area = Double.parseDouble(txtAreaTotal.getText());
            if (!Validador.validarRangoHectareas(area)) {
                errores.append("- El área debe estar entre 0.1 y 10,000 hectáreas\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- El área total debe ser un número válido\n");
        }

        // Validar teléfono si se proporciona
        if (!txtTelefono.getText().trim().isEmpty() &&
                !Validador.validarTelefono(txtTelefono.getText().trim())) {
            errores.append("- El formato del teléfono debe ser 123-456-7890 o 1234567890\n");
        }

        // Validar email si se proporciona
        if (!txtEmail.getText().trim().isEmpty() &&
                !Validador.validarEmail(txtEmail.getText().trim())) {
            errores.append("- El formato del email no es válido\n");
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
        txtCodigo.setText("");
        txtNombreFinca.setText("");
        txtPropietario.setText("");
        cmbMunicipio.setSelectedIndex(0);
        txtVereda.setText("");
        txtAreaTotal.setText("");
        cmbTipoTerreno.setSelectedIndex(0);
        txtInfraestructura.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");

        fincaSeleccionada = null;
        tabla.clearSelection();

        btnCrear.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
}