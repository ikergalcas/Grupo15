package es.taw.taw23.entity;

import javax.persistence.*;

@Entity
public class Movimientos {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "timeStamp")
    private String timeStamp;
    @Basic
    @Column(name = "monedaOrigen")
    private String monedaOrigen;
    @Basic
    @Column(name = "importeOrigen")
    private int importeOrigen;
    @Basic
    @Column(name = "importeDestino")
    private int importeDestino;
    @ManyToOne
    @JoinColumn(name = "cuenta_idCuenta", referencedColumnName = "idCuenta", nullable = false)
    private Cuenta cuentaByCuentaIdCuenta;
    @ManyToOne
    @JoinColumn(name = "tipoMovimiento_id", referencedColumnName = "id", nullable = false)
    private Tipomovimiento tipomovimientoByTipoMovimientoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getImporteOrigen() {
        return importeOrigen;
    }

    public void setImporteOrigen(int importeOrigen) {
        this.importeOrigen = importeOrigen;
    }

    public int getImporteDestino() {
        return importeDestino;
    }

    public void setImporteDestino(int importeDestino) {
        this.importeDestino = importeDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movimientos that = (Movimientos) o;

        if (id != that.id) return false;
        if (importeOrigen != that.importeOrigen) return false;
        if (importeDestino != that.importeDestino) return false;
        if (timeStamp != null ? !timeStamp.equals(that.timeStamp) : that.timeStamp != null) return false;
        if (monedaOrigen != null ? !monedaOrigen.equals(that.monedaOrigen) : that.monedaOrigen != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        result = 31 * result + (monedaOrigen != null ? monedaOrigen.hashCode() : 0);
        result = 31 * result + importeOrigen;
        result = 31 * result + importeDestino;
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