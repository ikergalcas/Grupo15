package es.taw.taw23.dto;

import es.taw.taw23.entity.CuentaEntity;

import java.io.Serializable;
/**
 * Hecho por: Carla Serracant Guevara
 */
public class CuentaSospechosa implements Serializable {
    private Integer id;
    private Integer cuenta_id; //numero de la cuenta sospechosa
    private CuentaEntity cuenta;
    private Cuenta cuentaDTO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCuenta_id() {
        return cuenta_id;
    }

    public void setCuenta_id(Integer cuenta_id) {
        this.cuenta_id = cuenta_id;
    }

    public CuentaEntity getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaEntity cuenta) {
        this.cuenta = cuenta;
    }

    public Cuenta getCuentaDTO() {
        return cuenta.toDTO();
    }
}
