package es.taw.taw23.service;

import es.taw.taw23.dao.GestorRepository;
import es.taw.taw23.dao.SolicitudRepository;
import es.taw.taw23.dto.Empleado;
import es.taw.taw23.dto.Solicitud;
import es.taw.taw23.entity.EmpleadoEntity;
import es.taw.taw23.entity.SolicitudEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Hecho por: Carla Serracant
 */
@Service
public class GestorService {
    @Autowired
    protected GestorRepository gestorRepository;

    @Autowired
    protected SolicitudRepository solicitudRepository;

    public Empleado BuscarGestor(Integer id) {
        /* Carla Serracant Guevara */
        EmpleadoEntity gestor = gestorRepository.findGestorById(id);
        if (gestor !=null) {
            return gestor.toDto();
        } else {
            return null;
        }
    }

    public List<Solicitud> buscarSolicitudesDeGestor(Integer id) {
        /* Carla Serracant Guevara */
        List<Solicitud> listaSolicitudesDto = new ArrayList<>();
        List<SolicitudEntity> listaSolicitudes = solicitudRepository.buscarSolicitudesPendientesDeUnGestor(id);
        if (!listaSolicitudes.isEmpty() || !(listaSolicitudes == null)) {
            for (SolicitudEntity s : listaSolicitudes) {
                listaSolicitudesDto.add(s.toDTO());
            }
            return listaSolicitudesDto;
        } else {
            return null;
        }
    }

    public List<Solicitud> buscarSolicitudesResueltasDeGestor(Integer id) {
        /* Carla Serracant Guevara */
        List<Solicitud> listaSolicitudesDto = new ArrayList<>();
        List<SolicitudEntity> listaSolicitudes = solicitudRepository.buscarSolicitudesResueltasDeUnGestor(id);
        if (!listaSolicitudes.isEmpty() || listaSolicitudes != null) {
            for (SolicitudEntity s : listaSolicitudes) {
                listaSolicitudesDto.add(s.toDTO());
            }

            return listaSolicitudesDto;
        } else {
            return null;
        }
    }

    public List<Solicitud> buscarSolicitudesRechazadasDeGestor(Integer id) {
        /* Carla Serracant Guevara */
        List<Solicitud> listaSolicitudesDto = new ArrayList<>();
        List<SolicitudEntity> listaSolicitudes = solicitudRepository.buscarSolicitudesNegadasDeUnGestor(id);
        if (!listaSolicitudes.isEmpty() || listaSolicitudes != null) {
            for (SolicitudEntity s : listaSolicitudes) {
                listaSolicitudesDto.add(s.toDTO());
            }
            return listaSolicitudesDto;
        } else {
            return null;
        }
    }
}
