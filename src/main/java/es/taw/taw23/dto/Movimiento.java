package es.taw.taw23.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Hecho por: Álvaro Yuste 50%
 * Carla Serracant 50%
 */
public class Movimiento implements Serializable {
    Integer id;
    Timestamp timeStamp;
    Double importeOrigen;
    Double importeDestino;
    String cuentaOrigen;
    String cuentaDestino;

    Integer idCuentaOrigen;
    Integer idCuentaDestino;
    String tipo;
    String divisaCuentaOrigen;
    String divisaCuentaDestino;
    Integer asociado;

    public Movimiento(Integer asociado) {
        this.asociado = asociado;
        importeDestino = null;
        importeOrigen = null;
        cuentaDestino = "";
        cuentaOrigen = "";
        tipo = "";
        divisaCuentaDestino = "";
        divisaCuentaOrigen = "";
    }
    public Movimiento() {
        timeStamp = null;
        importeDestino = null;
        importeOrigen = null;
        cuentaDestino = "";
        cuentaOrigen = "";
        tipo = "";
        divisaCuentaDestino = "";
        divisaCuentaOrigen = "";
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

    public Integer getAsociado() {
        return asociado;
    }

    public void setAsociado(Integer asociado) {
        this.asociado = asociado;
    }

    public Integer getIdCuentaOrigen() {
        return idCuentaOrigen;
    }

    public void setIdCuentaOrigen(Integer idCuentaOrigen) {
        this.idCuentaOrigen = idCuentaOrigen;
    }

    public Integer getIdCuentaDestino() {
        return idCuentaDestino;
    }

    public void setIdCuentaDestino(Integer idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }
}
