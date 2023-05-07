package es.taw.taw23.dto;

import es.taw.taw23.entity.CuentaClienteEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Hecho por:
 * Álvaro Yuste Moreno: 33%
 * Rocío Gómez Mancebo: 33%
 * Carla Serracant Guevara 33%
 */
public class Cuenta implements Serializable {
    private Integer id;
    private String numeroCuenta;
    private Timestamp fechaApertura;
    private Timestamp fechaCierre;
    private String estadoCuenta;
    private String tipoCuenta;
    private String moneda;
    private Double dinero;
    private List<CuentaCliente> cuentaCliente;
    private List<Movimiento> movimientosOrigen;     //Lista de movimientos en los que esta es la cuenta origen
    private List<Movimiento> movimientosDestino;    //Lista de movimientos en los que esta es la cuenta destino
    private Integer idSolicitud;
    private Integer idGestor;
    private Integer idCliente;
    private Integer idEmpresa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Timestamp getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Timestamp fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Timestamp getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Timestamp fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getDinero() {
        return dinero;
    }

    public void setDinero(Double dinero) {
        this.dinero = dinero;
    }

    public List<CuentaCliente> getCuentaCliente() {
        return cuentaCliente;
    }

    public void setCuentaCliente(List<CuentaCliente> cuentaCliente) {
        this.cuentaCliente = cuentaCliente;
    }

    public List<Movimiento> getMovimientosOrigen() {
        return movimientosOrigen;
    }

    public void setMovimientosOrigen(List<Movimiento> movimientosOrigen) {
        this.movimientosOrigen = movimientosOrigen;
    }

    public List<Movimiento> getMovimientosDestino() {
        return movimientosDestino;
    }

    public void setMovimientosDestino(List<Movimiento> movimientosDestino) {
        this.movimientosDestino = movimientosDestino;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(Integer idGestor) {
        this.idGestor = idGestor;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }


}
