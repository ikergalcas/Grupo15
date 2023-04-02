package es.taw.taw23.controller;

import es.taw.taw23.ui.FiltroEmpresa;
import es.taw.taw23.dao.CuentaRepository;
import es.taw.taw23.dao.EmpresaRepository;
import es.taw.taw23.dao.EstadoCuentaRepository;
import es.taw.taw23.entity.Cliente;
import es.taw.taw23.entity.Cuenta;
import es.taw.taw23.entity.Empresa;
import es.taw.taw23.entity.Estadocuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    protected EstadoCuentaRepository estadoCuentaRepository;
    @Autowired
    protected EmpresaRepository empresaRepository;

    @Autowired
    protected CuentaRepository cuentaRepository;


    @GetMapping("/")
    public String doListarClientesEmpresa(@RequestParam("id") Integer id, Model model) {
        List<Cliente> lista = this.empresaRepository.buscarPorTipoEmpresa();
        Cliente cliente = this.empresaRepository.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);
        model.addAttribute("socios", lista);
        return "socios";
    }

    @GetMapping("/miEmpresa")
    public String doListarMiEmpresa(@RequestParam("id") Integer idCliente, Model model) {
        return this.procesarFiltrado(null, model, idCliente);
    }

    //NO FURULA
    @PostMapping("/filtrar")
    public String doFiltrar(@RequestParam("idCliente") Integer idCliente, @ModelAttribute("filtro") FiltroEmpresa filtro, Model model) {
        return this.procesarFiltrado(filtro, model, idCliente);
    }

    protected String procesarFiltrado(FiltroEmpresa filtro, Model model, Integer id) {
        List<Cliente> listaAsociado;
        Cliente cliente = this.empresaRepository.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);
        if(filtro == null) {
            listaAsociado = this.empresaRepository.buscarSociosAutorizadosDeMiEmpresa(cliente.getEmpresaByEmpresaIdEmpresa().getIdEmpresa());
            //Busco por el primer nombre
        } else {
            listaAsociado = this.empresaRepository.buscarPorPrimerNombre(filtro.getPrimerNombre());
        }
        model.addAttribute("filtro", filtro);
        model.addAttribute("asociados", listaAsociado);
        return "miEmpresa";
    }


    @GetMapping("/miPerfil")
    public String doEditarPerfil(@RequestParam("id") Integer idCliente, Model model) {
        Cliente asociado = this.empresaRepository.findById(idCliente).orElse(null);
        model.addAttribute("asociadoEditado", asociado);
        return "perfil";
    }

    @PostMapping("/guardarPerfil")
    public String doGuardarPerfil(@RequestParam("id") Integer idCliente, @ModelAttribute("asociadoEditado") Cliente asociado) {
        Cliente editado = this.empresaRepository.findById(idCliente).orElse(null);
        asociado.setRolclienteByRolclienteId(editado.getRolclienteByRolclienteId());
        asociado.setEmpresaByEmpresaIdEmpresa(editado.getEmpresaByEmpresaIdEmpresa());
        asociado.setCuentasById(editado.getCuentasById());
        asociado.setChatsById(editado.getChatsById());
        asociado.setSolicitudsById(editado.getSolicitudsById());
        this.empresaRepository.save(asociado);
        return "redirect:/empresa/?id=" + idCliente;
    }

    @GetMapping("/editarEmpresa")
    public String doEditarEmpresa(@RequestParam("id") Integer idAsociado, Model model) {
        Cliente asociado = this.empresaRepository.findById(idAsociado).orElse(null);
        Empresa empresa = asociado.getEmpresaByEmpresaIdEmpresa();
        model.addAttribute("asociado", asociado);
        model.addAttribute("empresaEditada", empresa);
        return "editarEmpresa";
    }

    @PostMapping("/guardarEmpresa")
    public String doGuardarEmpresa(@RequestParam("id") Integer idCliente, @ModelAttribute("empresaEditada") Empresa empresa) {
        Cliente asociado = this.empresaRepository.findById(idCliente).orElse(null);
        return "redirect:/empresa/?id=" + asociado.getId();
    }


    @PostMapping("/bloquear")
    public String doBloquear(@RequestParam("idBloq") Integer idBloqueado,@RequestParam("id") Integer id, Model model) {
        Cliente bloqueado = this.empresaRepository.findById(idBloqueado).orElse(null);
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
        empresaRepository.save(bloqueado);

        return "redirect:/empresa/?id=" + id;
    }


}
