package es.taw.taw23.controller;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
    @Autowired
    protected ClienteRepository clienteRepository;

    @GetMapping("/")
    public String doListarEmpresa(Model model) {
        //List<Cliente> lista = clienteRepository.findAll();
        //List<Cliente> lista = clienteRepository.buscarPorTipoEmpresa();
        List<Cliente> lista = clienteRepository.buscarPorTipoEmpresa();
        model.addAttribute("socios", lista);
        return "socios";
    }
    /*
    @GetMapping("/editar")
    public String doEditarEmpresa(Model model) {

    }
    */
}
