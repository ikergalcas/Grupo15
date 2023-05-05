package es.taw.taw23.controller;

import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Empleado;
import es.taw.taw23.dto.Empresa;
import es.taw.taw23.dto.Solicitud;
import es.taw.taw23.entity.*;
import es.taw.taw23.service.ClienteService;
import es.taw.taw23.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Hecho por:
 * Álvaro Yuste Moreno: 20%
 * Iker Gálvez Castillo: 20%
 * Carla Serra Cant: 20%
 * Pablo Alarcón Carrión: 20%
 * Rocío Gómez: 20%
 */
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    ClienteService clienteService;

    @GetMapping("/")
    public String doLogin() {
        return "login";
    }

    @PostMapping("/")
    public String doInicioSesion (@RequestParam("nif") String nif, @RequestParam("contrasena") String contrasena, Model model) {
        String urlto = "login";
        Cliente cliente = this.loginService.buscarCliente(nif, contrasena);
        Empleado empleado = this.loginService.buscarEmpleado(nif, contrasena);
        Empresa empresa = this.loginService.buscarEmpresa(nif, contrasena);
        Solicitud solicitud = null;
        if (cliente != null){
            solicitud = this.loginService.buscarSolicitudAltaPorIdCliente(cliente.getId());
        }
        //si la busqueda de solicitud es null significa q esta resuelta, cuenta activa
        //Esta registrado
        if(cliente != null && solicitud == null) {

            //Lo mandamos a la vista de la Empresa
            if(cliente.getTipo().equals("socio") || cliente.getTipo().equals("autorizado")) {
                urlto = "redirect:/empresa/?id=" + cliente.getId(); // ------------- HAY QUE CAMBIAR ESTA URL -----------------
            } else{//if(cliente.getTipo().equals("individual")){
                urlto = "redirect:/cliente/" + cliente.getId();
            }
        } else if (empleado != null) {
            if (empleado.getRol().equals("asistente")){
                urlto = "redirect:/asistente/"+empleado.getId();
            } else {
                urlto = "redirect:/gestor/" + empleado.getId();
            }
        } else if (empresa != null) {
            urlto = "redirect:/empresa/vistaEmpresa?idEmpresa=" + empresa.getIdEmpresa();
        }
        return urlto;
    }

    @GetMapping("/registroEmpresa")
    public String doRegistroEmpresa(Model model) {
        Empresa empresa = new Empresa();
        model.addAttribute("empresa", empresa);

        Cliente socio = new Cliente();
        model.addAttribute("socio", socio);

        return "registroEmpresa";
    }


    @PostMapping("/registroEmpresa")
    public String doRegisterEmpresa(@ModelAttribute("empresa")Empresa empresa, @ModelAttribute("socio") Cliente socio, Model model) {
        this.loginService.registrarEmpresa(empresa, socio);

        return "login";
    }

    @GetMapping("/registroCliente")
    public String doRegistroCliente(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);

        return "registroCliente";
    }
    @PostMapping("/registroCliente")
    public String doRegistarCliente(@ModelAttribute("cliente") Cliente cliente) {
        this.clienteService.registrarCliente(cliente);
        return "login";
    }
}
