package es.taw.taw23.dto;

import java.io.Serializable;
import java.util.List;
/**
 * Hecho por: Álvaro Yuste Moreno
 */
public class Empresa implements Serializable {
    private Integer idEmpresa;
    private String nombre;
    private String cif;
    private String contrasenaEmpresa;
    private List<Integer> asociados;

    private List<Cliente> listaClientes;
    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer id) {
        this.idEmpresa = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getContrasenaEmpresa() {
        return contrasenaEmpresa;
    }

    public void setContrasenaEmpresa(String contrasena) {
        this.contrasenaEmpresa = contrasena;
    }

    public List<Integer> getAsociados() {
        return asociados;
    }

    public void setAsociados(List<Integer> asociados) {
        this.asociados = asociados;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }
}
