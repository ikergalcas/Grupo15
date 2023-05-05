package es.taw.taw23.dto;

import java.io.Serializable;

/**
 * Hecho por: Iker Gálvez Castillo
 */
public class Chat implements Serializable {
    private Integer id;
    private Byte cerrado;
    private String cliente;
    private int clienteId;
    private int empleado;
    private int empleadoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getCerrado() {
        return cerrado;
    }

    public void setCerrado(Byte cerrado) {
        this.cerrado = cerrado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }
}
