package es.taw.taw23.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Rolcliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "tipo", nullable = false)
    private String tipo;
    @OneToMany(mappedBy = "rolclienteByRolclienteId")
    private List<Cliente> clientesById;

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

        Rolcliente that = (Rolcliente) o;

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

    public List<Cliente> getClientesById() {
        return clientesById;
    }

    public void setClientesById(List<Cliente> clientesById) {
        this.clientesById = clientesById;
    }
}
