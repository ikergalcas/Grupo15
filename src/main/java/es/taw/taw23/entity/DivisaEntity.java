package es.taw.taw23.entity;

import es.taw.taw23.dto.DTO;
import es.taw.taw23.dto.Divisa;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "divisa", schema = "grupo15", catalog = "")
public class DivisaEntity implements DTO<Divisa> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "moneda", nullable = true)
    private String moneda;
    @OneToMany(mappedBy = "divisaByOrigenId")
    private List<CambioDivisaEntity> cambioDivisasById;
    @OneToMany(mappedBy = "divisaByDestinoId")
    private List<CambioDivisaEntity> cambioDivisasById_0;
    @OneToMany(mappedBy = "divisaByDivisaId")
    private List<CuentaEntity> cuentasById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DivisaEntity that = (DivisaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (moneda != null ? !moneda.equals(that.moneda) : that.moneda != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (moneda != null ? moneda.hashCode() : 0);
        return result;
    }

    public List<CambioDivisaEntity> getCambioDivisasById() {
        return cambioDivisasById;
    }

    public void setCambioDivisasById(List<CambioDivisaEntity> cambioDivisasById) {
        this.cambioDivisasById = cambioDivisasById;
    }

    public List<CambioDivisaEntity> getCambioDivisasById_0() {
        return cambioDivisasById_0;
    }

    public void setCambioDivisasById_0(List<CambioDivisaEntity> cambioDivisasById_0) {
        this.cambioDivisasById_0 = cambioDivisasById_0;
    }

    public List<CuentaEntity> getCuentasById() {
        return cuentasById;
    }

    public void setCuentasById(List<CuentaEntity> cuentasById) {
        this.cuentasById = cuentasById;
    }

    @Override
    public Divisa toDTO() {
        Divisa dto = new Divisa();

        dto.setMoneda(this.moneda);

        return dto;
    }
}
