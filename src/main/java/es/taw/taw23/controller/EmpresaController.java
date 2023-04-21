package es.taw.taw23.controller;

import es.taw.taw23.dao.*;
import es.taw.taw23.entity.*;
import es.taw.taw23.ui.FiltroEmpresa;
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
    protected TipoMovimientoRepository tipoMovimientoRepository;
    @Autowired
    protected MovimientoRepository movimientoRepository;
    @Autowired
    protected EmpresaRepository empresaRepository;
    @Autowired
    protected EstadoCuentaRepository estadoCuentaRepository;
    @Autowired
    protected AsociadoRepository asociadoRepository;

    @Autowired
    protected CuentaRepository cuentaRepository;

    private Integer idClient;

    @GetMapping("/")
    public String doListarClientesEmpresa(@RequestParam(value = "id", required = true) Integer id, Model model) {
        FiltroEmpresa filtro = new FiltroEmpresa();
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
        Cliente cliente = this.asociadoRepository.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);

        /**
         * No compruebo nada más porque sé que este objeto filtro solo tendra relleno el atributo nombre empresa.
         * Porque en el formulario que rellena este objeto filtro el resto de los campos los pongo como hidden.
         */
        if(filtro.getNombreEmpresa().isEmpty()) {
            lista = this.asociadoRepository.buscarPorTipoEmpresa();
        } else {
            lista = this.asociadoRepository.buscarPorEmpresa(filtro.getNombreEmpresa());
        }

        model.addAttribute("asociados", lista);

        return "socios";
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
        Cliente cliente = this.asociadoRepository.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);

        //Filtro vacio, busco todos
        if(filtro.getPrimerNombre().isEmpty() && filtro.getPrimerApellido().isEmpty() && filtro.getNif().isEmpty()) {
            listaAsociado = this.asociadoRepository.buscarSociosAutorizadosDeMiEmpresa(cliente.getEmpresaByEmpresaIdEmpresa().getIdEmpresa());

            //Busco por primer apellido
        } else if(filtro.getPrimerNombre().isEmpty() && filtro.getNif().isEmpty()) {
            listaAsociado = this.asociadoRepository.buscarPorPrimerApellido(cliente.getEmpresaByEmpresaIdEmpresa().getIdEmpresa(), filtro.getPrimerApellido());

            //Busco por el primer nombre
        } else if(filtro.getNif().isEmpty() && filtro.getPrimerApellido().isEmpty()){
            listaAsociado = this.asociadoRepository.buscarPorPrimerNombre(cliente.getEmpresaByEmpresaIdEmpresa().getIdEmpresa(), filtro.getPrimerNombre());

            //Busco por el nif
        } else if(filtro.getPrimerApellido().isEmpty() && filtro.getPrimerNombre().isEmpty()) {
            listaAsociado = this.asociadoRepository.buscarPorNif(cliente.getEmpresaByEmpresaIdEmpresa().getIdEmpresa(), filtro.getNif());

            //Busco por el primero nombre y primer apellido
        } else if(filtro.getNif().isEmpty()) {
            listaAsociado = this.asociadoRepository.buscarPorPrimerNombreyPrimerApellido(cliente.getEmpresaByEmpresaIdEmpresa().getIdEmpresa(), filtro.getPrimerNombre(), filtro.getPrimerApellido());

            //Busco por el primer nombre y el nif
        }   else if(filtro.getPrimerApellido().isEmpty()) {
            listaAsociado = this.asociadoRepository.buscarPorNifyPrimerNombre(cliente.getEmpresaByEmpresaIdEmpresa().getIdEmpresa(), filtro.getNif(), filtro.getPrimerNombre());

            //Busco por el primer apellido y el nif
        } else {
            listaAsociado = this.asociadoRepository.buscarPorNifyPrimerApellido(cliente.getEmpresaByEmpresaIdEmpresa().getIdEmpresa(), filtro.getNif(), filtro.getPrimerApellido());
        }

        model.addAttribute("asociados", listaAsociado);
        return "miEmpresa";
    }


    @GetMapping("/miPerfil")
    public String doEditarPerfil(@RequestParam("id") Integer idCliente, Model model) {
        Cliente asociado = this.asociadoRepository.findById(idCliente).orElse(null);
        model.addAttribute("asociadoEditado", asociado);
        idClient = idCliente;
        return "perfil";
    }

    @PostMapping("/guardarPerfil")
    public String doGuardarPerfil(@RequestParam("id") Integer idCliente, @ModelAttribute("asociadoEditado") Cliente asociado) {
        Cliente editado = this.asociadoRepository.findById(idCliente).orElse(null);
        asociado.setRolclienteByRolclienteId(editado.getRolclienteByRolclienteId());
        asociado.setEmpresaByEmpresaIdEmpresa(editado.getEmpresaByEmpresaIdEmpresa());
        asociado.setCuentasById(editado.getCuentasById());
        asociado.setChatsById(editado.getChatsById());
        asociado.setSolicitudsById(editado.getSolicitudsById());
        this.asociadoRepository.save(asociado);
        idClient = idCliente;
        return "redirect:/empresa/?id=" + idCliente;
    }

    @GetMapping("/editarEmpresa")
    public String doEditarEmpresa(@RequestParam("id") Integer idAsociado, Model model) {
        Cliente asociado = this.asociadoRepository.findById(idAsociado).orElse(null);
        Empresa empresa = asociado.getEmpresaByEmpresaIdEmpresa();
        model.addAttribute("asociado", asociado);
        model.addAttribute("empresaEditada", empresa);
        idClient = idAsociado;
        return "editarEmpresa";
    }

    //ESTA HECHO REGULAR. DEBERIA DE HACERSE SIN idClient
    @PostMapping("/guardarEmpresa")
    public String doGuardarEmpresa(@RequestParam("idEmpresa") Integer idEmpresa, @ModelAttribute("empresaEditada") Empresa empresa) {
        Empresa emp = this.empresaRepository.findById(idEmpresa).orElse(null);
        empresa.setClientesByIdEmpresa(emp.getClientesByIdEmpresa());
        this.empresaRepository.save(empresa);
        return "redirect:/empresa/?id=" + idClient;
    }


    @PostMapping("/bloquear")
    public String doBloquear(@RequestParam("idBloq") Integer idBloqueado,@RequestParam("id") Integer id, Model model) {
        Cliente bloqueado = this.asociadoRepository.findById(idBloqueado).orElse(null);
        List<Cuenta> cuentas = bloqueado.getCuentasById();
        Estadocuenta estadoBloqueado = this.estadoCuentaRepository.findById(2).orElse(null);
        //Como el cliente puede tener 2 cuentas, busco cuál es la de la empresa y cambio el estado a bloqueada
        if(cuentas.get(0).getTipocuentaByTipoCuentaId().getTipo().equals("empresa")) {
            cuentas.get(0).setEstadocuentaByEstadoCuentaId(estadoBloqueado);
            cuentaRepository.save(cuentas.get(0));
        } else {
            cuentas.get(1).setEstadocuentaByEstadoCuentaId(estadoBloqueado);
            cuentaRepository.save(cuentas.get(1));
        }

        //Actualizo la lista de cuentas del cliente bloqueado
        bloqueado.setCuentasById(cuentas);
        asociadoRepository.save(bloqueado);

        return "redirect:/empresa/?id=" + id;
    }

    @GetMapping("/transferencia")
    public String doTransferencia(@RequestParam("id") Integer idAsociado, Model model) {
        Cliente asociado = this.asociadoRepository.findById(idAsociado).orElse(null);
        MovimientoTransferencia transferencia = new MovimientoTransferencia();

        idClient = idAsociado;

        model.addAttribute("transferencia", transferencia);
        model.addAttribute("asociado", asociado);
        return "transferencia";
    }

    @PostMapping("/guardarTransferencia")
    public String doGuardarTransferencia(@ModelAttribute("transferencia") MovimientoTransferencia transferencia, @RequestParam("numeroCuenta") String numeroCuentaDest, Model model) {
        String urlto;
        String error;
        Cuenta cuentaDest = this.cuentaRepository.buscarCuentaPorNumeroCuenta(numeroCuentaDest);
        Cuenta cuentaOrig = transferencia.getCuenta();
        Date fecha = new Date();

        Movimientos movimientoOrigen = new Movimientos();
        movimientoOrigen.setTipomovimientoByTipoMovimientoId(this.tipoMovimientoRepository.findById(1).orElse(null));
        movimientoOrigen.setCuentaByCuentaIdCuenta(cuentaOrig);
        movimientoOrigen.setImporteOrigen(-transferencia.getImporte());
        movimientoOrigen.setImporteDestino(transferencia.getImporte());
        movimientoOrigen.setTimeStamp(fecha.toString());
        movimientoOrigen.setMonedaOrigen("Euro");

        Movimientos movimientoDestino = new Movimientos();
        movimientoDestino.setTipomovimientoByTipoMovimientoId(this.tipoMovimientoRepository.findById(1).orElse(null));
        movimientoDestino.setCuentaByCuentaIdCuenta(cuentaDest);
        movimientoDestino.setImporteOrigen(-transferencia.getImporte());
        movimientoDestino.setImporteDestino(transferencia.getImporte());
        movimientoDestino.setTimeStamp(fecha.toString());
        movimientoDestino.setMonedaOrigen("Euro");

        if(cuentaDest == null) {
            error = "cuentaDestino";
            urlto = "errorTransferencia";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idClient);
        } else if(cuentaOrig == null) {
            error = "cuentaOrigen";
            urlto = "errorTransferencia";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idClient);
        } else if(cuentaOrig.getDinero() < transferencia.getImporte()) {
            error = "dineroInsuficiente";
            urlto = "errorTransferencia";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idClient);
        } else {    //No hay errores en la transferencia
            cuentaOrig.setDinero(cuentaOrig.getDinero() - transferencia.getImporte());
            cuentaDest.setDinero(cuentaDest.getDinero() + transferencia.getImporte());
            this.movimientoRepository.save(movimientoOrigen);
            this.movimientoRepository.save(movimientoDestino);
            this.cuentaRepository.save(cuentaOrig);
            this.cuentaRepository.save(cuentaDest);
            urlto = "redirect:/empresa/?id=" + idClient;
        }
        return urlto;
    }


}
