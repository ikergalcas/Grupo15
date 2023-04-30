package es.taw.taw23.controller;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.GestorRepository;
import es.taw.taw23.dto.*;
import es.taw.taw23.entity.EmpleadoEntity;
import es.taw.taw23.entity.SolicitudEntity;
import es.taw.taw23.service.*;
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

    @Autowired
    protected CuentaService cuentaService;

    @Autowired
    protected EmpresaService empresaService;

    @Autowired
    protected ClienteService clienteService;

    @Autowired
    protected MovimientoService movimientoService;

    @Autowired
    protected CuentaSospechosaService cuentaSospechosaService;

    @GetMapping("/{idGestor}")
    public String doPaginaPrincipalGestor(@PathVariable("idGestor") Integer id, Model model) {

        /*EmpleadoEntity gestor = gestorRepository.findGestorById(id);
        model.addAttribute("gestor",gestor);*/

        Empleado gestor = gestorService.BuscarGestor(id);
        model.addAttribute("gestor",gestor);
        return "paginaPrincipalGestor";
    }

    @GetMapping("/solicitudes/{idGestor}")
    public String doMostrarSolicitudes(@PathVariable("idGestor") Integer id, Model model) {
        Empleado gestor = gestorService.BuscarGestor(id);
        model.addAttribute("gestor",gestor);

        List<Solicitud> solicitudes = gestorService.buscarSolicitudesDeGestor(id);
        model.addAttribute("solicitudes",solicitudes);
        return "solicitudesGestor";
    }

    @GetMapping("/solicitudesResueltas/{idGestor}")
    public String doMostrarSolicitudesResueltas(@PathVariable("idGestor") Integer id, Model model) {
        Empleado gestor = gestorService.BuscarGestor(id);
        model.addAttribute("gestor",gestor);

        List<Solicitud> solicitudes = gestorService.buscarSolicitudesResueltasDeGestor(id);
        model.addAttribute("solicitudes",solicitudes);
        return "solicitudResueltasGestor";
    }

    @GetMapping("/verSolicitudAltaClienteIndividual/{idSolicitud}/{idGestor}")
    public String doVerSolicitudIndividual(@PathVariable("idSolicitud") Integer idSolicitud,
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
        return "verSolicitudAltaClienteIndividual";
    }

    @GetMapping("/verSolicitudAltaClienteEmpresa/{idSolicitud}/{idGestor}")
    public String doVerSolicitudEmpresa(@PathVariable("idSolicitud") Integer idSolicitud,
                                 @PathVariable("idGestor") Integer idGestor,
                                 Model model) {
        Solicitud solicitud = solicitudService.buscarSolicitud(idSolicitud);
        model.addAttribute("solicitud",solicitud);

        Empleado gestor = gestorService.BuscarGestor(idGestor);
        model.addAttribute("gestor",gestor);

        Cliente cliente = solicitudService.buscarClienteAPartirDeSolicitud(idSolicitud);
        Empresa empresa = empresaService.buscarEmpresaAPartirDeCliente(cliente);

        model.addAttribute("empresa",empresa);
        model.addAttribute("cliente",cliente);

        Cuenta cuenta = new Cuenta();
        model.addAttribute("cuenta",cuenta);
        return "verSolicitudAltaClienteEmpresa";
    }

    @PostMapping("/crearCuentaBancariaClienteIndividual")
    public String doCrearCuentaBancaria(@ModelAttribute("cuenta") Cuenta cuenta,
                                        Model model) {

        Solicitud solicitud = solicitudService.buscarSolicitud(cuenta.getIdSolicitud());
        Empleado gestor = gestorService.BuscarGestor(cuenta.getIdGestor());
        cuentaService.crearNuevaCuentaIndividual(cuenta);

        solicitudService.aprobarSolicitud(solicitud.getId());

        model.addAttribute("solicitud",solicitud);
        model.addAttribute("gestor",gestor);
        model.addAttribute("cuenta",cuenta);
        return "confirmacionAprobarSolicitudAltaIndividual";
    }

    @PostMapping("/crearCuentaBancariaClienteEmpresa")
    public String doCrearCuentaBancariaEmpresa(@ModelAttribute("cuenta") Cuenta cuenta,
                                        Model model) {

        Solicitud solicitud = solicitudService.buscarSolicitud(cuenta.getIdSolicitud());
        Empleado gestor = gestorService.BuscarGestor(cuenta.getIdGestor());
        Empresa empresa = empresaService.buscarEmpresaDevuelveDTO(cuenta.getIdEmpresa());

        cuentaService.crearNuevaCuentaEmpresa(cuenta);

        solicitudService.aprobarSolicitud(solicitud.getId());

        model.addAttribute("empresa",empresa);
        model.addAttribute("solicitud",solicitud);
        model.addAttribute("gestor",gestor);
        model.addAttribute("cuenta",cuenta);
        return "confirmacionAprobarSolicitudAltaEmpresa";
    }

    @GetMapping("/activarCuentaCliente/{idSolicitud}/{idGestor}")
    public String doActivarCuentaClienteInactiva(@PathVariable("idSolicitud") Integer idSolicitud,
                                          @PathVariable("idGestor") Integer idGestor) {
        Solicitud solicitud = solicitudService.buscarSolicitud(idSolicitud);
        Cliente cliente = solicitud.getCliente();
        Cuenta cuenta = cuentaService.buscarCuentaClienteInactivaAPartirDeCliente(cliente);
        cuentaService.activarCuentaInactiva(cuenta);
        solicitudService.aprobarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudes/?id=" + idGestor;
    }

    @GetMapping("/activarCuentaEmpresa/{idSolicitud}/{idGestor}")
    public String doActivarCuentaEmpresaInactiva(@PathVariable("idSolicitud") Integer idSolicitud,
                                          @PathVariable("idGestor") Integer idGestor) {
        Solicitud solicitud = solicitudService.buscarSolicitud(idSolicitud);
        Cliente cliente = solicitud.getCliente();
        Cuenta cuenta = cuentaService.buscarCuentaEmpresaInactivaAPartirDeCliente(cliente);
        cuentaService.activarCuentaInactiva(cuenta);
        solicitudService.aprobarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudes/?id=" + idGestor;
    }


    @GetMapping("/desbloquearCuentaCliente/{idSolicitud}/{idGestor}")
    public String doDesbloquearCuentaClienteBloqueada(@PathVariable("idSolicitud") Integer idSolicitud,
                                               @PathVariable("idGestor") Integer idGestor) {
        Solicitud solicitud = solicitudService.buscarSolicitud(idSolicitud);
        Cliente cliente = solicitud.getCliente();
        Cuenta cuenta = cuentaService.buscarCuentaClienteBloqueadaAPartirDeCliente(cliente);
        cuentaService.desbloquearCuentaBloqueada(cuenta);
        solicitudService.aprobarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudes/?id=" + idGestor;
    }

    @GetMapping("/desbloquearCuentaEmpresa/{idSolicitud}/{idGestor}")
    public String doDesbloquearCuentaEmpresaBloqueada(@PathVariable("idSolicitud") Integer idSolicitud,
                                               @PathVariable("idGestor") Integer idGestor) {
        Solicitud solicitud = solicitudService.buscarSolicitud(idSolicitud);
        Cliente cliente = solicitud.getCliente();
        Cuenta cuenta = cuentaService.buscarCuentaEmpresaBloqueadaAPartirDeCliente(cliente);
        cuentaService.desbloquearCuentaBloqueada(cuenta);
        solicitudService.aprobarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudes/?id=" + idGestor;
    }

    @GetMapping("/listadoDeClientes")
    public String doMostrarListadoDeClientes(Model model) {
        List<Cliente> clientes = clienteService.buscarTodosLosClientes();
        model.addAttribute("clientes",clientes);
        return "mostrarListadoClientes";
    }

    @GetMapping("/listadoDeEmpresas")
    public String doMostrarListadoDeEmpresas(Model model) {
        List<Empresa> empresas = empresaService.buscarEmpresas();
        model.addAttribute("empresas",empresas);
        return "mostrarListadoEmpresas";
    }

    @GetMapping("/verCliente/{idCliente}")
    public String doVerCliente(@PathVariable("idCliente") Integer idCliente, Model model) {
        Cliente cliente = clienteService.buscarClientePorId(idCliente);
        model.addAttribute("cliente",cliente);
        List<Cuenta> cuentas = cuentaService.buscarCuentasPorCliente(cliente);
        model.addAttribute("cuentas",cuentas);
        List<Movimiento> movimientosOrigen = cuentaService.recogerMovimientosOrigenDeCuentaDeUnCliente(cuentas);
        List<Movimiento> movimientosDestino = cuentaService.recogerMovimientosDestinoDeCuentaDeUnCliente(cuentas);
        List<Movimiento> movimientosASiMismo = movimientoService.recogerMovimientosASiMismoEnUnaCuenta(cuentas);
        List<Movimiento> movimientos = movimientoService.unirMovimientosOrigenDestino(movimientosOrigen,movimientosDestino);
        model.addAttribute("movimientos",movimientos);
        return "verCliente";
    }

    @GetMapping("/verEmpresa/{idEmpresa}")
    public String doVerEmpresa(@PathVariable("idEmpresa") Integer idEmpresa, Model model) {
        Empresa empresa = empresaService.buscarEmpresaDevuelveDTO(idEmpresa);
        List<Cliente> clientes = empresa.getListaClientes();
        List<Cuenta> cuentasEmpresa = cuentaService.buscarCuentasDeEmpresaPorClientes(clientes);
        List<Movimiento> movimientosOrigen = cuentaService.recogerMovimientosOrigenDeCuentaDeUnCliente(cuentasEmpresa);
        List<Movimiento> movimientosDestino = cuentaService.recogerMovimientosDestinoDeCuentaDeUnCliente(cuentasEmpresa);
        List<Movimiento> movimientos = movimientoService.unirMovimientosOrigenDestino(movimientosOrigen,movimientosDestino);
        model.addAttribute("movimientos",movimientos);
        model.addAttribute("empresa",empresa);
        return "verEmpresa";
    }

    @GetMapping("/listadoCuentasSospechosas")
    public String doMostrarListadoCuentasSospechosas(Model model) {
        List<CuentaSospechosa> cuentasSospechosas = cuentaSospechosaService.obtenerCuentasSospechosas();
        model.addAttribute("cuentasSospechosas",cuentasSospechosas);
        return "verListadoCuentasSospechosas";
    }

    @GetMapping("/verInfoCuenta/{idCuenta}")
    public String doMostrarInfoCuenta(@PathVariable("idCuenta") Integer idCuenta, Model model) {
        Cuenta cuenta = cuentaService.buscarCuentaPorId(idCuenta);
        model.addAttribute("cuenta",cuenta);
        List<CuentaSospechosa> cuentasSospechosas = cuentaSospechosaService.obtenerCuentasSospechosas();
        model.addAttribute("cuentasSospechosas",cuentasSospechosas);
        return "verInfoCuenta";
    }

    @GetMapping("/anadirACuentasSospechosas/{idCuenta}")
    public String doAnadirACuentaSospechosa(@PathVariable("idCuenta") Integer idCuenta, Model model) {
        Cuenta cuenta = cuentaService.buscarCuentaPorId(idCuenta);
        cuentaSospechosaService.anadirCuentaACuentasSospechosas(cuenta);
        return "redirect:/gestor/verInfoCuenta/" + idCuenta;
    }

    @GetMapping("/quitarDeCuentasSospechosas/{idCuenta}")
    public String doQuitarDeCuentasSospechosas(@PathVariable("idCuenta") Integer idCuenta, Model model) {
        Cuenta cuenta = cuentaService.buscarCuentaPorId(idCuenta);
        cuentaSospechosaService.quitarCuentaDeCuentasSospechosas(cuenta);
        return "redirect:/gestor/verInfoCuenta/" + idCuenta;
    }

}
