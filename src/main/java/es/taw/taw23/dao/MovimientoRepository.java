package es.taw.taw23.dao;

import es.taw.taw23.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<MovimientoEntity, Integer> {
    @Query("select m from MovimientoEntity m where m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta")
    public List<MovimientoEntity> buscarMovimientosPorIdCuenta(@Param("idCuenta") Integer idCuenta);
}
