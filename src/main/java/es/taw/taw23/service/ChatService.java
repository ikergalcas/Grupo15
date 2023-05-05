package es.taw.taw23.service;

import es.taw.taw23.dao.AsistenteRepository;
import es.taw.taw23.dao.ChatRepository;
import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.EmpleadoRepository;
import es.taw.taw23.dto.Chat;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.entity.ChatEntity;
import es.taw.taw23.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.aspectj.runtime.internal.Conversions.byteValue;

/**
 * Hecho por: Iker Gálvez
 */
@Service
public class ChatService {
    @Autowired
    protected ChatRepository chatRepository;

    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected EmpleadoRepository empleadoRepository;

    public List<Chat> listarChats (int asistenteId){
        List<ChatEntity> lista = chatRepository.findByEmpleadoId(asistenteId);
        return listaChatsADTO(lista);
    }

    protected List<Chat> listaChatsADTO (List<ChatEntity> lista) {
        ArrayList dtos = new ArrayList<Chat>();
        lista.forEach((final ChatEntity chat) -> dtos.add(chat.toDTO()));
        return dtos;
    }

    public  Chat findById(int chatId){
        Chat chat = chatRepository.findById(chatId).get().toDTO();
        return chat;
    }

    //Lista de clientes sin chat abierto
    public List<Cliente> buscarClientesSinChat() {
        List<ClienteEntity> clientes = clienteRepository.findAll();
        List<ClienteEntity> clientesSinChat = new ArrayList<ClienteEntity>();
        for (ClienteEntity cliente : clientes){
            ChatEntity chat = chatRepository.findChatAbierto(cliente.getId());
            if (chat == null){
                clientesSinChat.add(cliente);
            }
        }
        return listaClientesADTO(clientesSinChat);
    }

    protected List<Cliente> listaClientesADTO (List<ClienteEntity> clientes) {
        ArrayList dtos = new ArrayList<Cliente>();
        clientes.forEach((final ClienteEntity cliente) -> dtos.add(cliente.toDTO()));
        return dtos;
    }

    public void nuevoChat(Chat chat) {
        ChatEntity entity = new ChatEntity();
        entity.setCerrado(chat.getCerrado());
        entity.setClienteByClienteId(clienteRepository.findById(chat.getClienteId()).orElse(null));
        entity.setEmpleadoByEmpleadoId(empleadoRepository.findById(chat.getEmpleadoId()).orElse(null));
        if (chatRepository.findChatsAbiertosCliente(chat.getClienteId()).size() == 0) {
            chatRepository.save(entity);
        }
    }

    public List<Chat> filtrarPorNifCerrado(int idAsistente, String nif) {
        List<ChatEntity> lista = chatRepository.findByEmpleadoIdFiltrarByNifOrdernadByCerrado(idAsistente,nif);
        return listaChatsADTO(lista);
    }

    public List<Chat> filtrarPorNifAbierto(int idAsistente, String nif) {
        List<ChatEntity> lista = chatRepository.findByEmpleadoIdFiltrarByNifOrdernadByAbierto(idAsistente,nif);
        return listaChatsADTO(lista);
    }

    public List<Chat> ordenarPorCerrado(int idAsistente) {
        List<ChatEntity> lista = chatRepository.findByEmpleadoIdCerrado(idAsistente);
        return listaChatsADTO(lista);
    }

    public List<Chat> ordenarPorAbierto(int idAsistente) {
        List<ChatEntity> lista = chatRepository.findByEmpleadoIdAbierto(idAsistente);
        return listaChatsADTO(lista);
    }

    public List<Chat> filtrarPorNif(int idAsistente, String nif) {
        List<ChatEntity> lista = chatRepository.findByEmpleadoIdFiltrarByNif(idAsistente,nif);
        return listaChatsADTO(lista);
    }

    public Chat buscarChatCliente(int idCliente) {
        Chat chat = null;
        if (chatRepository.findChatsAbiertosCliente(idCliente).size() > 0) {
            ChatEntity entidadChat = chatRepository.findChatAbierto(idCliente);
            chat = entidadChat.toDTO();
        }
        return chat;
    }

    public void cerrarChat(int idChat) {
        ChatEntity chat = chatRepository.findById(idChat).orElse(null);
        chat.setCerrado(byteValue(1));
        chatRepository.save(chat);
    }
}
