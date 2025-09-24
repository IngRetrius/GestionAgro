package co.unibague.agropecuario.view.dialogs;

import co.unibague.agropecuario.model.singleton.CooperativaInfo;
import co.unibague.agropecuario.utils.Constantes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diálogo que muestra información sobre la aplicación.
 * Incluye información del equipo de desarrollo y versión.
 */
public class AcercaDeDialog extends JDialog {

    private JButton btnCerrar;
    private JButton btnInfo;

    public AcercaDeDialog(Frame parent) {
        super(parent, "Acerca de - " + Constantes.TITULO_APLICACION, true);

        configurarDialog();
        crearComponentes();
    }

    private void configurarDialog() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void crearComponentes() {
        // Panel superior con logo/imagen
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central con información
        JPanel panelCentral = crearPanelCentral();
        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelInferior = crearPanelInferior();
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Constantes.COLOR_PRIMARY);
        panel.setPreferredSize(new Dimension(0, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título principal
        JLabel lblTitulo = new JLabel(Constantes.TITULO_APLICACION, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);

        // Versión
        JLabel lblVersion = new JLabel("Versión " + Constantes.VERSION, SwingConstants.CENTER);
        lblVersion.setFont(Constantes.FONT_NORMAL);
        lblVersion.setForeground(Constantes.COLOR_SECONDARY);

        panel.add(lblTitulo, BorderLayout.CENTER);
        panel.add(lblVersion, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Información del sistema
        JLabel lblDescripcion = new JLabel("<html><div style='text-align: center;'>" +
                "<h3>Sistema de Gestión Agropecuaria</h3>" +
                "<p>Aplicación para el manejo integral de productos agrícolas, ganaderos y fincas<br>" +
                "del departamento del Tolima, Colombia.</p>" +
                "</div></html>");
        lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescripcion.setFont(Constantes.FONT_NORMAL);

        // Información técnica
        JPanel panelTecnico = new JPanel(new GridBagLayout());
        panelTecnico.setBackground(Color.WHITE);
        panelTecnico.setBorder(BorderFactory.createTitledBorder("Información Técnica"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Detalles técnicos
        String[][] infoTecnica = {
                {"Lenguaje:", "Java " + System.getProperty("java.version")},
                {"Framework GUI:", "Swing"},
                {"Arquitectura:", "MVC (Modelo-Vista-Controlador)"},
                {"Patrones:", "Singleton, Observer, SOLID"},
                {"Base de Datos:", "Memoria (Fase 1)"},
                {"SO:", System.getProperty("os.name")}
        };

        for (int i = 0; i < infoTecnica.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            JLabel lblLabel = new JLabel(infoTecnica[i][0]);
            lblLabel.setFont(new Font("Arial", Font.BOLD, 12));
            panelTecnico.add(lblLabel, gbc);

            gbc.gridx = 1;
            JLabel lblValue = new JLabel(infoTecnica[i][1]);
            lblValue.setFont(Constantes.FONT_NORMAL);
            panelTecnico.add(lblValue, gbc);
        }

        // Información del equipo
        JPanel panelEquipo = new JPanel();
        panelEquipo.setLayout(new BoxLayout(panelEquipo, BoxLayout.Y_AXIS));
        panelEquipo.setBackground(Color.WHITE);
        panelEquipo.setBorder(BorderFactory.createTitledBorder("Equipo de Desarrollo"));

        JLabel lblEquipo = new JLabel("<html><div style='text-align: center;'>" +
                "<b>" + Constantes.DESARROLLADORES + "</b><br><br>" +
                "Universidad de Ibagué<br>" +
                "Facultad de Ingeniería<br>" +
                "Programa de Ingeniería de Sistemas<br>" +
                "Curso: Desarrollo de Aplicaciones Empresariales<br>" +
                "Año: 2025" +
                "</div></html>");
        lblEquipo.setHorizontalAlignment(SwingConstants.CENTER);
        lblEquipo.setFont(Constantes.FONT_NORMAL);
        panelEquipo.add(lblEquipo);

        // Copyright
        JLabel lblCopyright = new JLabel("<html><div style='text-align: center;'>" +
                "<small><i>© 2025 Universidad de Ibagué. Todos los derechos reservados.<br>" +
                "Este software fue desarrollado con fines educativos.</i></small>" +
                "</div></html>");
        lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
        lblCopyright.setFont(Constantes.FONT_SMALL);
        lblCopyright.setForeground(Color.GRAY);

        // Agregar componentes al panel central
        panel.add(lblDescripcion);
        panel.add(Box.createVerticalStrut(15));
        panel.add(panelTecnico);
        panel.add(Box.createVerticalStrut(10));
        panel.add(panelEquipo);
        panel.add(Box.createVerticalStrut(15));
        panel.add(lblCopyright);

        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Constantes.COLOR_LIGHT);

        btnInfo = new JButton("Info de la Cooperativa");
        btnInfo.setBackground(Constantes.COLOR_INFO);
        btnInfo.setForeground(Color.WHITE);
        btnInfo.setFont(Constantes.FONT_NORMAL);
        btnInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInfoCooperativa();
            }
        });

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(Constantes.COLOR_SECONDARY);
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setFont(Constantes.FONT_NORMAL);
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Hacer que el botón Cerrar sea el botón por defecto
        getRootPane().setDefaultButton(btnCerrar);

        panel.add(btnInfo);
        panel.add(btnCerrar);

        return panel;
    }

    private void mostrarInfoCooperativa() {
        String info = CooperativaInfo.getInstance().getInformacionCompleta();

        JTextArea textArea = new JTextArea(info);
        textArea.setEditable(false);
        textArea.setFont(Constantes.FONT_NORMAL);
        textArea.setBackground(Constantes.COLOR_LIGHT);
        textArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 300));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "Información de " + CooperativaInfo.getInstance().getNombre(),
                JOptionPane.INFORMATION_MESSAGE);
    }
}