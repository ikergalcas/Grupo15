package es.taw.taw23.dao;

import es.taw.taw23.entity.EstadoSolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 * Hecho por:
 * Álvaro Yuste Moreno: 50%
 * Rocío Gómez Mancebo: 50%
 */
public interface EstadoSolicitudRepository extends JpaRepository<EstadoSolicitudEntity, Integer> {
    @Query("select e from EstadoSolicitudEntity e where e.estado = 'pendiente'")
    public EstadoSolicitudEntity buscarEstadoPendiente();
}
