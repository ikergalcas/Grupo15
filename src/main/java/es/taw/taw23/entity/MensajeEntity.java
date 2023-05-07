package es.taw.taw23.entity;

import es.taw.taw23.dto.Chat;
import es.taw.taw23.dto.Mensaje;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "mensaje", schema = "grupo15", catalog = "")
public class MensajeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "texto", nullable = false, length = 500)
    private String texto;
    @Basic
    @Column(name = "fecha_envio", nullable = true)
    private Timestamp fechaEnvio;
    @Basic
    @Column(name = "remitente", nullable = false, length = 45)
    private String remitente;
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false)
    private ChatEntity chatByChatId;
    @ManyToOne
    @JoinColumn(name = "remitente_id", referencedColumnName = "id", nullable = false)
    private RemitenteEntity remitenteByRemitenteId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Timestamp fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
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

        MensajeEntity that = (MensajeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (texto != null ? !texto.equals(that.texto) : that.texto != null) return false;
        if (fechaEnvio != null ? !fechaEnvio.equals(that.fechaEnvio) : that.fechaEnvio != null) return false;
        if (remitente != null ? !remitente.equals(that.remitente) : that.remitente != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (texto != null ? texto.hashCode() : 0);
        result = 31 * result + (fechaEnvio != null ? fechaEnvio.hashCode() : 0);
        result = 31 * result + (remitente != null ? remitente.hashCode() : 0);
        return result;
    }

    public ChatEntity getChatByChatId() {
        return chatByChatId;
    }

    public void setChatByChatId(ChatEntity chatByChatId) {
        this.chatByChatId = chatByChatId;
    }

    public RemitenteEntity getRemitenteByRemitenteId() {
        return remitenteByRemitenteId;
    }

    public void setRemitenteByRemitenteId(RemitenteEntity remitenteByRemitenteId) {
        this.remitenteByRemitenteId = remitenteByRemitenteId;
    }

    public Mensaje toDTO(){
        Mensaje dto = new Mensaje();
        dto.setId(id);
        dto.setTexto(texto);
        dto.setFechaEnvio(fechaEnvio);
        dto.setRemitente(remitenteByRemitenteId.getRemitente());
        dto.setChatId(chatByChatId.getId());
        return dto;
    }
}
