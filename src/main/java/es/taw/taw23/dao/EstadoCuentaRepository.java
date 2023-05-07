package es.taw.taw23.dao;

import es.taw.taw23.entity.EstadoCuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 * Hecho por: Carla Serracant Guevara
 */
public interface EstadoCuentaRepository extends JpaRepository<EstadoCuentaEntity, Integer> {

    @Query("select e from EstadoCuentaEntity e where e.estadoCuenta = 'bloqueada'")
    public EstadoCuentaEntity buscarBloqueada();
}
