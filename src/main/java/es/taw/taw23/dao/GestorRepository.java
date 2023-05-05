package es.taw.taw23.dao;

import es.taw.taw23.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GestorRepository extends JpaRepository<EmpleadoEntity,Integer> {

    /* Carla Serracant Guevara */
    @Query("select e from EmpleadoEntity e where e.rolEmpleadoByRolEmpleadoId.id = 1")
    public List<EmpleadoEntity> findAllGestores();

    /* Carla Serracant Guevara */
    @Query("select e from EmpleadoEntity e where e.rolEmpleadoByRolEmpleadoId.id = 1 and e.id = :id")
    public EmpleadoEntity findGestorById(@Param("id") Integer id);
}
