package es.taw.taw23.controller;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.Divisa;
import es.taw.taw23.entity.*;
import es.taw.taw23.service.EmpresaService;
import es.taw.taw23.ui.FiltroEmpresa;
import es.taw.taw23.ui.MovimientoCambioDivisa;
import es.taw.taw23.ui.MovimientoTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
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

        model.addAttribute("divisas", divisas);
        model.addAttribute("cambioDivisa", cambioDivisa);
        model.addAttribute("cuentas", cuentas);
        model.addAttribute("filtro", filtro);
        idClient = id;
        return procesarFiltrado(filtro, model, id);
    }

    @PostMapping("/filtrar")
    public String doFiltrar(@ModelAttribute("filtro") FiltroEmpresa filtro, Model model) {
        model.addAttribute("filtro", filtro);
        return procesarFiltrado(filtro, model, idClient);
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

        return "socios";
    }

    @PostMapping("/cambioDivisa")
    public String doCambioDivisa(@ModelAttribute("divisa") MovimientoCambioDivisa divisa, Model model) {
        this.empresaService.cambioDivisa(divisa);
        return "redirect:/empresa/?id=" + divisa.getIdCliente();
    }

    @GetMapping("/miEmpresa")
    public String doListarMiEmpresa(@RequestParam("id") Integer idCliente, Model model) {
        idClient = idCliente;
        FiltroEmpresa filtro = new FiltroEmpresa();
        model.addAttribute("filtro", filtro);
        return this.procesarFiltradoMiEmpresa(filtro, model, idCliente);
    }

    //ESTA HECHO REGULAR. DEBERIA DE HACERSE SIN idClient
    //NO ME DEJA PASAR EL idClient como parametro. Porque el formulario no es del Cliente??
    @PostMapping("/filtrarMiEmpresa")
    public String doFiltrarMiEmpresa(@ModelAttribute("filtro") FiltroEmpresa filtro, Model model) {
        model.addAttribute("filtro", filtro);
        return this.procesarFiltradoMiEmpresa(filtro, model, idClient);
    }

    protected String procesarFiltradoMiEmpresa(FiltroEmpresa filtro, Model model, Integer id) {
        List<Cliente> listaAsociado = null;
        Cliente cliente = this.empresaService.buscarCliente(id);
        model.addAttribute("cliente", cliente);

        //Filtro vacio, busco todos
        if(filtro.getPrimerNombre().isEmpty() && filtro.getPrimerApellido().isEmpty() && filtro.getNif().isEmpty()) {
            listaAsociado = this.empresaService.listarAsociadosDeMiEmpresa(cliente.getIdEmpresa());

            //Busco por primer apellido
        } else {
            listaAsociado = this.empresaService.listarAsociadosDeMiEmpresa(cliente.getIdEmpresa(), filtro.getNif(), filtro.getPrimerNombre(), filtro.getPrimerApellido());
        }

        model.addAttribute("asociados", listaAsociado);
        return "miEmpresa";
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
        EmpresaEntity empresa = this.empresaService.buscarEmpresa(asociado.getIdEmpresa());
        model.addAttribute("asociado", asociado);
        model.addAttribute("empresaEditada", empresa);
        idClient = idAsociado;
        return "editarEmpresa";
    }

    //ESTA HECHO REGULAR. DEBERIA DE HACERSE SIN idClient
    @PostMapping("/guardarEmpresa")
    public String doGuardarEmpresa(@ModelAttribute("empresaEditada") EmpresaEntity empresaForm) {
        EmpresaEntity empresaBD = this.empresaService.buscarEmpresa(empresaForm.getId());
        this.empresaService.guardarEmpresa(empresaForm, empresaBD);
        return "redirect:/empresa/?id=" + idClient;
    }

    /*
    Duda para el profe. Cómo irían las cuentas en el tema de la empresa?
    - Hay una cuenta de empresa que comparten todos los socios y asociados y aparte cada uno puede tener las cuentas propias
      que quiera (como los clientes de rol individual)? Si es asi cuando un socio bloquea a otro socio/autorizado, este
      bloquea el acceso a la cuenta de la empresa? No tiene poder sobre las cuentas individuales?
      */
    @PostMapping("/bloquear")
    public String doBloquear(@RequestParam("id") Integer id, @RequestParam("idCuenta") Integer idCuenta, Model model) {
        this.empresaService.bloquearCuenta(idCuenta);

        return "redirect:/empresa/?id=" + id;
    }


    @GetMapping("/transferencia")
    public String doTransferencia(@RequestParam("id") Integer idAsociado, Model model) {
        Cliente asociado = this.empresaService.buscarCliente(idAsociado);
        MovimientoTransferencia transferencia = new MovimientoTransferencia();
        List<Cuenta> cuentas = this.empresaService.buscarCuentasAsociado(idAsociado);

        idClient = idAsociado;

        model.addAttribute("asociado", asociado);
        model.addAttribute("transferencia", transferencia);
        model.addAttribute("cuentas", cuentas);

        return "transferencia";
    }

    //AHORA MISMO ESTA SIN EL CAMBIO DE DIVISA
    @PostMapping("/guardarTransferencia")
    public String doGuardarTransferencia(@ModelAttribute("transferencia") MovimientoTransferencia transferencia, Model model) {
        String urlto;
        String error = this.empresaService.transferencia(transferencia);

        if(error.equals("cuentaDestino")) {
            urlto = "errorTransferencia";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idClient);
        } else if(error.equals("cuentaOrigen")) {
            urlto = "errorTransferencia";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idClient);
        } else if(error.equals("dineroInsuficiente")) {
            urlto = "errorTransferencia";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idClient);
        } else {    //No hay errores en la transferencia
            urlto = "redirect:/empresa/?id=" + idClient;
        }
        return urlto;
    }


}
