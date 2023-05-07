package es.taw.taw23.dao;

import es.taw.taw23.entity.DivisaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Hecho por: Álvaro Yuste Moreno
 */
public interface DivisaRepository extends JpaRepository<DivisaEntity, Integer> {

    @Query("select d from DivisaEntity d where d.moneda = :divisa")
    public DivisaEntity buscarDivisaPorNombre(@Param("divisa") String divisa);
}
