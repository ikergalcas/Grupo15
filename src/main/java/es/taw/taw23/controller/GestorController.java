package es.taw.taw23.controller;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.GestorRepository;
import es.taw.taw23.dto.*;
import es.taw.taw23.entity.EmpleadoEntity;
import es.taw.taw23.entity.SolicitudEntity;
import es.taw.taw23.service.*;
import es.taw.taw23.ui.FiltroClienteNif;
import es.taw.taw23.ui.FiltroEmpresaCif;
import es.taw.taw23.ui.FiltroMovimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gestor")
public class GestorController {
// Carla Serracant Guevara

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
    protected MovimientosService movimientoService;

    @Autowired
    protected CuentaSospechosaService cuentaSospechosaService;


    @GetMapping("/{idGestor}")
    public String doPaginaPrincipalGestor(@PathVariable("idGestor") Integer id, Model model) {

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

    @GetMapping("/borrarSolicitudAprobada/{idGestor}/{idSolicitud}")
    public String doBorrarSolicitudAprobada(@PathVariable("idGestor") Integer idGestor,
                                            @PathVariable("idSolicitud") Integer idSolicitud,
                                            Model model) {
        solicitudService.borrarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudesResueltas/" + idGestor;
    }

    @GetMapping("/borrarSolicitudRechazada/{idGestor}/{idSolicitud}")
    public String doBorrarSolicitudRechazada(@PathVariable("idGestor") Integer idGestor,
                                             @PathVariable("idSolicitud") Integer idSolicitud) {
        solicitudService.borrarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudesRechazadas/" + idGestor;
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

        return "redirect:/gestor/solicitudes/"+gestor.getId();
    }

    @PostMapping("/crearCuentaBancariaClienteEmpresa")
    public String doCrearCuentaBancariaEmpresa(@ModelAttribute("cuenta") Cuenta cuenta, @RequestParam("numCuentaSocio") String num,
                                        Model model) {

        Solicitud solicitud = solicitudService.buscarSolicitud(cuenta.getIdSolicitud());
        Empleado gestor = gestorService.BuscarGestor(cuenta.getIdGestor());

        cuentaService.crearNuevaCuentaEmpresa(cuenta);

        cuenta.setNumeroCuenta(num);
        cuentaService.crearNuevaCuentaIndividual(cuenta);

        solicitudService.aprobarSolicitud(solicitud.getId());

        return "redirect:/gestor/solicitudes/" + gestor.getId();
    }


    @GetMapping("/activarCuentaCliente/{idSolicitud}/{idGestor}")
    public String doActivarCuentaClienteInactiva(@PathVariable("idSolicitud") Integer idSolicitud,
                                          @PathVariable("idGestor") Integer idGestor) {
        Solicitud solicitud = solicitudService.buscarSolicitud(idSolicitud);
        Cliente cliente = solicitud.getCliente();
        Cuenta cuenta = cuentaService.buscarCuentaClienteInactivaAPartirDeCliente(cliente);
        cuentaService.activarCuentaInactiva(cuenta);
        solicitudService.aprobarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudes/" + idGestor;
    }

    @GetMapping("/activarCuentaEmpresa/{idSolicitud}/{idGestor}")
    public String doActivarCuentaEmpresaInactiva(@PathVariable("idSolicitud") Integer idSolicitud,
                                          @PathVariable("idGestor") Integer idGestor) {
        Solicitud solicitud = solicitudService.buscarSolicitud(idSolicitud);
        Cliente cliente = solicitud.getCliente();
        Cuenta cuenta = cuentaService.buscarCuentaEmpresaInactivaAPartirDeCliente(cliente);
        cuentaService.activarCuentaInactiva(cuenta);
        solicitudService.aprobarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudes/" + idGestor;
    }


    @GetMapping("/desbloquearCuentaCliente/{idSolicitud}/{idGestor}")
    public String doDesbloquearCuentaClienteBloqueada(@PathVariable("idSolicitud") Integer idSolicitud,
                                               @PathVariable("idGestor") Integer idGestor) {
        Solicitud solicitud = solicitudService.buscarSolicitud(idSolicitud);
        Cliente cliente = solicitud.getCliente();
        Cuenta cuenta = cuentaService.buscarCuentaClienteBloqueadaAPartirDeCliente(cliente);
        cuentaService.desbloquearCuentaBloqueada(cuenta);
        solicitudService.aprobarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudes/" + idGestor;
    }

    @GetMapping("/desbloquearCuentaEmpresa/{idSolicitud}/{idGestor}")
    public String doDesbloquearCuentaEmpresaBloqueada(@PathVariable("idSolicitud") Integer idSolicitud,
                                               @PathVariable("idGestor") Integer idGestor) {
        Solicitud solicitud = solicitudService.buscarSolicitud(idSolicitud);
        Cliente cliente = solicitud.getCliente();
        Cuenta cuenta = cuentaService.buscarCuentaEmpresaBloqueadaAPartirDeCliente(cliente);
        cuentaService.desbloquearCuentaBloqueada(cuenta);
        solicitudService.aprobarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudes/" + idGestor;
    }

    @GetMapping("/listadoDeClientes/{idGestor}")
    public String doMostrarListadoDeClientes(@PathVariable("idGestor") Integer idGestor, Model model) {
        List<Cliente> clientes = clienteService.buscarTodosLosClientes();
        model.addAttribute("clientes",clientes);
        model.addAttribute("idGestor",idGestor);
        FiltroClienteNif filtro = new FiltroClienteNif();
        model.addAttribute("filtro",filtro);
        return "mostrarListadoClientes";
    }

    @PostMapping("/listadoDeClientes/buscarPorNif")
    public String doMostrarListadoDeClientesPorNif(@ModelAttribute("filtro") FiltroClienteNif filtro, Model model) {
        List<Cliente> clientes;
        if (filtro.getNif().isEmpty()) {
            clientes = clienteService.buscarTodosLosClientes();
            filtro.setNif("");
        } else {
            clientes = clienteService.buscarClientePorFiltroNif(filtro.getNif());
        }

        model.addAttribute("clientes",clientes);
        model.addAttribute("idGestor",filtro.getIdGestor());
        model.addAttribute("filtro",filtro);
        return "mostrarListadoClientes";
    }

    @GetMapping("/listadoDeEmpresas/{idGestor}")
    public String doMostrarListadoDeEmpresas(@PathVariable("idGestor") Integer idGestor, Model model) {
        List<Empresa> empresas = empresaService.buscarEmpresas();
        FiltroEmpresaCif filtro = new FiltroEmpresaCif();
        model.addAttribute("filtro",filtro);
        model.addAttribute("empresas",empresas);
        model.addAttribute("idGestor",idGestor);
        return "mostrarListadoEmpresas";
    }

    @PostMapping("/listaDeEmpresas/Ordenado")
    public String doMostrarListadoDeEmpresasPorCif(@ModelAttribute("filtro") FiltroEmpresaCif filtro, Model model) {
        List<Empresa> empresas;
        if (filtro.getCif().isEmpty()) {
            empresas = empresaService.buscarEmpresas();
        } else {
            empresas = empresaService.buscarEmpresaPorCif(filtro.getCif());
        }

        model.addAttribute("empresas",empresas);
        model.addAttribute("idGestor",filtro.getIdGestor());
        model.addAttribute("filtro",filtro);
        return "mostrarListadoEmpresas";
    }

    @GetMapping("/verCliente/{idCliente}/{idGestor}")
    public String doVerCliente(@PathVariable("idCliente") Integer idCliente,
                               @PathVariable("idGestor") Integer idGestor,
                               Model model) {
        Cliente cliente = clienteService.buscarClientePorId(idCliente);
        model.addAttribute("cliente",cliente);

        List<Cuenta> cuentas = cuentaService.buscarCuentasPorCliente(cliente);
        List<Movimiento> movimientos = movimientoService.encuentraMovimientos(cuentas);

        model.addAttribute("movimientos",movimientos);
        model.addAttribute("idGestor",idGestor);

        List<String> tiposDeMovimientos = movimientoService.encuentraTiposDeMovimientos();
        model.addAttribute("tiposDeMovimientos",tiposDeMovimientos);

        FiltroMovimiento filtro = new FiltroMovimiento();
        model.addAttribute("filtro",filtro);

        return "verCliente";
    }

    @PostMapping("/verCliente/Ordenado")
    public String doVerClienteOrdenado(@ModelAttribute("filtro") FiltroMovimiento filtro, Model model) {
        List<Movimiento> movimientos = new ArrayList<>();
        Cliente cliente = clienteService.buscarClientePorId(filtro.getIdCliente());
        List<Cuenta> cuentas = cuentaService.buscarCuentasPorCliente(cliente);

        if (filtro.getTipoMovimiento().equals("NONE") && !filtro.isFecha()) {
            movimientos = movimientoService.encuentraMovimientos(cuentas);
        } else if (!filtro.isFecha()){
            movimientos = movimientoService.encuentraMovimientoPorTipoYIdCuenta(filtro.getTipoMovimiento(),cuentas);
        } else if (filtro.getTipoMovimiento().equals("NONE") && filtro.isFecha()) {
            movimientos = movimientoService.encuentraMovimientosRecientes(cuentas);
        } else if (filtro.isFecha()) {
            movimientos = movimientoService.encuentraMovimientosRecientesYConTipo(cuentas,filtro.getTipoMovimiento());
        }

        List<String> tiposDeMovimientos = movimientoService.encuentraTiposDeMovimientos();
        model.addAttribute("tiposDeMovimientos",tiposDeMovimientos);
        model.addAttribute("movimientos",movimientos);
        model.addAttribute("cliente",cliente);
        model.addAttribute("idGestor",filtro.getIdGestor());

        return "verCliente";
    }

    @GetMapping("/verEmpresa/{idEmpresa}/{idGestor}")
    public String doVerEmpresa(@PathVariable("idEmpresa") Integer idEmpresa,
                               @PathVariable("idGestor") Integer idGestor, Model model) {
        Empresa empresa = empresaService.buscarEmpresaDevuelveDTO(idEmpresa);
        List<Cliente> clientes = empresa.getListaClientes();
        List<Cuenta> cuentasEmpresa = cuentaService.buscarCuentasDeEmpresaPorClientes(clientes);

        List<Movimiento> movimientos = movimientoService.encuentraMovimientos(cuentasEmpresa);
        model.addAttribute("movimientos",movimientos);
        model.addAttribute("empresa",empresa);
        model.addAttribute("idGestor",idGestor);
        List<String> tiposDeMovimientos = movimientoService.encuentraTiposDeMovimientos();
        model.addAttribute("tiposDeMovimientos",tiposDeMovimientos);
        FiltroMovimiento filtro = new FiltroMovimiento();
        model.addAttribute("filtro",filtro);

        return "verEmpresa";
    }

    @PostMapping("/verEmpresa/Ordenado")
    public String doVerEmpresaOrdenado(@ModelAttribute("filtro") FiltroMovimiento filtro, Model model) {
        List<Movimiento> movimientos = new ArrayList<>();
        Empresa empresa = empresaService.buscarEmpresaDevuelveDTO(filtro.getIdEmpresa());
        List<Cliente> clientes = empresa.getListaClientes();
        List<Cuenta> cuentasEmpresa = cuentaService.buscarCuentasDeEmpresaPorClientes(clientes);

        if (filtro.getTipoMovimiento().equals("NONE") && !filtro.isFecha()) {
            movimientos = movimientoService.encuentraMovimientos(cuentasEmpresa);
        } else if (!filtro.isFecha()){
            movimientos = movimientoService.encuentraMovimientoPorTipoYIdCuenta(filtro.getTipoMovimiento(),cuentasEmpresa);
        } else if (filtro.getTipoMovimiento().equals("NONE") && filtro.isFecha()) {
            movimientos = movimientoService.encuentraMovimientosRecientes(cuentasEmpresa);
        } else if (filtro.isFecha()) {
            movimientos = movimientoService.encuentraMovimientosRecientesYConTipo(cuentasEmpresa,filtro.getTipoMovimiento());
        }

        List<String> tiposDeMovimientos = movimientoService.encuentraTiposDeMovimientos();
        model.addAttribute("tiposDeMovimientos",tiposDeMovimientos);
        model.addAttribute("movimientos",movimientos);
        model.addAttribute("idGestor",filtro.getIdGestor());
        model.addAttribute("empresa",empresa);
        model.addAttribute("filtro",filtro);
        return "verEmpresa";
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

    @GetMapping("/bloquearCuenta/{idCuenta}")
    public String doBloquearCuenta(@PathVariable("idCuenta") Integer idCuenta) {
        cuentaService.bloquearCuenta(idCuenta);
        return "redirect:/gestor/verInfoCuenta/" + idCuenta;
    }

    @GetMapping("/desbloquearCuentaSinSolicitud/{idCuenta}")
    public String doDesbloquearCuentaSinSolicitud(@PathVariable("idCuenta") Integer idCuenta) {
        Cuenta cuenta = cuentaService.buscarCuentaPorId(idCuenta);
        cuentaService.desbloquearCuentaBloqueada(cuenta);
        return "redirect:/gestor/verInfoCuenta/" + idCuenta;
    }

    @GetMapping("/desactivarCuenta/{idCuenta}")
    public String doDesactivarCuenta(@PathVariable("idCuenta") Integer idCuenta) {
        cuentaService.desactivarCuenta(idCuenta);
        return "redirect:/gestor/verInfoCuenta/" + idCuenta;
    }

    @GetMapping("/activarCuentaSinSolicitud/{idCuenta}")
    public String doActivarCuentaSinSolicitud(@PathVariable("idCuenta") Integer idCuenta) {
        Cuenta cuenta = cuentaService.buscarCuentaPorId(idCuenta);
        cuentaService.activarCuentaInactiva(cuenta);
        return "redirect:/gestor/verInfoCuenta/" + idCuenta;
    }

    @GetMapping("/rechazarSolicitud/{idSolicitud}/{idGestor}")
    public String doRechazarSolicitud(@PathVariable("idSolicitud") Integer idSolicitud,
                                      @PathVariable("idGestor") Integer idGestor) {
        solicitudService.rechazarSolicitud(idSolicitud);
        return "redirect:/gestor/solicitudes/" + idGestor;
    }

    @GetMapping("/solicitudesRechazadas/{idGestor}")
    public String doMostrarSolicitudesRechazadas(@PathVariable("idGestor") Integer idGestor, Model model) {
        Empleado gestor = gestorService.BuscarGestor(idGestor);
        model.addAttribute("gestor",gestor);
        List<Solicitud> solicitudes = gestorService.buscarSolicitudesRechazadasDeGestor(idGestor);
        model.addAttribute("solicitudes",solicitudes);
        return "solicitudesRechazadas";
    }

    @GetMapping("/listadoDeTransferenciasACuentasSospechosas/{idGestor}")
    public String doMostrarTransferenciasACuentasSospechosas(@PathVariable("idGestor") Integer idGestor, Model model) {
        List<Movimiento> movimientosSospechosos = movimientoService.encuentraMovimientosACuentasSospechosas();
        model.addAttribute("movimientos",movimientosSospechosos);
        model.addAttribute("idGestor",idGestor);
        return "ListadoDeTransferenciasACuentasSospechosas";
    }

    @GetMapping("/listadoCuentasSospechosas/{idGestor}")
    public String doMostrarListadoCuentasSospechosas(@PathVariable("idGestor") Integer idGestor, Model model) {
        List<CuentaSospechosa> cuentasSospechosas = cuentaSospechosaService.obtenerCuentasSospechosas();
        model.addAttribute("cuentasSospechosas",cuentasSospechosas);
        model.addAttribute("idGestor",idGestor);
        return "verListadoCuentasSospechosas";
    }

    @GetMapping("/listadoCuentasSinOperacionesElUltimoMes/{idGestor}")
    public String doMostrarListadoDeCuentasSinActividad(@PathVariable("idGestor") Integer idGestor, Model model) {
        List<Cuenta> cuentas = cuentaService.encontrarCuentasSinActividad();
        model.addAttribute("cuentas",cuentas);
        model.addAttribute("idGestor",idGestor);
        return "ListadoCuentasSinOperacionesElUltimoMes";
    }

}
