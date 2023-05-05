package es.taw.taw23.dao;

import es.taw.taw23.dto.Cuenta;
import es.taw.taw23.dto.Movimiento;
import es.taw.taw23.entity.CuentaEntity;
import es.taw.taw23.entity.MovimientosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<MovimientosEntity, Integer> {

    /* Carla Serracant Guevara */
    @Query("select m from MovimientosEntity m where m.cuentaByCuentaOrigenId.id = :idCuenta and m.cuentaByCuentaDestinoId.id = :idCuenta")
    List<MovimientosEntity> encontrarMovimientosASiMismo(@Param("idCuenta") Integer idCuenta);

    /* Carla Serracant Guevara */
    @Query("select m from MovimientosEntity m where m.cuentaByCuentaOrigenId.id = :idCuenta or m.cuentaByCuentaDestinoId.id = :idCuenta")
    List<MovimientosEntity> findMovimientosByIdCuenta(@Param("idCuenta") Integer idCuenta);

    /* Carla Serracant Guevara */
    @Query("select m from MovimientosEntity m where m.tipoMovimientoByTipoMovimientoId.tipo = 'pago' and (m.cuentaByCuentaOrigenId.id = :idCuentaSospechosa or m.cuentaByCuentaDestinoId.id = :idCuentaSospechosa)")
    List<MovimientosEntity> findMovimientosSospechosos(@Param("idCuentaSospechosa") Integer idCuentaSospechosa);

    /* Carla Serracant Guevara */
    @Query("select m from MovimientosEntity m where m.tipoMovimientoByTipoMovimientoId.tipo = :tipoMovimiento and (m.cuentaByCuentaOrigenId.id = :idCuenta or m.cuentaByCuentaDestinoId.id = :idCuenta)")
    List<MovimientosEntity> findAllByTipoMovimiento(@Param("tipoMovimiento") String tipoMovimiento, @Param("idCuenta") Integer idCuenta);

    /* Carla Serracant Guevara */
    @Query ("select m from MovimientosEntity m where m.timeStamp > :fecha and (m.cuentaByCuentaOrigenId.id = :idCuenta or m.cuentaByCuentaDestinoId.id = :idCuenta)")
    List<MovimientosEntity> findMovimientosMasRecientesQueFecha(@Param("fecha") Timestamp fecha, @Param("idCuenta") Integer idCuenta);

    default List<MovimientosEntity> findMasRecientesQueTreintaDias(Integer idCuenta) {
        long treintaDiasEnMillis = 30L * 24L * 60L * 60L * 1000L;
        Timestamp fechaAnterior = new Timestamp(System.currentTimeMillis() - treintaDiasEnMillis);
        return findMovimientosMasRecientesQueFecha(fechaAnterior,idCuenta);
    }

    /* Carla Serracant Guevara */
    @Query("select m from MovimientosEntity m where m.timeStamp > :fecha and (m.cuentaByCuentaOrigenId.id = :idCuenta or m.cuentaByCuentaDestinoId.id = :idCuenta) and m.tipoMovimientoByTipoMovimientoId.tipo = :tipoMovimiento")
    List<MovimientosEntity> findMovimientosMasRecientesYConTipo(@Param("fecha") Timestamp fecha, @Param("idCuenta") Integer idCuenta, @Param("tipoMovimiento") String tipoMovimiento);

    default List<MovimientosEntity> findMasRecientesQueTreintaDiasConTipoFiltrado(Integer idCuenta, String tipo) {
        long treintaDiasEnMillis = 30L * 24L * 60L * 60L * 1000L;
        Timestamp fechaAnterior = new Timestamp(System.currentTimeMillis() - treintaDiasEnMillis);
        return findMovimientosMasRecientesYConTipo(fechaAnterior,idCuenta,tipo);
    }

}
