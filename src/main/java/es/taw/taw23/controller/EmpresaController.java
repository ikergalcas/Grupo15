package es.taw.taw23.controller;

import es.taw.taw23.dto.*;
import es.taw.taw23.entity.*;
import es.taw.taw23.service.EmpresaService;
import es.taw.taw23.dto.Empresa;
import es.taw.taw23.ui.FiltroEmpresa;
import es.taw.taw23.ui.MovimientoCambioDivisa;
import es.taw.taw23.ui.MovimientoTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    private Integer idClient;

    @GetMapping("/")
    public String doListarClientesEmpresa(@RequestParam(value = "id", required = true) Integer id, Model model) {
        FiltroEmpresa filtro = new FiltroEmpresa();
        List<Cuenta> cuentas = this.empresaService.buscarCuentasAsociado(id);
        MovimientoCambioDivisa cambioDivisa = new MovimientoCambioDivisa();
        List<Divisa> divisas = this.empresaService.buscarDivisas();
        List<Empresa> empresas = this.empresaService.listarEmpresas();

        //Si estos booleanos son true significa que el asociado ya ha enviado una solicitud del tipo que sea especificado
        boolean solicitudDesbloqueoEnviada = this.empresaService.comprobarSolicitudDesbloqueoEnviada(id);
        boolean solicitudActivacionEnviada = this.empresaService.comprobarSolicitudActivacionEnviada(id);

        model.addAttribute("solicitudDesbloqueo", solicitudDesbloqueoEnviada);
        model.addAttribute("solicitudActivacion", solicitudActivacionEnviada);
        model.addAttribute("empresas", empresas);
        model.addAttribute("divisas", divisas);
        model.addAttribute("cambioDivisa", cambioDivisa);
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("filtro", filtro);
        return procesarFiltrado(filtro, model, id);
    }

    @GetMapping("/vistaEmpresa")
    public String doMostrarEmpresa(@RequestParam("idEmpresa") Integer idEmpresa, Model model) {
        Cliente asociado = new Cliente();
        Empresa empresa = this.empresaService.buscarEmpresa(idEmpresa);
        List<Cliente> asociados = this.empresaService.listarAsociadosDeMiEmpresa(idEmpresa);

        model.addAttribute("asociado", asociado);
        model.addAttribute("empresa", empresa);
        model.addAttribute("asociados", asociados);

        return "empresa";
    }

    @PostMapping("/darDeAlta")
    public String doDarDeAlta(@ModelAttribute("asociado") Cliente asociado, Model model) {
        this.empresaService.registrarAsociado(asociado);

        return "redirect:/?idEmpresa=" + asociado.getEmpresa();
    }

    @PostMapping("/filtrar")
    public String doFiltrar(@ModelAttribute("filtro") FiltroEmpresa filtro, @RequestParam("idAsociado") Integer idAsociado,Model model) {
        List<Cuenta> cuentas = this.empresaService.buscarCuentasAsociado(idAsociado);
        MovimientoCambioDivisa cambioDivisa = new MovimientoCambioDivisa();
        List<Divisa> divisas = this.empresaService.buscarDivisas();
        List<Empresa> empresas = this.empresaService.listarEmpresas();
        boolean solicitudDesbloqueoEnviada = this.empresaService.comprobarSolicitudDesbloqueoEnviada(idAsociado);
        boolean solicitudActivacionEnviada = this.empresaService.comprobarSolicitudActivacionEnviada(idAsociado);
        model.addAttribute("empresas", empresas);
        model.addAttribute("filtro", filtro);
        model.addAttribute("divisas", divisas);
        model.addAttribute("cambioDivisa", cambioDivisa);
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("solicitudDesbloqueo", solicitudDesbloqueoEnviada);
        model.addAttribute("solicitudActivacion", solicitudActivacionEnviada);
        return procesarFiltrado(filtro, model, idAsociado);
    }

    protected String procesarFiltrado(FiltroEmpresa filtro, Model model, Integer id) {
        List<Cliente> lista = new ArrayList<>();
        Cliente cliente = this.empresaService.buscarCliente(id);
        model.addAttribute("cliente", cliente);

        /**
         * No compruebo nada más porque sé que este objeto filtro solo tendra relleno el atributo nombre empresa.
         * Porque en el formulario que rellena este objeto filtro el resto de los campos los pongo como hidden.
         */
        if(filtro.getNombreEmpresa().isEmpty()) {
            lista = this.empresaService.listarAsociados();
        } else {
            lista = this.empresaService.listarAsociados(filtro.getNombreEmpresa());
        }

        model.addAttribute("asociados", lista);

        return "inicio";
    }

    @PostMapping("/cambioDivisa")
    public String doCambioDivisa(@ModelAttribute("divisa") MovimientoCambioDivisa divisa, @RequestParam("idAsociado") Integer idAsociado, Model model) {
        Cuenta cuenta = this.empresaService.buscarCuentaPorIBAN(divisa.getCuenta());
        if(!cuenta.getMoneda().equals(divisa.getDivisa())) {    //Hago esto en caso de que le den a cambiar divisa y hayan
            this.empresaService.cambioDivisa(divisa);           //elegido la que ya estaba
        }
        return "redirect:/empresa/?id=" + idAsociado;
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
        System.out.println(filtro.getNif());

        //Filtro vacio, busco todos
        if(filtro.getPrimerNombre().isEmpty() && filtro.getPrimerApellido().isEmpty() && filtro.getNif().isEmpty() && filtro.getPuesto().isEmpty()) {
            listaAsociado = this.empresaService.listarAsociadosDeMiEmpresa(cliente.getEmpresa());

            //Busco solo por el nif ya que este es unico
        } else if(!filtro.getNif().isEmpty()) {
            if(filtro.getNif().length() == 9) { //El nif esta bien introducido
                Cliente cliente1 = this.empresaService.buscarAsociadoPorNif(cliente.getEmpresa(), filtro.getNif());
                listaAsociado.add(cliente1);
            } else {    //No han introducido el nif entero, por lo que lo mando a la pagina de error
                urlto = "error";
                String error = "nifMal";
                model.addAttribute("error", error);
                model.addAttribute("idAsociado", id);
            }
            //Aplico el filtro, empresaService se encarga de ver que filtros estan rellenos
        } else {
            listaAsociado = this.empresaService.listarAsociadosDeMiEmpresa(cliente.getEmpresa(), filtro.getPrimerNombre(), filtro.getPrimerApellido(), filtro.getPuesto());
        }

        model.addAttribute("asociados", listaAsociado);
        return urlto;
    }


    @GetMapping("/miPerfil")
    public String doEditarPerfil(@RequestParam("id") Integer idCliente, Model model) {
        Cliente asociado = this.empresaService.buscarCliente(idCliente);
        model.addAttribute("asociadoEditado", asociado);
        idClient = idCliente;
        return "perfil";
    }

    @PostMapping("/guardarPerfil")
    public String doGuardarPerfil(@RequestParam("id") Integer idCliente, @ModelAttribute("asociadoEditado") Cliente editado) {
        this.empresaService.guardarAsociado(editado);

        idClient = idCliente;
        return "redirect:/empresa/?id=" + idCliente;
    }

    @GetMapping("/editarEmpresa")
    public String doEditarEmpresa(@RequestParam("id") Integer idAsociado, Model model) {
        Cliente asociado = this.empresaService.buscarCliente(idAsociado);
        Empresa empresa = this.empresaService.buscarEmpresa(asociado.getEmpresa());

        model.addAttribute("asociado", asociado);
        model.addAttribute("empresaEditada", empresa);
        return "editarEmpresa";
    }

    //ESTA HECHO REGULAR. DEBERIA DE HACERSE SIN idClient
    @PostMapping("/guardarEmpresa")
    public String doGuardarEmpresa(@ModelAttribute("empresaEditada") Empresa empresa, @RequestParam("idAsociado") Integer idAsociado) {
        this.empresaService.guardarEmpresa(empresa);
        return "redirect:/empresa/?id=" + idAsociado;
    }

    /*
    Duda para el profe. Cómo irían las cuentas en el tema de la empresa?
    - Hay una cuenta de empresa que comparten todos los socios y asociados y aparte cada uno puede tener las cuentas propias
      que quiera (como los clientes de rol individual)? Si es asi cuando un socio bloquea a otro socio/autorizado, este
      bloquea el acceso a la cuenta de la empresa? No tiene poder sobre las cuentas individuales?
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
        MovimientoTransferencia transferencia = new MovimientoTransferencia();
        List<Cuenta> cuentas = this.empresaService.buscarCuentasAsociadoActivas(idAsociado);

        model.addAttribute("asociado", asociado);
        model.addAttribute("transferencia", transferencia);
        model.addAttribute("cuentas", cuentas);

        return "transferencia";
    }

    //AHORA MISMO ESTA SIN EL CAMBIO DE DIVISA
    @PostMapping("/guardarTransferencia")
    public String doGuardarTransferencia(@ModelAttribute("transferencia") MovimientoTransferencia transferencia, @RequestParam("idAsociado") Integer idAsociado, Model model) {
        String urlto;
        String error = this.empresaService.transferencia(transferencia);

        if(error.equals("cuentaDestino")) {
            urlto = "error";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idAsociado);
        } else if(error.equals("dineroInsuficiente")) {
            urlto = "error";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idAsociado);
        } else {    //No hay errores en la transferencia
            urlto = "redirect:/empresa/?id=" + idAsociado;
        }
        return urlto;
    }

    @GetMapping("/misMovimientos")
    public String doVerMisMovimientos(@RequestParam("idCuenta") Integer idCuenta, @RequestParam("idAsociado") Integer idAsociado, Model model) {
        List<Movimiento> movimientosCuenta = this.empresaService.listarMovimientosCuenta(idCuenta);
        Cuenta cuenta = this.empresaService.buscarCuentaPorId(idCuenta);

        model.addAttribute("cuenta", cuenta);
        model.addAttribute("idAsociado", idAsociado);
        model.addAttribute("movimientos", movimientosCuenta);
        return "misMovimientos";
    }

    @GetMapping("/movimientosAsociado")
    public String doVerMovimientosAsociado(@RequestParam("idAsociado") Integer idAsociado, Model model) {
        Cliente asociado = this.empresaService.buscarCliente(idAsociado);

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
