package es.taw.taw23.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

@Entity
public class Cliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "NIF")
    private String nif;
    @Basic
    @Column(name = "primernombre")
    private String primerNombre;
    @Basic
    @Column(name = "segundonombre")
    private String segundoNombre;
    @Basic
    @Column(name = "primerapellido")
    private String primerApellido;
    @Basic
    @Column(name = "segundoapellido")
    private String segundoApellido;
    @Basic
    @Column(name = "fechanacimiento")
    private Date fechaNacimiento;
    @Basic
    @Column(name = "calle")
    private String calle;
    @Basic
    @Column(name = "numero")
    private String numero;
    @Basic
    @Column(name = "puerta")
    private String puerta;
    @Basic
    @Column(name = "ciudad")
    private String ciudad;
    @Basic
    @Column(name = "pais")
    private String pais;
    @Basic
    @Column(name = "region")
    private String region;
    @Basic
    @Column(name = "CP")
    private String cp;
    @Basic
    @Column(name = "contrasena")
    private String contrasena;
    @OneToMany(mappedBy = "clienteByClienteId")
    private Collection<Chat> chatsById;
    @ManyToOne
    @JoinColumn(name = "rolcliente_id", referencedColumnName = "id", nullable = false)
    private Rolcliente rolclienteByRolclienteId;
    @OneToMany(mappedBy = "clienteByClienteId")
    private Collection<Cuenta> cuentasById;
    @OneToMany(mappedBy = "clienteByClienteId")
    private Collection<Solicitud> solicitudsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cliente cliente = (Cliente) o;

        if (id != cliente.id) return false;
        if (nif != null ? !nif.equals(cliente.nif) : cliente.nif != null) return false;
        if (primerNombre != null ? !primerNombre.equals(cliente.primerNombre) : cliente.primerNombre != null)
            return false;
        if (segundoNombre != null ? !segundoNombre.equals(cliente.segundoNombre) : cliente.segundoNombre != null)
            return false;
        if (primerApellido != null ? !primerApellido.equals(cliente.primerApellido) : cliente.primerApellido != null)
            return false;
        if (segundoApellido != null ? !segundoApellido.equals(cliente.segundoApellido) : cliente.segundoApellido != null)
            return false;
        if (fechaNacimiento != null ? !fechaNacimiento.equals(cliente.fechaNacimiento) : cliente.fechaNacimiento != null)
            return false;
        if (calle != null ? !calle.equals(cliente.calle) : cliente.calle != null) return false;
        if (numero != null ? !numero.equals(cliente.numero) : cliente.numero != null) return false;
        if (puerta != null ? !puerta.equals(cliente.puerta) : cliente.puerta != null) return false;
        if (ciudad != null ? !ciudad.equals(cliente.ciudad) : cliente.ciudad != null) return false;
        if (pais != null ? !pais.equals(cliente.pais) : cliente.pais != null) return false;
        if (region != null ? !region.equals(cliente.region) : cliente.region != null) return false;
        if (cp != null ? !cp.equals(cliente.cp) : cliente.cp != null) return false;
        if (contrasena != null ? !contrasena.equals(cliente.contrasena) : cliente.contrasena != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
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
        return result;
    }

    public Collection<Chat> getChatsById() {
        return chatsById;
    }

    public void setChatsById(Collection<Chat> chatsById) {
        this.chatsById = chatsById;
    }

    public Rolcliente getRolclienteByRolclienteId() {
        return rolclienteByRolclienteId;
    }

    public void setRolclienteByRolclienteId(Rolcliente rolclienteByRolclienteId) {
        this.rolclienteByRolclienteId = rolclienteByRolclienteId;
    }

    public Collection<Cuenta> getCuentasById() {
        return cuentasById;
    }

    public void setCuentasById(Collection<Cuenta> cuentasById) {
        this.cuentasById = cuentasById;
    }

    public Collection<Solicitud> getSolicitudsById() {
        return solicitudsById;
    }

    public void setSolicitudsById(Collection<Solicitud> solicitudsById) {
        this.solicitudsById = solicitudsById;
    }
}
