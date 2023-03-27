package es.taw.taw23.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Rolempleado {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(mappedBy = "rolempleadoByRolEmpleadoId")
    private Collection<Empleado> empleadosById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rolempleado that = (Rolempleado) o;

        if (id != that.id) return false;
        if (tipo != null ? !tipo.equals(that.tipo) : that.tipo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    public Collection<Empleado> getEmpleadosById() {
        return empleadosById;
    }

    public void setEmpleadosById(Collection<Empleado> empleadosById) {
        this.empleadosById = empleadosById;
    }
}