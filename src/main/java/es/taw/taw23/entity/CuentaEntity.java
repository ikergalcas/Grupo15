package es.taw.taw23.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "cuenta", schema = "grupo15", catalog = "")
public class CuentaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "numero_cuenta", nullable = false, length = 24)
    private String numeroCuenta;
    @Basic
    @Column(name = "fecha_apertura", nullable = false)
    private Timestamp fechaApertura;
    @Basic
    @Column(name = "fecha_cierre", nullable = true)
    private Timestamp fechaCierre;
    @Basic
    @Column(name = "dinero", nullable = true)
    private Integer dinero;
    @ManyToOne
    @JoinColumn(name = "estado_cuenta_id", referencedColumnName = "id", nullable = false)
    private EstadoCuentaEntity estadoCuentaByEstadoCuentaId;
    @ManyToOne
    @JoinColumn(name = "tipo_cuenta_id", referencedColumnName = "id", nullable = false)
    private TipoCuentaEntity tipoCuentaByTipoCuentaId;
    @ManyToOne
    @JoinColumn(name = "divisa_id", referencedColumnName = "id", nullable = false)
    private DivisaEntity divisaByDivisaId;
    @OneToMany(mappedBy = "cuentaByCuentaId")
    private List<CuentaClienteEntity> cuentaClientesById;
    @OneToMany(mappedBy = "cuentaByCuentaId")
    private List<CuentaSospechosaEntity> cuentaSospechosasById;
    @OneToMany(mappedBy = "cuentaByCuentaOrigenId")
    private List<MovimientosEntity> movimientosById;
    @OneToMany(mappedBy = "cuentaByCuentaDestinoId")
    private List<MovimientosEntity> movimientosById_0;

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

    public Integer getDinero() {
        return dinero;
    }

    public void setDinero(Integer dinero) {
        this.dinero = dinero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CuentaEntity that = (CuentaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (numeroCuenta != null ? !numeroCuenta.equals(that.numeroCuenta) : that.numeroCuenta != null) return false;
        if (fechaApertura != null ? !fechaApertura.equals(that.fechaApertura) : that.fechaApertura != null)
            return false;
        if (fechaCierre != null ? !fechaCierre.equals(that.fechaCierre) : that.fechaCierre != null) return false;
        if (dinero != null ? !dinero.equals(that.dinero) : that.dinero != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (numeroCuenta != null ? numeroCuenta.hashCode() : 0);
        result = 31 * result + (fechaApertura != null ? fechaApertura.hashCode() : 0);
        result = 31 * result + (fechaCierre != null ? fechaCierre.hashCode() : 0);
        result = 31 * result + (dinero != null ? dinero.hashCode() : 0);
        return result;
    }

    public EstadoCuentaEntity getEstadoCuentaByEstadoCuentaId() {
        return estadoCuentaByEstadoCuentaId;
    }

    public void setEstadoCuentaByEstadoCuentaId(EstadoCuentaEntity estadoCuentaByEstadoCuentaId) {
        this.estadoCuentaByEstadoCuentaId = estadoCuentaByEstadoCuentaId;
    }

    public TipoCuentaEntity getTipoCuentaByTipoCuentaId() {
        return tipoCuentaByTipoCuentaId;
    }

    public void setTipoCuentaByTipoCuentaId(TipoCuentaEntity tipoCuentaByTipoCuentaId) {
        this.tipoCuentaByTipoCuentaId = tipoCuentaByTipoCuentaId;
    }

    public DivisaEntity getDivisaByDivisaId() {
        return divisaByDivisaId;
    }

    public void setDivisaByDivisaId(DivisaEntity divisaByDivisaId) {
        this.divisaByDivisaId = divisaByDivisaId;
    }

    public List<CuentaClienteEntity> getCuentaClientesById() {
        return cuentaClientesById;
    }

    public void setCuentaClientesById(List<CuentaClienteEntity> cuentaClientesById) {
        this.cuentaClientesById = cuentaClientesById;
    }

    public List<CuentaSospechosaEntity> getCuentaSospechosasById() {
        return cuentaSospechosasById;
    }

    public void setCuentaSospechosasById(List<CuentaSospechosaEntity> cuentaSospechosasById) {
        this.cuentaSospechosasById = cuentaSospechosasById;
    }

    public List<MovimientosEntity> getMovimientosById() {
        return movimientosById;
    }

    public void setMovimientosById(List<MovimientosEntity> movimientosById) {
        this.movimientosById = movimientosById;
    }

    public List<MovimientosEntity> getMovimientosById_0() {
        return movimientosById_0;
    }

    public void setMovimientosById_0(List<MovimientosEntity> movimientosById_0) {
        this.movimientosById_0 = movimientosById_0;
    }
}
