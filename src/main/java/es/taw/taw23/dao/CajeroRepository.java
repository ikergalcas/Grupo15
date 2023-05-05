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

    @Query("Select x from SolicitudEntity x where x.clienteByClienteId.id = :idCliente")
    SolicitudEntity buscarSolicitudPorIdCliente(@Param ("idCliente") Integer idCliente);

    @Query("select e from EmpleadoEntity e where e.rolEmpleadoByRolEmpleadoId.tipo = 'gestor'")
    public List<EmpleadoEntity> buscarGestores();

    //Aqui empieza las solicitudes de los filtros. Hay 63 solicitudes

    //Divisa, numero y movimiento -> Completa
    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento%")
    List<MovimientoEntity> filtrarDivisaNumeroMovimiento(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                         @Param("numero") String numero, @Param("movimiento") String movimiento);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.importeOrigen ASC")
    List<MovimientoEntity> filtrarDivisaNumeroMovimientoOrdenarImporte(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                         @Param("numero") String numero, @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarDivisaNumeroMovimientoOrdenarTipo(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                       @Param("numero") String numero, @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC ")
    List<MovimientoEntity> filtrarDivisaNumeroMovimientoOrdenarTipoImporte(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                    @Param("numero") String numero, @Param("movimiento") String movimiento);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.timeStamp ASC ")
    List<MovimientoEntity> filtrarDivisaNumeroMovimientoOrdenarFecha(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                           @Param("numero") String numero, @Param("movimiento") String movimiento);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.timeStamp ASC, x.importeOrigen ASC ")
    List<MovimientoEntity> filtrarDivisaNumeroMovimientoOrdenarFechaImporte(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                     @Param("numero") String numero, @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC ")
    List<MovimientoEntity> filtrarDivisaNumeroMovimientoOrdenarFechaTipo(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                            @Param("numero") String numero, @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC ")
    List<MovimientoEntity> filtrarDivisaNumeroMovimientoOrdenarAll(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                         @Param("numero") String numero, @Param("movimiento") String movimiento);


    //Divisa y numero -> Completa
    @Query("Select distinct x from MovimientoEntity x where " +
            " (x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%)")
    List<MovimientoEntity> filtrarDivisaNumero(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                         @Param("numero") String numero);

    @Query("Select distinct x from MovimientoEntity x where " +
            " (x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) ORDER BY x.timeStamp ASC")
    List<MovimientoEntity> filtrarDivisaNumeroOrdenarPorTimeStampAsc(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                     @Param("numero") String numero);

    @Query("Select  x from MovimientoEntity x where " +
            " (x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarDivisaNumeroOrdenarPorTipoMovimientoAsc(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                          @Param("numero") String numero);

    @Query("Select distinct x from MovimientoEntity x where " +
            " (x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) ORDER BY x.importeOrigen ASC")
    List<MovimientoEntity> filtrarDivisaNumeroOrdenarPorImporteOrigenAsc(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                         @Param("numero") String numero);

    @Query("Select  x from MovimientoEntity x where " +
            " (x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) ORDER BY x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarDivisaNumeroOrdenarTimeStampTipo(@Param("cuentaId") Integer cuentaId,
                                                                   @Param("divisa") String divisa, @Param("numero") String numero);

    @Query("Select distinct x from MovimientoEntity x where " +
            " (x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) ORDER BY x.timeStamp ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarDivisaNumeroOrdenarTimeStampImporteOrigen(@Param("cuentaId") Integer cuentaId,
                                                                            @Param("divisa") String divisa, @Param("numero") String numero);

    @Query("Select  x from MovimientoEntity x where " +
            " (x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarDivisaNumeroOrdenarTipoImporteOrigen(@Param("cuentaId") Integer cuentaId,
                                                                       @Param("divisa") String divisa, @Param("numero") String numero);

    @Query("Select  x from MovimientoEntity x where " +
            " (x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) " +
            "ORDER BY x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarDivisaNumeroOrdenarAll(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                      @Param("numero") String numero);


    //Divisa y movimiento -> Completo
    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and" +
            " (x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento%")
    List<MovimientoEntity> filtrarDivisaMovimiento(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                               @Param("movimiento") String movimiento);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and" +
            " (x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.timeStamp ASC")
    List<MovimientoEntity> filtrarDivisaMovimientoOrdenarPorTimeStamp(@Param("cuentaId") Integer cuentaId,
                                                                      @Param("divisa") String divisa,
                                                                      @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and" +
            " (x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarDivisaMovimientoOrdenarPorTipoMovimiento(@Param("cuentaId") Integer cuentaId,
                                                                           @Param("divisa") String divisa,
                                                                           @Param("movimiento") String movimiento);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and" +
            " (x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.importeOrigen ASC")
    List<MovimientoEntity> filtrarDivisaMovimientoOrdenarImporte(@Param("cuentaId") Integer cuentaId,
                                                                           @Param("divisa") String divisa,
                                                                           @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and" +
            " (x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento%" +
            " ORDER BY x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarDivisaMovimientoOrdenarTimeStampTipoMovimiento(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                                 @Param("movimiento") String movimiento);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and" +
            " (x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento%" +
            " ORDER BY x.timeStamp ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarDivisaMovimientoOrdenarTimeStampImporteOrigen(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                                 @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and" +
            " (x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento%" +
            " ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarDivisaMovimientoOrdenarTipoImporte(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                                @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and" +
            " (x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento%" +
            " ORDER BY x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarDivisaMovimientoOrdenarAll(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa,
                                                                                @Param("movimiento") String movimiento);


    //Divisa -> Completo
    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%)")
    List<MovimientoEntity> filtrarDivisa(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) order by x.timeStamp asc")
    List<MovimientoEntity> filtrarDivisaOrdenarPorTimeStamp(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) order by x.tipoMovimientoByTipoMovimientoId.tipo asc")
    List<MovimientoEntity> filtrarDivisaOrdenarPorTipoMovimiento(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) order by x.importeOrigen asc")
    List<MovimientoEntity> filtrarDivisaOrdenarPorImporteOrigen(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) " +
            "order by x.timeStamp asc, x.tipoMovimientoByTipoMovimientoId.tipo asc")
    List<MovimientoEntity> filtrarDivisaOrdenarPorTimeStampTipoMovimiento(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) " +
            "order by x.timeStamp asc, x.importeOrigen asc")
    List<MovimientoEntity> filtrarDivisaOrdenarPorTimeStampImporteOrigen(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) " +
            "order by x.tipoMovimientoByTipoMovimientoId.tipo asc, x.importeOrigen asc")
    List<MovimientoEntity> filtrarDivisaOrdenarPorTipoMovimientoImporteOrigen(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.divisaByMonedaOrigenId.moneda LIKE %:divisa% " +
            "OR x.divisaByMonedaDestinoId.moneda LIKE %:divisa%) " +
            "order by x.timeStamp asc,x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen asc")
    List<MovimientoEntity> filtrarDivisaOrdenarAll(@Param("cuentaId") Integer cuentaId, @Param("divisa") String divisa);

    //Numero y movimiento -> Completo
    @Query("Select distinct x from MovimientoEntity x where  " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.cuentaByCuentaOrigenId.id = :cuentaId and (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento%")
    List<MovimientoEntity> filtrarNumeroMovimiento(@Param("cuentaId") Integer cuentaId, @Param("numero") String numero,
                                                   @Param("movimiento") String movimiento);

    @Query("Select distinct x from MovimientoEntity x where  " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.cuentaByCuentaOrigenId.id = :cuentaId and (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.timeStamp ASC")
    List<MovimientoEntity> filtrarNumeroMovimientoOrdenarPorTimeStamp(@Param("cuentaId") Integer cuentaId, @Param("numero") String numero,
                                                                      @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where  " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.cuentaByCuentaOrigenId.id = :cuentaId and (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarNumeroMovimientoOrdenarPorTipoMovimiento(@Param("cuentaId") Integer cuentaId, @Param("numero") String numero,
                                                                           @Param("movimiento") String movimiento);

    @Query("Select distinct x from MovimientoEntity x where  " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.cuentaByCuentaOrigenId.id = :cuentaId and (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.importeOrigen ASC")
    List<MovimientoEntity> filtrarNumeroMovimientoOrdenarPorImporteOrigen(@Param("cuentaId") Integer cuentaId, @Param("numero") String numero,
                                                                          @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where  " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.cuentaByCuentaOrigenId.id = :cuentaId and (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarNumeroMovimientoOrdenarPorFechaTipo(@Param("cuentaId") Integer cuentaId, @Param("numero") String numero,
                                                                      @Param("movimiento") String movimiento);

    @Query("Select distinct x from MovimientoEntity x where  " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.cuentaByCuentaOrigenId.id = :cuentaId and (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.timeStamp ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarNumeroMovimientoOrdenarPorFechaImporte(@Param("cuentaId") Integer cuentaId, @Param("numero") String numero,
                                                                         @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where  " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.cuentaByCuentaOrigenId.id = :cuentaId and (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarNumeroMovimientoOrdenarPorTipoImporte(@Param("cuentaId") Integer cuentaId, @Param("numero") String numero,
                                                                      @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where  " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.cuentaByCuentaOrigenId.id = :cuentaId and (x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) AND x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.timeStamp ASC,x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarNumeroMovimientoOrdenarAll(@Param("cuentaId") Integer cuentaId, @Param("numero") String numero,
                                                                        @Param("movimiento") String movimiento);
    //Numero -> Completo
    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%)")
    List<MovimientoEntity> filtrarNumero(@Param("cuentaId") Integer cuentaId, @Param("numero") String numero);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) " +
            "ORDER BY x.timeStamp ASC")
    List<MovimientoEntity> filtrarNumeroOrdenarPorTimeStamp(@Param("cuentaId") Integer cuentaId,
                                                            @Param("numero") String numero);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) " +
            "ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarNumeroOrdenarPorTipoMovimiento(@Param("cuentaId") Integer cuentaId,
                                                                 @Param("numero") String numero);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) " +
            "ORDER BY x.importeOrigen ASC")
    List<MovimientoEntity> filtrarNumeroOrdenarPorImporteOrigen(@Param("cuentaId") Integer cuentaId,
                                                                @Param("numero") String numero);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) " +
            "ORDER BY x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarNumeroOrdenarPorTimeStampYTipoMovimiento(@Param("cuentaId") Integer cuentaId,
                                                                           @Param("numero") String numero);


    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) " +
            "order by x.tipoMovimientoByTipoMovimientoId.tipo asc, x.importeOrigen asc")
    List<MovimientoEntity> filtrarNumeroOrdenarPorTipoMovimientoEImporteOrigen(@Param("cuentaId") Integer cuentaId,
                                                                               @Param("numero") String numero);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) " +
            "order by x.timeStamp asc, x.importeOrigen asc")
    List<MovimientoEntity> filtrarNumeroOrdenarPorFechaEImporteOrigen(@Param("cuentaId") Integer cuentaId,
                                                                               @Param("numero") String numero);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "(x.cuentaByCuentaOrigenId.numeroCuenta LIKE %:numero% OR " +
            "x.cuentaByCuentaDestinoId.numeroCuenta LIKE %:numero%) " +
            "order by x.timeStamp asc,x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen asc")
    List<MovimientoEntity> filtrarNumeroOrdenarAll(@Param("cuentaId") Integer cuentaId,
                                                                      @Param("numero") String numero);


    //Movimiento -> Completo
    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento%")
    List<MovimientoEntity> filtrarMovimiento(@Param("cuentaId") Integer cuentaId , @Param("movimiento") String movimiento);

    @Query("SELECT DISTINCT x FROM MovimientoEntity x WHERE " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) AND " +
            "x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.timeStamp ASC")
    List<MovimientoEntity> filtrarMovimientoOrdenarTimeStamp(@Param("cuentaId") Integer cuentaId, @Param("movimiento") String movimiento);

    @Query("SELECT  x FROM MovimientoEntity x WHERE " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) AND " +
            "x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarMovimientoOrdenarTipoMovimiento(@Param("cuentaId") Integer cuentaId, @Param("movimiento") String movimiento);

    @Query("SELECT DISTINCT x FROM MovimientoEntity x WHERE " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) AND " +
            "x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "ORDER BY x.importeOrigen ASC")
    List<MovimientoEntity> filtrarMovimientoOrdenarImporteOrigen(@Param("cuentaId") Integer cuentaId, @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "order by x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> filtrarMovimientoOrdenarFechaTipo(@Param("cuentaId") Integer cuentaId, @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "order by x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarMovimientoOrdenarTipoImporteOrigen(@Param("cuentaId") Integer cuentaId, @Param("movimiento") String movimiento);

    @Query("Select distinct x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "order by x.timeStamp ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarMovimientoOrdenarFechaImporteOrigen(@Param("cuentaId") Integer cuentaId, @Param("movimiento") String movimiento);

    @Query("Select  x from MovimientoEntity x where " +
            "(x.cuentaByCuentaOrigenId.id = :cuentaId OR x.cuentaByCuentaDestinoId.id = :cuentaId) and " +
            "x.tipoMovimientoByTipoMovimientoId.tipo LIKE %:movimiento% " +
            "order by x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC")
    List<MovimientoEntity> filtrarMovimientoOrdenarAll(@Param("cuentaId") Integer cuentaId, @Param("movimiento") String movimiento);

    //Sin filtrar
    @Query("Select distinct x from MovimientoEntity x where (x.cuentaByCuentaOrigenId.id = :idCuenta " +
            "OR x.cuentaByCuentaDestinoId.id = :idCuenta) " +
            "ORDER BY x.importeOrigen ASC")
    List<MovimientoEntity> OrdenarImporte(@Param("idCuenta") Integer idCuenta);

    @Query("Select  x from MovimientoEntity x where (x.cuentaByCuentaOrigenId.id = :idCuenta " +
            "OR x.cuentaByCuentaDestinoId.id = :idCuenta) " +
            "ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> OrdenarTipo(@Param("idCuenta") Integer idCuenta);

    @Query("Select distinct x from MovimientoEntity x where (x.cuentaByCuentaOrigenId.id = :idCuenta " +
            "OR x.cuentaByCuentaDestinoId.id = :idCuenta) " +
            "ORDER BY x.timeStamp ASC")
    List<MovimientoEntity> OrdenarFecha(@Param("idCuenta") Integer idCuenta);

    @Query("Select  x from MovimientoEntity x where (x.cuentaByCuentaOrigenId.id = :idCuenta " +
            "OR x.cuentaByCuentaDestinoId.id = :idCuenta) " +
            "ORDER BY x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC")
    List<MovimientoEntity> OrdenarFechaTipo(@Param("idCuenta") Integer idCuenta);

    @Query("Select  x from MovimientoEntity x where (x.cuentaByCuentaOrigenId.id = :idCuenta " +
            "OR x.cuentaByCuentaDestinoId.id = :idCuenta) " +
            "ORDER BY x.tipoMovimientoByTipoMovimientoId.tipo ASC, x.importeOrigen ASC")
    List<MovimientoEntity> OrdenarTipoImporte(@Param("idCuenta") Integer idCuenta);

    @Query("Select distinct x from MovimientoEntity x where (x.cuentaByCuentaOrigenId.id = :idCuenta " +
            "OR x.cuentaByCuentaDestinoId.id = :idCuenta) " +
            "ORDER BY x.timeStamp ASC,x.importeOrigen ASC")
    List<MovimientoEntity> OrdenarFechaImporte(@Param("idCuenta") Integer idCuenta);

    @Query("Select  x from MovimientoEntity x where (x.cuentaByCuentaOrigenId.id = :idCuenta " +
            "OR x.cuentaByCuentaDestinoId.id = :idCuenta) " +
            "ORDER BY x.timeStamp ASC, x.tipoMovimientoByTipoMovimientoId.tipo ASC,x.importeOrigen ASC")
    List<MovimientoEntity> OrdenarAll(@Param("idCuenta") Integer idCuenta);





}
