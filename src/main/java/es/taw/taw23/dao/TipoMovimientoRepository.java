package es.taw.taw23.dao;

import es.taw.taw23.entity.TipoMovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Hecho por: Álvaro Yuste Moreno 30%
 * Carla Serracant Guevara 70%
 */
public interface TipoMovimientoRepository extends JpaRepository<TipoMovimientoEntity, Integer> {
    @Query("select t from TipoMovimientoEntity t where t.tipo = 'pago'")
    public TipoMovimientoEntity buscarTipoTransferencia();

    @Query("select t from TipoMovimientoEntity t where t.tipo = 'cambioDivisa'")
    public TipoMovimientoEntity buscarTipoCambioDivisa();

    @Query("select t.tipo from TipoMovimientoEntity t")
    List<String> findAllTiposMovimientos();
}
