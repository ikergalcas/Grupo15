package es.taw.taw23.service;

import es.taw.taw23.dao.CuentaRepository;
import es.taw.taw23.dao.CuentaSospechosaRepository;
import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.CuentaSospechosa;
import es.taw.taw23.entity.CuentaEntity;
import es.taw.taw23.entity.CuentaSospechosaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Hecho por: Carla Serracant Guevara
 */
@Service
public class CuentaSospechosaService {

    @Autowired
    protected CuentaSospechosaRepository cuentaSospechosaRepository;

    @Autowired
    protected CuentaRepository cuentaRepository;

    public List<CuentaSospechosa> obtenerCuentasSospechosas() {
        /* Carla Serracant Guevara */
        List<CuentaSospechosaEntity> cuentaSospechosaEntities = cuentaSospechosaRepository.findAll();
        List<CuentaSospechosa> cuentasSospechosasDTO = new ArrayList<>();
        for (CuentaSospechosaEntity c : cuentaSospechosaEntities) {
            cuentasSospechosasDTO.add(c.toDTO());
        }

        return cuentasSospechosasDTO;
    }

    public void anadirCuentaACuentasSospechosas(Cuenta cuenta) {
        /* Carla Serracant Guevara */
        CuentaEntity cuentaEntity = cuentaRepository.findById(cuenta.getId()).orElse(null);
        CuentaSospechosaEntity cuentaSospechosaEntity = new CuentaSospechosaEntity();
        cuentaSospechosaEntity.setCuentaByCuentaId(cuentaEntity);
        cuentaSospechosaRepository.save(cuentaSospechosaEntity);
    }

    public void quitarCuentaDeCuentasSospechosas(Cuenta cuenta) {
        /* Carla Serracant Guevara */
        CuentaSospechosaEntity cuentaSospechosaEntity = cuentaSospechosaRepository.findCuentaSospechosaByIdCuenta(cuenta.getId());
        cuentaSospechosaRepository.delete(cuentaSospechosaEntity);
    }
}
