package es.taw.taw23.controller;

import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.Divisa;
import es.taw.taw23.dto.Movimiento;
import es.taw.taw23.service.ClienteService;
import es.taw.taw23.service.MovimientosService;
import es.taw.taw23.ui.MovimientoCambioDivisa;
import es.taw.taw23.ui.MovimientoTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

   @Autowired
   protected ClienteService clienteService;

   @Autowired
   protected MovimientosService movimientosService;

    private Integer idClient;

    @GetMapping("/{id}")
    public String doListarOperaciones(@PathVariable(value = "id") int id, Model model){
        Cliente cliente = this.clienteService.buscarCliente(id);
        model.addAttribute("cliente",cliente);

        Cuenta cuenta = this.clienteService.obtenerCuenta(id);
        model.addAttribute("cuenta", cuenta);

        List<Movimiento> movimientos = this.movimientosService.buscarMovimientosConClienteId(cuenta.getId());
        model.addAttribute("movimientos",movimientos);

        List<Divisa> divisas = this.clienteService.buscarDivisas();
        model.addAttribute("divisas", divisas);

        MovimientoCambioDivisa cambioDivisa = new MovimientoCambioDivisa();
        model.addAttribute("cambioDivisa", cambioDivisa);

        return "cliente";
    }


      @GetMapping("/transferenciaCliente/{idIndividual}")
    public String doTransferencia(@PathVariable("idIndividual") Integer idIndividual, Model model) {
        Cliente cliente = this.clienteService.buscarCliente(idIndividual);
        MovimientoTransferencia transferencia = new MovimientoTransferencia();
        Cuenta cuenta = this.clienteService.obtenerCuenta(idIndividual);

        idClient = idIndividual;

        model.addAttribute("cliente", cliente);
        model.addAttribute("transferenciaCliente", transferencia);
        model.addAttribute("cuenta", cuenta);

        return "transferenciaCliente";
    }
    @PostMapping("/guardarTransferenciaCliente")
    public String doGuardarTransferencia(@ModelAttribute("transferenciaCliente") MovimientoTransferencia transferencia,@RequestParam("idClient") Integer idCliente, Model model) {
        String urlto;
        String error = this.clienteService.transferencia(transferencia);

        if(error.equals("cuentaDestino")) {
            urlto = "errorTransferenciaCliente";
            model.addAttribute("error", error);
            model.addAttribute("idCliente", idCliente);
        } else if(error.equals("dineroInsuficiente")) {
            urlto = "errorTransferenciaCliente";
            model.addAttribute("error", error);
            model.addAttribute("idCliente", idCliente);
        } else {    //No hay errores en la transferencia
            urlto = "redirect:/cliente/" + idCliente;
        }
        return urlto;
    }

    @PostMapping("/cambioDivisa")
    public String doCambioDivisa(@ModelAttribute("divisa") MovimientoCambioDivisa divisa, Model model) {
        this.clienteService.cambioDivisa(divisa);
        return "redirect:/cliente/" + divisa.getIdCliente();
    }

    @GetMapping("/perfilCliente/{idCliente}")
    public String doEditarPerfil(@PathVariable("idCliente") Integer idCliente, Model model) {
        Cliente cliente = this.clienteService.buscarCliente(idCliente);
        model.addAttribute("clienteEditado", cliente);
        return "perfilCliente";
    }
    @PostMapping("/guardarPerfilCliente")
    public String doGuardarPerfil(@ModelAttribute("clienteEditado") Cliente editado) {
        this.clienteService.guardarCliente(editado);
        return "redirect:/cliente/"+ editado.getId();
    }
}
