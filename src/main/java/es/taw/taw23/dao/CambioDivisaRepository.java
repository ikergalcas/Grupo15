package es.taw.taw23.dao;

import es.taw.taw23.entity.CambioDivisaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * Hecho por: Álvaro Yuste Moreno
 */
public interface CambioDivisaRepository extends JpaRepository<CambioDivisaEntity, Integer> {

    @Query("select c from CambioDivisaEntity c where c.divisaByOrigenId.moneda = :origen and c.divisaByDestinoId.moneda = :destino")
    public CambioDivisaEntity buscarCambioDivisa(@Param("origen")String divisaOrigen, @Param("destino") String divisaDestino);
}
