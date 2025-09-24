package co.unibague.agropecuario.model.singleton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Singleton que mantiene información global de la cooperativa.
 * Implementa el patrón Singleton siguiendo las mejores prácticas.
 */
public class CooperativaInfo {

    private static volatile CooperativaInfo instance;
    private static final Object lock = new Object();

    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String email;
    private String municipio;
    private String gerente;
    private LocalDateTime fechaFundacion;
    private int numeroSocios;
    private String version;

    /**
     * Constructor privado para prevenir instanciación externa
     */
    private CooperativaInfo() {
        inicializarDatos();
    }

    /**
     * Obtiene la instancia única de CooperativaInfo (Thread-safe)
     * @return instancia única de CooperativaInfo
     */
    public static CooperativaInfo getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new CooperativaInfo();
                }
            }
        }
        return instance;
    }

    /**
     * Inicializa los datos por defecto de la cooperativa
     */
    private void inicializarDatos() {
        this.nombre = "Cooperativa Agropecuaria del Tolima - COAGROTOL";
        this.nit = "890.123.456-7";
        this.direccion = "Carrera 3 # 12-34, Ibagué, Tolima";
        this.telefono = "(608) 261-1234";
        this.email = "info@coagrotol.co";
        this.municipio = "Ibagué";
        this.gerente = "María Elena Rodríguez";
        this.fechaFundacion = LocalDateTime.of(1995, 8, 15, 9, 0);
        this.numeroSocios = 245;
        this.version = "1.0.0";
    }

    /**
     * Obtiene información completa de la cooperativa
     * @return String con toda la información
     */
    public String getInformacionCompleta() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder info = new StringBuilder();

        info.append("=== INFORMACIÓN DE LA COOPERATIVA ===\n\n");
        info.append("Nombre: ").append(nombre).append("\n");
        info.append("NIT: ").append(nit).append("\n");
        info.append("Dirección: ").append(direccion).append("\n");
        info.append("Teléfono: ").append(telefono).append("\n");
        info.append("Email: ").append(email).append("\n");
        info.append("Municipio: ").append(municipio).append("\n");
        info.append("Gerente: ").append(gerente).append("\n");
        info.append("Fundación: ").append(fechaFundacion.format(formatter)).append("\n");
        info.append("Número de Socios: ").append(numeroSocios).append("\n");
        info.append("Versión del Sistema: ").append(version).append("\n");

        return info.toString();
    }

    /**
     * Obtiene información resumida para mostrar en la aplicación
     * @return String con información resumida
     */
    public String getInformacionResumida() {
        return String.format("%s - %s | Socios: %d", nombre, municipio, numeroSocios);
    }

    /**
     * Incrementa el número de socios
     */
    public void incrementarSocios() {
        this.numeroSocios++;
    }

    /**
     * Actualiza la información del gerente
     * @param nuevoGerente nombre del nuevo gerente
     */
    public void actualizarGerente(String nuevoGerente) {
        this.gerente = nuevoGerente;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getGerente() { return gerente; }

    public LocalDateTime getFechaFundacion() { return fechaFundacion; }

    public int getNumeroSocios() { return numeroSocios; }
    public void setNumeroSocios(int numeroSocios) { this.numeroSocios = numeroSocios; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
}