package es.taw.taw23.dto;

import java.io.Serializable;

/**
 * Hecho por: Álvaro Yuste Moreno
 */
public class Divisa implements Serializable {

    String moneda;

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

}
