package es.taw.taw23.service;

import ch.qos.logback.core.net.server.Client;
import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.CuentaClienteRepository;
import es.taw.taw23.dao.CuentaRepository;
import es.taw.taw23.dao.SolicitudRepository;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.Movimiento;
import es.taw.taw23.dto.Solicitud;
import es.taw.taw23.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class CuentaService {
    @Autowired
    protected CuentaRepository cuentaRepository;

    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected CuentaClienteRepository cuentaClienteRepository;

    @Autowired
    protected SolicitudRepository solicitudRepository;

    public Cuenta buscarCuentaPorId(Integer id) {
        CuentaEntity cuentaEntity = cuentaRepository.findById(id).orElse(null);
        return cuentaEntity.toDTO();
    }

    public List<Cuenta> buscarCuentasPorCliente(Cliente cliente) {
        ClienteEntity clienteEntity = clienteRepository.findById(cliente.getId()).orElse(null);
        List<CuentaClienteEntity> cuentaClienteEntities = clienteEntity.getCuentaClientesById();
        List<Cuenta> cuentasDTO = new ArrayList<>();
        for (CuentaClienteEntity cuentacliente : cuentaClienteEntities) {
            cuentasDTO.add(cuentacliente.getCuentaByCuentaId().toDTO());
        }

        return cuentasDTO;
    }

    public List<Cuenta> buscarCuentasDeEmpresaPorClientes(List<Cliente> clientes) {
        List<Cuenta> cuentas = new ArrayList<>();
        List<CuentaEntity> cuentasEntities = new ArrayList<>();
        for (Cliente c : clientes) {
            for (CuentaClienteEntity cuentacliente : c.getCuentas()) {
                if (cuentacliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId().getId() == 1) {
                    cuentasEntities.add(cuentacliente.getCuentaByCuentaId());
                }
            }
        }

        for (CuentaEntity cuenta : cuentasEntities) {
            cuentas.add(cuenta.toDTO());
        }

        return cuentas;
    }

    public void crearNuevaCuentaEmpresa(Cuenta cuenta) {

        CuentaEntity cuentaEntity = new CuentaEntity();
        cuentaEntity.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaEntity.setFechaApertura(cuenta.getFechaApertura());

        EstadoCuentaEntity estadoCuentaEntity = new EstadoCuentaEntity();
        estadoCuentaEntity.setId(1);
        cuentaEntity.setEstadoCuentaByEstadoCuentaId(estadoCuentaEntity);

        TipoCuentaEntity tipoCuentaEntity = new TipoCuentaEntity();
        tipoCuentaEntity.setId(1);
        cuentaEntity.setTipoCuentaByTipoCuentaId(tipoCuentaEntity);

        DivisaEntity divisaEntity = new DivisaEntity();
        divisaEntity.setId(1);
        cuentaEntity.setDivisaByDivisaId(divisaEntity);

        cuentaEntity.setDinero((double) 0);

        cuentaRepository.save(cuentaEntity);

        CuentaClienteEntity cuentaCliente = new CuentaClienteEntity();
        cuentaCliente.setCuentaByCuentaId(cuentaEntity);

        ClienteEntity cliente = clienteRepository.findById(cuenta.getIdCliente()).orElse(null);
        cuentaCliente.setClienteByClienteId(cliente);

        cuentaClienteRepository.save(cuentaCliente);
    }

    public void crearNuevaCuentaIndividual(Cuenta cuenta) {

        CuentaEntity cuentaEntity = new CuentaEntity();
        cuentaEntity.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaEntity.setFechaApertura(cuenta.getFechaApertura());

        EstadoCuentaEntity estadoCuentaEntity = new EstadoCuentaEntity();
        estadoCuentaEntity.setId(1);
        cuentaEntity.setEstadoCuentaByEstadoCuentaId(estadoCuentaEntity);

        TipoCuentaEntity tipoCuentaEntity = new TipoCuentaEntity();
        tipoCuentaEntity.setId(2);
        cuentaEntity.setTipoCuentaByTipoCuentaId(tipoCuentaEntity);

        DivisaEntity divisaEntity = new DivisaEntity();
        divisaEntity.setId(1);
        cuentaEntity.setDivisaByDivisaId(divisaEntity);

        cuentaEntity.setDinero((double) 0);

        cuentaRepository.save(cuentaEntity);

        CuentaClienteEntity cuentaCliente = new CuentaClienteEntity();
        cuentaCliente.setCuentaByCuentaId(cuentaEntity);

        ClienteEntity cliente = clienteRepository.findById(cuenta.getIdCliente()).orElse(null);
        cuentaCliente.setClienteByClienteId(cliente);

        cuentaClienteRepository.save(cuentaCliente);
    }

    public Cuenta buscarCuentaClienteInactivaAPartirDeCliente(Cliente cliente) {
        ClienteEntity clienteEntity = clienteRepository.findById(cliente.getId()).orElse(null);
        CuentaEntity cuentaInactiva = null;
        for (CuentaClienteEntity cuentacliente : clienteEntity.getCuentaClientesById()) {
            EstadoCuentaEntity estadoCuentaEntity = cuentacliente.getCuentaByCuentaId().getEstadoCuentaByEstadoCuentaId();
            TipoCuentaEntity tipoCuentaEntity = cuentacliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId();
            if (estadoCuentaEntity.getId()== 4 && tipoCuentaEntity.getId() == 2) {
                cuentaInactiva = cuentacliente.getCuentaByCuentaId();
            }
        }

        return cuentaInactiva.toDTO();
    }

    public Cuenta buscarCuentaEmpresaInactivaAPartirDeCliente(Cliente cliente) {
        ClienteEntity clienteEntity = clienteRepository.findById(cliente.getId()).orElse(null);
        CuentaEntity cuentaInactiva = null;
        for (CuentaClienteEntity cuentacliente : clienteEntity.getCuentaClientesById()) {
            EstadoCuentaEntity estadoCuentaEntity = cuentacliente.getCuentaByCuentaId().getEstadoCuentaByEstadoCuentaId();
            TipoCuentaEntity tipoCuentaEntity = cuentacliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId();
            if (estadoCuentaEntity.getId()== 4 && tipoCuentaEntity.getId() == 1) {
                cuentaInactiva = cuentacliente.getCuentaByCuentaId();
            }
        }

        return cuentaInactiva.toDTO();
    }

    public Cuenta buscarCuentaClienteBloqueadaAPartirDeCliente(Cliente cliente) {
        ClienteEntity clienteEntity = clienteRepository.findById(cliente.getId()).orElse(null);
        CuentaEntity cuentaBloqueada = null;
        for (CuentaClienteEntity cuentacliente : clienteEntity.getCuentaClientesById()) {
                EstadoCuentaEntity estadoCuentaEntity = cuentacliente.getCuentaByCuentaId().getEstadoCuentaByEstadoCuentaId();
                TipoCuentaEntity tipoCuentaEntity = cuentacliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId();
                if (estadoCuentaEntity.getId() == 2 && tipoCuentaEntity.getId() == 2) {
                    cuentaBloqueada = cuentacliente.getCuentaByCuentaId();
                }
        }

        return cuentaBloqueada.toDTO();
    }

    public Cuenta buscarCuentaEmpresaBloqueadaAPartirDeCliente(Cliente cliente) {
        ClienteEntity clienteEntity = clienteRepository.findById(cliente.getId()).orElse(null);
        CuentaEntity cuentaBloqueada = null;
        for (CuentaClienteEntity cuentacliente : clienteEntity.getCuentaClientesById()) {
            EstadoCuentaEntity estadoCuentaEntity = cuentacliente.getCuentaByCuentaId().getEstadoCuentaByEstadoCuentaId();
            TipoCuentaEntity tipoCuentaEntity = cuentacliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId();
            if (estadoCuentaEntity.getId() == 2 && tipoCuentaEntity.getId() == 1) {
                cuentaBloqueada = cuentacliente.getCuentaByCuentaId();
            }
        }

        return cuentaBloqueada.toDTO();
    }


    public void activarCuentaInactiva(Cuenta cuenta) {
        CuentaEntity cuentaEntity = cuentaRepository.findById(cuenta.getId()).orElse(null);
        if (cuentaEntity.getEstadoCuentaByEstadoCuentaId().getId() == 4) {
            EstadoCuentaEntity estadoCuentaEntity = new EstadoCuentaEntity();
            estadoCuentaEntity.setId(1);
            cuentaEntity.setEstadoCuentaByEstadoCuentaId(estadoCuentaEntity);
            cuentaRepository.save(cuentaEntity);
        }
    }

    public void desbloquearCuentaBloqueada(Cuenta cuenta) {
        CuentaEntity cuentaEntity = cuentaRepository.findById(cuenta.getId()).orElse(null);
        if (cuentaEntity.getEstadoCuentaByEstadoCuentaId().getId() == 2) {
            EstadoCuentaEntity estadoCuentaEntity = new EstadoCuentaEntity();
            estadoCuentaEntity.setId(1);
            cuentaEntity.setEstadoCuentaByEstadoCuentaId(estadoCuentaEntity);
            cuentaRepository.save(cuentaEntity);
        }
    }

    public List<Movimiento> recogerMovimientosOrigenDeCuentaDeUnCliente(List<Cuenta> cuentas) {
        List<MovimientosEntity> movimientosEntities= new ArrayList<>();
        List<Movimiento> movimientos = new ArrayList<>();

        for (Cuenta c : cuentas) {
            CuentaEntity cuentaEntity = cuentaRepository.findById(c.getId()).orElse(null);
            movimientosEntities = cuentaEntity.getMovimientosById();
        }

        for (MovimientosEntity m : movimientosEntities) {
                movimientos.add(m.toDTO());
        }

        return movimientos;
    }

    public List<Movimiento> recogerMovimientosDestinoDeCuentaDeUnCliente(List<Cuenta> cuentas) {
        List<MovimientosEntity> movimientosEntities = new ArrayList<>();
        List<Movimiento> movimientos = new ArrayList<>();

        for (Cuenta c: cuentas) {
            CuentaEntity cuentaEntity = cuentaRepository.findById(c.getId()).orElse(null);
            movimientosEntities = cuentaEntity.getMovimientosById_0();
        }

        for (MovimientosEntity m : movimientosEntities) {
            if (!(m.getCuentaByCuentaOrigenId().equals(m.getCuentaByCuentaDestinoId()))) {
                movimientos.add(m.toDTO());
            }
        }

        return movimientos;
    }
}
