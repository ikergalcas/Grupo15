package es.taw.taw23.controller;

import es.taw.taw23.dao.CuentaRepository;
import es.taw.taw23.dao.EmpresaRepository;
import es.taw.taw23.entity.Cliente;
import es.taw.taw23.entity.Cuenta;
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
        Cliente cliente = this.empresaRepository.findById(idCliente).orElse(null);
        List<Cliente> lista = empresaRepository.buscarSociosAutorizadosDeMiEmpresa(cliente.getEmpresaByEmpresaIdEmpresa().getIdEmpresa());
        model.addAttribute("cliente", cliente);
        model.addAttribute("asociados", lista);
        return "miEmpresa";
    }

    @GetMapping("/miPerfil")
    public String doEditarPerfil(@RequestParam("id") Integer idCliente, Model model) {
        /*
        Cliente asociado = this.empresaRepository.findById(idCliente).orElse(null);
        model.addAttribute("asociado", asociado);
        */
        return "perfil";
    }

    @PostMapping("/guardarPerfil")
    public String doGuardarPerfil(@ModelAttribute("asociadoEditado") Cliente asociado) {
        this.empresaRepository.save(asociado);
        return "socios";
    }

    //NO FURULA
    @PostMapping("/bloquear")
    public String doBloquear(@RequestParam("idBloq") Integer idBloqueado,@RequestParam("id") Integer id, Model model) {
        Cliente bloqueado = this.empresaRepository.findById(idBloqueado).orElse(null);
        List<Cuenta> cuentas = bloqueado.getCuentasById();

        //Como el cliente puede tener 2 cuentas, busco cuál es la de la empresa y cambio el estado a bloqueada
        if(cuentas.get(0).getTipocuentaByTipoCuentaId().getTipo().equals("empresa")) {
            cuentas.get(0).getEstadocuentaByEstadoCuentaId().setEstadoCuenta("bloqueada");
            cuentaRepository.save(cuentas.get(0));
        } else {
            cuentas.get(1).getEstadocuentaByEstadoCuentaId().setEstadoCuenta("bloqueada");
            cuentaRepository.save(cuentas.get(1));
        }

        //Actualizo la lista de cuentas del cliente bloqueado
        bloqueado.setCuentasById(cuentas);
        empresaRepository.save(bloqueado);

        return doListarMiEmpresa(id, model);
    }


}
