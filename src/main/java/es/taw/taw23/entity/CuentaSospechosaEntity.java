package es.taw.taw23.entity;

import es.taw.taw23.dto.CuentaSospechosa;

import javax.persistence.*;

@Entity
@Table(name = "cuenta_sospechosa", schema = "grupo15", catalog = "")
public class CuentaSospechosaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
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

        CuentaSospechosaEntity that = (CuentaSospechosaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public CuentaEntity getCuentaByCuentaId() {
        return cuentaByCuentaId;
    }

    public void setCuentaByCuentaId(CuentaEntity cuentaByCuentaId) {
        this.cuentaByCuentaId = cuentaByCuentaId;
    }

    public CuentaSospechosa toDTO() {
        CuentaSospechosa dto = new CuentaSospechosa();
        dto.setId(this.id);
        dto.setCuenta_id(this.cuentaByCuentaId.getId());
        dto.setCuenta(this.cuentaByCuentaId);
        return dto;
    }
}
