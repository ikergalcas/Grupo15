package es.taw.taw23.dto;

import es.taw.taw23.entity.TipoSolicitudEntity;

import java.io.Serializable;

/**
 * Hecho por: Carla Serracant
 */
public class Solicitud implements Serializable {
    private Integer id;
    private Integer cliente_id;
    private Integer empleado_id;
    private Integer tipo_solicitud_id;
    private Integer estado_solicitud_id;
    private Cliente cliente;
    private Tipo_solicitud tipo_solicitud;
    private Estado_solicitud estado_solicitud;
    private String estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Integer cliente_id) {
        this.cliente_id = cliente_id;
    }

    public Integer getEmpleado_id() {
        return empleado_id;
    }

    public void setEmpleado_id(Integer empleado_id) {
        this.empleado_id = empleado_id;
    }

    public Integer getTipo_solicitud_id() {
        return tipo_solicitud_id;
    }

    public void setTipo_solicitud_id(Integer tipo_solicitud_id) {
        this.tipo_solicitud_id = tipo_solicitud_id;
    }

    public Integer getEstado_solicitud_id() {
        return estado_solicitud_id;
    }

    public void setEstado_solicitud_id(Integer estado_solicitud_id) {
        this.estado_solicitud_id = estado_solicitud_id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tipo_solicitud getTipo_solicitud() {
        return tipo_solicitud;
    }

    public void setTipo_solicitud(Tipo_solicitud tipo_solicitud) {
        this.tipo_solicitud = tipo_solicitud;
    }

    public Estado_solicitud getEstado_solicitud() {
        return estado_solicitud;
    }

    public void setEstado_solicitud(Estado_solicitud estado_solicitud) {
        this.estado_solicitud = estado_solicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
