package es.taw.taw23.controller;

import es.taw.taw23.dao.EmpleadoRepository;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Empleado;
import es.taw.taw23.dto.Mensaje;
import es.taw.taw23.service.AsistenteService;
import es.taw.taw23.service.ChatService;
import es.taw.taw23.service.MensajeService;
import es.taw.taw23.ui.FiltroAsistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import es.taw.taw23.dto.Chat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.aspectj.runtime.internal.Conversions.byteValue;

@Controller
@RequestMapping("/asistente")
public class AsistenteController {
    @Autowired
    protected ChatService chatService;

    @Autowired
    protected MensajeService mensajeService;

    @Autowired
    protected AsistenteService asistenteService;

    @GetMapping("/{id}")
    public String doListarChats(@PathVariable("id") int idAsistente, Model model){
        return procesarFiltro(null, idAsistente, model);
    }

    protected String procesarFiltro(FiltroAsistente filtro, int idAsistente, Model model){
        List<Chat> lista;
        Empleado asistente = asistenteService.buscarEmpleado(idAsistente);
        List<Cliente> clientes = chatService.buscarClientesSinChat();
        if (filtro == null || filtro.getNif() == "" && filtro.getEstado() == null){
            lista = chatService.listarChats(idAsistente);
            filtro = new FiltroAsistente();
        } else if (filtro.getNif() != "" && filtro.getEstado() != null){
            if (filtro.getEstado().equals(byteValue(0))){
                lista = chatService.filtrarPorNifCerrado(idAsistente, filtro.getNif());
            } else {
                lista = chatService.filtrarPorNifAbierto(idAsistente, filtro.getNif());
            }
        } else if (filtro.getEstado() == null) {
            lista = chatService.filtrarPorNif(idAsistente, filtro.getNif());
        } else {
            if (filtro.getEstado().equals(byteValue(0))){
                lista = chatService.ordenarPorCerrado(idAsistente);
            } else {
                lista = chatService.ordenarPorAbierto(idAsistente);
            }
        }
        model.addAttribute("chats", lista);
        model.addAttribute("asistente", asistente);
        model.addAttribute("clientes", clientes);
        model.addAttribute("filtro",filtro);
        return "asistente";
    }

    @PostMapping("/filtrar")
    public String doFiltrar(Model model, @ModelAttribute("filtro") FiltroAsistente filtro) {
        return procesarFiltro(filtro, filtro.getId(), model);
    }

    @GetMapping("/chatAsistente/{id}")
    public String doChat(@PathVariable("id") int idChat, Model model){
        List<Mensaje> mensajes = mensajeService.listarMensajes(idChat);
        Chat chat = chatService.findById(idChat);
        model.addAttribute("mensajes",mensajes);
        model.addAttribute("chat",chat);
        return "chatAsistente";
    }

    @PostMapping("/enviar/{idChat}")
    public String doEnviar(@RequestParam("texto") String texto, @PathVariable("idChat") int idChat){
        Mensaje mensaje = new Mensaje();
        mensaje.setTexto(texto);
        mensaje.setFechaEnvio(new Timestamp(System.currentTimeMillis()));
        mensaje.setRemitente("empleado");
        mensaje.setChatId(idChat);
        mensajeService.enviarMensaje(mensaje);
        String url = "redirect:/asistente/chatAsistente/"+idChat;
        return url;
    }

    @PostMapping("/abrirChat/{idAsistente}")
    public String doAbrirChat(@RequestParam("cliente") int clienteId, @PathVariable("idAsistente") int asistenteId){
        Chat chat = new Chat();
        chat.setCerrado(byteValue(0));
        chat.setClienteId(clienteId);
        chat.setEmpleadoId(asistenteId);
        chatService.nuevoChat(chat);
        String url = "redirect:/asistente/"+asistenteId;
        return url;
    }
}
