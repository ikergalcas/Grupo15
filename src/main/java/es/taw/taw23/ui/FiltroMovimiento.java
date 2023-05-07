package es.taw.taw23.ui;

import es.taw.taw23.dto.Movimiento;

import java.util.List;
/**
 * Hecho por: Carla Serracant Guevara
 */
public class FiltroMovimiento {
    /* Carla Serracant Guevara */
    private String tipoMovimiento;
    private Integer idGestor;
    private Integer idCliente;
    private Integer idEmpresa;
    private boolean fecha;

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
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

    public boolean isFecha() {
        return fecha;
    }

    public void setFecha(boolean fecha) {
        this.fecha = fecha;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
