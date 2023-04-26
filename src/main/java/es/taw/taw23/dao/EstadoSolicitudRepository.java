package es.taw.taw23.dao;

import es.taw.taw23.entity.EstadoSolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstadoSolicitudRepository extends JpaRepository<EstadoSolicitudEntity, Integer> {
    @Query("select e from EstadoSolicitudEntity e where e.estado = 'pendiente'")
    public EstadoSolicitudEntity buscarEstadoPendiente();
}
