package es.taw.taw23.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Cuenta {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idcuenta", nullable = false)
    private Integer idCuenta;
    @Basic
    @Column(name = "numerocuenta", nullable = false, length = 24)
    private String numeroCuenta;
    @Basic
    @Column(name = "fechaapertura", nullable = false)
    private Timestamp fechaApertura;
    @Basic
    @Column(name = "fechacierre", nullable = true)
    private Timestamp fechaCierre;
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    private Cliente clienteByClienteId;
    @ManyToOne
    @JoinColumn(name = "estadocuenta_id", referencedColumnName = "id", nullable = false)
    private Estadocuenta estadocuentaByEstadoCuentaId;
    @ManyToOne
    @JoinColumn(name = "tipocuenta_id", referencedColumnName = "id", nullable = false)
    private Tipocuenta tipocuentaByTipoCuentaId;
    @OneToMany(mappedBy = "cuentaByCuentaIdCuenta")
    private List<Movimientos> movimientosByIdCuenta;

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cuenta cuenta = (Cuenta) o;

        if (idCuenta != null ? !idCuenta.equals(cuenta.idCuenta) : cuenta.idCuenta != null) return false;
        if (numeroCuenta != null ? !numeroCuenta.equals(cuenta.numeroCuenta) : cuenta.numeroCuenta != null)
            return false;
        if (fechaApertura != null ? !fechaApertura.equals(cuenta.fechaApertura) : cuenta.fechaApertura != null)
            return false;
        if (fechaCierre != null ? !fechaCierre.equals(cuenta.fechaCierre) : cuenta.fechaCierre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCuenta != null ? idCuenta.hashCode() : 0;
        result = 31 * result + (numeroCuenta != null ? numeroCuenta.hashCode() : 0);
        result = 31 * result + (fechaApertura != null ? fechaApertura.hashCode() : 0);
        result = 31 * result + (fechaCierre != null ? fechaCierre.hashCode() : 0);
        return result;
    }

    public Cliente getClienteByClienteId() {
        return clienteByClienteId;
    }

    public void setClienteByClienteId(Cliente clienteByClienteId) {
        this.clienteByClienteId = clienteByClienteId;
    }

    public Estadocuenta getEstadocuentaByEstadoCuentaId() {
        return estadocuentaByEstadoCuentaId;
    }

    public void setEstadocuentaByEstadoCuentaId(Estadocuenta estadocuentaByEstadoCuentaId) {
        this.estadocuentaByEstadoCuentaId = estadocuentaByEstadoCuentaId;
    }

    public Tipocuenta getTipocuentaByTipoCuentaId() {
        return tipocuentaByTipoCuentaId;
    }

    public void setTipocuentaByTipoCuentaId(Tipocuenta tipocuentaByTipoCuentaId) {
        this.tipocuentaByTipoCuentaId = tipocuentaByTipoCuentaId;
    }

    public List<Movimientos> getMovimientosByIdCuenta() {
        return movimientosByIdCuenta;
    }

    public void setMovimientosByIdCuenta(List<Movimientos> movimientosByIdCuenta) {
        this.movimientosByIdCuenta = movimientosByIdCuenta;
    }
}
