package es.taw.taw23.service;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.MovimientoRepository;
import es.taw.taw23.dto.Movimiento;
import es.taw.taw23.entity.MovimientosEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovimientosService {
    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected MovimientoRepository movimientoRepository;

    public List<Movimiento> buscarMovimientosConClienteId(Integer idCuenta) {

        List<MovimientosEntity> movimientosEntityList = this.movimientoRepository.buscarMovimientosPorIdCuenta(idCuenta);
        List<Movimiento> movimientosDTO = new ArrayList<>();

        for(MovimientosEntity entity : movimientosEntityList){
            movimientosDTO.add(entity.toDTO());
        }
        return movimientosDTO;
    }
}
