package es.taw.taw23.controller;

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

    @GetMapping("/")
    public String doListarClientesEmpresa(Model model) {
        List<Cliente> lista = empresaRepository.buscarPorTipoEmpresa();
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

    @GetMapping("/bloquear")
    public String doBloquear(@RequestParam("idBloq") Integer idBloqueado,@RequestParam("id") Integer id, Model model) {
        Cliente bloqueado = this.empresaRepository.findById(idBloqueado).orElse(null);
        List<Cuenta> cuentas = bloqueado.getCuentasById();

        //Como el cliente puede tener 2 cuentas, busco cuál es la de la empresa y cambio el estado a bloqueada
        if(cuentas.get(0).getTipocuentaByTipoCuentaId().equals("empresa")) {
            cuentas.get(0).getEstadocuentaByEstadoCuentaId().setEstadoCuenta("bloqueada");
        } else {
            cuentas.get(1).getEstadocuentaByEstadoCuentaId().setEstadoCuenta("bloqueada");
        }

        //Actualizo la lista de cuentas del cliente bloqueado
        bloqueado.setCuentasById(cuentas);
        empresaRepository.save(bloqueado);
        //COMO PASO EL ATRIBUTO ID POR LA URL???
        return "redirect:/empresa/miEmpresa";
    }


}
