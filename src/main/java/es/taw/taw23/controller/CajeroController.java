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
!!LE PASO LA ID DEL CLIENTE!!!!
Cuenta tiene: id, Lista de movimientos_origen, lista de movimientos_destino. Por tanto, necesito los movimientos para sacar el tipo, importe, el id y la fecha
 */

    @GetMapping("/{clienteId}")
    public String doMostrarCuentas(@PathVariable("clienteId") Integer clienteId, Model model){
        List<Cuenta> cuentasAsociadas = this.cajeroService.buscarCuentasAsociadas(clienteId);
        Cliente cliente = this.cajeroService.buscarCliente(clienteId);
        model.addAttribute("cuentasAsociadas",cuentasAsociadas);
        model.addAttribute("cliente",cliente);
        return "cuentasCliente";
    }

    @GetMapping("/{clienteId}/cuenta/{id}")
    public String doListarCajero(@PathVariable("id") Integer id,@PathVariable("clienteId") Integer clienteId, Model model){
        Cliente cliente = this.cajeroService.buscarCliente(clienteId);
        Cuenta cuenta = this.cajeroService.buscarCuenta(id);
        List<Movimiento> movimientos = cuenta.getMovimientosOrigen();
        for (Movimiento x : cuenta.getMovimientosDestino()){
            if (!x.getCuentaOrigen().equals(x.getCuentaDestino()))
                movimientos.add(x);
        }
        model.addAttribute("cuenta",cuenta);
        model.addAttribute("movimientos",movimientos);
        model.addAttribute("cliente",cliente);
        return "cajero";
    }
    @GetMapping("/{clienteId}/modificar")
    public String doModificar(@PathVariable("clienteId") Integer clienteId, Model model){
        Cliente cliente = this.cajeroService.buscarCliente(clienteId);
        model.addAttribute("cliente",cliente);
        return "modificar";
    }
    @PostMapping("/guardar")
    public String procesarModificacion(@ModelAttribute("cliente") Cliente cliente){
        this.cajeroService.setNewCliente(cliente);
        return "redirect:/"+cliente.getId();
    }


    @GetMapping("/{clienteId}/cuenta/{id}/transferencia")
    public String doTransferencia(@PathVariable("id") Integer id, @PathVariable("clienteId") Integer clienteId, Model model){
        Cliente cliente = this.cajeroService.buscarCliente(clienteId);
        Cuenta cuenta = this.cajeroService.buscarCuenta(id);
        List<Cuenta> cuentasMenosOrigen = this.cajeroService.obtenerTodasLasCuentasMenosOrigen(id);
        model.addAttribute("cliente",cliente);
        model.addAttribute("cuenta",cuenta);
        model.addAttribute("cuentasMenosOrigen",cuentasMenosOrigen);
        return "transferencia";
    }
    @PostMapping("/transferir")
    public String procesarTransferencia(@ModelAttribute("movimiento") Movimiento mov,
                                        @ModelAttribute("cliente") Cliente cliente,
                                        @ModelAttribute("cuenta") Cuenta cuenta){

        //Cuenta origen = this.cajeroService.buscarCuentaPorNumero(mov.getCuentaOrigen());
        //this.cajeroService.procesarDinero(origen,mov.getImporteOrigen());

        //Cuenta destino = this.cajeroService.buscarCuentaPorNumero(mov.getCuentaDestino());
        //this.cajeroService.procesarDinero(destino,mov.getImporteDestino());

        this.cajeroService.addMovimiento(movAux(mov));
        return "redirect:/"+cliente.getId()+"/cuenta/"+cuenta.getId();
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

    @GetMapping("/{clienteId}/cuenta/{id}/retirar")
    public String doRetirar(@PathVariable("id") Integer id,@PathVariable("clienteId") Integer clienteId, Model model){
        Cliente cliente = this.cajeroService.buscarCliente(clienteId);
        Cuenta cuenta = this.cajeroService.buscarCuenta(id);
        model.addAttribute("cliente",cliente);
        model.addAttribute("cuenta",cuenta);
        return "retirar";
    }
    @PostMapping("/sacar")
    public String procesarRetirar(@ModelAttribute("cuenta") Cuenta cuenta,
                                  @ModelAttribute("cliente") Cliente cliente){
        this.cajeroService.setNewSaldo(cuenta);
        return "redirect:/"+cliente.getId()+"/cuenta/"+cuenta.getId();
    }

    @GetMapping("/{clienteId}/cuenta/{id}/cambiarDivisa")
    public String doCambiarDivisa(@PathVariable("id") Integer id, Model model){
        return "cambiarDivisa";
    }
    @PostMapping("/cambiar")
    public String procesarCambiarDivisa(@ModelAttribute("divisa") Divisa divisa, @ModelAttribute("cuenta") Cuenta cuenta){
        return "redirect:/{clienteId}/cuenta/{id}";
    }
}
