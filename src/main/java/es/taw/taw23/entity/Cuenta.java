package es.taw.taw23.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Cuenta {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idCuenta")
    private int idCuenta;
    @Basic
    @Column(name = "numeroCuenta")
    private String numeroCuenta;
    @Basic
    @Column(name = "fechaApertura")
    private Timestamp fechaApertura;
    @Basic
    @Column(name = "fechaCierre")
    private Timestamp fechaCierre;
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    private Cliente clienteByClienteId;
    @ManyToOne
    @JoinColumn(name = "estadoCuenta_id", referencedColumnName = "id", nullable = false)
    private Estadocuenta estadocuentaByEstadoCuentaId;
    @OneToMany(mappedBy = "cuentaByCuentaIdCuenta")
    private Collection<Movimientos> movimientosByIdCuenta;

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
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

        if (idCuenta != cuenta.idCuenta) return false;
        if (numeroCuenta != null ? !numeroCuenta.equals(cuenta.numeroCuenta) : cuenta.numeroCuenta != null)
            return false;
        if (fechaApertura != null ? !fechaApertura.equals(cuenta.fechaApertura) : cuenta.fechaApertura != null)
            return false;
        if (fechaCierre != null ? !fechaCierre.equals(cuenta.fechaCierre) : cuenta.fechaCierre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCuenta;
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

    public Collection<Movimientos> getMovimientosByIdCuenta() {
        return movimientosByIdCuenta;
    }

    public void setMovimientosByIdCuenta(Collection<Movimientos> movimientosByIdCuenta) {
        this.movimientosByIdCuenta = movimientosByIdCuenta;
    }
}
