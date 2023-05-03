package es.taw.taw23.ui;

public class FiltroCajero {


    String ordenar;
    String filtrarPorDivisa;
    String filtrarPorMovimiento;

    public FiltroCajero(){
        ordenar="";
        filtrarPorDivisa="";
        filtrarPorMovimiento="";
    }
    public String getOrdenar() {
        return ordenar;
    }

    public void setOrdenar(String ordenar) {
        this.ordenar = ordenar;
    }

    public String getFiltrarPorDivisa() {
        return filtrarPorDivisa;
    }

    public void setFiltrarPorDivisa(String filtrarPorDivisa) {
        this.filtrarPorDivisa = filtrarPorDivisa;
    }

    public String getFiltrarPorMovimiento() {
        return filtrarPorMovimiento;
    }

    public void setFiltrarPorMovimiento(String filtrarPorMovimiento) {
        this.filtrarPorMovimiento = filtrarPorMovimiento;
    }



}
