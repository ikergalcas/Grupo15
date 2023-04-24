package es.taw.taw23.entity;

import javax.persistence.*;

@Entity
@Table(name = "cambio_divisa", schema = "grupo15", catalog = "")
public class CambioDivisaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "cambio", nullable = false)
    private Double cambio;
    @ManyToOne
    @JoinColumn(name = "origen_id", referencedColumnName = "id", nullable = false)
    private DivisaEntity divisaByOrigenId;
    @ManyToOne
    @JoinColumn(name = "destino_id", referencedColumnName = "id", nullable = false)
    private DivisaEntity divisaByDestinoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCambio() {
        return cambio;
    }

    public void setCambio(Double cambio) {
        this.cambio = cambio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CambioDivisaEntity that = (CambioDivisaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cambio != null ? !cambio.equals(that.cambio) : that.cambio != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cambio != null ? cambio.hashCode() : 0);
        return result;
    }

    public DivisaEntity getDivisaByOrigenId() {
        return divisaByOrigenId;
    }

    public void setDivisaByOrigenId(DivisaEntity divisaByOrigenId) {
        this.divisaByOrigenId = divisaByOrigenId;
    }

    public DivisaEntity getDivisaByDestinoId() {
        return divisaByDestinoId;
    }

    public void setDivisaByDestinoId(DivisaEntity divisaByDestinoId) {
        this.divisaByDestinoId = divisaByDestinoId;
    }
}
