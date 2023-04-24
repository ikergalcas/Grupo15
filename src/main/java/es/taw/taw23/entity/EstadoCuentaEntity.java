package es.taw.taw23.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "estado_cuenta", schema = "grupo15", catalog = "")
public class EstadoCuentaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "estado_cuenta", nullable = true)
    private String estadoCuenta;
    @OneToMany(mappedBy = "estadoCuentaByEstadoCuentaId")
    private List<CuentaEntity> cuentasById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstadoCuentaEntity that = (EstadoCuentaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (estadoCuenta != null ? !estadoCuenta.equals(that.estadoCuenta) : that.estadoCuenta != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (estadoCuenta != null ? estadoCuenta.hashCode() : 0);
        return result;
    }

    public List<CuentaEntity> getCuentasById() {
        return cuentasById;
    }

    public void setCuentasById(List<CuentaEntity> cuentasById) {
        this.cuentasById = cuentasById;
    }
}
