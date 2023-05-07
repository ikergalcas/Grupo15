package es.taw.taw23.entity;

import es.taw.taw23.dto.Estado_solicitud;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "estado_solicitud", schema = "grupo15", catalog = "")
public class EstadoSolicitudEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "estado", nullable = true)
    private String estado;
    @OneToMany(mappedBy = "estadoSolicitudByEstadoSolicitudId")
    private List<SolicitudEntity> solicitudsById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EstadoSolicitudEntity that = (EstadoSolicitudEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    public Estado_solicitud toDTO() {
        Estado_solicitud dto = new Estado_solicitud();
        dto.setId(this.id);
        dto.setEstado(this.estado);
        return dto;
    }

    public List<SolicitudEntity> getSolicitudsById() {
        return solicitudsById;
    }

    public void setSolicitudsById(List<SolicitudEntity> solicitudsById) {
        this.solicitudsById = solicitudsById;
    }
}
