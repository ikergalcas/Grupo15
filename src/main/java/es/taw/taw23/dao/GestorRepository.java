package es.taw.taw23.dao;

import es.taw.taw23.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Hecho por: Carla Serracant Guevara
 */
public interface GestorRepository extends JpaRepository<EmpleadoEntity,Integer> {
    @Query("select e from EmpleadoEntity e where e.rolEmpleadoByRolEmpleadoId.id = 1")
    public List<EmpleadoEntity> findAllGestores();

    @Query("select e from EmpleadoEntity e where e.rolEmpleadoByRolEmpleadoId.id = 1 and e.id = :id")
    public EmpleadoEntity findGestorById(@Param("id") Integer id);

    @Query("select e from EmpleadoEntity e where e.rolEmpleadoByRolEmpleadoId.tipo = 'gestor'")
    public List<EmpleadoEntity> buscarGestores();

}
