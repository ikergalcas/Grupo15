package es.taw.taw23.controller;

import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Empleado;
import es.taw.taw23.dto.Empresa;
import es.taw.taw23.entity.*;
import es.taw.taw23.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/")
    public String doLogin() {
        return "login";
    }

    @PostMapping("/")
    public String doInicioSesion (@RequestParam("nif") String nif, @RequestParam("contrasena") String contrasena, Model model) {
        String urlto = "login";
        Cliente cliente = this.loginService.buscarCliente(nif, contrasena);
        Empleado empleado = this.loginService.buscarEmpleado(nif, contrasena);
        //Esta registrado
        if(cliente != null) {
            //Lo mandamos a la vista de la Empresa
            if(cliente.getTipo().equals("socio") ||
                cliente.getTipo().equals("autorizado")) {
                urlto = "redirect:/empresa/?id=" + cliente.getId(); // ------------- HAY QUE CAMBIAR ESTA URL -----------------
            }
        } else if (empleado != null) {
            if (empleado.getRol().equals("asistente")){
                urlto = "redirect:/asistente/"+empleado.getId();
            }
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
}
