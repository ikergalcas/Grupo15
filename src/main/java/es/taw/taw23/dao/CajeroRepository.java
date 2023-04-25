package es.taw.taw23.dao;

import es.taw.taw23.entity.CuentaEntity;
import es.taw.taw23.entity.DivisaEntity;
import es.taw.taw23.entity.TipoMovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CajeroRepository extends JpaRepository<CuentaEntity,Integer> {
    @Query("Select x FROM CuentaEntity x WHERE x.numeroCuenta = :numeroCuenta")
    CuentaEntity findByAccountNumber(@Param("numeroCuenta") String numeroCuenta);

    @Query("Select x FROM TipoMovimientoEntity x WHERE x.tipo = :tipo")
    TipoMovimientoEntity findByMovementName(@Param("tipo") String tipo);

    @Query("SELECT X FROM CuentaEntity x WHERE x.id != :id")
    List<CuentaEntity> findAllButNotThis(@Param("id") Integer id);

    @Query("Select x From DivisaEntity x WHERE x.moneda = :moneda")
    DivisaEntity findByMoneyName(@Param("moneda") String moneda);
}
