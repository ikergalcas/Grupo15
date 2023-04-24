package es.taw.taw23.controller;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.GestorRepository;
import es.taw.taw23.dto.Empleado;
import es.taw.taw23.dto.Solicitud;
import es.taw.taw23.entity.EmpleadoEntity;
import es.taw.taw23.service.EmpresaService;
import es.taw.taw23.service.GestorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/gestor")
public class GestorController {

    @Autowired
    protected GestorRepository gestorRepository;

    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected GestorService gestorService;
    @GetMapping("/")
    public String doPaginaPrincipalGestor(@RequestParam(value="id", required = true)Integer id, Model model) {

        /*EmpleadoEntity gestor = gestorRepository.findGestorById(id);
        model.addAttribute("gestor",gestor);*/

        Empleado gestor = gestorService.BuscarGestor(id);
        model.addAttribute("gestor",gestor);
        return "paginaPrincipalGestor";
    }

    @GetMapping("/solicitudes/")
    public String doMostrarSolicitudes(@RequestParam(value="id",required = true) Integer id, Model model) {
        Empleado gestor = gestorService.BuscarGestor(id);
        model.addAttribute("gestor",gestor);

        List<Solicitud> solicitudes = gestorService.buscarSolicitudesDeGestor(id);
        model.addAttribute("solicitudes",solicitudes);
        return "solicitudesGestor";
    }
}
