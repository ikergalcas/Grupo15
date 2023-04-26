package es.taw.taw23.dto;

public class Cajero {
    Integer clienteId;
    Integer cuentaId;

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Integer getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }

    public Cajero(Integer clienteId, Integer cuentaId){
        this.clienteId=clienteId;
        this.cuentaId=cuentaId;
    }
}
