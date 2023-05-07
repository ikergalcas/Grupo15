package es.taw.taw23.entity;

import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.CuentaCliente;
import es.taw.taw23.dto.DTO;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente", schema = "grupo15", catalog = "")
public class ClienteEntity implements DTO<Cliente> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "nif", nullable = false, length = 45)
    private String nif;
    @Basic
    @Column(name = "primer_nombre", nullable = true, length = 45)
    private String primerNombre;
    @Basic
    @Column(name = "segundo_nombre", nullable = true, length = 45)
    private String segundoNombre;
    @Basic
    @Column(name = "primer_apellido", nullable = true, length = 45)
    private String primerApellido;
    @Basic
    @Column(name = "segundo_apellido", nullable = true, length = 45)
    private String segundoApellido;
    @Basic
    @Column(name = "fecha_nacimiento", nullable = true)
    private Date fechaNacimiento;
    @Basic
    @Column(name = "calle", nullable = true, length = 45)
    private String calle;
    @Basic
    @Column(name = "numero", nullable = true, length = 45)
    private String numero;
    @Basic
    @Column(name = "puerta", nullable = true, length = 45)
    private String puerta;
    @Basic
    @Column(name = "ciudad", nullable = true, length = 45)
    private String ciudad;
    @Basic
    @Column(name = "pais", nullable = true, length = 45)
    private String pais;
    @Basic
    @Column(name = "region", nullable = true, length = 45)
    private String region;
    @Basic
    @Column(name = "CP", nullable = true, length = 45)
    private String cp;
    @Basic
    @Column(name = "contrasena", nullable = false, length = 45)
    private String contrasena;
    @Basic
    @Column(name = "acceso", nullable = true)
    private Integer acceso;
    @OneToMany(mappedBy = "clienteByClienteId")
    private List<ChatEntity> chatsById;
    @ManyToOne
    @JoinColumn(name = "rolcliente_id", referencedColumnName = "id", nullable = false)
    private RolClienteEntity rolClienteByRolclienteId;
    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private EmpresaEntity empresaByEmpresaId;
    @OneToMany(mappedBy = "clienteByClienteId")
    private List<CuentaClienteEntity> cuentaClientesById;
    @OneToMany(mappedBy = "clienteByClienteId")
    private List<SolicitudEntity> solicitudsById;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Integer getAcceso() {
        return acceso;
    }

    public void setAcceso(Integer acceso) {
        this.acceso = acceso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClienteEntity that = (ClienteEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nif != null ? !nif.equals(that.nif) : that.nif != null) return false;
        if (primerNombre != null ? !primerNombre.equals(that.primerNombre) : that.primerNombre != null) return false;
        if (segundoNombre != null ? !segundoNombre.equals(that.segundoNombre) : that.segundoNombre != null)
            return false;
        if (primerApellido != null ? !primerApellido.equals(that.primerApellido) : that.primerApellido != null)
            return false;
        if (segundoApellido != null ? !segundoApellido.equals(that.segundoApellido) : that.segundoApellido != null)
            return false;
        if (fechaNacimiento != null ? !fechaNacimiento.equals(that.fechaNacimiento) : that.fechaNacimiento != null)
            return false;
        if (calle != null ? !calle.equals(that.calle) : that.calle != null) return false;
        if (numero != null ? !numero.equals(that.numero) : that.numero != null) return false;
        if (puerta != null ? !puerta.equals(that.puerta) : that.puerta != null) return false;
        if (ciudad != null ? !ciudad.equals(that.ciudad) : that.ciudad != null) return false;
        if (pais != null ? !pais.equals(that.pais) : that.pais != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;
        if (cp != null ? !cp.equals(that.cp) : that.cp != null) return false;
        if (contrasena != null ? !contrasena.equals(that.contrasena) : that.contrasena != null) return false;
        if (acceso != null ? !acceso.equals(that.acceso) : that.acceso != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nif != null ? nif.hashCode() : 0);
        result = 31 * result + (primerNombre != null ? primerNombre.hashCode() : 0);
        result = 31 * result + (segundoNombre != null ? segundoNombre.hashCode() : 0);
        result = 31 * result + (primerApellido != null ? primerApellido.hashCode() : 0);
        result = 31 * result + (segundoApellido != null ? segundoApellido.hashCode() : 0);
        result = 31 * result + (fechaNacimiento != null ? fechaNacimiento.hashCode() : 0);
        result = 31 * result + (calle != null ? calle.hashCode() : 0);
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        result = 31 * result + (puerta != null ? puerta.hashCode() : 0);
        result = 31 * result + (ciudad != null ? ciudad.hashCode() : 0);
        result = 31 * result + (pais != null ? pais.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (cp != null ? cp.hashCode() : 0);
        result = 31 * result + (contrasena != null ? contrasena.hashCode() : 0);
        result = 31 * result + (acceso != null ? acceso.hashCode() : 0);
        return result;
    }

    public List<ChatEntity> getChatsById() {
        return chatsById;
    }

    public void setChatsById(List<ChatEntity> chatsById) {
        this.chatsById = chatsById;
    }

    public RolClienteEntity getRolClienteByRolclienteId() {
        return rolClienteByRolclienteId;
    }

    public void setRolClienteByRolclienteId(RolClienteEntity rolClienteByRolclienteId) {
        this.rolClienteByRolclienteId = rolClienteByRolclienteId;
    }

    public EmpresaEntity getEmpresaByEmpresaId() {
        return empresaByEmpresaId;
    }

    public void setEmpresaByEmpresaId(EmpresaEntity empresaByEmpresaId) {
        this.empresaByEmpresaId = empresaByEmpresaId;
    }

    public List<CuentaClienteEntity> getCuentaClientesById() {
        return cuentaClientesById;
    }

    public void setCuentaClientesById(List<CuentaClienteEntity> cuentaClientesById) {
        this.cuentaClientesById = cuentaClientesById;
    }

    public List<SolicitudEntity> getSolicitudsById() {
        return solicitudsById;
    }

    public void setSolicitudsById(List<SolicitudEntity> solicitudsById) {
        this.solicitudsById = solicitudsById;
    }

    @Override
    public Cliente toDTO() {
        Cliente dto = new Cliente();

        dto.setId(this.id);
        dto.setNif(this.nif);
        dto.setPrimerNombre(this.primerNombre);
        dto.setSegundoNombre(this.segundoNombre);
        dto.setPrimerApellido(this.primerApellido);
        dto.setSegundoApellido(this.segundoApellido);
        dto.setFechaNacimiento(this.fechaNacimiento);
        dto.setCalle(this.calle);
        dto.setNumero(this.numero);
        dto.setPuerta(this.puerta);
        dto.setCalle(this.ciudad);
        dto.setPais(this.pais);
        dto.setRegion(this.region);
        dto.setCp(this.cp);
        dto.setContrasena(this.contrasena);
        dto.setTipo(this.getRolClienteByRolclienteId().getTipo());
        dto.setAcceso(this.acceso);

        if(this.empresaByEmpresaId != null){
            dto.setIdEmpresa(this.empresaByEmpresaId.getId());
        }

        if(this.cuentaClientesById != null) {
            List<Cuenta> cuentas = new ArrayList<>();
            List<CuentaCliente> cuentaClientes = new ArrayList<>();
            for(CuentaClienteEntity c : this.cuentaClientesById) {
                cuentas.add(c.getCuentaByCuentaId().toDTO());
                cuentaClientes.add(c.toDTO());
            }
            dto.setCuentaList(cuentas);
            dto.setCuentaClientesDTO(cuentaClientes);
        }

        if(this.cuentaClientesById != null) {
            dto.setCuentas(this.cuentaClientesById);
        }

        return dto;
    }
}
