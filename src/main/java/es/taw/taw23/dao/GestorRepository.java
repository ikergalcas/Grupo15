package es.taw.taw23.dao;

import es.taw.taw23.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GestorRepository extends JpaRepository<Empleado, Integer> {

    @Query("select e from Empleado e where e.rolempleadoByRolEmpleadoId.id = 1")
    public List<Empleado> findAllGestores();

}
