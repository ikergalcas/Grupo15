package es.taw.taw23.ui;

public class FiltroEmpresa {
    private String nif;
    private String primerNombre;
    private String primerApellido;
    private String nombreEmpresa;

    public FiltroEmpresa() {
        nif = "";
        primerApellido = "";
        primerNombre = "";
        nombreEmpresa = "";
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
}
