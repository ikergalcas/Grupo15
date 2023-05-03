package es.taw.taw23.dao;

import es.taw.taw23.entity.ChatEntity;
import es.taw.taw23.entity.EmpleadoEntity;
import es.taw.taw23.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsistenteRepository extends JpaRepository<EmpleadoEntity, Integer> {
    @Query("select e from EmpleadoEntity e where e.rolEmpleadoByRolEmpleadoId.tipo = 'asistente' ")
    public List<EmpleadoEntity> findAsistentes();
}
