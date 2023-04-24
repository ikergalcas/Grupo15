package es.taw.taw23.entity;

import es.taw.taw23.dto.Empleado;
import es.taw.taw23.dto.Solicitud;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empleado", schema = "grupo15", catalog = "")
public class EmpleadoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "nombre", nullable = true, length = 45)
    private String nombre;
    @Basic
    @Column(name = "numero_empleado", nullable = false)
    private Integer numeroEmpleado;
    @Basic
    @Column(name = "contraseña", nullable = false, length = 45)
    private String contraseña;
    @OneToMany(mappedBy = "empleadoByEmpleadoId")
    private List<ChatEntity> chatsById;
    @ManyToOne
    @JoinColumn(name = "rol_empleado_id", referencedColumnName = "id", nullable = false)
    private RolEmpleadoEntity rolEmpleadoByRolEmpleadoId;
    @OneToMany(mappedBy = "empleadoByEmpleadoId")
    private List<SolicitudEntity> solicitudsById;

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

    public Integer getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(Integer numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmpleadoEntity that = (EmpleadoEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (numeroEmpleado != null ? !numeroEmpleado.equals(that.numeroEmpleado) : that.numeroEmpleado != null)
            return false;
        if (contraseña != null ? !contraseña.equals(that.contraseña) : that.contraseña != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (numeroEmpleado != null ? numeroEmpleado.hashCode() : 0);
        result = 31 * result + (contraseña != null ? contraseña.hashCode() : 0);
        return result;
    }

    public List<ChatEntity> getChatsById() {
        return chatsById;
    }

    public void setChatsById(List<ChatEntity> chatsById) {
        this.chatsById = chatsById;
    }

    public RolEmpleadoEntity getRolEmpleadoByRolEmpleadoId() {
        return rolEmpleadoByRolEmpleadoId;
    }

    public void setRolEmpleadoByRolEmpleadoId(RolEmpleadoEntity rolEmpleadoByRolEmpleadoId) {
        this.rolEmpleadoByRolEmpleadoId = rolEmpleadoByRolEmpleadoId;
    }

    public List<SolicitudEntity> getSolicitudsById() {
        return solicitudsById;
    }

    public void setSolicitudsById(List<SolicitudEntity> solicitudsById) {
        this.solicitudsById = solicitudsById;
    }

    public Empleado toDto() {
        Empleado dto = new Empleado();
        dto.setId(this.id);
        dto.setNombre(this.nombre);
        dto.setNumero_empleado(this.numeroEmpleado);
        dto.setContrasena(this.contraseña);
        dto.setRol(this.rolEmpleadoByRolEmpleadoId.getTipo());
        dto.setSolicitudes(this.listaSolicitudPasaDTO(this.solicitudsById));
        return dto;
    }

    public List<Solicitud> listaSolicitudPasaDTO(List<SolicitudEntity> listaEntity) {
        List<Solicitud> listaDTO = new ArrayList<>();

        if (listaEntity.isEmpty() || listaEntity != null) {

            for (SolicitudEntity s : listaEntity) {
                listaDTO.add(s.toDTO());
            }
        }

        return listaDTO;
    }
}
