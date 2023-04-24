package es.taw.taw23.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "remitente", schema = "grupo15", catalog = "")
public class RemitenteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "remitente", nullable = true)
    private String remitente;
    @OneToMany(mappedBy = "remitenteByRemitenteId")
    private List<MensajeEntity> mensajesById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemitenteEntity that = (RemitenteEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (remitente != null ? !remitente.equals(that.remitente) : that.remitente != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (remitente != null ? remitente.hashCode() : 0);
        return result;
    }

    public List<MensajeEntity> getMensajesById() {
        return mensajesById;
    }

    public void setMensajesById(List<MensajeEntity> mensajesById) {
        this.mensajesById = mensajesById;
    }
}
