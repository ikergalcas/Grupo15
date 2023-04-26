package es.taw.taw23.controller;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.GestorRepository;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.Empleado;
import es.taw.taw23.dto.Solicitud;
import es.taw.taw23.entity.EmpleadoEntity;
import es.taw.taw23.entity.SolicitudEntity;
import es.taw.taw23.service.EmpresaService;
import es.taw.taw23.service.GestorService;
import es.taw.taw23.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/gestor")
public class GestorController {

    @Autowired
    protected SolicitudService solicitudService;

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

    @GetMapping("/verSolicitudAltaCliente/{idSolicitud}/{idGestor}")
    public String doVerSolicitud(@PathVariable("idSolicitud") Integer idSolicitud,
                                 @PathVariable("idGestor") Integer idGestor,
                                 Model model) {
        Solicitud solicitud = solicitudService.buscarSolicitud(idSolicitud);
        model.addAttribute("solicitud",solicitud);

        Empleado gestor = gestorService.BuscarGestor(idGestor);
        model.addAttribute("gestor",gestor);

        Cliente cliente = solicitudService.buscarClienteAPartirDeSolicitud(idSolicitud);
        model.addAttribute("cliente",cliente);

        Cuenta cuenta = new Cuenta();
        model.addAttribute("cuenta",cuenta);
        return "verSolicitudAltaCliente";
    }

    @PostMapping("/crearCuentaBancariaCliente")
    public String doCrearCuentaBancaria(@ModelAttribute("cuenta") Cuenta cuenta,
                                        Model model) {

        Solicitud solicitud = solicitudService.buscarSolicitud(cuenta.getIdSolicitud());
        Empleado gestor = gestorService.BuscarGestor(cuenta.getIdGestor());
        model.addAttribute("solicitud",solicitud);
        model.addAttribute("gestor",gestor);
        model.addAttribute("cuenta",cuenta);
        return "confirmacionAprobarSolicitud";
    }
    @GetMapping("/aprobarSolicitud/{idSolicitud}/{idGestor}")
    public String doAprobarSolicitud(@PathVariable("idSolicitud") Integer idSolicitud,
                                     @PathVariable("idGestor") Integer idGestor,
                                     Model model) {
        solicitudService.aprobarSolicitud(idSolicitud);
        List<Solicitud> solicitudes = gestorService.buscarSolicitudesDeGestor(idGestor);
        model.addAttribute("solicitudes", solicitudes);
        Empleado gestor = gestorService.BuscarGestor(idGestor);
        model.addAttribute("gestor",gestor);
        return "redirect:/gestor/solicitudes/?id=" + idGestor;
    }
}
