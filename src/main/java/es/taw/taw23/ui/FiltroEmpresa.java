package es.taw.taw23.ui;
/**
 * Hecho por: Álvaro Yuste Moreno
 */
public class FiltroEmpresa {
    private String nif;
    private String primerNombre;
    private String primerApellido;
    private String nombreEmpresa;
    private String puesto;

    public FiltroEmpresa() {
        nif = "";
        primerApellido = "";
        primerNombre = "";
        nombreEmpresa = "";
        puesto = "";
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getPrimerApellido() { return primerApellido; }

    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }

    public String getNif() { return nif; }

    public void setNif(String nif) { this.nif = nif; }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

}
