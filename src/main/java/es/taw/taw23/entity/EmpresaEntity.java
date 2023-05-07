package es.taw.taw23.entity;

import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Empresa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresa", schema = "grupo15", catalog = "")
public class EmpresaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;
    @Basic
    @Column(name = "cif", nullable = false, length = 45)
    private String cif;
    @Basic
    @Column(name = "contrasena", nullable = false, length = 45)
    private String contrasena;

    @OneToMany(mappedBy = "empresaByEmpresaId")
    private List<ClienteEntity> clientesById;

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

        EmpresaEntity that = (EmpresaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    public List<ClienteEntity> getClientesById() {
        return clientesById;
    }

    public void setClientesById(List<ClienteEntity> clientesById) {
        this.clientesById = clientesById;
    }


    public Empresa toDTO() {
        Empresa dto = new Empresa();

        dto.setIdEmpresa(this.id);
        dto.setNombre(this.nombre);
        dto.setCif(this.cif);
        dto.setContrasenaEmpresa(this.contrasena);
        List<Integer> asociados = new ArrayList<>();
        for(ClienteEntity c : this.clientesById) {
            asociados.add(c.getId());
        }
        dto.setAsociados(asociados);
        dto.setListaClientes(this.listaClientestoDTO(this.clientesById));
        dto.setCif(this.cif);
        return dto;
    }

    private List<Cliente> listaClientestoDTO(List<ClienteEntity> clientes) {
        List<Cliente> clientesDTO = new ArrayList<>();
        for (ClienteEntity c : clientes) {
            clientesDTO.add(c.toDTO());
        }
        return clientesDTO;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
