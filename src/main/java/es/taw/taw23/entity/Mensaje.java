package es.taw.taw23.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Mensaje {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "texto")
    private String texto;
    @Basic
    @Column(name = "fechaEnvio")
    private Timestamp fechaEnvio;
    @Basic
    @Column(name = "remitente")
    private String remitente;
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false)
    private Chat chatByChatId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

        Mensaje mensaje = (Mensaje) o;

        if (id != mensaje.id) return false;
        if (texto != null ? !texto.equals(mensaje.texto) : mensaje.texto != null) return false;
        if (fechaEnvio != null ? !fechaEnvio.equals(mensaje.fechaEnvio) : mensaje.fechaEnvio != null) return false;
        if (remitente != null ? !remitente.equals(mensaje.remitente) : mensaje.remitente != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (texto != null ? texto.hashCode() : 0);
        result = 31 * result + (fechaEnvio != null ? fechaEnvio.hashCode() : 0);
        result = 31 * result + (remitente != null ? remitente.hashCode() : 0);
        return result;
    }

    public Chat getChatByChatId() {
        return chatByChatId;
    }

    public void setChatByChatId(Chat chatByChatId) {
        this.chatByChatId = chatByChatId;
    }
}
