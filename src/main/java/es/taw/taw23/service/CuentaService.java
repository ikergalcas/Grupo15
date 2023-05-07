package es.taw.taw23.service;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.CuentaClienteRepository;
import es.taw.taw23.dao.CuentaRepository;
import es.taw.taw23.dao.MovimientoRepository;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Hecho por: Carla Serracant
 */
@Service
public class CuentaService {
    @Autowired
    protected CuentaRepository cuentaRepository;

    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected CuentaClienteRepository cuentaClienteRepository;

    @Autowired
    protected MovimientoRepository movimientoRepository;

    public Cuenta buscarCuentaPorId(Integer id) {
        /* Carla Serracant Guevara */
        CuentaEntity cuentaEntity = cuentaRepository.findById(id).orElse(null);
        return cuentaEntity.toDTO();
    }

    public List<Cuenta> buscarCuentasPorCliente(Cliente cliente) {
        /* Carla Serracant Guevara */
        ClienteEntity clienteEntity = clienteRepository.findById(cliente.getId()).orElse(null);
        List<CuentaClienteEntity> cuentaClienteEntities = clienteEntity.getCuentaClientesById();
        List<Cuenta> cuentasDTO = new ArrayList<>();
        for (CuentaClienteEntity cuentacliente : cuentaClienteEntities) {
            cuentasDTO.add(cuentacliente.getCuentaByCuentaId().toDTO());
        }

        return cuentasDTO;
    }

    public List<Cuenta> buscarCuentasDeEmpresaPorClientes(List<Cliente> clientes) {
        /* Carla Serracant Guevara */
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
        /* Carla Serracant Guevara */
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
        /* Carla Serracant Guevara */
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
        /* Carla Serracant Guevara */
        ClienteEntity clienteEntity = clienteRepository.findById(cliente.getId()).orElse(null);
        CuentaEntity cuentaInactiva = null;
        for (CuentaClienteEntity cuentacliente : clienteEntity.getCuentaClientesById()) {
            EstadoCuentaEntity estadoCuentaEntity = cuentacliente.getCuentaByCuentaId().getEstadoCuentaByEstadoCuentaId();
            TipoCuentaEntity tipoCuentaEntity = cuentacliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId();
            if (estadoCuentaEntity.getId()== 3 && tipoCuentaEntity.getId() == 2) {
                cuentaInactiva = cuentacliente.getCuentaByCuentaId();
            }
        }

        return cuentaInactiva.toDTO();
    }

    public Cuenta buscarCuentaEmpresaInactivaAPartirDeCliente(Cliente cliente) {
        /* Carla Serracant Guevara */
        ClienteEntity clienteEntity = clienteRepository.findById(cliente.getId()).orElse(null);
        CuentaEntity cuentaInactiva = null;
        for (CuentaClienteEntity cuentacliente : clienteEntity.getCuentaClientesById()) {
            EstadoCuentaEntity estadoCuentaEntity = cuentacliente.getCuentaByCuentaId().getEstadoCuentaByEstadoCuentaId();
            TipoCuentaEntity tipoCuentaEntity = cuentacliente.getCuentaByCuentaId().getTipoCuentaByTipoCuentaId();
            if (estadoCuentaEntity.getId()== 3 && tipoCuentaEntity.getId() == 1) {
                cuentaInactiva = cuentacliente.getCuentaByCuentaId();
            }
        }

        return cuentaInactiva.toDTO();
    }

    public Cuenta buscarCuentaClienteBloqueadaAPartirDeCliente(Cliente cliente) {
        /* Carla Serracant Guevara */
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
        /* Carla Serracant Guevara */
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
        /* Carla Serracant Guevara */
        CuentaEntity cuentaEntity = cuentaRepository.findById(cuenta.getId()).orElse(null);
        if (cuentaEntity.getEstadoCuentaByEstadoCuentaId().getId() == 3) {
            EstadoCuentaEntity estadoCuentaEntity = new EstadoCuentaEntity();
            estadoCuentaEntity.setId(1);
            cuentaEntity.setEstadoCuentaByEstadoCuentaId(estadoCuentaEntity);
            cuentaRepository.save(cuentaEntity);
        }
    }

    public void desbloquearCuentaBloqueada(Cuenta cuenta) {
        /* Carla Serracant Guevara */
        CuentaEntity cuentaEntity = cuentaRepository.findById(cuenta.getId()).orElse(null);
        if (cuentaEntity.getEstadoCuentaByEstadoCuentaId().getId() == 2) {
            EstadoCuentaEntity estadoCuentaEntity = new EstadoCuentaEntity();
            estadoCuentaEntity.setId(1);
            cuentaEntity.setEstadoCuentaByEstadoCuentaId(estadoCuentaEntity);
            cuentaRepository.save(cuentaEntity);
        }
    }

    public void bloquearCuenta(Integer idCuenta) {
        /* Carla Serracant Guevara */
        CuentaEntity cuenta = cuentaRepository.findById(idCuenta).orElse(null);
        EstadoCuentaEntity estadoCuenta = new EstadoCuentaEntity();
        estadoCuenta.setId(2);
        cuenta.setEstadoCuentaByEstadoCuentaId(estadoCuenta);
        cuentaRepository.save(cuenta);
    }

    public void desactivarCuenta(Integer idCuenta) {
        /* Carla Serracant Guevara */
        CuentaEntity cuenta = cuentaRepository.findById(idCuenta).orElse(null);
        EstadoCuentaEntity estadoCuenta = new EstadoCuentaEntity();
        estadoCuenta.setId(3);
        cuenta.setEstadoCuentaByEstadoCuentaId(estadoCuenta);
        cuentaRepository.save(cuenta);
    }

    public List<Cuenta> encontrarCuentasSinActividad() {
        /* Carla Serracant Guevara */
        List<Cuenta> cuentas = new ArrayList<>();
        List<CuentaEntity> cuentaEntities = cuentaRepository.findAll();
        for (CuentaEntity c : cuentaEntities) {
            List<MovimientoEntity> movimientosUltimoMes = movimientoRepository.findMasRecientesQueTreintaDias(c.getId());
            if ((movimientosUltimoMes.isEmpty() || movimientosUltimoMes == null) && !cuentas.contains(c.toDTO())) {
                cuentas.add(c.toDTO());
            }
        }
        return cuentas;
    }

    public void crearNuevaCuenta(Cuenta cuenta) {

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
}
