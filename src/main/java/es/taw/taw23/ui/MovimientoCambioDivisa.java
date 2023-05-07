package es.taw.taw23.ui;

import java.sql.Timestamp;

/**
 * Hecho por: Rocío Gómez Mancebo
 */
public class MovimientoCambioDivisa {
    Integer idCliente;
    String divisa;
    Timestamp timeStamp;
    String cuenta;

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
}
