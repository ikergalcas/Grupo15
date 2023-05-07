package es.taw.taw23.dao;

import es.taw.taw23.entity.RolClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * Hecho por: Álvaro Yuste Moreno
 */
public interface RolClienteRepository extends JpaRepository<RolClienteEntity, Integer> {

    @Query("select r from RolClienteEntity r where r.tipo = :rol")
    public RolClienteEntity buscarRol(@Param("rol") String rol);
}
