package es.taw.taw23.entity;

import es.taw.taw23.dto.Chat;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chat", schema = "grupo15", catalog = "")
public class ChatEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "cerrado", nullable = false)
    private Byte cerrado;
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    private ClienteEntity clienteByClienteId;
    @ManyToOne
    @JoinColumn(name = "empleado_id", referencedColumnName = "id", nullable = false)
    private EmpleadoEntity empleadoByEmpleadoId;
    @OneToMany(mappedBy = "chatByChatId")
    private List<MensajeEntity> mensajesById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getCerrado() {
        return cerrado;
    }

    public void setCerrado(Byte cerrado) {
        this.cerrado = cerrado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatEntity that = (ChatEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cerrado != null ? !cerrado.equals(that.cerrado) : that.cerrado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cerrado != null ? cerrado.hashCode() : 0);
        return result;
    }

    public ClienteEntity getClienteByClienteId() {
        return clienteByClienteId;
    }

    public void setClienteByClienteId(ClienteEntity clienteByClienteId) {
        this.clienteByClienteId = clienteByClienteId;
    }

    public EmpleadoEntity getEmpleadoByEmpleadoId() {
        return empleadoByEmpleadoId;
    }

    public void setEmpleadoByEmpleadoId(EmpleadoEntity empleadoByEmpleadoId) {
        this.empleadoByEmpleadoId = empleadoByEmpleadoId;
    }

    public List<MensajeEntity> getMensajesById() {
        return mensajesById;
    }

    public void setMensajesById(List<MensajeEntity> mensajesById) {
        this.mensajesById = mensajesById;
    }

    public Chat toDTO(){
        Chat dto = new Chat();
        dto.setId(id);
        dto.setCerrado(cerrado);
        dto.setCliente(clienteByClienteId.getNif());
        dto.setClienteId(clienteByClienteId.getId());
        dto.setEmpleado(empleadoByEmpleadoId.getNumeroEmpleado());
        dto.setEmpleadoId(empleadoByEmpleadoId.getId());
        return dto;
    }
}
