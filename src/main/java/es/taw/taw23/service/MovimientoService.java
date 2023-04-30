package es.taw.taw23.service;

import es.taw.taw23.dao.CuentaRepository;
import es.taw.taw23.dao.MovimientoRepository;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.Movimiento;
import es.taw.taw23.entity.CuentaEntity;
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

    public List<Movimiento> unirMovimientosOrigenDestino(List<Movimiento> origen, List<Movimiento> destino) {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.addAll(origen);
        movimientos.addAll(destino);
        return movimientos;
    }

    public List<Movimiento> recogerMovimientosASiMismoEnUnaCuenta(List<Cuenta> cuentas) {
        List<MovimientosEntity> movimientos = new ArrayList<>();
        for (Cuenta c : cuentas) {
            CuentaEntity cuentaEntity = cuentaRepository.findById(c.getId()).orElse(null);
            movimientos.addAll(movimientoRepository.encontrarMovimientosASiMismo(cuentaEntity.getId()));
        }

        List<Movimiento> movimientosDTO = new ArrayList<>();
        for (MovimientosEntity m : movimientos) {
            movimientosDTO.add(m.toDTO());
        }

        return movimientosDTO;
    }
}
