package es.taw.taw23.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Estadocuenta {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "estadocuenta")
    private String estadoCuenta;
    @OneToMany(mappedBy = "estadocuentaByEstadoCuentaId")
    private Collection<Cuenta> cuentasById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

        if (id != that.id) return false;
        if (estadoCuenta != null ? !estadoCuenta.equals(that.estadoCuenta) : that.estadoCuenta != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (estadoCuenta != null ? estadoCuenta.hashCode() : 0);
        return result;
    }

    public Collection<Cuenta> getCuentasById() {
        return cuentasById;
    }

    public void setCuentasById(Collection<Cuenta> cuentasById) {
        this.cuentasById = cuentasById;
    }
}
