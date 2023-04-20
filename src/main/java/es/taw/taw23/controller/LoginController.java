package es.taw.taw23.controller;

import es.taw.taw23.dao.*;
import es.taw.taw23.entity.*;
import es.taw.taw23.ui.ClienteAux;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    protected SolicitudRepository solicitudRepository;

    @Autowired
    protected GestorRepository gestorRepository;

    @Autowired
    protected RolClienteRepository rolClienteRepository;

    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected AsociadoRepository asociadoRepository;

    @Autowired
    protected EmpresaRepository empresaRepository;

    @GetMapping("/")
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

    @GetMapping("/registroEmpresa")
    public String doRegistroEmpresa(Model model) {
        Empresa empresa = new Empresa();
        model.addAttribute(("empresa"), empresa);

        Cliente socio = new Cliente();
        model.addAttribute("socio", socio);

        return "registroEmpresa";
    }


    @PostMapping("/registroEmpresa")
    public String doRegisterEmpresa(@ModelAttribute("empresa")Empresa empresa, @ModelAttribute("socio") Cliente socio, Model model) {
        /**
         * Primero tengo que guardar ambos objetos en la bd sin asociarlos ya que si no da error.
         * Me traigo ambos objetos de la base de datos y entonces si que puedo crear las relaciones.
         */
        /** Todo esto lo hace el gestor al aceptar la solicitud de registro, yo solo tengo que enviarle la solicitud
         Rolcliente rol = this.rolClienteRepository.buscarRol("socio");
         socio.setRolclienteByRolclienteId(rol);
         this.clienteRepository.save(socio);
         this.empresaRepository.save(empresa);

         Cliente socioDefinitivo = this.clienteRepository.buscarPorNif(socio.getNif());
         Empresa empresaDefinitiva = this.empresaRepository.buscarEmpresaPorNombreRegistro(empresa.getNombre());

         socioDefinitivo.setEmpresaByEmpresaIdEmpresa(empresa);
         this.clienteRepository.save(socioDefinitivo);

         List<Cliente> socios = new ArrayList<>();
         socios.add(socio);
         empresa.setClientesByIdEmpresa(socios);
         this.empresaRepository.save(empresaDefinitiva);
         */


        //List<Empleado> gestores = this.gestorRepository.findAllGestores();
        //Empleado gestorMenosOcupado = this.gestorRepository.findById(3).orElse(null);
        Empleado gestorMenosOcupado = null;
        /*
        Empleado gestorMenosOcupado = null;
        int minimo = Integer.MAX_VALUE;
        for(Empleado gestor : gestores) {
            if(this.solicitudRepository.buscarSolicitudesPendientesDeUnGestor(gestor.getId()).size() < minimo) {
                gestorMenosOcupado = gestor;
                minimo = gestor.getSolicitudsById().size();
            }
        }
        */
        Rolcliente rol = this.rolClienteRepository.buscarRol("socio");
        socio.setRolclienteByRolclienteId(rol);
        this.clienteRepository.save(socio);

        Solicitud solicitud = new Solicitud();
        solicitud.setEstado("pendiente");
        solicitud.setClienteByClienteId(socio);
        solicitud.setEmpleadoByEmpleadoId(gestorMenosOcupado);
        solicitud.setTipo("registroEmpresa");

        return "login";
    }
}
