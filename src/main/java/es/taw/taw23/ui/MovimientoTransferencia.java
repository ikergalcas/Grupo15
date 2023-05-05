package es.taw.taw23.ui;

import es.taw.taw23.dto.Cuenta;

import java.sql.Timestamp;

/**
 * Hecho por: Rocío Gómez Mancebo
 */
public class MovimientoTransferencia {
    private Double importe;

    private String cuentaOrigen;

    private String cuentaDestino;

    private Timestamp timeStamp;

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
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

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
}
