package es.taw.taw23.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class Movimiento implements Serializable {
    Integer id;
    Timestamp timeStamp;
    Double importeOrigen;
    Double importeDestino;
    String cuentaOrigen;
    String cuentaDestino;
    String tipo;

    String divisaCuentaOrigen;

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

    String divisaCuentaDestino;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
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

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
