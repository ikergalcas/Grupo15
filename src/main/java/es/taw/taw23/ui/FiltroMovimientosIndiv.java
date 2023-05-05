package es.taw.taw23.ui;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Hecho por: Rocío Gómez
 */
public class FiltroMovimientosIndiv {

    String tipo;
    String cuentaDestino;
    String cuentaOrigen;
    Timestamp timestamp;
    String divisaCuentaOrigen;
    String divisaCuentaDestino;
    Double importeOrigen;
    Double importeDestino;
    List<String> opciones;
    String orden;

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }
/*
    public FiltroMovimientosIndiv(){
        tipo="";
        cuentaDestino="";
        cuentaOrigen="";
        Date fecha = new Date();
        timestamp= new Timestamp(fecha.getTime());
        divisaCuentaDestino="";
        divisaCuentaOrigen="";
        importeDestino=null;
        importeOrigen=null;
    }
    */


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDivisaCuentaOrigen() {
        return divisaCuentaOrigen;
    }

    public void setDivisaCuentaOrigen(String divisaCuentaOrigen) {
        this.divisaCuentaOrigen = divisaCuentaOrigen;
    }

    public String getDivisaCuentaDestino() {
        return divisaCuentaDestino;
    }

    public void setDivisaCuentaDestino(String divisaCuentaDestino) {
        this.divisaCuentaDestino = divisaCuentaDestino;
    }

    public Double getImporteOrigen() {
        return importeOrigen;
    }

    public void setImporteOrigen(Double importeOrigen) {
        this.importeOrigen = importeOrigen;
    }

    public Double getImporteDestino() {
        return importeDestino;
    }

    public void setImporteDestino(Double importeDestino) {
        this.importeDestino = importeDestino;
    }
}
