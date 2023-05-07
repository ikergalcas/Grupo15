package es.taw.taw23.dto;

import es.taw.taw23.entity.ClienteEntity;
import es.taw.taw23.entity.CuentaEntity;

import java.io.Serializable;
/**
 * Hecho por: Carla Serracant Guevara
 */
public class CuentaCliente implements Serializable {
    private Integer id;
    private ClienteEntity cliente;
    private CuentaEntity cuenta;
    private Cliente clienteDTO;
    private Cuenta cuentaDTO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public CuentaEntity getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaEntity cuenta) {
        this.cuenta = cuenta;
    }

    public Cliente getClienteDTO() {
        return cliente.toDTO();
    }

    public void setClienteDTO(Cliente clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public Cuenta getCuentaDTO() {
        return cuenta.toDTO();
    }

    public void setCuentaDTO(Cuenta cuentaDTO) {
        this.cuentaDTO = cuentaDTO;
    }
}
