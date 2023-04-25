package es.taw.taw23.service;

import es.taw.taw23.dao.*;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.Divisa;
import es.taw.taw23.dto.Movimiento;
import es.taw.taw23.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Cuenta buscarCuenta(Integer id){
        CuentaEntity cuenta = cuentaRepository.findById(id).orElse(null);
        return (cuenta!=null) ? cuenta.toDTO() : null;
    }

    public Cuenta buscarCuentaPorNumero(String numero){
        CuentaEntity cuenta = cajeroRepository.findByAccountNumber(numero);
        return (cuenta!=null) ? cuenta.toDTO() : null;
    }

    public List<Cliente> buscarClientes(Integer id){
        CuentaEntity cuenta = cuentaRepository.findById(id).orElse(null);
        List<CuentaClienteEntity> cuentas = cuenta.getCuentaClientesById();
        List<ClienteEntity> clientes = new ArrayList<>();
        for (CuentaClienteEntity x : cuentas){
            clientes.add(x.getClienteByClienteId());
        }
        List<Cliente> clientesDTO = new ArrayList<>();
        for (ClienteEntity x : clientes){
            clientesDTO.add(x.toDTO());
        }
        return clientesDTO;
    }

    public void addMovimiento(Movimiento mov){
        MovimientosEntity aux = new MovimientosEntity();
        aux.setId(mov.getId());
        aux.setCuentaByCuentaOrigenId(cajeroRepository.findByAccountNumber(mov.getCuentaOrigen()));
        aux.setCuentaByCuentaDestinoId(cajeroRepository.findByAccountNumber(mov.getCuentaDestino()));
        aux.setTipoMovimientoByTipoMovimientoId(cajeroRepository.findByMovementName(mov.getTipo()));
        aux.setImporteOrigen(mov.getImporteOrigen());
        aux.setImporteDestino(mov.getImporteDestino());
        aux.setTimeStamp(mov.getTimeStamp());
        this.movimientoRepository.save(aux);
    }

    public void setNewCliente(Cliente cliente){


    }

    public void setNewSaldo(Cuenta cuenta){
        CuentaEntity aux = this.cajeroRepository.findByAccountNumber(cuenta.getNumeroCuenta());
        aux.setDinero(cuenta.getDinero());
        this.cuentaRepository.save(aux);
    }

    public void setNewDivisa(Divisa divisaDTO, Cuenta cuenta){
        DivisaEntity divisa = this.cajeroRepository.findByMoneyName(divisaDTO.getMoneda());
        CuentaEntity aux = this.cajeroRepository.findByAccountNumber(cuenta.getNumeroCuenta());
        aux.setDivisaByDivisaId(divisa);
        this.cuentaRepository.save(aux);
    }

    public List<Cuenta> obtenerTodasLasCuentasMenosOrigen(Integer id){
        List<CuentaEntity> cuentas = this.cajeroRepository.findAllButNotThis(id);
        List<Cuenta> cuentasDTO = new ArrayList<>();
        for (CuentaEntity x : cuentas){
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
}
