//Pablo Alarcón Carrión
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

    @GetMapping("/{clienteId}")
    public String doMostrarCuentas(@PathVariable("clienteId") Integer clienteId, Model model){
        List<Cuenta> cuentasAsociadas = this.cajeroService.buscarCuentasAsociadas(clienteId);
        Cliente cliente = this.cajeroService.buscarCliente(clienteId);
        model.addAttribute("cuentasAsociadas",cuentasAsociadas);
        model.addAttribute("cliente",cliente);
        return "cajeroCuentasCliente";
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
        return "cajeroModificarCliente";
    }
    @PostMapping("/guardar")
    public String procesarModificacion(@ModelAttribute("cliente") Cliente cliente){
        this.cajeroService.setNewCliente(cliente);
        return "redirect:/"+cliente.getId();
    }

    @GetMapping("/{clienteId}/cuenta/{id}/transferencia")
    public String doTransferencia(@PathVariable("id") Integer id, @PathVariable("clienteId") Integer clienteId,
                                  boolean badAmount, Model model){
        Cliente cliente = this.cajeroService.buscarCliente(clienteId);
        Cuenta cuenta = this.cajeroService.buscarCuenta(id);
        List<Cuenta> cuentasMenosOrigen = this.cajeroService.obtenerTodasLasCuentasMenosOrigen(id);
        Movimiento mov = new Movimiento();
        mov.setCuentaOrigen(cuenta.getNumeroCuenta());
        model.addAttribute("movimiento",mov);
        model.addAttribute("cliente",cliente);
        model.addAttribute("cuenta",cuenta);
        model.addAttribute("cuentasMenosOrigen",cuentasMenosOrigen);

        if (badAmount)
            return "cajeroTransferencia";
        else
            return "cajeroTransferenciaAux";
    }
    @PostMapping("/transferir")
    public String procesarTransferencia(@ModelAttribute("movimiento") Movimiento mov, @RequestParam("idCliente") Integer idCliente,
                                        @RequestParam("idCuenta") Integer idCuenta, Model model){

        Cuenta origen = this.cajeroService.buscarCuentaPorNumero(mov.getCuentaOrigen());
        Cuenta destino = this.cajeroService.buscarCuentaPorNumero(mov.getCuentaDestino());
        if(mov.getImporteOrigen()>origen.getDinero() || mov.getImporteOrigen()<=0 || destino==null)
            return doTransferencia(idCuenta,idCliente,true,model);
        else{
            this.cajeroService.setNewMovimiento(origen,destino,mov.getImporteOrigen());
            this.cajeroService.setNewSaldo(origen,mov.getImporteOrigen());
            if (origen.getMoneda().equals(destino.getMoneda()))
                this.cajeroService.setNewSaldo(destino,-mov.getImporteOrigen());
            else
                this.cajeroService.setNewSaldoDivisaDistinta(origen,destino,mov.getImporteOrigen());
            return "redirect:/cajero/"+idCliente+"/cuenta/"+idCuenta;
        }

    }

    @GetMapping("/{clienteId}/cuenta/{id}/retirar")
    public String doRetirar(@PathVariable("id") Integer id,@PathVariable("clienteId") Integer clienteId, boolean badAmount,Model model){
        Cliente cliente = this.cajeroService.buscarCliente(clienteId);
        Cuenta cuenta = this.cajeroService.buscarCuenta(id);
        model.addAttribute("cliente",cliente);
        model.addAttribute("cuenta",cuenta);
        if (badAmount)
            return "cajeroRetirarAux";
        else
            return "cajeroRetirar";
    }
    @PostMapping("/sacar")
    public String procesarRetirar(@ModelAttribute("cuenta") Cuenta cuenta,
                                  @RequestParam("idCliente") Integer idCliente, Model model){
        Cuenta aux = this.cajeroService.buscarCuenta(cuenta.getId());
        if (cuenta.getDinero()>aux.getDinero() || cuenta.getDinero()<0){
            return doRetirar(cuenta.getId(),idCliente,true,model);
        } else{
            this.cajeroService.setNewSaldo(aux, cuenta.getDinero());
            this.cajeroService.setNewMovimiento(aux,aux,cuenta.getDinero());
            return "redirect:/cajero/"+idCliente+"/cuenta/"+cuenta.getId();
            }
        }

    @GetMapping("/{clienteId}/cuenta/{id}/cambiarDivisa")
    public String doCambiarDivisa(@PathVariable("id") Integer id,@PathVariable("clienteId") Integer clienteId, Model model){
        List<Divisa> divisas = this.cajeroService.obtenerTodasLasDivisas();
        Cuenta cuenta = this.cajeroService.buscarCuenta(id);
        Cliente cliente = this.cajeroService.buscarCliente(clienteId);

        model.addAttribute("divisas",divisas);
        model.addAttribute("cuenta",cuenta);
        model.addAttribute("cliente",cliente);

        return "cajeroCambiarDivisa";
    }
    @PostMapping("/cambiar")
    public String procesarCambiarDivisa(@RequestParam("idCliente") Integer clienteId,
                                        @ModelAttribute("cuenta") Cuenta cuenta){
        Cuenta aux = this.cajeroService.buscarCuenta(cuenta.getId());
        if (!aux.getMoneda().equals(cuenta.getMoneda())){
            this.cajeroService.setNewDivisa(cuenta.getMoneda(),aux);
            this.cajeroService.setNewMovimiento(aux,cuenta,cuenta.getDinero());
        }
        return "redirect:/cajero/"+clienteId+"/cuenta/"+cuenta.getId();
    }
}
