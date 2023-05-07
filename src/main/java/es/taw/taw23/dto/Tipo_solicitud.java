package es.taw.taw23.dto;

import java.io.Serializable;

/**
 * Hecho por: Carla Serracant
 */
public class Tipo_solicitud implements Serializable {
    private Integer id;
    private String tipo;

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
}
