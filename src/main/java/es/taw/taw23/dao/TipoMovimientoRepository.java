package es.taw.taw23.dao;

import es.taw.taw23.entity.TipoMovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TipoMovimientoRepository extends JpaRepository<TipoMovimientoEntity, Integer> {

    @Query("select t from TipoMovimientoEntity t where t.tipo = 'pago'")
    public TipoMovimientoEntity buscarTipoTransferencia();
}
