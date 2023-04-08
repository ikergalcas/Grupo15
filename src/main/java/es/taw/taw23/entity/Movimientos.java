package es.taw.taw23.entity;

import javax.persistence.*;

@Entity
public class Movimientos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "timestamp", nullable = false, length = 45)
    private String timeStamp;
    @Basic
    @Column(name = "monedaorigen", nullable = false, length = 45)
    private String monedaOrigen;
    @Basic
    @Column(name = "importeorigen", nullable = false)
    private Integer importeOrigen;
    @Basic
    @Column(name = "importedestino", nullable = false)
    private Integer importeDestino;
    @ManyToOne
    @JoinColumn(name = "cuenta_idcuenta", referencedColumnName = "idCuenta", nullable = false)
    private Cuenta cuentaByCuentaIdCuenta;
    @ManyToOne
    @JoinColumn(name = "tipomovimiento_id", referencedColumnName = "id", nullable = false)
    private Tipomovimiento tipomovimientoByTipoMovimientoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public void setMonedaOrigen(String monedaOrigen) {
        this.monedaOrigen = monedaOrigen;
    }

    public Integer getImporteOrigen() {
        return importeOrigen;
    }

    public void setImporteOrigen(Integer importeOrigen) {
        this.importeOrigen = importeOrigen;
    }

    public Integer getImporteDestino() {
        return importeDestino;
    }

    public void setImporteDestino(Integer importeDestino) {
        this.importeDestino = importeDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movimientos that = (Movimientos) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;
        if (monedaOrigen != null ? !monedaOrigen.equals(that.monedaOrigen) : that.monedaOrigen != null) return false;
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
        result = 31 * result + (monedaOrigen != null ? monedaOrigen.hashCode() : 0);
        result = 31 * result + (importeOrigen != null ? importeOrigen.hashCode() : 0);
        result = 31 * result + (importeDestino != null ? importeDestino.hashCode() : 0);
        return result;
    }

    public Cuenta getCuentaByCuentaIdCuenta() {
        return cuentaByCuentaIdCuenta;
    }

    public void setCuentaByCuentaIdCuenta(Cuenta cuentaByCuentaIdCuenta) {
        this.cuentaByCuentaIdCuenta = cuentaByCuentaIdCuenta;
    }

    public Tipomovimiento getTipomovimientoByTipoMovimientoId() {
        return tipomovimientoByTipoMovimientoId;
    }

    public void setTipomovimientoByTipoMovimientoId(Tipomovimiento tipomovimientoByTipoMovimientoId) {
        this.tipomovimientoByTipoMovimientoId = tipomovimientoByTipoMovimientoId;
    }
}
