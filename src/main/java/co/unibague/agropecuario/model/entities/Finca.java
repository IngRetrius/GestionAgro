package co.unibague.agropecuario.model.entities;

/**
 * Clase que representa las fincas asociadas a los productos agrícolas.
 * Implementa el principio de Single Responsibility.
 */
public class Finca {

    private String codigo;
    private String nombreFinca;
    private String propietario;
    private String municipio;
    private String vereda;
    private double areaTotal;
    private String tipoTerreno;
    private String infraestructura;
    private String telefono;
    private String email;

    /**
     * Constructor completo para Finca
     */
    public Finca(String codigo, String nombreFinca, String propietario,
                 String municipio, String vereda, double areaTotal,
                 String tipoTerreno, String infraestructura) {
        this.codigo = codigo;
        this.nombreFinca = nombreFinca;
        this.propietario = propietario;
        this.municipio = municipio;
        this.vereda = vereda;
        this.areaTotal = areaTotal;
        this.tipoTerreno = tipoTerreno;
        this.infraestructura = infraestructura;
    }

    /**
     * Constructor por defecto
     */
    public Finca() {
        this.municipio = "Ibagué";
        this.tipoTerreno = "Plano";
        this.infraestructura = "Básica";
    }

    /**
     * Calcula el área disponible para cultivo (asume 10% para infraestructura)
     * @return área cultivable en hectáreas
     */
    public double calcularAreaCultivable() {
        return areaTotal * 0.9;
    }

    /**
     * Determina si la finca es grande (más de 50 hectáreas)
     * @return true si es finca grande
     */
    public boolean esFincaGrande() {
        return areaTotal > 50.0;
    }

    /**
     * Genera código de ubicación basado en municipio y vereda
     * @return código de ubicación
     */
    public String generarCodigoUbicacion() {
        String municipioCode = municipio.substring(0, Math.min(3, municipio.length())).toUpperCase();
        String veredaCode = vereda != null && !vereda.isEmpty() ?
                vereda.substring(0, Math.min(3, vereda.length())).toUpperCase() : "GEN";
        return municipioCode + "-" + veredaCode + "-" + codigo;
    }

    /**
     * Verifica si tiene infraestructura de riego
     * @return true si tiene sistema de riego
     */
    public boolean tieneRiego() {
        return infraestructura != null &&
                infraestructura.toLowerCase().contains("riego");
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombreFinca() { return nombreFinca; }
    public void setNombreFinca(String nombreFinca) { this.nombreFinca = nombreFinca; }

    public String getPropietario() { return propietario; }
    public void setPropietario(String propietario) { this.propietario = propietario; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getVereda() { return vereda; }
    public void setVereda(String vereda) { this.vereda = vereda; }

    public double getAreaTotal() { return areaTotal; }
    public void setAreaTotal(double areaTotal) { this.areaTotal = areaTotal; }

    public String getTipoTerreno() { return tipoTerreno; }
    public void setTipoTerreno(String tipoTerreno) { this.tipoTerreno = tipoTerreno; }

    public String getInfraestructura() { return infraestructura; }
    public void setInfraestructura(String infraestructura) { this.infraestructura = infraestructura; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("Finca: %s (%s) | Propietario: %s | Municipio: %s | Área: %.2f ha | Terreno: %s",
                nombreFinca, codigo, propietario, municipio, areaTotal, tipoTerreno);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Finca finca = (Finca) obj;
        return codigo != null ? codigo.equals(finca.codigo) : finca.codigo == null;
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
}