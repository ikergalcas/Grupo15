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
        SolicitudEntity solicitud = solicitudRepository.findById(idSolicitud).orElse(null);
        ClienteEntity cliente = solicitud.getClienteByClienteId();

        return cliente.toDTO();
    }
    public Solicitud buscarSolicitud(Integer id) {
        SolicitudEntity solicitud = solicitudRepository.findById(id).orElse(null);
        Solicitud solicitudDTO = solicitud.toDTO();
        return solicitudDTO;
    }

    public void aprobarSolicitud(Integer id) {

        SolicitudEntity solicitud = solicitudRepository.findById(id).orElse(null);
        EstadoSolicitudEntity estadoSolicitud = new EstadoSolicitudEntity();
        estadoSolicitud.setId(3);
        solicitud.setEstadoSolicitudByEstadoSolicitudId(estadoSolicitud);
        solicitudRepository.save(solicitud);

        /*SolicitudEntity solicitudEntity = solicitudRepository.findById(id).orElse(null);
        EstadoSolicitudEntity estadoSolicitudEntity = solicitudEntity.getEstadoSolicitudByEstadoSolicitudId();
        ClienteEntity clienteEntity = solicitudEntity.getClienteByClienteId();
        EmpleadoEntity gestorEntity = solicitudEntity.getEmpleadoByEmpleadoId();
        List<SolicitudEntity> solicitudesGestor = gestorEntity.getSolicitudsById();
        solicitudesGestor.remove(solicitudEntity);//borramos la solicitud para modificarla
        estadoSolicitudEntity.setEstado("resuelta");
        solicitudEntity.setEstado("resuelta");
        solicitudEntity.setEstadoSolicitudByEstadoSolicitudId(estadoSolicitudEntity);

        solicitudesGestor.add(solicitudEntity);
        gestorEntity.setSolicitudsById(solicitudesGestor);

        estadoSolicitudRepository.save(estadoSolicitudEntity);
        solicitudRepository.save(solicitudEntity);*/
    }
}
