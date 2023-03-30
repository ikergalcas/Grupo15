package es.taw.taw23.controller;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.EmpresaRepository;
import es.taw.taw23.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected EmpresaRepository empresaRepository;

    @RequestMapping("/")
    public String doLogin() {
        return "login";
    }

    @PostMapping("/")
    public String doInicioSesion (@RequestParam("nif") String nif, @RequestParam("contrasena") String contrasena, Model model) {
        String urlto = "login";
        Cliente cliente = this.clienteRepository.inicioSesion(nif, contrasena);

        //Esta registrado
        if(cliente != null) {
            //Lo mandamos a la vista de la Empresa
            if(cliente.getRolclienteByRolclienteId().getTipo().equals("socio") ||
                cliente.getRolclienteByRolclienteId().getTipo().equals("autorizado")) {
                urlto = "redirect:/empresa/?id=" + cliente.getId();
            }
        }
        return urlto;
    }
}
