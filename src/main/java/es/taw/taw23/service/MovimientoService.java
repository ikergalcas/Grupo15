package es.taw.taw23.service;

import es.taw.taw23.dao.CuentaRepository;
import es.taw.taw23.dao.CuentaSospechosaRepository;
import es.taw.taw23.dao.MovimientoRepository;
import es.taw.taw23.dao.TipoMovimientoRepository;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.CuentaSospechosa;
import es.taw.taw23.dto.Movimiento;
import es.taw.taw23.entity.CuentaEntity;
import es.taw.taw23.entity.CuentaSospechosaEntity;
import es.taw.taw23.entity.MovimientosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    protected MovimientoRepository movimientoRepository;

    @Autowired
    protected CuentaRepository cuentaRepository;

    @Autowired
    protected TipoMovimientoRepository tipoMovimientoRepository;

    @Autowired
    protected CuentaSospechosaRepository cuentaSospechosaRepository;


    public List<Movimiento> encuentraMovimientosRecientes(List<Cuenta> cuentas) {
        /* Carla Serracant Guevara */
        List<Movimiento> movimientos = new ArrayList<>();
        for (Cuenta c : cuentas) {
            List<MovimientosEntity> movimientosEntities = movimientoRepository.findMasRecientesQueTreintaDias(c.getId());
            for (MovimientosEntity m : movimientosEntities) {
                movimientos.add(m.toDTO());
            }
        }
        return movimientos;
    }

    public List<Movimiento> encuentraMovimientosRecientesYConTipo(List<Cuenta> cuentas, String tipo) {
        /* Carla Serracant Guevara */
        List<Movimiento> movimientos = new ArrayList<>();
        for (Cuenta c : cuentas) {
            List<MovimientosEntity> movimientosEntities = movimientoRepository.findMasRecientesQueTreintaDiasConTipoFiltrado(c.getId(),tipo);
            for (MovimientosEntity m : movimientosEntities) {
                movimientos.add(m.toDTO());
            }
        }

        return movimientos;
    }

    public List<Movimiento> encuentraMovimientos(List<Cuenta> cuentas) {
        /* Carla Serracant Guevara */
        List<Movimiento> movimientos = new ArrayList<>();
        for (Cuenta c : cuentas) {
            List<MovimientosEntity> movimientosEntities = movimientoRepository.findMovimientosByIdCuenta(c.getId());
            for (MovimientosEntity m : movimientosEntities) {
                movimientos.add(m.toDTO());
            }
        }

        return movimientos;
    }


    public List<String> encuentraTiposDeMovimientos() {
        /* Carla Serracant Guevara */
        List<String> tipoMovimientos = tipoMovimientoRepository.findAllTiposMovimientos();
        return tipoMovimientos;
    }

    public List<Movimiento> encuentraMovimientoPorTipoYIdCuenta (String tipo, List<Cuenta> cuentas) {
        /* Carla Serracant Guevara */
        List<Movimiento> movimientos = new ArrayList<>();
        for (Cuenta c : cuentas) {
            List<MovimientosEntity> movimientosEntities = movimientoRepository.findAllByTipoMovimiento(tipo, c.getId());
            for (MovimientosEntity m : movimientosEntities) {
                movimientos.add(m.toDTO());
            }
        }

        return movimientos;
    }

    public List<Movimiento> encuentraMovimientosACuentasSospechosas() {
        /* Carla Serracant Guevara */
        List<CuentaSospechosaEntity> cuentaSospechosas = cuentaSospechosaRepository.findAll();
        List<Movimiento> movimientos = new ArrayList<>();
        for (CuentaSospechosaEntity c : cuentaSospechosas) {
            List<MovimientosEntity> movimientosEntities = movimientoRepository.findMovimientosSospechosos(c.getCuentaByCuentaId().getId());
            for (MovimientosEntity m : movimientosEntities) {
                movimientos.add(m.toDTO());
            }
        }

        return movimientos;
    }
}
