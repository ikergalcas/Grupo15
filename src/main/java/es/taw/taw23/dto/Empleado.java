package es.taw.taw23.dto;

import es.taw.taw23.entity.SolicitudEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Hecho por:
 * Iker Gálvez: 50%
 * Carla Serracant: 50%
 */
public class Empleado implements Serializable{
    private Integer id;
    private String nombre;
    private Integer numero_empleado;
    private String contrasena;
    private String rol;
    private List<Solicitud> solicitudes;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }

    public Integer getNumero_empleado() { return numero_empleado; }

    public String getContrasena() { return contrasena; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setNumero_empleado(Integer numero_empleado) { this.numero_empleado = numero_empleado; }

    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }

    public void setRol(String rol) { this.rol = rol; }

    public List<Solicitud> getSolicitudes() { return solicitudes; }

    public void setSolicitudes(List<Solicitud> solicitudes) { this.solicitudes = solicitudes; }
}
