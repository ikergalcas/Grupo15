package es.taw.taw23.service;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.CuentaClienteRepository;
import es.taw.taw23.dao.CuentaRepository;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Hecho por: Carla Serra Cant
 */
@Service
public class CuentaService {
    @Autowired
    protected CuentaRepository cuentaRepository;

    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected CuentaClienteRepository cuentaClienteRepository;

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
