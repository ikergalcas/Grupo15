package es.taw.taw23.controller;

import es.taw.taw23.dto.*;
import es.taw.taw23.service.ChatService;
import es.taw.taw23.service.EmpresaService;
import es.taw.taw23.dto.Empresa;
import es.taw.taw23.ui.FiltroEmpresa;
import es.taw.taw23.ui.MovimientoTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Hecho por: Álvaro Yuste Moreno
 */
@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    @Autowired
    ChatService chatService;

    /**
     * Vista de la pagina de inicio. Se muestran las cuentas del asociado y todos los asociados de todas las empresas
     * con un buscador por el nombre de las empresas.
     */
    @GetMapping("/")
    public String doListarClientesEmpresa(@RequestParam(value = "id", required = true) Integer id, Model model) {
        FiltroEmpresa filtro = new FiltroEmpresa();

        return procesarFiltrado(filtro, model, id);
    }

    @GetMapping("/{id}")
    public String doRedirect(@PathVariable("id") Integer id) {
        return "redirect:/empresa/?id=" + id;
    }

    @PostMapping("/filtrar")
    public String doFiltrar(@ModelAttribute("filtro") FiltroEmpresa filtro, @RequestParam("id") Integer id, Model model) {
        return procesarFiltrado(filtro, model, id);
    }

    protected String procesarFiltrado(FiltroEmpresa filtro, Model model, Integer id) {
        List<Cliente> lista = new ArrayList<>();
        Cliente cliente = this.empresaService.buscarCliente(id);
        List<Cuenta> cuentas = this.empresaService.buscarCuentasAsociado(id);
        Movimiento cambioDivisa = new Movimiento();
        List<Divisa> divisas = this.empresaService.buscarDivisas();
        List<Empresa> empresas = this.empresaService.listarEmpresas();


        //Si estos booleanos son true significa que el asociado ya ha enviado una solicitud del tipo que se especifica
        boolean solicitudDesbloqueoEnviada = this.empresaService.comprobarSolicitudDesbloqueoEnviada(id);
        boolean solicitudActivacionEnviada = this.empresaService.comprobarSolicitudActivacionEnviada(id);

        model.addAttribute("solicitudDesbloqueo", solicitudDesbloqueoEnviada);
        model.addAttribute("solicitudActivacion", solicitudActivacionEnviada);
        model.addAttribute("empresas", empresas);
        model.addAttribute("divisas", divisas);
        model.addAttribute("cambioDivisa", cambioDivisa);
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("filtro", filtro);
        model.addAttribute("cliente", cliente);
        model.addAttribute("chat", chatService.buscarChatCliente(id));
        /**
         * No compruebo nada más porque sé que este objeto filtro solo tendrá relleno el atributo nombre empresa.
         * Porque en el formulario que rellena este objeto filtro el resto de los campos los pongo como hidden.
         */
        if(filtro.getNombreEmpresa().isEmpty()) {
            lista = this.empresaService.listarAsociados();
        } else {
            lista = this.empresaService.listarAsociados(filtro.getNombreEmpresa());
        }

        model.addAttribute("asociados", lista);

        return "inicioAsociado";
    }

    @PostMapping("/cambioDivisa")
    public String doCambioDivisa(@ModelAttribute("divisa") Movimiento divisa, @RequestParam("idAsociado") Integer idAsociado, Model model) {
        Cuenta cuenta = this.empresaService.buscarCuentaPorIBAN(divisa.getCuentaOrigen());
        if(!cuenta.getMoneda().equals(divisa.getDivisaCuentaDestino())) {    //Hago esto en caso de que le den a cambiar divisa y hayan
            this.empresaService.cambioDivisa(divisa, idAsociado);           //elegido la que ya estaba
        }
        return "redirect:/empresa/?id=" + idAsociado;
    }

    @GetMapping("/vistaEmpresa")
    public String doMostrarEmpresa(@RequestParam("idEmpresa") Integer idEmpresa, Model model) {
        Cliente asociado = new Cliente();
        Empresa empresa = this.empresaService.buscarEmpresa(idEmpresa);
        List<Cliente> asociados = this.empresaService.listarAsociadosDeMiEmpresa(idEmpresa);
        Cuenta cuentaEmpresa = this.empresaService.buscarCuentaEmpresa(asociados.get(0).getCuentaList());

        model.addAttribute("cuentaEmpresa", cuentaEmpresa);
        model.addAttribute("asociado", asociado);
        model.addAttribute("empresa", empresa);
        model.addAttribute("asociados", asociados);

        return "vistaEmpresa";
    }

    @PostMapping("/darDeAlta")
    public String doDarDeAlta(@ModelAttribute("asociado") Cliente asociado, @RequestParam("idCuenta") Integer idCuenta, Model model) {
        this.empresaService.registrarAsociado(asociado, idCuenta);

        return "redirect:/empresa/vistaEmpresa?idEmpresa=" + asociado.getIdEmpresa();
    }

    @GetMapping("/miEmpresa")
    public String doListarMiEmpresa(@RequestParam("id") Integer idCliente, Model model) {
        FiltroEmpresa filtro = new FiltroEmpresa();
        model.addAttribute("filtro", filtro);
        return this.procesarFiltradoMiEmpresa(filtro, model, idCliente);
    }

    //ESTA HECHO REGULAR. DEBERIA DE HACERSE SIN idClient
    //NO ME DEJA PASAR EL idClient como parametro. Porque el formulario no es del Cliente??
    @PostMapping("/filtrarMiEmpresa")
    public String doFiltrarMiEmpresa(@ModelAttribute("filtro") FiltroEmpresa filtro, @RequestParam("idAsociado") Integer idAsociado, Model model) {
        model.addAttribute("filtro", filtro);
        return this.procesarFiltradoMiEmpresa(filtro, model, idAsociado);
    }

    protected String procesarFiltradoMiEmpresa(FiltroEmpresa filtro, Model model, Integer id) {
        String urlto = "miEmpresa";

        List<Cliente> listaAsociado = new ArrayList<>();
        Cliente cliente = this.empresaService.buscarCliente(id);
        model.addAttribute("cliente", cliente);

        //Filtro vacio, busco todos
        if(filtro.getPrimerNombre().isEmpty() && filtro.getPrimerApellido().isEmpty() && filtro.getNif().isEmpty() && filtro.getPuesto().isEmpty()) {
            listaAsociado = this.empresaService.listarAsociadosDeMiEmpresa(cliente.getIdEmpresa());

            //Busco solo por el nif ya que este es unico
        } else if(!filtro.getNif().isEmpty()) {
            Cliente cliente1 = this.empresaService.buscarAsociadoPorNif(cliente.getIdEmpresa(), filtro.getNif());
            if(cliente1 != null) {
                listaAsociado.add(cliente1);
            }

            //Aplico el filtro, empresaService se encarga de ver que filtros estan rellenos
        } else {
            listaAsociado = this.empresaService.listarAsociadosDeMiEmpresa(cliente.getIdEmpresa(), filtro.getPrimerNombre(), filtro.getPrimerApellido(), filtro.getPuesto());
        }

        model.addAttribute("asociados", listaAsociado);
        return urlto;
    }


    /**
     * Muestro los datos del perfil del socio/autorizado
     */
    @GetMapping("/miPerfil")
    public String doEditarPerfil(@RequestParam("id") Integer idCliente, Model model) {
        Cliente asociado = this.empresaService.buscarCliente(idCliente);
        model.addAttribute("asociadoEditado", asociado);
        return "perfilAsociado";
    }

    @PostMapping("/guardarPerfil")
    public String doGuardarPerfil(@RequestParam("id") Integer idCliente, @ModelAttribute("asociadoEditado") Cliente editado) {
        this.empresaService.guardarAsociado(editado);

        return "redirect:/empresa/?id=" + idCliente;
    }

    @GetMapping("/editarEmpresa")
    public String doEditarEmpresa(@RequestParam("id") Integer idAsociado, Model model) {
        Cliente asociado = this.empresaService.buscarCliente(idAsociado);
        Empresa empresa = this.empresaService.buscarEmpresa(asociado.getIdEmpresa());

        model.addAttribute("asociado", asociado);
        model.addAttribute("empresaEditada", empresa);
        return "editarEmpresa";
    }

    @PostMapping("/guardarEmpresa")
    public String doGuardarEmpresa(@ModelAttribute("empresaEditada") Empresa empresa, @RequestParam("idAsociado") Integer idAsociado) {
        this.empresaService.guardarEmpresa(empresa);
        return "redirect:/empresa/?id=" + idAsociado;
    }

    /**
     * Funciones para bloquear y permitir el acceso a la cuenta de empresa
     */
    @PostMapping("/bloquear")
    public String doBloquear(@RequestParam("id") Integer id, @RequestParam("idBloqueado") Integer idBloq, Model model) {
        this.empresaService.bloquear(idBloq);

        return "redirect:/empresa/miEmpresa?id=" + id;
    }

    @PostMapping("/desbloquear")
    public String doDesbloquear(@RequestParam("id") Integer id, @RequestParam("idBloqueado") Integer idBloq, Model model) {
        this.empresaService.desbloquear(idBloq);

        return "redirect:/empresa/miEmpresa?id=" + id;
    }

    @GetMapping("/transferencia")
    public String doTransferencia(@RequestParam("id") Integer idAsociado, Model model) {
        Cliente asociado = this.empresaService.buscarCliente(idAsociado);
        Movimiento transferencia = new Movimiento();
        List<Cuenta> cuentas = this.empresaService.buscarCuentasAsociadoActivas(idAsociado);
        List<Cuenta> cuentasDestino = this.empresaService.buscarCuentasTransferencia(cuentas);

        model.addAttribute("asociado", asociado);
        model.addAttribute("transferencia", transferencia);
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("cuentasDestino", cuentasDestino);

        return "transferencia";
    }

    @PostMapping("/guardarTransferencia")
    public String doGuardarTransferencia(@ModelAttribute("transferencia") Movimiento transferencia, @RequestParam("idAsociado") Integer idAsociado, Model model) {
        String urlto;
        String error = this.empresaService.transferencia(transferencia, idAsociado);

        if(error.equals("")) {      //No hay errores en la transferencia
            urlto = "redirect:/empresa/?id=" + idAsociado;
        } else {   //Hay error en la transferencia (la causa del error esta en la cadena "error"
            urlto = "errorTransferenciaEmpresa";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idAsociado);
        }
        return urlto;
    }

    /**
     * Muestro los movimientos de la cuenta de la empresa
     */
    @GetMapping("/misMovimientos")
    public String doVerMisMovimientos(@RequestParam("idCuenta") Integer idCuenta, @RequestParam("id") Integer id, Model model) {
        List<Movimiento> movimientosCuenta = this.empresaService.listarMovimientosCuenta(idCuenta);
        Cuenta cuenta = this.empresaService.buscarCuentaPorId(idCuenta);

        model.addAttribute("cuenta", cuenta);
        model.addAttribute("id", id);
        model.addAttribute("movimientos", movimientosCuenta);
        return "movimientosEmpresa";
    }

    /**
     * Muestro los movimientos que ha hecho un asociado con la cuenta de su empresa
     */
    @GetMapping("/movimientosAsociado")
    public String doVerMovimientosAsociado(@RequestParam("idAsociado") Integer idAsociado, Model model) {
        Movimiento filtro = new Movimiento(idAsociado);
        return procesarFiltradoMovimientos(filtro, new ArrayList<>(), new String(), model);
    }

    @PostMapping("/filtrarMovimiento")
    public String doFiltrarMovimientoEmpresa(@ModelAttribute("movimientoFiltro") Movimiento filtro, @RequestParam(value = "opciones[]", required = false) List<String> opciones, @RequestParam("orden") String orden, Model model) {
        return procesarFiltradoMovimientos(filtro, opciones, orden, model);
    }

    protected String procesarFiltradoMovimientos(Movimiento filtro, List<String> opciones, String orden, Model model) {
        List<Movimiento> movimientoList = new ArrayList<>();

        Cliente asociado = this.empresaService.buscarCliente(filtro.getAsociado());
        Empresa empresa = this.empresaService.buscarEmpresaAPartirDeCliente(asociado);
        Cuenta cuentaEmpresa = this.empresaService.buscarCuentaEmpresa(asociado.getCuentaList());
        Movimiento movimientoFiltro = new Movimiento();
        List<Divisa> monedas = this.empresaService.buscarDivisas();
        movimientoList = this.empresaService.buscarMovimientosAsociado(asociado.getId(), cuentaEmpresa.getId(), filtro.getTipo(), filtro.getCuentaDestino(), filtro.getDivisaCuentaOrigen(), filtro.getDivisaCuentaDestino(), filtro.getImporteOrigen(), filtro.getImporteDestino(), opciones, orden);

        model.addAttribute("movimientos", movimientoList);
        model.addAttribute("monedas", monedas);
        model.addAttribute("movimientoFiltro", movimientoFiltro);
        model.addAttribute("cuenta", cuentaEmpresa);
        model.addAttribute("empresa", empresa);
        model.addAttribute("asociado", asociado);

        return "movimientosAsociado";
    }

    @PostMapping("/solicitarDesbloqueo")
    public String doSolicitarDesbloqueo(@RequestParam("idAsociado") Integer idAsociado, Model model) {
        this.empresaService.solicitarDesbloqueo(idAsociado);

        return "redirect:/empresa/?id=" + idAsociado;
    }

    @PostMapping("/solicitarActivacion")
    public String doSolicitarActivacion(@RequestParam("idAsociado") Integer idAsociado, Model model) {
        this.empresaService.solicitarActivacion(idAsociado);

        return "redirect:/empresa/?id=" + idAsociado;
    }
}
