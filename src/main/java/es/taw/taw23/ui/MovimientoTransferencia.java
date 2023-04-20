package es.taw.taw23.ui;

import es.taw.taw23.entity.Cuenta;
import es.taw.taw23.entity.Tipomovimiento;

public class MovimientoTransferencia {
    private Integer importe;

    private Cuenta cuenta;


    public MovimientoTransferencia() {
        cuenta = null;
    }

    public Integer getImporte() {
        return importe;
    }

    public void setImporte(Integer importe) {
        this.importe = importe;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
