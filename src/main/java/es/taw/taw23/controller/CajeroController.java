//Pablo Alarcón Carrión
package es.taw.taw23.controller;

import es.taw.taw23.dto.*;
import es.taw.taw23.service.CajeroService;
import es.taw.taw23.ui.FiltroCajero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        return this.procesarFiltroCajero(id,clienteId,model,null);
    }
    @PostMapping("/filtrar")
    public String doFiltrarCajero(@RequestParam("idCuenta") Integer idCuenta, @RequestParam("clienteId") Integer clienteId,
                                  Model model, FiltroCajero filtroCajero){
        return this.procesarFiltroCajero(idCuenta,clienteId,model,filtroCajero);
    }

    protected String procesarFiltroCajero(Integer idCuenta, Integer clienteId, Model model, FiltroCajero filtroCajero){
        Cliente cliente = this.cajeroService.buscarCliente(clienteId);
        Cuenta cuenta = this.cajeroService.buscarCuenta(idCuenta);

        if ((filtroCajero==null) || filterEmpty(filtroCajero)){
            filtroCajero = new FiltroCajero();
        }

        List<Movimiento> movimientos = this.cajeroService.filtrarToDTO(idCuenta, filtroCajero);

        List<Divisa> monedas = this.cajeroService.obtenerTodasLasDivisas();
        List<String> nombreMoneda = new ArrayList<>();
        for (Divisa x : monedas){
            nombreMoneda.add(x.getMoneda());
        }
        model.addAttribute("monedas",nombreMoneda);

        List<Cuenta> listaCuentas = this.cajeroService.obtenerTodasLasCuentas();
        List<String> nombreCuentas = new ArrayList<>();
        for (Cuenta x : listaCuentas){
            nombreCuentas.add(x.getNumeroCuenta());
        }
        model.addAttribute("todasLasCuentas", nombreCuentas);

        model.addAttribute("cuenta",cuenta);
        model.addAttribute("movimientos",movimientos);
        model.addAttribute("cliente",cliente);
        model.addAttribute("filtroCajero", filtroCajero);

        Solicitud solicitud = this.cajeroService.buscarSolicitud(clienteId);
        model.addAttribute("solicitud",solicitud);
        return "cajero";
    }

    private boolean filterEmpty(FiltroCajero filtroCajero){
        return (filtroCajero == null || (filtroCajero.getFiltrarPorDivisa().equals("") &&
                filtroCajero.getFiltrarPorMovimiento().equals("") &&
                filtroCajero.getFiltrarPorNumeroDeCuenta().equals("") &&
                !filtroCajero.isOrdenarTipo() && !filtroCajero.isOrdenarFecha()
                && !filtroCajero.isOrdenarImporte()));
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
        return "redirect:/cajero/"+cliente.getId();
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
            return "cajeroTransferenciaAux";
        else
            return "cajeroTransferencia";
    }
    @PostMapping("/transferir")
    public String procesarTransferencia(@ModelAttribute("movimiento") Movimiento mov, @RequestParam("idCliente") Integer idCliente,
                                        @RequestParam("idCuenta") Integer idCuenta, Model model){

        Cuenta origen = this.cajeroService.buscarCuentaPorNumero(mov.getCuentaOrigen());
        Cuenta destino = this.cajeroService.buscarCuentaPorNumero(mov.getCuentaDestino());
        if(mov.getImporteOrigen()>origen.getDinero() ||
                mov.getImporteOrigen()<=0 || destino==null || mov.getCuentaOrigen().equals(mov.getCuentaDestino()))
            return doTransferencia(idCuenta,idCliente,true,model);
        else{
            this.cajeroService.setNewMovimiento(origen,destino,mov.getImporteOrigen(), idCliente);
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
        model.addAttribute("dinero", new String(""));
        if (badAmount)
            return "cajeroRetirarAux";
        else
            return "cajeroRetirar";
    }
    @PostMapping("/sacar")
    public String procesarRetirar(@RequestParam("idCuenta") Integer idCuenta,
                                  @RequestParam("idCliente") Integer idCliente, @RequestParam("dinero") String dinero,Model model){
        Cuenta aux = this.cajeroService.buscarCuenta(idCuenta);
        Double importe = Double.valueOf(dinero);
        if (importe>aux.getDinero() || importe<0){
            return doRetirar(idCuenta,idCliente,true,model);
        } else{
            this.cajeroService.setNewSaldo(aux, importe);
            this.cajeroService.setNewMovimiento(aux,aux,importe, idCliente);
            return "redirect:/cajero/"+idCliente+"/cuenta/"+idCuenta;
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
            this.cajeroService.setNewMovimiento(aux,cuenta,cuenta.getDinero(),clienteId);
            this.cajeroService.setNewDivisa(cuenta.getMoneda(),aux);
        }
        return "redirect:/cajero/"+clienteId+"/cuenta/"+cuenta.getId();
    }

    @PostMapping("/solicitud")
    public String procesarSolicitud(@RequestParam("idCliente") Integer idCliente, @RequestParam("idCuenta") Integer idCuenta,
                                    @ModelAttribute("solicitud") Solicitud solicitud){
        this.cajeroService.setNewDesbloqueo(idCliente);
        return "redirect:/cajero/"+idCliente+"/cuenta/"+idCuenta;
    }
}
