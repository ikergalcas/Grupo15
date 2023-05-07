package es.taw.taw23.entity;

import es.taw.taw23.dto.CuentaCliente;

import javax.persistence.*;

@Entity
@Table(name = "cuenta_cliente", schema = "grupo15", catalog = "")
public class CuentaClienteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    private ClienteEntity clienteByClienteId;
    @ManyToOne
    @JoinColumn(name = "cuenta_id", referencedColumnName = "id", nullable = false)
    private CuentaEntity cuentaByCuentaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CuentaClienteEntity that = (CuentaClienteEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public ClienteEntity getClienteByClienteId() {
        return clienteByClienteId;
    }

    public void setClienteByClienteId(ClienteEntity clienteByClienteId) {
        this.clienteByClienteId = clienteByClienteId;
    }

    public CuentaEntity getCuentaByCuentaId() {
        return cuentaByCuentaId;
    }

    public void setCuentaByCuentaId(CuentaEntity cuentaByCuentaId) {
        this.cuentaByCuentaId = cuentaByCuentaId;
    }

    public CuentaCliente toDTO() {
        CuentaCliente dto = new CuentaCliente();
        dto.setId(this.id);
        dto.setCliente(this.clienteByClienteId);
        dto.setCuenta(this.cuentaByCuentaId);
        return dto;
    }
}
