package es.taw.taw23.dao;

import es.taw.taw23.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
/**
 * Hecho por:
 * Álvaro Yuste Moreno 40%
 * Rocío Gómez Mancebo 30%
 * Carla Serracant Guevara 30%
 */
public interface MovimientoRepository extends JpaRepository<MovimientoEntity, Integer> {
    @Query("select m from MovimientoEntity m where m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta")
    public List<MovimientoEntity> buscarMovimientosPorIdCuenta(@Param("idCuenta") Integer idCuenta);

    //Filtro movimientos parte de empresa
    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta")
    public List<MovimientoEntity> buscarMovimientosAsociado(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorTipo(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.cuentaByCuentaDestinoId.numeroCuenta = :cuenta")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorCuentaDestino(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("cuenta") String cuentaDestino);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.divisaByMonedaDestinoId.moneda = :divisaDestino")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorDivisaDestino(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("divisaDestino") String divisaDestino);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorDivisaOrigen(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.cuentaByCuentaDestinoId.numeroCuenta = :cuenta")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorTipoYCuentaDestino(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("cuenta") String cuentaDestino);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.divisaByMonedaDestinoId.moneda = :divisaDestino")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorTipoYDivisaDestino(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("divisaDestino") String divisaDestino);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorTipoYDivisaOrigen(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaDestinoId.moneda = :divisaDestino")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorCuentaDestinoYDivisaDestino(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("cuentaDestino") String cuentaDestino, @Param("divisaDestino") String divisaDestino);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorCuentaDestinoYDivisaOrigen(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("cuentaDestino") String cuentaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.divisaByMonedaDestinoId.moneda = :divisaDestino and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorDivisaDestinoYDivisaOrigen(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("divisaDestino") String divisaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaDestinoId.moneda = :divisaDestino")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorTipoCuentaDestinoDivisaDestino(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("cuentaDestino") String cuentaDestino, @Param("divisaDestino") String divisaDestino);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.divisaByMonedaOrigenId.moneda = :divisaOrigen and m.divisaByMonedaDestinoId.moneda = :divisaDestino")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorTipoDivisaDestinoDivisaOrigen(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("divisaDestino") String divisaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorTipoCuentaDestinoDivisaOrigen(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("cuentaDestino") String cuentaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaDestinoId.moneda = :divisaDestino and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorCuentaDestinoDivisaDestinoDivisaOrigen(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("cuentaDestino") String cuentaDestino, @Param("divisaDestino") String divisaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where m.clienteByClienteId.id = :idAsociado and m.cuentaByCuentaOrigenId.id = :idCuenta and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaDestinoId.moneda = :divisaDestino and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    public List<MovimientoEntity> buscarMovimientosAsociadoPorTipoCuentaDestinoDivisaDestinoDivisaOrigen(@Param("idAsociado") Integer id, @Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("cuentaDestino") String cuentaDestino, @Param("divisaDestino") String divisaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeOrigen, m.importeDestino asc")
    public List<MovimientoEntity> ordenarPorImporteOrigenYDestinoAscendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeOrigen, m.importeDestino desc")
    public List<MovimientoEntity> ordenarPorImporteOrigenYDestinoDescendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeOrigen asc")
    public List<MovimientoEntity> ordenarPorImporteOrigenAscendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeOrigen desc")
    public List<MovimientoEntity> ordenarPorImporteOrigenDescendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeDestino asc")
    public List<MovimientoEntity> ordenarPorImporteDestinoAscendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeDestino desc")
    public List<MovimientoEntity> ordenarPorImporteDestinoDescendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.timeStamp desc")
    public List<MovimientoEntity> ordenarPorFechaDescendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeDestino, m.timeStamp desc")
    public List<MovimientoEntity> ordenarPorImporteDestinoYFechaDescendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeOrigen, m.timeStamp desc")
    public List<MovimientoEntity> ordenarPorImporteOrigenYFechaDescendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeOrigen, m.importeDestino, m.timeStamp desc")
    public List<MovimientoEntity> ordenarPorImporteOrigenDestinoYFechaDescendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.timeStamp asc")
    public List<MovimientoEntity> ordenarPorFechaAscendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeDestino, m.timeStamp asc")
    public List<MovimientoEntity> ordenarPorImporteDestinoYFechaAscendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeOrigen, m.timeStamp asc")
    public List<MovimientoEntity> ordenarPorImporteOrigenYFechaAscendente(@Param("lista") List<MovimientoEntity> entities);

