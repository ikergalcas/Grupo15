package es.taw.taw23.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Empleado {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "nombre", nullable = true, length = 45)
    private String nombre;
    @OneToMany(mappedBy = "empleadoByEmpleadoId")
    private Collection<Chat> chatsById;
    @ManyToOne
    @JoinColumn(name = "rolEmpleado_id", referencedColumnName = "id", nullable = false)
    private Rolempleado rolempleadoByRolEmpleadoId;
    @OneToMany(mappedBy = "empleadoByEmpleadoId")
    private Collection<Solicitud> solicitudsById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Empleado empleado = (Empleado) o;

        if (id != null ? !id.equals(empleado.id) : empleado.id != null) return false;
        if (nombre != null ? !nombre.equals(empleado.nombre) : empleado.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    public Collection<Chat> getChatsById() {
        return chatsById;
    }

    public void setChatsById(Collection<Chat> chatsById) {
        this.chatsById = chatsById;
    }

    public Rolempleado getRolempleadoByRolEmpleadoId() {
        return rolempleadoByRolEmpleadoId;
    }

    public void setRolempleadoByRolEmpleadoId(Rolempleado rolempleadoByRolEmpleadoId) {
        this.rolempleadoByRolEmpleadoId = rolempleadoByRolEmpleadoId;
    }

    public Collection<Solicitud> getSolicitudsById() {
        return solicitudsById;
    }

    public void setSolicitudsById(Collection<Solicitud> solicitudsById) {
        this.solicitudsById = solicitudsById;
    }
}
