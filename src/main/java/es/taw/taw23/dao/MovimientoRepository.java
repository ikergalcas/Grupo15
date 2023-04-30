package es.taw.taw23.dao;

import es.taw.taw23.entity.MovimientosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<MovimientosEntity, Integer> {
    @Query("select m from MovimientosEntity m where m.cuentaByCuentaOrigenId.id = :idCuenta and m.cuentaByCuentaDestinoId.id = :idCuenta")
    List<MovimientosEntity> encontrarMovimientosASiMismo(@Param("idCuenta") Integer idCuenta);
}
