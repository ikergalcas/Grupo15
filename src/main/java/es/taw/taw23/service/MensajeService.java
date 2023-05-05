package es.taw.taw23.service;

import es.taw.taw23.dao.ChatRepository;
import es.taw.taw23.dao.MensajeRepository;
import es.taw.taw23.dao.RemitenteRepository;
import es.taw.taw23.dto.Mensaje;
import es.taw.taw23.entity.MensajeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Hecho por: Iker Gálvez
 */
@Service
public class MensajeService {
    @Autowired
    protected MensajeRepository mensajeRepository;

    @Autowired
    protected RemitenteRepository remitenteRepository;

    @Autowired
    protected ChatRepository chatRepository;

    public List<Mensaje> listarMensajes(int chatId){
        List<MensajeEntity> lista = mensajeRepository.findByChatId(chatId);
        return listaMensajesADTO(lista);
    }

    protected List<Mensaje> listaMensajesADTO (List<MensajeEntity> lista) {
        ArrayList dtos = new ArrayList<Mensaje>();
        lista.forEach((final MensajeEntity mensaje) -> dtos.add(mensaje.toDTO()));
        return dtos;
    }


    public void enviarMensaje(Mensaje mensaje) {
        MensajeEntity entidad = new MensajeEntity();
        entidad.setTexto(mensaje.getTexto());
        entidad.setFechaEnvio(mensaje.getFechaEnvio());
        entidad.setRemitente("");
        entidad.setRemitenteByRemitenteId(remitenteRepository.findByRemitente(mensaje.getRemitente()));
        entidad.setChatByChatId(chatRepository.findById(mensaje.getChatId()).orElse(null));
        mensajeRepository.save(entidad);
    }
}
