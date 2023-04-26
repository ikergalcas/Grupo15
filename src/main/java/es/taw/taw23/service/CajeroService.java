//Pablo Alarcón Carrión
package es.taw.taw23.service;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.*;
import es.taw.taw23.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CajeroService {
    @Autowired
    protected DivisaRepository divisaRepository;
    @Autowired
    protected CuentaClienteRepository cuentaClienteRepository;
    @Autowired
    protected CambioDivisaRepository cambioDivisaRepository;
    @Autowired
    protected EstadoCuentaRepository estadoCuentaRepository;
    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected MovimientoRepository movimientoRepository;
    @Autowired
    protected TipoMovimientoRepository tipoMovimientoRepository;
    @Autowired
    protected CajeroRepository cajeroRepository;
    @Autowired
    private ClienteRepository clienteRepository;


    public Cliente buscarCliente(Integer id){
        ClienteEntity cliente = clienteRepository.findById(id).orElse(null);
        return (cliente!=null) ? cliente.toDTO() : null;
    }

    public List<Cuenta> buscarCuentasAsociadas(Integer id){
        ClienteEntity cliente = clienteRepository.findById(id).orElse(null);
        List<CuentaClienteEntity> cuentas = cliente.getCuentaClientesById();

        List<Cuenta> cuentasAsociadas = new ArrayList<>();
        for (CuentaClienteEntity x : cuentas){
            cuentasAsociadas.add(x.getCuentaByCuentaId().toDTO());
        }
        return cuentasAsociadas;
    }
    public Cuenta buscarCuenta(Integer id){
        CuentaEntity cuenta = cuentaRepository.findById(id).orElse(null);
        return (cuenta!=null) ? cuenta.toDTO() : null;
    }

    public Cuenta buscarCuentaPorNumero(String numero){
        CuentaEntity cuenta = cajeroRepository.findByAccountNumber(numero);
        return (cuenta!=null) ? cuenta.toDTO() : null;
    }

    public void setNewCliente(Cliente cliente){
        this.clienteRepository.save(guardarAux(cliente));
    }

    private ClienteEntity guardarAux(Cliente cliente){
        ClienteEntity aux = this.clienteRepository.findById(cliente.getId()).orElse(null);
        aux.setNif(cliente.getNif());
        aux.setPrimerNombre(cliente.getPrimerNombre());
        aux.setSegundoNombre(cliente.getSegundoNombre());
        aux.setPrimerApellido(cliente.getPrimerApellido());
        aux.setSegundoApellido(cliente.getSegundoApellido());
        aux.setFechaNacimiento((Date) cliente.getFechaNacimiento());
        aux.setCalle(cliente.getCalle());
        aux.setNumero(cliente.getNumero());
        aux.setPuerta(cliente.getPuerta());
        aux.setCiudad(cliente.getCiudad());
        aux.setPais(cliente.getPais());
        aux.setRegion(cliente.getRegion());
        aux.setCp(cliente.getCp());
        aux.setContrasena(cliente.getContrasena());
        return aux;
    }

    public void setNewMovimiento(Cuenta origen, Cuenta destino,
                                 Double cantidadOrigen){
        CuentaEntity origenAux = cuentaRepository.findById(origen.getId()).orElse(null);
        CuentaEntity destinoAux = cuentaRepository.findById(destino.getId()).orElse(null);
        MovimientosEntity movimiento = new MovimientosEntity();
        List<MovimientosEntity> movimientosTotales = movimientoRepository.findAll();
        movimiento.setId(movimientosTotales.size()+1);
        if (origen.getId().equals(destino.getId()) && origen.getMoneda().equals(destino.getMoneda())){
            TipoMovimientoEntity tipoMovimiento = cajeroRepository.findByMovementName("sacarDinero");
            movimiento.setImporteOrigen(redondear(origenAux.getDinero()));
            movimiento.setImporteDestino(redondear(origenAux.getDinero()-cantidadOrigen));
            movimiento.setTipoMovimientoByTipoMovimientoId(tipoMovimiento);
        }
        else if(origen.getId().equals(destino.getId()) && !origen.getMoneda().equals(destino.getMoneda())){
            TipoMovimientoEntity tipoMovimiento = cajeroRepository.findByMovementName("cambioDivisa");
            CambioDivisaEntity cambioDivisa = cajeroRepository.cambiarDivisa(
                    cajeroRepository.findByMoneyName(origen.getMoneda()).getId(),
                    cajeroRepository.findByMoneyName(destino.getMoneda()).getId());
            movimiento.setImporteOrigen(redondear(origenAux.getDinero()));
            movimiento.setImporteDestino(redondear(cambioDivisa.getCambio()*origenAux.getDinero()));
            movimiento.setTipoMovimientoByTipoMovimientoId(tipoMovimiento);
        }
        else{
            TipoMovimientoEntity tipoMovimiento = cajeroRepository.findByMovementName("pago");
            movimiento.setImporteOrigen(redondear(cantidadOrigen));
            if (origen.getMoneda().equals(destino.getMoneda())){
                movimiento.setImporteDestino(redondear(cantidadOrigen));
            }else{
                CambioDivisaEntity cambioDivisa = cajeroRepository.cambiarDivisa(
                        cajeroRepository.findByMoneyName(origen.getMoneda()).getId(),
                        cajeroRepository.findByMoneyName(destino.getMoneda()).getId());
                movimiento.setImporteDestino(redondear(cambioDivisa.getCambio()*cantidadOrigen));

            }
            movimiento.setTipoMovimientoByTipoMovimientoId(tipoMovimiento);

        }
        movimiento.setCuentaByCuentaOrigenId(origenAux);
        movimiento.setCuentaByCuentaDestinoId(destinoAux);
        movimiento.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        movimientoRepository.save(movimiento);
    }

    public void setNewSaldo(Cuenta cuenta, Double cantidad){
        CuentaEntity aux = this.cajeroRepository.findByAccountNumber(cuenta.getNumeroCuenta());
        aux.setDinero(redondear(cuenta.getDinero() - cantidad));
        this.cuentaRepository.save(aux);
    }

    public void setNewSaldoDivisaDistinta(Cuenta origen, Cuenta destino, Double cantidad){
        CambioDivisaEntity cambioDivisa = cajeroRepository.cambiarDivisa(
                cajeroRepository.findByMoneyName(origen.getMoneda()).getId(),
                cajeroRepository.findByMoneyName(destino.getMoneda()).getId());
        CuentaEntity aux = this.cajeroRepository.findByAccountNumber(destino.getNumeroCuenta());
        aux.setDinero(redondear(destino.getDinero()+(cantidad*cambioDivisa.getCambio())));
        this.cuentaRepository.save(aux);
    }

    public void setNewDivisa(String divisaNombre, Cuenta cuenta){
        DivisaEntity divisa = this.cajeroRepository.findByMoneyName(divisaNombre);
        CuentaEntity aux = this.cajeroRepository.findByAccountNumber(cuenta.getNumeroCuenta());

        Integer divisaOrigenId = this.cajeroRepository.findByMoneyName(aux.getDivisaByDivisaId().getMoneda()).getId();
        Integer divisaDestinoId = divisa.getId();

        CambioDivisaEntity cambioDivisa = cajeroRepository.cambiarDivisa(divisaOrigenId, divisaDestinoId);

        aux.setDivisaByDivisaId(divisa);
        aux.setDinero(redondear(cambioDivisa.getCambio()*aux.getDinero()));
        this.cuentaRepository.save(aux);
    }

    public List<Cuenta> obtenerTodasLasCuentasMenosOrigen(Integer id){
        List<CuentaEntity> cuentas = this.cuentaRepository.findAll();
        List<Cuenta> cuentasDTO = new ArrayList<>();
        for (CuentaEntity x : cuentas){
            if (!x.getId().equals(id)) //Esto debería hacerlo con SQL pero no se honestamente
                cuentasDTO.add(x.toDTO());
        }
        return cuentasDTO;
    }

    public List<Divisa> obtenerTodasLasDivisas(){
        List<DivisaEntity> divisas = this.divisaRepository.findAll();
        List<Divisa> divisasDTO = new ArrayList<>();
        for (DivisaEntity x : divisas){
            divisasDTO.add(x.toDTO());
        }
        return divisasDTO;
    }

    private Double redondear(Double cantidad){
        return Double.valueOf(Math.round((cantidad.doubleValue()*100d) / 100));
    }
}
