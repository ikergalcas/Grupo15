package es.taw.taw23.controller;

import es.taw.taw23.dao.*;
import es.taw.taw23.entity.*;
import es.taw.taw23.ui.FiltroEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<Cliente> lista = this.asociadoRepository.buscarPorTipoEmpresa();
        Cliente cliente = this.asociadoRepository.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);
        model.addAttribute("socios", lista);
        return "socios";
    }

    @GetMapping("/miEmpresa")
    public String doListarMiEmpresa(@RequestParam("id") Integer idCliente, Model model) {
        idClient = idCliente;
        FiltroEmpresa filtro = new FiltroEmpresa();
        model.addAttribute("filtro", filtro);
        return this.procesarFiltrado(filtro, model, idCliente);
    }

    //ESTA HECHO REGULAR. DEBERIA DE HACERSE SIN idClient
    //NO ME DEJA PASAR EL idClient como parametro. Porque el formulario no es del Cliente??
    @PostMapping("/filtrar")
    public String doFiltrar(@ModelAttribute("filtro") FiltroEmpresa filtro, Model model) {
        model.addAttribute("filtro", filtro);
        return this.procesarFiltrado(filtro, model, idClient);
    }

    protected String procesarFiltrado(FiltroEmpresa filtro, Model model, Integer id) {
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
        Movimientos mov = new Movimientos();
        idClient = idAsociado;
        Tipomovimiento tipoMov = this.tipoMovimientoRepository.findById(1).orElse(null);
        mov.setTipomovimientoByTipoMovimientoId(tipoMov);
        model.addAttribute("movimiento", mov);
        model.addAttribute("asociado", asociado);
        return "transferencia";
    }

    @PostMapping("/guardarTransferencia")
    public String doGuardarTransferencia(@ModelAttribute("movimiento") Movimientos movimiento, @RequestParam("numeroCuenta") String numeroCuentaDest, Model model) {
        String urlto;
        String error;
        Cuenta cuentaDest = this.cuentaRepository.buscarCuentaPorNumeroCuenta(numeroCuentaDest);
        Cuenta cuentaOrig = movimiento.getCuentaByCuentaIdCuenta();
        if(cuentaDest == null) {
            error = "numeroCuenta";
            urlto = "errorTransferencia";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idClient);
        } else if(cuentaOrig.getDinero() < movimiento.getImporteOrigen()) {
            error = "dineroInsuficiente";
            urlto = "errorTransferencia";
            model.addAttribute("error", error);
            model.addAttribute("idAsociado", idClient);
        } else {    //No hay errores en la transferencia
            cuentaOrig.setDinero(cuentaOrig.getDinero() - movimiento.getImporteOrigen());
            cuentaDest.setDinero(cuentaDest.getDinero() + movimiento.getImporteOrigen());
            this.movimientoRepository.save(movimiento);
            this.cuentaRepository.save(cuentaOrig);
            this.cuentaRepository.save(cuentaDest);
            urlto = "redirect:/empresa/?id=" + idClient;
        }
        return urlto;
    }


}
