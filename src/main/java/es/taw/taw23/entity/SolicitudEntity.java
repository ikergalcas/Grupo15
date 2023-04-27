package es.taw.taw23.entity;

import es.taw.taw23.dto.Solicitud;

import javax.persistence.*;

@Entity
@Table(name = "solicitud", schema = "grupo15", catalog = "")
public class SolicitudEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private ClienteEntity clienteByClienteId;
    @ManyToOne
    @JoinColumn(name = "empleado_id", referencedColumnName = "id")
    private EmpleadoEntity empleadoByEmpleadoId;
    @ManyToOne
    @JoinColumn(name = "tipo_solicitud_id", referencedColumnName = "id", nullable = false)
    private TipoSolicitudEntity tipoSolicitudByTipoSolicitudId;
    @ManyToOne
    @JoinColumn(name = "estado_solicitud_id", referencedColumnName = "id", nullable = false)
    private EstadoSolicitudEntity estadoSolicitudByEstadoSolicitudId;

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

        SolicitudEntity that = (SolicitudEntity) o;

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

    public EmpleadoEntity getEmpleadoByEmpleadoId() {
        return empleadoByEmpleadoId;
    }

    public void setEmpleadoByEmpleadoId(EmpleadoEntity empleadoByEmpleadoId) {
        this.empleadoByEmpleadoId = empleadoByEmpleadoId;
    }

    public TipoSolicitudEntity getTipoSolicitudByTipoSolicitudId() {
        return tipoSolicitudByTipoSolicitudId;
    }

    public void setTipoSolicitudByTipoSolicitudId(TipoSolicitudEntity tipoSolicitudByTipoSolicitudId) {
        this.tipoSolicitudByTipoSolicitudId = tipoSolicitudByTipoSolicitudId;
    }

    public EstadoSolicitudEntity getEstadoSolicitudByEstadoSolicitudId() {
        return estadoSolicitudByEstadoSolicitudId;
    }

    public void setEstadoSolicitudByEstadoSolicitudId(EstadoSolicitudEntity estadoSolicitudByEstadoSolicitudId) {
        this.estadoSolicitudByEstadoSolicitudId = estadoSolicitudByEstadoSolicitudId;
    }

    public Solicitud toDTO() {
        Solicitud dto = new Solicitud();
        dto.setId(this.id);
        dto.setCliente_id(this.clienteByClienteId.getId());
        dto.setEmpleado_id(this.empleadoByEmpleadoId.getId());
        dto.setEstado_solicitud_id(this.estadoSolicitudByEstadoSolicitudId.getId());
        dto.setTipo_solicitud_id(this.tipoSolicitudByTipoSolicitudId.getId());
        dto.setCliente(this.clienteByClienteId.toDTO());
        dto.setTipo_solicitud(this.tipoSolicitudByTipoSolicitudId.toDTO());
        dto.setEstado_solicitud(this.estadoSolicitudByEstadoSolicitudId.toDTO());
        return dto;
    }
}
