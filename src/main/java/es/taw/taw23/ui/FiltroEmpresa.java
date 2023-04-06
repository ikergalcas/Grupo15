package es.taw.taw23.ui;

public class FiltroEmpresa {
    private String nif;
    private String primerNombre;
    private String primerApellido;

    public FiltroEmpresa() {
        nif = "";
        primerApellido = "";
        primerNombre = "";
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
}
