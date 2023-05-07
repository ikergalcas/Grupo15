package es.taw.taw23.service;

import es.taw.taw23.dao.ClienteRepository;
import es.taw.taw23.dao.EstadoSolicitudRepository;
import es.taw.taw23.dao.GestorRepository;
import es.taw.taw23.dao.SolicitudRepository;
import es.taw.taw23.dto.Cliente;
import es.taw.taw23.dto.Estado_solicitud;
import es.taw.taw23.dto.Solicitud;
import es.taw.taw23.entity.ClienteEntity;
import es.taw.taw23.entity.EmpleadoEntity;
import es.taw.taw23.entity.EstadoSolicitudEntity;
import es.taw.taw23.entity.SolicitudEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Hecho por: Carla Serracant Guevara
 */
@Service
public class SolicitudService {

    @Autowired
    protected SolicitudRepository solicitudRepository;

    @Autowired
    protected EstadoSolicitudRepository estadoSolicitudRepository;

    @Autowired
    protected ClienteRepository clienteRepository;

    @Autowired
    protected GestorRepository gestorRepository;

    public Cliente buscarClienteAPartirDeSolicitud(Integer idSolicitud) {
        /* Carla Serracant Guevara */
        SolicitudEntity solicitud = solicitudRepository.findById(idSolicitud).orElse(null);
        ClienteEntity cliente = solicitud.getClienteByClienteId();

        return cliente.toDTO();
    }
    public Solicitud buscarSolicitud(Integer id) {
        /* Carla Serracant Guevara */
        SolicitudEntity solicitud = solicitudRepository.findById(id).orElse(null);
        Solicitud solicitudDTO = solicitud.toDTO();
        return solicitudDTO;
    }

    public void aprobarSolicitud(Integer id) {
        /* Carla Serracant Guevara */
        SolicitudEntity solicitud = solicitudRepository.findById(id).orElse(null);
        EstadoSolicitudEntity estadoSolicitud = new EstadoSolicitudEntity();
        estadoSolicitud.setId(3);
        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoSolicitud);
        solicitudRepository.save(solicitud);

    }

    public void borrarSolicitud(Integer id) {
        /* Carla Serracant Guevara */
        SolicitudEntity solicitud = solicitudRepository.findById(id).orElse(null);
        solicitudRepository.delete(solicitud);
    }

    public void rechazarSolicitud(Integer id) {
        /* Carla Serracant Guevara */
        SolicitudEntity solicitud = solicitudRepository.findById(id).orElse(null);
        EstadoSolicitudEntity estadoSolicitudEntity = new EstadoSolicitudEntity();
        estadoSolicitudEntity.setId(2);
        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoSolicitudEntity);
        solicitudRepository.save(solicitud);
    }
}
