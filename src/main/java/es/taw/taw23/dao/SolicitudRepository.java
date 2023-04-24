package es.taw.taw23.dao;

import es.taw.taw23.entity.SolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<SolicitudEntity, Integer> {

    @Query("select s from SolicitudEntity s where s.estado = 'pendiente' and s.empleadoByEmpleadoId.id = :idGestor")
    public List<SolicitudEntity> buscarSolicitudesPendientesDeUnGestor(@Param("idGestor") Integer id);
}
