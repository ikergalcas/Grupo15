package es.taw.taw23.entity;

import es.taw.taw23.dto.Tipo_solicitud;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_solicitud", schema = "grupo15", catalog = "")
public class TipoSolicitudEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "tipo", nullable = false)
    private String tipo;
    @OneToMany(mappedBy = "tipoSolicitudByTipoSolicitudId")
    private List<SolicitudEntity> solicitudsById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

        TipoSolicitudEntity that = (TipoSolicitudEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tipo != null ? !tipo.equals(that.tipo) : that.tipo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    public Tipo_solicitud toDTO() {
        Tipo_solicitud dto = new Tipo_solicitud();
        dto.setId(this.id);
        dto.setTipo(this.tipo);
        return dto;
    }

    public List<SolicitudEntity> getSolicitudsById() {
        return solicitudsById;
    }

    public void setSolicitudsById(List<SolicitudEntity> solicitudsById) {
        this.solicitudsById = solicitudsById;
    }
}
