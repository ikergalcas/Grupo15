package es.taw.taw23.dto;

import es.taw.taw23.entity.CuentaClienteEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Cuenta implements Serializable {
    private Integer id;
    private String numeroCuenta;
    private Timestamp fechaApertura;
    private Timestamp fechaCierre;
    private String estadoCuenta;
    private String tipoCuenta;
    private String moneda;
    private Double dinero;
    private List<CuentaClienteEntity> cuentaCliente;
    private List<Movimiento> movimientosOrigen;     //Lista de movimientos en los que esta es la cuenta origen
    private List<Movimiento> movimientosDestino;    //Lista de movimientos en los que esta es la cuenta destino
    private Integer idSolicitud;
    private Integer idGestor;

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

    public List<CuentaClienteEntity> getCuentaCliente() {
        return cuentaCliente;
    }

    public void setCuentaCliente(List<CuentaClienteEntity> cuentaCliente) {
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
}
