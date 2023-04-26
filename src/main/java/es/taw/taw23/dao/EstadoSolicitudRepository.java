package es.taw.taw23.dao;

import es.taw.taw23.entity.EstadoSolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoSolicitudRepository extends JpaRepository<EstadoSolicitudEntity,Integer> {
}
