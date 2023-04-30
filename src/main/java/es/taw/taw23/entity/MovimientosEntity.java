package es.taw.taw23.entity;

import es.taw.taw23.dto.DTO;
import es.taw.taw23.dto.Movimiento;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "movimientos", schema = "grupo15", catalog = "")
public class MovimientosEntity implements DTO<Movimiento> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "time_stamp", nullable = true)
    private Timestamp timeStamp;
    @Basic
    @Column(name = "importe_origen", nullable = false)
    private Double importeOrigen;
    @Basic
    @Column(name = "importe_destino", nullable = false)
    private Double importeDestino;
    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id", referencedColumnName = "id", nullable = false)
    private CuentaEntity cuentaByCuentaOrigenId;
    @ManyToOne
    @JoinColumn(name = "tipo_movimiento_id", referencedColumnName = "id", nullable = false)
    private TipoMovimientoEntity tipoMovimientoByTipoMovimientoId;
    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id", referencedColumnName = "id", nullable = false)
    private CuentaEntity cuentaByCuentaDestinoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getImporteOrigen() {
        return importeOrigen;
    }

    public void setImporteOrigen(Double importeOrigen) {
        this.importeOrigen = importeOrigen;
    }

    public Double getImporteDestino() {
        return importeDestino;
    }

    public void setImporteDestino(Double importeDestino) {
        this.importeDestino = importeDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovimientosEntity that = (MovimientosEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;
        if (importeOrigen != null ? !importeOrigen.equals(that.importeOrigen) : that.importeOrigen != null)
            return false;
        if (importeDestino != null ? !importeDestino.equals(that.importeDestino) : that.importeDestino != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        result = 31 * result + (importeOrigen != null ? importeOrigen.hashCode() : 0);
        result = 31 * result + (importeDestino != null ? importeDestino.hashCode() : 0);
        return result;
    }

    public CuentaEntity getCuentaByCuentaOrigenId() {
        return cuentaByCuentaOrigenId;
    }

    public void setCuentaByCuentaOrigenId(CuentaEntity cuentaByCuentaOrigenId) {
        this.cuentaByCuentaOrigenId = cuentaByCuentaOrigenId;
    }

    public TipoMovimientoEntity getTipoMovimientoByTipoMovimientoId() {
        return tipoMovimientoByTipoMovimientoId;
    }

    public void setTipoMovimientoByTipoMovimientoId(TipoMovimientoEntity tipoMovimientoByTipoMovimientoId) {
        this.tipoMovimientoByTipoMovimientoId = tipoMovimientoByTipoMovimientoId;
    }

    public CuentaEntity getCuentaByCuentaDestinoId() {
        return cuentaByCuentaDestinoId;
    }

    public void setCuentaByCuentaDestinoId(CuentaEntity cuentaByCuentaDestinoId) {
        this.cuentaByCuentaDestinoId = cuentaByCuentaDestinoId;
    }

    @Override
    public Movimiento toDTO() {
        Movimiento dto = new Movimiento();

        dto.setId(this.id);
        dto.setTimeStamp(this.timeStamp);
        dto.setImporteOrigen(this.importeOrigen);
        dto.setImporteDestino(this.importeDestino);
        dto.setCuentaOrigen(this.cuentaByCuentaOrigenId.getNumeroCuenta());
        dto.setCuentaDestino(this.cuentaByCuentaDestinoId.getNumeroCuenta());
        dto.setTipo(this.tipoMovimientoByTipoMovimientoId.getTipo());
        dto.setIdCuentaOrigen(this.cuentaByCuentaOrigenId.getId());
        dto.setIdCuentaDestino(this.cuentaByCuentaDestinoId.getId());
        return dto;
    }
}

