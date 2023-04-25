package es.taw.taw23.controller;

import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.Movimiento;
import es.taw.taw23.dto.Divisa;
import es.taw.taw23.service.CajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/cajero")
public class CajeroController {

    @Autowired
    CajeroService cajeroService;


/*
Cuenta tiene: id, Lista de movimientos_origen, lista de movimientos_destino. Por tanto, necesito los movimientos para sacar el tipo, importe, el id y la fecha
 */
    @GetMapping("/{id}")
    public String doListarCajero(@PathVariable("id") Integer id, Model model){
        Cuenta cuenta = this.cajeroService.buscarCuenta(id);
        List<Movimiento> movimientos = cuenta.getMovimientosOrigen();
        for (Movimiento x : cuenta.getMovimientosDestino()){
            if (!x.getCuentaOrigen().equals(x.getCuentaDestino()))
                movimientos.add(x);
        }
        model.addAttribute("cuenta",cuenta);
        model.addAttribute("movimientos",movimientos);
        return "cajero";
    }
    @GetMapping("/modificar/{id}")
    public String doModificar(@PathVariable("id") Integer id, Model model){
        List<Cliente> clientes = this.cajeroService.buscarClientes(id);
        model.addAttribute("clientes",clientes);
        return "modificar";
    }
    @PostMapping("/guardar")
    public String procesarModificacion(@ModelAttribute("cliente") Cliente cliente){
        this.cajeroService.setNewCliente(cliente);
        return "redirect:/cajero/{id}";
    }


    @GetMapping("/transferencia/{id}")
    public String doTransferencia(@PathVariable("id") Integer id, Model model){
        Cuenta cuenta = this.cajeroService.buscarCuenta(id);
        List<Cuenta> cuentasMenosOrigen = this.cajeroService.obtenerTodasLasCuentasMenosOrigen(id);
        model.addAttribute("cuenta",cuenta);
        model.addAttribute("cuentasMenosOrigen",cuentasMenosOrigen);
        return "transferencia";
    }
    @PostMapping("/transferir")
    public String procesarTransferencia(@ModelAttribute("movimiento") Movimiento mov){

        //Cuenta origen = this.cajeroService.buscarCuentaPorNumero(mov.getCuentaOrigen());
        //this.cajeroService.procesarDinero(origen,mov.getImporteOrigen());

        //Cuenta destino = this.cajeroService.buscarCuentaPorNumero(mov.getCuentaDestino());
        //this.cajeroService.procesarDinero(destino,mov.getImporteDestino());

        this.cajeroService.addMovimiento(movAux(mov));
        return "redirect:/cajero/{id}";
    }
    private Movimiento movAux(Movimiento mov){
        Movimiento aux = new Movimiento();
        aux.setId(mov.getId());
        aux.setCuentaOrigen(mov.getCuentaOrigen());
        aux.setCuentaDestino(mov.getCuentaDestino());
        aux.setTipo(mov.getTipo());
        aux.setImporteOrigen(mov.getImporteOrigen());
        aux.setImporteDestino(mov.getImporteDestino());
        aux.setTimeStamp(mov.getTimeStamp());
        return aux;
    }

    @GetMapping("/retirar/{id}")
    public String doRetirar(@PathVariable("id") Integer id, Model model){
        Cuenta cuenta = this.cajeroService.buscarCuenta(id);
        model.addAttribute("cuenta",cuenta);
        return "retirar";
    }
    @PostMapping("/sacar")
    public String procesarRetirar(@ModelAttribute("cuenta") Cuenta cuenta){
        this.cajeroService.setNewSaldo(cuenta);
        return "redirect:/cajero/{id}";
    }

    @GetMapping("/cambiarDivisa/{id}")
    public String doCambiarDivisa(@PathVariable("id") Integer id, Model model){
        Cuenta cuenta = this.cajeroService.buscarCuenta(id);
        List<Divisa> divisas = this.cajeroService.obtenerTodasLasDivisas();
        model.addAttribute("cuenta",cuenta);
        model.addAttribute("divisas",divisas);
        return "cambiarDivisa";
    }
    @PostMapping("/cambiar")
    public String procesarCambiarDivisa(@ModelAttribute("divisa") Divisa divisa, @ModelAttribute("cuenta") Cuenta cuenta){
        this.cajeroService.setNewDivisa(divisa,cuenta);
        return "redirect:/cajero/{id}";
    }
}
