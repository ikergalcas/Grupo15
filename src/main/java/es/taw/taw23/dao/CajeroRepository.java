//Pablo Alarcón Carrión
package es.taw.taw23.dao;

import es.taw.taw23.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CajeroRepository extends JpaRepository<CuentaEntity,Integer> {
    @Query("Select x FROM CuentaEntity x WHERE x.numeroCuenta = :numeroCuenta")
    CuentaEntity findByAccountNumber(@Param("numeroCuenta") String numeroCuenta);

    @Query("Select x FROM TipoMovimientoEntity x WHERE x.tipo = :tipo")
    TipoMovimientoEntity findByMovementName(@Param("tipo") String tipo);

    @Query("Select x From DivisaEntity x WHERE x.moneda = :moneda")
    DivisaEntity findByMoneyName(@Param("moneda") String moneda);

    @Query("Select x From CambioDivisaEntity x Where x.divisaByOrigenId.id = :origen AND x.divisaByDestinoId.id = :destino")
    CambioDivisaEntity cambiarDivisa(@Param("origen") Integer origen, @Param("destino") Integer destino);

    @Query("Select distinct x from MovimientoEntity x where (x.cuentaByCuentaOrigenId.id = :idCuenta " +
            "OR x.cuentaByCuentaDestinoId.id = :idCuenta)")
    List<MovimientoEntity> findAllMovimientos(@Param("idCuenta") Integer idCuenta);

    @Query ("Select distinct x From MovimientoEntity x where (x.cuentaByCuentaOrigenId.id = :idCuenta " +
            "OR x.cuentaByCuentaDestinoId.id = :idCuenta) ORDER BY x.timeStamp ASC")
    List<MovimientoEntity> findByFechaMovimientoAsc(@Param("idCuenta") Integer idCuenta);

    @Query("Select x from MovimientoEntity x WHERE (x.cuentaByCuentaOrigenId.id = :idCuenta OR x.cuentaByCuentaDestinoId.id = :idCuenta) ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> findByTipoDeMovimientoAsc(@Param("idCuenta") Integer idCuenta);

    @Query("Select x from SolicitudEntity x where x.clienteByClienteId.id = :idCliente")
    SolicitudEntity buscarSolicitudPorIdCliente(@Param ("idCliente") Integer idCliente);


}