    @Query("select m from MovimientoEntity m where m in :lista order by m.importeDestino, m.importeOrigen, m.timeStamp asc")
    public List<MovimientoEntity> ordenarPorImporteOrigenDestinoYFechaAscendente(@Param("lista") List<MovimientoEntity> entities);

    //filtrado de cliente individual

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo")
    List<MovimientoEntity> buscarMovimientosClientePorTipo(@Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.cuentaByCuentaDestinoId.numeroCuenta = :cuenta")
    List<MovimientoEntity> buscarMovimientosClientePorCuentaDestino(@Param("idCuenta") Integer idCuenta, @Param("cuenta") String cuentaDestino);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.divisaByMonedaDestinoId.moneda = :divisaDestino")
    List<MovimientoEntity> buscarMovimientosClientePorDivisaDestino(@Param("idCuenta") Integer idCuenta, @Param("divisaDestino") String divisaDestino);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    List<MovimientoEntity> buscarMovimientosClientePorDivisaOrigen(@Param("idCuenta") Integer idCuenta, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.cuentaByCuentaDestinoId.numeroCuenta = :cuenta")
    List<MovimientoEntity> buscarMovimientosClientePorTipoYCuentaDestino(@Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("cuenta") String cuentaDestino);

    @Query("select m from MovimientoEntity m where(m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.divisaByMonedaDestinoId.moneda = :divisaDestino")
    List<MovimientoEntity> buscarMovimientosClientePorTipoYDivisaDestino(@Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("divisaDestino") String divisaDestino);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    List<MovimientoEntity> buscarMovimientosClientePorTipoYDivisaOrigen(@Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaDestinoId.moneda = :divisaDestino")
    List<MovimientoEntity> buscarMovimientosClientePorCuentaDestinoYDivisaDestino(@Param("idCuenta") Integer idCuenta, @Param("cuentaDestino") String cuentaDestino, @Param("divisaDestino") String divisaDestino);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    List<MovimientoEntity> buscarMovimientosClientePorCuentaDestinoYDivisaOrigen(@Param("idCuenta") Integer idCuenta, @Param("cuentaDestino") String cuentaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.divisaByMonedaDestinoId.moneda = :divisaDestino and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    List<MovimientoEntity> buscarMovimientosClientePorDivisaDestinoYDivisaOrigen(@Param("idCuenta") Integer idCuenta, @Param("divisaDestino") String divisaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaDestinoId.moneda = :divisaDestino")
    List<MovimientoEntity> buscarMovimientosClientePorTipoCuentaDestinoDivisaDestino(@Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("cuentaDestino") String cuentaDestino, @Param("divisaDestino") String divisaDestino);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.divisaByMonedaOrigenId.moneda = :divisaOrigen and m.divisaByMonedaDestinoId.moneda = :divisaDestino")
    List<MovimientoEntity> buscarMovimientosClientePorTipoDivisaDestinoDivisaOrigen(@Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("divisaDestino") String divisaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    List<MovimientoEntity> buscarMovimientosClientePorTipoCuentaDestinoDivisaOrigen( @Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("cuentaDestino") String cuentaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaDestinoId.moneda = :divisaDestino and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    List<MovimientoEntity> buscarMovimientosClientePorCuentaDestinoDivisaDestinoDivisaOrigen( @Param("idCuenta") Integer idCuenta, @Param("cuentaDestino") String cuentaDestino, @Param("divisaDestino") String divisaDestino, @Param("divisaOrigen") String divisaOrigen);

    @Query("select m from MovimientoEntity m where (m.cuentaByCuentaDestinoId.id = :idCuenta or m.cuentaByCuentaOrigenId.id = :idCuenta) and m.tipoMovimientoByTipoMovimientoId.tipo = :tipo and m.cuentaByCuentaDestinoId.numeroCuenta = :cuentaDestino and m.divisaByMonedaDestinoId.moneda = :divisaDestino and m.divisaByMonedaOrigenId.moneda = :divisaOrigen")
    List<MovimientoEntity> buscarMovimientosClientePorTipoCuentaDestinoDivisaDestinoDivisaOrigen( @Param("idCuenta") Integer idCuenta, @Param("tipo") String tipo, @Param("cuentaDestino") String cuentaDestino, @Param("divisaDestino") String divisaDestino, @Param("divisaOrigen") String divisaOrigen);


    /* Carla Serracant Guevara */
    @Query("select m from MovimientoEntity m where m.cuentaByCuentaOrigenId.id = :idCuenta and m.cuentaByCuentaDestinoId.id = :idCuenta")
    List<MovimientoEntity> encontrarMovimientosASiMismo(@Param("idCuenta") Integer idCuenta);

    /* Carla Serracant Guevara */
    @Query("select m from MovimientoEntity m where m.cuentaByCuentaOrigenId.id = :idCuenta or m.cuentaByCuentaDestinoId.id = :idCuenta")
    List<MovimientoEntity> findMovimientosByIdCuenta(@Param("idCuenta") Integer idCuenta);

    /* Carla Serracant Guevara */
    @Query("select m from MovimientoEntity m where m.tipoMovimientoByTipoMovimientoId.tipo = 'pago' and (m.cuentaByCuentaOrigenId.id = :idCuentaSospechosa or m.cuentaByCuentaDestinoId.id = :idCuentaSospechosa)")
    List<MovimientoEntity> findMovimientosSospechosos(@Param("idCuentaSospechosa") Integer idCuentaSospechosa);

    /* Carla Serracant Guevara */
    @Query("select m from MovimientoEntity m where m.tipoMovimientoByTipoMovimientoId.tipo = :tipoMovimiento and (m.cuentaByCuentaOrigenId.id = :idCuenta or m.cuentaByCuentaDestinoId.id = :idCuenta)")
    List<MovimientoEntity> findAllByTipoMovimiento(@Param("tipoMovimiento") String tipoMovimiento, @Param("idCuenta") Integer idCuenta);

    /* Carla Serracant Guevara */
    @Query ("select m from MovimientoEntity m where m.timeStamp > :fecha and (m.cuentaByCuentaOrigenId.id = :idCuenta or m.cuentaByCuentaDestinoId.id = :idCuenta)")
    List<MovimientoEntity> findMovimientosMasRecientesQueFecha(@Param("fecha") Timestamp fecha, @Param("idCuenta") Integer idCuenta);

    default List<MovimientoEntity> findMasRecientesQueTreintaDias(Integer idCuenta) {
        long treintaDiasEnMillis = 30L * 24L * 60L * 60L * 1000L;
        Timestamp fechaAnterior = new Timestamp(System.currentTimeMillis() - treintaDiasEnMillis);
        return findMovimientosMasRecientesQueFecha(fechaAnterior,idCuenta);
    }

    /* Carla Serracant Guevara */
    @Query("select m from MovimientoEntity m where m.timeStamp > :fecha and (m.cuentaByCuentaOrigenId.id = :idCuenta or m.cuentaByCuentaDestinoId.id = :idCuenta) and m.tipoMovimientoByTipoMovimientoId.tipo = :tipoMovimiento")
    List<MovimientoEntity> findMovimientosMasRecientesYConTipo(@Param("fecha") Timestamp fecha, @Param("idCuenta") Integer idCuenta, @Param("tipoMovimiento") String tipoMovimiento);

    default List<MovimientoEntity> findMasRecientesQueTreintaDiasConTipoFiltrado(Integer idCuenta, String tipo) {
        long treintaDiasEnMillis = 30L * 24L * 60L * 60L * 1000L;
        Timestamp fechaAnterior = new Timestamp(System.currentTimeMillis() - treintaDiasEnMillis);
        return findMovimientosMasRecientesYConTipo(fechaAnterior,idCuenta,tipo);
    }
}
