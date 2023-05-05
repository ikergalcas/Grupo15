package es.taw.taw23.ui;

public class FiltroCajero {




    boolean ordenarFecha;
    boolean ordenarTipo;
    boolean ordenarImporte;
    String filtrarPorDivisa;
    String filtrarPorMovimiento;

    String filtrarPorNumeroDeCuenta;

    public FiltroCajero(){
        ordenarFecha=false;
        ordenarTipo=false;
        ordenarImporte=false;
        filtrarPorDivisa="";
        filtrarPorMovimiento="";
        filtrarPorNumeroDeCuenta="";
    }

    public boolean isOrdenarFecha() {
        return ordenarFecha;
    }

    public void setOrdenarFecha(boolean ordenarFecha) {
        this.ordenarFecha = ordenarFecha;
    }

    public boolean isOrdenarTipo() {
        return ordenarTipo;
    }

    public void setOrdenarTipo(boolean ordenarTipo) {
        this.ordenarTipo = ordenarTipo;
    }

    public boolean isOrdenarImporte() {
        return ordenarImporte;
    }

    public void setOrdenarImporte(boolean ordenarImporte) {
        this.ordenarImporte = ordenarImporte;
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

    public String getFiltrarPorNumeroDeCuenta(){
        return filtrarPorNumeroDeCuenta;
    }

    public void setFiltrarPorNumeroDeCuenta(String filtrarPorNumeroDeCuenta){
        this.filtrarPorNumeroDeCuenta=filtrarPorNumeroDeCuenta;
    }



}
