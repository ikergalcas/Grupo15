package es.taw.taw23.dto;

import es.taw.taw23.entity.CuentaClienteEntity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
/**
 * Hecho por:
 * Álvaro Yuste Moreno: 33%
 * Rocío Gómez Mancebo: 33%
 * Carla Serracant Guevara 33%
 */
public class Cliente implements Serializable {
    private Integer id;
    private String nif;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private Date fechaNacimiento;
    private String calle;
    private String numero;
    private String puerta;
    private String ciudad;
    private String pais;
    private String region;
    private String cp;
    private String contrasena;
    private String tipo;
    private Integer idEmpresa;

    private List<CuentaClienteEntity> cuentas;
    private List<CuentaCliente> cuentaClientesDTO;
    private Integer acceso;

    private List<Cuenta> cuentaList;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer empresa) {
        this.idEmpresa = empresa;
    }

    public Integer getAcceso() {
        return acceso;
    }

    public void setAcceso(Integer acceso) {
        this.acceso = acceso;
    }

    public List<Cuenta> getCuentaList() {
        return cuentaList;
    }

    public void setCuentaList(List<Cuenta> cuentaList) {
        this.cuentaList = cuentaList;
    }

    public List<CuentaClienteEntity> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaClienteEntity> cuentas) {
        this.cuentas = cuentas;
    }

    public List<CuentaCliente> getCuentaClientesDTO() {
        return cuentaClientesDTO;
    }

    public void setCuentaClientesDTO(List<CuentaCliente> cuentaClientesDTO) {
        this.cuentaClientesDTO = cuentaClientesDTO;
    }
}
