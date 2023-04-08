package es.taw.taw23.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Estadocuenta {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "estadocuenta", nullable = true)
    private String estadoCuenta;
    @OneToMany(mappedBy = "estadocuentaByEstadoCuentaId")
    private List<Cuenta> cuentasById;

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

        Estadocuenta that = (Estadocuenta) o;

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

    public List<Cuenta> getCuentasById() {
        return cuentasById;
    }

    public void setCuentasById(List<Cuenta> cuentasById) {
        this.cuentasById = cuentasById;
    }
}
