package es.taw.taw23.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Chat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "cerrado")
    private int cerrado;
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    private Cliente clienteByClienteId;
    @ManyToOne
    @JoinColumn(name = "empleado_id", referencedColumnName = "id", nullable = false)
    private Empleado empleadoByEmpleadoId;
    @OneToMany(mappedBy = "chatByChatId")
    private Collection<Mensaje> mensajesById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCerrado() {
        return cerrado;
    }

    public void setCerrado(int cerrado) {
        this.cerrado = cerrado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        if (id != chat.id) return false;
        if (cerrado != chat.cerrado) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + cerrado;
        return result;
    }

    public Cliente getClienteByClienteId() {
        return clienteByClienteId;
    }

    public void setClienteByClienteId(Cliente clienteByClienteId) {
        this.clienteByClienteId = clienteByClienteId;
    }

    public Empleado getEmpleadoByEmpleadoId() {
        return empleadoByEmpleadoId;
    }

    public void setEmpleadoByEmpleadoId(Empleado empleadoByEmpleadoId) {
        this.empleadoByEmpleadoId = empleadoByEmpleadoId;
    }

    public Collection<Mensaje> getMensajesById() {
        return mensajesById;
    }

    public void setMensajesById(Collection<Mensaje> mensajesById) {
        this.mensajesById = mensajesById;
    }
}
