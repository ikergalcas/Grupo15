package es.taw.taw23.dao;

import es.taw.taw23.entity.RemitenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RemitenteRepository extends JpaRepository<RemitenteEntity,Integer> {
    @Query("select r from RemitenteEntity r where r.remitente = :remitente")
    public RemitenteEntity findByRemitente(@Param("remitente") String remitente);
}
