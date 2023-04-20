package es.taw.taw23.dao;

import es.taw.taw23.entity.Rolcliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolClienteRepository extends JpaRepository<Rolcliente, Integer> {

    @Query("select r from Rolcliente r where r.tipo = :rol")
    public Rolcliente buscarRol(@Param("rol") String rol);
}
